package com.xiely.common.rpc.common.protocol;

/**
 * 通信协议接口
 * MessageProtocol
 */
public interface MessageProtocol {
	/**
	 * 编组请求消息
	 * @param req
	 * @return
	 */
	byte[] marshallingRequest(Request req);
	
	/**
	 * 解编组请求消息
	 * @param data
	 * @return
	 */
	Request unmarshallingRequest(byte[] data);
	
	/**
	 * 编组响应消息
	 * @param rsp
	 * @return
	 */
	byte[] marshallingResponse(Response rsp);
	
	/**
	 * 解编组响应消息
	 * @param data
	 * @return
	 */
	Response unmarshallingResponse(byte[] data);
}
