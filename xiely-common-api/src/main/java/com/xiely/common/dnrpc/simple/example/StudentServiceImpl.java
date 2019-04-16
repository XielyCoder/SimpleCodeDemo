package com.xiely.common.dnrpc.simple.example;

import com.xiely.common.dnrpc.simple.Service;

/**
 * StudentServiceImpl
 * 
 */
@Service(StudentService.class)
public class StudentServiceImpl implements StudentService {

	public Student getInfo() {
		Student person = new Student();
		person.setAge(18);
		person.setName("arrylist");
		person.setSex("女");
		return person;
	}

	public boolean printInfo(Student person) {
		if (person != null) {
			System.out.println(person);
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		new Thread(()->{
			System.out.println("111");
		}).start();;
	}
}
