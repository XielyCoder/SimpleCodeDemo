package com.xiely.common.rpc.common.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * Request
 */
public class Request {

	private String serviceName;

	private String method;

	private Map<String, String> headers = new HashMap<String, String>();

	private Class<?>[] prameterTypes;

	private Object[] parameters;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Class<?>[] getPrameterTypes() {
		return prameterTypes;
	}

	public void setPrameterTypes(Class<?>[] prameterTypes) {
		this.prameterTypes = prameterTypes;
	}

	public void setParameters(Object[] prameters) {
		this.parameters = prameters;
	}

	public String getHeader(String name) {
		return this.headers == null ? null : this.headers.get(name);
	}

	public Object[] getParameters() {
		return this.parameters;
	}

}
