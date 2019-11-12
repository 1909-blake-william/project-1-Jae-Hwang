package com.revature.daos;

import java.util.List;

import com.revature.models.User;

public interface UserDao {
	
	public final UserDao currentImplementation = new UserDaoSQL();
	
	int save(User u);
	
	List<User> findAll();
	
	User findById(int userId);
	
	User findByUsernameAndPassword(String username, String password);
}
