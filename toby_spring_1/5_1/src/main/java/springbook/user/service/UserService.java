package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domian.Level;
import springbook.user.domian.User;

public class UserService implements UserLevelUpgradePolicy{
	UserDao userDao;
	Level level;
	
	public static final int MIN_LOGOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void upgradeLevels(){
		List<User> users = userDao.getAll();
		for(User user : users){
			if(canUpgradeLevel(user)) 
				upgradeLevel(user);
		}
	}
	public boolean canUpgradeLevel(User user){
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
			case BASIC : return (user.getLogin() >= MIN_LOGOUNT_FOR_SILVER);
			case SILVER : return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD : return false;
			default : throw new IllegalArgumentException("UnKnown Level : "+currentLevel);
		}
	}
	public void upgradeLevel(User user){
		user.upgradeLevel();
		userDao.update(user);
	}
	
	public void add(User user){
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
