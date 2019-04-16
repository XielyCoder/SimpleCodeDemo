package com.xiely.common.rpc.common.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应消息
 * Response
 */
public class Response {

	private Status status;

	private Map<String, String> headers = new HashMap<>();

	private Object returnValue;

	private Exception exception;

	public Response() {
	};

	public Response(Status status) {
		this.status = status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Status getStatus() {
		return status;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public Exception getException() {
		return exception;
	}

	public String getHeader(String name) {
		return this.headers == null ? null : this.headers.get(name);
	}

	public void setHeader(String name, String value) {
		this.headers.put(name, value);

	}
}
