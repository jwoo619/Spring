package com.javalec.ex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class ApplicationConfig3 {
	
	@Value("${admin.id}")
	private String adminId;
	@Value("${admin.pw}")
	private String adminPw;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer Properties(){
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		
		Resource locations = null;
		locations = new ClassPathResource("admin.properties");
		configurer.setLocation(locations);
		
		return configurer;
		
	}
	
	@Bean
	public AdminConnection adminconfig(){
		AdminConnection adminConnection = new AdminConnection();
		adminConnection.setAdminId(adminId);
		adminConnection.setAdminPw(adminPw);
		
		return adminConnection;
	}
	

}
