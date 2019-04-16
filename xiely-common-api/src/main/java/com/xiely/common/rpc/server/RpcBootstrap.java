package com.xiely.common.rpc.server;

import java.io.IOException;
import java.util.Map;

import com.xiely.common.rpc.common.protocol.JSONMessageProtocol;
import com.xiely.common.rpc.server.register.ServiceObject;
import com.xiely.common.rpc.server.register.ServiceRegister;
import com.xiely.common.rpc.server.register.ZookeeperExportServiceRegister;


/**
 * ServerBootstap
 * 
 * 
 */
public class RpcBootstrap {
	private ServiceRegister register = new ZookeeperExportServiceRegister();
	private ServiceLoader loader = new ServiceLoader();
	private String protocol = JSONMessageProtocol.class.getSimpleName();
	private int port = 9082;
	private RpcServer server;
	
	public void start(String packName) {
		Map<String, Object> services = loader.getService(packName);
		System.out.println("扫描到实现类："+services);
		
		services.forEach((k,v)->{
			Class<?> interf = null;
			Class<?>[] interfaces = v.getClass().getInterfaces();
			for(Class<?> face : interfaces) {
				if(k.equals(face.getName())) {
					interf = face;
				}
			}
			ServiceObject so = new ServiceObject(k, interf, v);
			register.register(so, protocol, port);
			System.out.println("完成类的注册："+so);
		});
		
		server = new RpcServer(protocol, port);
		RequestHandler handler = new RequestHandler();
		handler.setProtocol(new JSONMessageProtocol());
		handler.setServiceRegister(register);
		server.setHandler(handler);
		server.start();
	}
	
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

