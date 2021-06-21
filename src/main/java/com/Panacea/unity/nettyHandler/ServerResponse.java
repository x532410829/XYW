package com.Panacea.unity.nettyHandler;

import io.netty.buffer.ByteBuf;

public class ServerResponse {

	private short packgeLength;
	private short dataType;
	private short dataLength;
	private ByteBuf buf;

	public short getDataLength() {
		return dataLength;
	}

	public ServerResponse setDataLength(short dataLength) {
		this.dataLength = dataLength;
		return this;
	}

	public short getDataType() {
		return dataType;
	}

	public ServerResponse setDataType(short dataType) {
		this.dataType = dataType;
		return this;
	}

	public ByteBuf getBuf() {
		return buf;
	}

	public ServerResponse setBuf(ByteBuf buf) {
		this.buf = buf;
		return this;
	}

	public short getPackgeLength() {
		return packgeLength;
	}

	public ServerResponse setPackgeLength(short packgeLength) {
		this.packgeLength = packgeLength;
		return this;
	}

}
