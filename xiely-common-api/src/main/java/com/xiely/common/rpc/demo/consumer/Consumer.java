package com.xiely.common.rpc.demo.consumer;

import java.util.HashMap;
import java.util.Map;

import com.xiely.common.rpc.client.ClientStubProxyFactory;
import com.xiely.common.rpc.client.net.NettyNetClient;
import com.xiely.common.rpc.common.protocol.JSONMessageProtocol;
import com.xiely.common.rpc.common.protocol.MessageProtocol;
import com.xiely.common.rpc.demo.DemoService;
import com.xiely.common.rpc.discovery.ZookeeperServiceInfoDiscoverer;

/**
 * 消费者端示例代码
 * Consumer
 */
public class Consumer {
	
	/*
	 * 运行代码依赖zk地址，在app.properties中配置即可
	 * 配置项：zk.address=
	 */
	public static void main(String[] args) throws Exception {
		// 构建客户端stub代理
		ClientStubProxyFactory clientStubFactory = new ClientStubProxyFactory();
		clientStubFactory.setNetClient(new NettyNetClient());
		clientStubFactory.setSid(new ZookeeperServiceInfoDiscoverer());
		Map<String, MessageProtocol> supportMessageProtocols = new HashMap<String, MessageProtocol>();
		supportMessageProtocols.put(JSONMessageProtocol.class.getSimpleName(), new JSONMessageProtocol());
		clientStubFactory.setSupportMessageProtocols(supportMessageProtocols);
		
		// 通过代理工厂获得客户端接口
		DemoService demoService =  clientStubFactory.getProxy(DemoService.class); 	// 获取远程服务代理
		
		//System.out.println(demoService);
		System.out.println("获得代理接口");
		
		// 执行远程方法
		String hello = demoService.sayHello("world");
		System.out.println(hello); // 显示调用结果
		
		hello = demoService.sayHello("dog");
		System.out.println(hello); // 显示调用结果
	}
}
