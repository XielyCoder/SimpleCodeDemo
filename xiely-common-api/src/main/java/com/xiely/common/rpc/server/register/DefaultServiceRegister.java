package com.xiely.common.rpc.server.register;

import java.util.HashMap;
import java.util.Map;

public class DefaultServiceRegister implements ServiceRegister {

	private Map<String, ServiceObject> serviceMap = new HashMap<>();

	@Override
	public void register(ServiceObject so, String protocolName, int prot) {
		if (so == null) {
			throw new IllegalArgumentException("参数不能为空");
		}

		this.serviceMap.put(so.getName(), so);
	}

	@Override
	public ServiceObject getServiceObject(String name) {
		return this.serviceMap.get(name);
	}

}
