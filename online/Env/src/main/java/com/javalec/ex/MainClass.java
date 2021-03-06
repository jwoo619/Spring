package com.javalec.ex;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class MainClass {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext ctx = new GenericXmlApplicationContext();
		ConfigurableEnvironment env = ctx.getEnvironment();
		MutablePropertySources propertySources = env.getPropertySources();
		
		try {
			propertySources.addLast(new ResourcePropertySource("classpath:admin.properties"));
			
			System.out.println(env.getProperty("admin.id"));
			System.out.println(env.getProperty("admin.pw"));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		GenericXmlApplicationContext gctx = (GenericXmlApplicationContext) ctx;
		gctx.load("applicationCTX.xml");
		gctx.refresh();
		
		AdminConnection adminConnection = gctx.getBean("adminConnection",AdminConnection.class);
		System.out.println("Admin Id :" + adminConnection.getAdminId());
		System.out.println("Admin Pw :" + adminConnection.getAdminPw());
		
		gctx.close();
		ctx.close();

	}

}
