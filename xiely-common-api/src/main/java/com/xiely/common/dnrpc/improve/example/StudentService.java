package com.xiely.common.dnrpc.improve.example;
/**
 * StudentService
 * 
 */
public interface StudentService {
	/**
	 * 获取信息
	 * @return
	 */
	public Student getInfo();
	
	public boolean printInfo(Student student);
}

