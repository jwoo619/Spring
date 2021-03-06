package springbook.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dao.UserDao;
import springbook.user.domian.Level;
import springbook.user.domian.User;

import static springbook.user.service.UserService.MIN_LOGOUNT_FOR_SILVER;
import static springbook.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserServiceTest {
	@Autowired UserService userService;
	@Autowired UserDao userDao;
	List<User> users;
	
	@Before
	public void setUp(){
		users = Arrays.asList(
				new User("bbbb","b유저","p1",Level.BASIC,MIN_LOGOUNT_FOR_SILVER-1,0),
				new User("jjjj","j유저","p2",Level.BASIC,MIN_LOGOUNT_FOR_SILVER,0),
				new User("eeee","e유저","p3",Level.SILVER,60,MIN_RECOMMEND_FOR_GOLD-1),
				new User("mmmm","m유저","p4",Level.SILVER,65,MIN_RECOMMEND_FOR_GOLD),
				new User("gggg","g유저","p5",Level.GOLD,100,Integer.MAX_VALUE)
			);
	}
	
	@Test
	public void upgradeLevels(){
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		userService.upgradeLevels();
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
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

}