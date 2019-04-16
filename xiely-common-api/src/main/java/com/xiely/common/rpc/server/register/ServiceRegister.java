package com.xiely.common.rpc.server.register;

/**
 * 服务注册器
 * ServiceRegister
 */
public interface ServiceRegister {
	/**
	 * 注册服务
	 * @param so
	 */
	void register(ServiceObject so, String protocolName, int port);
	
	/**
	 * 获取服务对象
	 * @param name
	 * @return
	 */
	ServiceObject getServiceObject(String name);
}
