package com.Panacea.unity.nettyHandler;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;


public class LoginHandler extends MessageToMessageDecoder<ServerResponse> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ServerResponse res, List<Object> out) throws Exception {
		if (ServerMsgType.fromType(res.getDataType()) != ServerMsgType.客户端登录认证) {
			ctx.fireChannelRead(res);
			return;
		}
	}

}
