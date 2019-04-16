package com.xiely.common.rpc.demo.provider;

import com.xiely.common.rpc.demo.DemoService;
import com.xiely.common.rpc.server.Service;

@Service(DemoService.class)
public class DemoServiceImpl implements DemoService {
	/**
	 * 代码实现
	 */
	public String sayHello(String name) {
		return "Hello " + name;
	}
}
