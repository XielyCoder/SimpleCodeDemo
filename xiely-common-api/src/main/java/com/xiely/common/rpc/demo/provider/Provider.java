package com.xiely.common.rpc.demo.provider;

import com.xiely.common.rpc.server.RpcBootstrap;

public class Provider {
	
	/*
	 * 运行代码依赖zk地址，在app.properties中配置即可
	 * 配置项：zk.address=
	 */
	public static void main(String[] args) throws Exception {
		RpcBootstrap bootstrap = new RpcBootstrap();
		bootstrap.start("edu.dongnao.study.rpc.demo");
		System.in.read(); // 按任意键退出
	}
}
