package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domian.User;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {
	@Autowired
	private ApplicationContext context;
	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp(){
		System.out.println(this.context);
		System.out.println(this);
		
		this.dao = context.getBean("userDao",UserDao.class);
		
		this.user1 = new User("tello","이진우","password1");
		this.user2 = new User("admin","관리자","password2");
		this.user3 = new User("guest","손님","password3");
	}
	
	@Test
	public void addAndGet() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
	}
	
	@Test
	public void getCount() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException{
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}
	
	public static void main(String[] args) {
		JUnitCore.main("springbook.user.dao.UserDaoTest");
	}

}
