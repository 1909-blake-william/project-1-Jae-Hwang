package com.revature.drivers;

import java.util.List;

import com.revature.daos.ReimbursementDao;
import com.revature.daos.UserDao;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class TestDriver {
	
	static ConnectionUtil connectionUtil = ConnectionUtil.instance;
	static ReimbursementDao reimbDao = ReimbursementDao.currentImplementation;
	static UserDao userDao = UserDao.currentImplementation;

	public static void main(String[] args) {
		
		connectionUtil.setConnection();
		
		List<Reimbursement> reimbs = reimbDao.findAll();
		reimbs.forEach( reimb -> {
			System.out.println(reimb);
		});
		
		System.out.println();
		
		List<User> users = userDao.findAll();
		users.forEach( user -> {
			System.out.println(user);
		});
		
		System.out.println();
		
		reimbs = reimbDao.findByAuthorId(2);
		reimbs.forEach( reimb -> {
			System.out.println(reimb);
		});
		
		System.out.println();
	}
}
