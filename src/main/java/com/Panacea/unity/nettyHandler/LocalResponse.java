package com.Panacea.unity.nettyHandler;

import io.netty.buffer.ByteBuf;

public class LocalResponse {

	private ResponseType responseType;
	private int dataLength;
	private ByteBuf data;

	

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public ByteBuf getData() {
		return data;
	}

	public void setData(ByteBuf data) {
		this.data = data;
	}

}
