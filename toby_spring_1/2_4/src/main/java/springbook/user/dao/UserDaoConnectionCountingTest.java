package springbook.user.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class UserDaoConnectionCountingTest {

	public static void main(String[] args) throws ClassNotFoundException , SQLException {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		UserDao dao = context.getBean("userDao",UserDao.class);
		
		CountingConnectionMaker ccm = context.getBean("dataSource",CountingConnectionMaker.class);
		System.out.println("Connection counter : " + ccm.getCounter());
		
		

	}

}
