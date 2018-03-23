package springbook.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import springbook.user.domian.User;

@Transactional
public interface UserService{
	void add(User user);
	void deleteAll();
	void update(User user);
	void upgradeLevels();
	
	@Transactional(readOnly=true)
	User get(String id);
	@Transactional
	List<User> getAll();
	
}
