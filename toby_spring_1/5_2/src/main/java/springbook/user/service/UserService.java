package springbook.user.service;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import springbook.user.dao.UserDao;
import springbook.user.domian.Level;
import springbook.user.domian.User;

public class UserService{
	private DataSource dataSource;
	UserDao userDao;
	
	public static final int MIN_LOGOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void upgradeLevels() throws Exception{
		TransactionSynchronizationManager.initSynchronization();
		Connection c = DataSourceUtils.getConnection(dataSource);
		c.setAutoCommit(false);
		
		try {
			List<User> users = userDao.getAll();
			for(User user : users){
				if(canUpgradeLevel(user))
					upgradeLevel(user);
			}
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			DataSourceUtils.releaseConnection(c, dataSource);
			TransactionSynchronizationManager.unbindResource(this.dataSource);
			TransactionSynchronizationManager.clearSynchronization();
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
	protected void upgradeLevel(User user){
		user.upgradeLevel();
		userDao.update(user);
	}
	
	public void add(User user){
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}