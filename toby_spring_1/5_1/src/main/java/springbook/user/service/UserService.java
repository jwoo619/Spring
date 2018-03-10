package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domian.Level;
import springbook.user.domian.User;

public class UserService {
	UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void upgradeLevels(){
		List<User> users = userDao.getAll();
		for(User user : users){
			Boolean change = null;
			if(user.getLevel() == Level.BASIC && user.getLogin() >= 50){
				user.setLevel(Level.SILVER);
				change = true;
			}
			else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30){
				user.setLevel(Level.GOLD);
				change = true;
			}
			else if(user.getLevel() == Level.GOLD){change = false;}
			else {change = false;}
			
			if(change){ userDao.update(user);}
		}
	}
	
	public void add(User user){
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
