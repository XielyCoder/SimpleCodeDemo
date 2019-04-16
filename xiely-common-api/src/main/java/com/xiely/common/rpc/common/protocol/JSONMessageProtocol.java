package com.xiely.common.rpc.common.protocol;

import com.alibaba.fastjson.JSON;

/**
 * json协议编组解组器实现
 * JSONMessageProtocol
 */
public class JSONMessageProtocol implements MessageProtocol {

	@Override
	public byte[] marshallingRequest(Request req) {
		Request temp = new Request();
		temp.setServiceName(req.getServiceName());
		temp.setMethod(req.getMethod());
		temp.setHeaders(req.getHeaders());
		temp.setPrameterTypes(req.getPrameterTypes());

		if (req.getParameters() != null) {
			Object[] params = req.getParameters();
			Object[] serizeParmas = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				serizeParmas[i] = JSON.toJSONString(params[i]);
			}

			temp.setParameters(serizeParmas);
		}

		return JSON.toJSONBytes(temp);
	}

	@Override
	public Request unmarshallingRequest(byte[] data) {
		Request req = JSON.parseObject(data, Request.class);
		if(req.getParameters() != null) {
			Object[] serizeParmas = req.getParameters();
			Object[] params = new Object[serizeParmas.length];
			for(int i = 0; i < serizeParmas.length; i++) {
				Object param = JSON.parseObject(serizeParmas[i].toString(), Object.class);
				params[i] = param;
			}
			req.setParameters(params);
		}
		return req;
	}

	@Override
	public byte[] marshallingResponse(Response rsp) {
		Response resp = new Response();
		resp.setHeaders(rsp.getHeaders());
		resp.setException(rsp.getException());
		resp.setReturnValue(rsp.getReturnValue());
		resp.setStatus(rsp.getStatus());
		return JSON.toJSONBytes(resp);
	}

	@Override
	public Response unmarshallingResponse(byte[] data) {
		return JSON.parseObject(data, Response.class);
	}

}
