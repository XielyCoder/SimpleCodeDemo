package com.xiely.common.rpc.server;
/**
 * Service
 * 
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Service
 * 一个提供了RPC服务的实现类。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
	/**
	 * 注解所属接口类型
	 * @return
	 */
	Class<?> value();
}

