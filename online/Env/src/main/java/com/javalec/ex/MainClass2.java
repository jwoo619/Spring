package com.javalec.ex;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass2 {

	public static void main(String[] args) {
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX2.xml");
		
		AdminConnection adminConnection = ctx.getBean("adminConnection",AdminConnection.class);
		System.out.println(adminConnection.getAdminId());
		System.out.println(adminConnection.getAdminPw());
		
		

	}

}
