package com.javalec.ex;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		String configLocation = "classpath:applicationCTX.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
		StudentInfo studentInfo = ctx.getBean("studentInfo",StudentInfo.class);
		studentInfo.getStundetInfo();
		
		Student student2 = ctx.getBean("student2",Student.class);
		studentInfo.setStundet(student2);
		studentInfo.getStundetInfo();
		
		ctx.close();
		

	}

}
