package springbook.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.dao.UserDao;
import springbook.user.domian.Level;
import springbook.user.domian.User;

import static springbook.user.service.UserServiceImpl.MIN_LOGOUNT_FOR_SILVER;
import static springbook.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserServiceTest{
	@Autowired UserService userService;
	@Autowired UserServiceImpl userServiceImpl;
	@Autowired UserDao userDao;
	@Autowired PlatformTransactionManager transactionManager;
	@Autowired MailSender mailSender;
	
	List<User> users;
	
	@Before
	public void setUp(){
		users = Arrays.asList(
				new User("bbbb","b유저","p1","user1@ksug.org",Level.BASIC,MIN_LOGOUNT_FOR_SILVER-1,0),
				new User("jjjj","j유저","p2","user2@ksug.org",Level.BASIC,MIN_LOGOUNT_FOR_SILVER,0),
				new User("eeee","e유저","p3","user3@ksug.org",Level.SILVER,60,MIN_RECOMMEND_FOR_GOLD-1),
				new User("mmmm","m유저","p4","user4@ksug.org",Level.SILVER,65,MIN_RECOMMEND_FOR_GOLD),
				new User("gggg","g유저","p5","user5@ksug.org",Level.GOLD,100,Integer.MAX_VALUE)
			);
	}
	
	@Test
	public void upgradeLevels() throws Exception{
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		userService.upgradeLevels();
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
		
		List<String> request = mockMailSender.getRequests();
		assertThat(request.size(), is(2));
		assertThat(request.get(0), is(users.get(1).getEmail()));
		assertThat(request.get(1), is(users.get(3).getEmail()));
	}
	private void checkLevelUpgraded(User user , boolean upgrade){
		User userUpdate = userDao.get(user.getId());
		if(upgrade)
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		else
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
	}
	
	@Test
	public void add(){
		userDao.deleteAll();
		
		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);
		
		userService.add(userWithoutLevel);
		userService.add(userWithLevel);
		
		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevel.getLevel(), is(Level.BASIC));	
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception{
		TestUserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(userDao);
		testUserService.setMailSender(mailSender);
		
		UserServiceTx txUserService = new UserServiceTx();
		txUserService.setTransactionManager(transactionManager);
		txUserService.setUserService(testUserService);
		
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		try {
			txUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
		}
		
		checkLevelUpgraded(users.get(1), false);
	}
	
	static class TestUserService extends UserServiceImpl {
		private String id;
		
		private TestUserService(String id) {  
			this.id = id;
		}

		protected void upgradeLevel(User user) {
			if (user.getId().equals(this.id)) throw new TestUserServiceException();  
			super.upgradeLevel(user);  
		}
	}
	static class TestUserServiceException extends RuntimeException {
	}
	
	static class MockMailSender implements MailSender{
		private List<String> requsets = new ArrayList<String>();
		
		public List<String> getRequests(){
			return requsets;
		}
		
		public void send(SimpleMailMessage mailMessage) throws MailException{
			requsets.add(mailMessage.getTo()[0]);
		}
		
		public void send(SimpleMailMessage[] mailMessages) throws MailException{
		}
	}

}