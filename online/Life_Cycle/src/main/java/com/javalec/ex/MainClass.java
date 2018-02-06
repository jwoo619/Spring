package com.javalec.ex;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		
		ctx.load("classpath:applicationCTX.xml");
		
		ctx.refresh();
		
		Student student = ctx.getBean("student",Student.class);
		
		System.out.println(student.getName());
		System.out.println(student.getAge());
		
		Student student2 = ctx.getBean("student",Student.class);
		
		student2.setName("홍길순");
		student2.setAge(30);
		System.out.println(student.getName());
		System.out.println(student.getAge());
		
		if(student.equals(student2))
			System.out.println("stu1 == stu2");
		else
			System.out.println("stu1 != stu2");
		
		ctx.close(); 

	}

}
