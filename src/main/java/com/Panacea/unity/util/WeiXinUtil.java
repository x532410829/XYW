package com.Panacea.unity.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.jfinal.kit.HttpKit;

/**
 * 微信的参数设置工具
 * @author 夜未
 * @since 2020年9月28日
 */
public class WeiXinUtil {

	/**
	 * 获取支付所需签名
	 * @param bizObj
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getPayCustomSign(Map<String, String> bizObj, String key) throws Exception {
		String bizString = FormatBizQueryParaMap(bizObj, false);
		return sign(bizString, key);
	}

	/**
	 * 字典排序
	 * 
	 * @param paraMap
	 * @param urlencode
	 * @return
	 * @throws Exception
	 */
	public static String FormatBizQueryParaMap(Map<String, String> paraMap, boolean urlencode) throws Exception {
		String buff = "";
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(paraMap.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			for (int i = 0; i < infoIds.size(); i++) {
				Map.Entry<String, String> item = infoIds.get(i);
				// System.out.println(item.getKey());
				if (item.getKey() != "") {
					String key = item.getKey();
					String val = item.getValue();
					if (urlencode) {
						// 将val解码
						val = URLEncoder.encode(val, "utf-8");
					}
					buff += key + "=" + val + "&";
				}
			}
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return buff;
	}

	// 支付所需签名处调用此方法
	public static String sign(String content, String key) throws Exception {
		String signStr = "";
		signStr = content + "&key=" + key;
		return MD5(signStr).toUpperCase();
	}

	// 上一方法，MD5加密处理
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 转为XML格式
	public static String ArrayToXml(Map<String, String> arr) {
		String xml = "<xml>";
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";
			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}
		xml += "</xml>";
		return xml;
	}

	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}

	// 解析XML
	public static Map<String, String> doXMLParse(String xml) throws XmlPullParserException, IOException {

		InputStream inputStream = new ByteArrayInputStream(xml.getBytes());

		Map<String, String> map = null;

		XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
		// 新建XmlPullParser的实例
		XmlPullParser pullParser = pullParserFactory.newPullParser();

		/*
		 * XmlPullParser pullParser = XmlPullParserFactory.newInstance()
		 * .newPullParser();
		 */

		pullParser.setInput(inputStream, "UTF-8");// 为xml设置要解析的xml数据

		int eventType = pullParser.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				map = new HashMap<String, String>();
				break;

			case XmlPullParser.START_TAG:
				String key = pullParser.getName();
				if (key.equals("xml"))
					break;
				String value = pullParser.nextText();
				map.put(key, value);
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			eventType = pullParser.next();
		}
		return map;
	}

	/**
	 * 
	 * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
	 * 实现步骤: <br>
	 * 
	 * @param paraMap
	 *            要排序的Map对象
	 * @param urlEncode
	 *            是否需要URLENCODE
	 * @param keyToLower
	 *            是否需要将Key转换为全小写 true:key转化成小写，false:不转化
	 * @return
	 */
	public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
		String buff = "";
		Map<String, String> tmpMap = paraMap;
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				@Override
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			// 构造URL 键值对的格式
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, String> item : infoIds) {
				if (!StringUtils.isEmpty(item.getKey())) {
					String key = item.getKey();
					String val = item.getValue();
					if (urlEncode) {
						val = URLEncoder.encode(val, "utf-8");
					}
					if (keyToLower) {
						buf.append(key.toLowerCase() + "=" + val);
					} else {
						buf.append(key + "=" + val);
					}
					buf.append("&");
				}

			}
			buff = buf.toString();
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			return null;
		}
		return buff;
	}

	// 通知微信正确接收
	public static void noticeWeChatSuccess(String weiXinPayUrl) {
		Map<String, String> parames = new HashMap<String, String>();
		parames.put("return_code", "SUCCESS");
		parames.put("return_msg", "OK");
		// 将参数转成xml格式
		String xmlWeChat = ArrayToXml(parames);
		// visitPost(weiXinPayUrl, xmlWeChat);
		HttpKit.post(weiXinPayUrl, xmlWeChat);
	}

	// 通知微信错误
	public static void noticeWeChatFAIL(String weiXinPayUrl) {
		Map<String, String> parames = new HashMap<String, String>();
		parames.put("return_code", "FAIL");
		parames.put("return_msg", "校验错误");
		// 将参数转成xml格式
		String xmlWeChat = ArrayToXml(parames);
		visitPost(weiXinPayUrl, xmlWeChat);
	}

	/* 使用时注意字符集 "GBK""UTF-8" */
	public static String visitPost(String urlStr, String code) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), code));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			con.disconnect();
			String res = buffer.toString();
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 处理xml请求信息
	 */
	public static String getWeiXinResponse(HttpServletRequest request) {
		BufferedReader bis = null;
		String result = "";
		try {
			bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			while ((line = bis.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 微信退款接口
	 * xyw
	 */
	public static String doRefund(String mchId, String url, String data) throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		// P12文件目录 证书路径
		String filepath = File.separator+"home"+File.separator+"panacea"+File.separator+"java"+File.separator;
		FileInputStream instream = new FileInputStream(filepath + "apiclient_cert.p12");
		try {
			keyStore.load(instream, mchId.toCharArray());// 这里写证书密码..默认是MCHID
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray())// 这里也是写密码的
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(url); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();

				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
}
