package com.javalec.ex;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass3 {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig3.class);
		AdminConnection adminConnection = ctx.getBean("adminconfig",AdminConnection.class);
		
		System.out.println(adminConnection.getAdminId());
		System.out.println(adminConnection.getAdminPw());
		
		ctx.close();

	}

}
