package com.Panacea.unity.nettyHandler;

import java.util.HashMap;
import java.util.Map;

public enum ServerMsgType {

	Undifiend(0), 心跳上报(0x1E), 心跳回应(0x1F), 消息发送(0x0A), 传感器数据(0x0B), 控制数据(0x0C), 服务端登录要求(0x50), 客户端登录认证(0x51);

	private int val;

	private ServerMsgType(int val) {
		this.val = val;
	}

	private static Map<Integer, ServerMsgType> map = new HashMap<Integer, ServerMsgType>();

	static {
		for (ServerMsgType s : values()) {
			map.put(s.val, s);
		}
	}

	public static Integer fromVal(ServerMsgType t) {
		Integer val = null;
		for (Map.Entry<Integer, ServerMsgType> entry : map.entrySet()) {
			if (entry.getValue() == t) {
				val = entry.getKey();
			}
		}
		return val == null ? Undifiend.val : val;
	}

	public static ServerMsgType fromType(int val) {
		ServerMsgType s = map.get(val);
		return s == null ? ServerMsgType.Undifiend : s;
	}

}
