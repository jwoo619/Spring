package com.javalec.ex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
//@ImportResource("classpath:applicationCTX.xml");
public class ApplicationConfig {
	
	@Bean
	public Student student4(){
		
		ArrayList<String> hobbys = new ArrayList<String>();
		hobbys.add("노래");
		hobbys.add("영화");
		
		Student student = new Student("김자바", 13, hobbys);
		student.setHeight(170);
		student.setWeight(50);
		
		return student;
		
	}
}
