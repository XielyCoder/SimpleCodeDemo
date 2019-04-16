package com.xiely.common.dnrpc.simple;

import java.io.Serializable;

/**
 * RpcRequest
 * Rpc请求对象，请求远端服务服务的内容，在网络上进行传输。
 */
public class RpcRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	// 需要请求的类名
	private String className;
	
	// 需求请求的方法名
    private String methodName;
    
    // 请求方法的参数类型
    private Class<?>[] paramTypes;
    
    // 请求的参数值
    private Object[] params;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParamTypes() {
		return paramTypes;
	}
	public void setParamTypes(Class<?>[] paramTypes) {
		this.paramTypes = paramTypes;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}
 
}

