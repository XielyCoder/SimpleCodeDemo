package com.xiely.common.rpc.discovery;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import com.alibaba.fastjson.JSON;

import com.xiely.common.rpc.common.protocol.PropertiesUtils;
import com.xiely.common.rpc.common.serialize.MyZkSerializer;

/**
 * Zookeeper方式获取远程服务信息类。
 * 
 * ZookeeperServiceInfoDiscoverer
 */
public class ZookeeperServiceInfoDiscoverer implements ServiceInfoDiscoverer {
	ZkClient client;
	
	private String centerRootPath = "/dnRpc-framework";
	
	public ZookeeperServiceInfoDiscoverer() {
		String addr = PropertiesUtils.getProperties("zk.address");
		client = new ZkClient(addr);
		client.setZkSerializer(new MyZkSerializer());
	}
	
	public void regist(ServiceInfo serviceResource) {
		String serviceName = serviceResource.getName();
		String uri = JSON.toJSONString(serviceResource);
		try {
			uri = URLEncoder.encode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String servicePath = centerRootPath + "/"+serviceName+"/service";
		if(! client.exists(servicePath)) {
			client.createPersistent(servicePath, true);
		}
		String uriPath = servicePath+"/"+uri;
		client.createEphemeral(uriPath);
	}
	
	/**
	 * 加载配置中心中服务资源信息
	 * @param serviceName
	 * @return
	 */
	public List<ServiceInfo> loadServiceResouces(String serviceName) {
		String servicePath = centerRootPath + "/"+serviceName+"/service";
		List<String> children = client.getChildren(servicePath);
		List<ServiceInfo> resources = new ArrayList<ServiceInfo>();
		for(String ch : children) {
			try {
				String deCh = URLDecoder.decode(ch, "UTF-8");
				ServiceInfo r = JSON.parseObject(deCh, ServiceInfo.class);
				resources.add(r);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return resources;
	}
	
	@Override
	public ServiceInfo getServiceInfo(String name) {
		List<ServiceInfo> list = loadServiceResouces(name);
		ServiceInfo info = list.get(0);
		list.forEach((e)->{
			if(e != info) {
				info.addAddress(e.getAddress().get(0));
			}
		});
		return info;
	}

}
