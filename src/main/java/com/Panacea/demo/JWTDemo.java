package com.Panacea.demo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JWT的应用，Token的生成和解析
 * @author 夜未
 * @since 2020年11月25日
 */
public class JWTDemo {

	//导包，看pom文件，把这当成工具来使用就好了，加在需要验证的地方
	
	
	
	/**
	 * 测试用例
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
	   String token= createToken("这里放要放在token里面的数据");
		System.out.println("生成的Token="+token);
	   //解析Token
		System.out.println(getData(token));
		Thread.sleep(10000);//测试10秒后过期
		System.out.println(getData(token));
	}


    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
    public static final String SECRET = "JKKLJOoasdlfj";
    /** token 过期时间: */
    public static final int calendarField = Calendar.SECOND;//秒
    public static final int calendarInterval = 10;//10秒

    /**
     * JWT生成Token
     * JWT构成: header 头信息, payload 载荷信息体, signature 签名
     * @param jsonData 需要放到token里面的JSON数据，如果信息重要，放进去之前自己加密一下
     */
    public static String createToken(String jsonData) throws Exception {
        Date iatDate = new Date();
        // 过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        String token = JWT.create().withHeader(map) // 头部
        		//载荷
                .withClaim("iss", "Service") // jwt签发者
                .withClaim("aud", "WEB") //jwt所面向的用户
                //自定义数据,可以存放我们自己的数据key-value值，关键信息最好加密在放进去
                .withClaim("data", jsonData)
//                .withClaim("user_name","用户名称")
                .withIssuedAt(iatDate) // 签名时间
                .withExpiresAt(expiresDate) // 过期时间
                .sign(Algorithm.HMAC256(SECRET)); //签名
        return token;
    }

    /**
     * 解密Token
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        jwt = verifier.verify(token);
        if(jwt==null||jwt.getClaims()==null) {
        	throw new JWTDecodeException("Token验证失败");
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取数据data
     * 
     * @param token
     * @return  JSON 数据 data 
     */
    public static String getData(String token)throws JWTDecodeException{
        Map<String, Claim> claims = verifyToken(token);
        Claim claim = claims.get("data");
        if (null == claim || StringUtils.isEmpty(claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        	throw new JWTDecodeException("Token验证失败");
        }
        return claim.asString();
    }
	
}
