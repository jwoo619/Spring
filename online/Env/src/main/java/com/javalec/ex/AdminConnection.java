package com.javalec.ex;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class AdminConnection implements EnvironmentAware , InitializingBean , DisposableBean {
	
	private Environment env;
	private String adminId;
	private String adminPw;
	
	@Override
	public void setEnvironment(Environment env) {
		System.out.println("setEnv()");
		setEnv(env);
	}

	
	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminPw() {
		return adminPw;
	}

	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception{
		System.out.println("after()");
		//setAdminId(env.getProperty("admin.id")); Main1
		//setAdminPw(env.getProperty("admin.pw"));
	}
	
	@Override
	public void destroy() throws Exception{
		System.out.println("des()");
	}
	
}
