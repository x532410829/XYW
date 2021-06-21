package com.Panacea.unity.nettyHandler;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import space.cl.fb.bean.LocalResponse;
import space.cl.fb.bean.ResponseType;
import space.cl.fb.bean.server.ServerResponse;

public class ServerMsgHandler extends MessageToMessageDecoder<ServerResponse> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ServerResponse res, List<Object> out) throws Exception {
		
		LocalResponse localRes = new LocalResponse();
		ByteBuf buf = res.getBuf();
		localRes.setResponseType(ResponseType.fromVal(buf.readByte()));
		localRes.setDataLength(buf.readByte());
		localRes.setData(buf.readSlice(localRes.getDataLength()));
		out.add(localRes);
	}

}
