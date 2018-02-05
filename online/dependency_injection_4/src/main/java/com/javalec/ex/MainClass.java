package com.javalec.ex;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		String configLocation1 = "classpath:applicationCTX.xml";
		String configLocation2 = "classpath:applicationCTX1.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation1,configLocation2);
		Student student1 = ctx.getBean("student1",Student.class);
		System.out.println(student1.getName());
		System.out.println(student1.getHobbys());
		
		StudentInfo studentInfo = ctx.getBean("studentInfo1",StudentInfo.class);
		Student student2 = studentInfo.getStudent();
		System.out.println(student2.getName());
		System.out.println(student2.getHobbys());
		
		if(student1.equals(student2))
			System.out.println("stu1 == stu2");
		
		Student student3 = ctx.getBean("student3",Student.class);
		System.out.println(student3.getName());
		
		if(student1.equals(student3))
			System.out.println("stu1 == stu3");
		else
			System.out.println("stu1 != stu3");
		
		Family family = ctx.getBean("family",Family.class);
		System.out.println(family.getPapaName());
		System.out.println(family.getMamName());
		System.out.println(family.getSisterName());
		System.out.println(family.getBrotherName());
		
		ctx.close();
		System.out.println("=================");
		AnnotationConfigApplicationContext ctx2 = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		Student student4 = ctx2.getBean("student4",Student.class);
		System.out.println(student4.getName());
		System.out.println(student4.getAge());
		System.out.println(student4.getHobbys());
		System.out.println(student4.getHeight());
		System.out.println(student4.getWeight());
		
	}

}
