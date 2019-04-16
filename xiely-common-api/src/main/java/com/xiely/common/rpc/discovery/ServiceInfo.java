package com.xiely.common.rpc.discovery;

import java.util.ArrayList;
import java.util.List;

/**
 * 远程服务信息类
 * ServiceInfo
 */
public class ServiceInfo {

	private String name;		// 服务名称

	private String protocol;	// 服务提供的协议

	private List<String> address;	// 服务地址信息

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}
	public void addAddress(String address) {
		if(this.address == null) {
			this.address = new ArrayList<String>();
		}
		this.address.add(address);
	}

}
