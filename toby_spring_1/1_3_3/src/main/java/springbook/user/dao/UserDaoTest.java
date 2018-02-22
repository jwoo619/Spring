package springbook.user.dao;

import java.sql.SQLException;


import springbook.user.domian.User;

public class UserDaoTest {

	public static void main(String[] args) throws ClassNotFoundException , SQLException {
		
		ConnectionMaker connectionMaker = new DConnectionMaker();
		UserDao dao = new UserDao(connectionMaker); 
		
		User user = new User();
		user.setId("tello");
		user.setName("이진우");
		user.setPassword("q1w2e3r4");
		
		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + " 조회 성공");

	}

}
