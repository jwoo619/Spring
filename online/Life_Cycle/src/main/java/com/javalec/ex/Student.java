package com.javalec.ex;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import javax.annotation.*;

public class Student implements InitializingBean ,  DisposableBean {
	
	private String name;
	private int age;
	
	public Student(String name , int age){
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public void afterPropertiesSet() throws Exception{
		System.out.println("afterPropertiesSet()");
	}
	
	@Override
	public void destroy() throws Exception{
		System.out.println("destory()");
	}
	
	@PostConstruct
	public void init(){
		System.out.println("init()");
	}
	
	@PreDestroy
	public void des(){
		System.out.println("des()");
	}
	
	

}
