package com.revature.drivers;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.daos.ReimbursementDao;
import com.revature.daos.UserDao;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;
import com.revature.util.ObjectUtil;

public class TestDriver {
	
	static ConnectionUtil connectionUtil = ConnectionUtil.instance;
	static ReimbursementDao reimbDao = ReimbursementDao.currentImplementation;
	static UserDao userDao = UserDao.currentImplementation;
	private static ObjectUtil ou = ObjectUtil.instance;

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
		
		reimbs = reimbDao.findByAuthor("potato");
		reimbs.forEach( reimb -> {
			System.out.println(reimb);
		});
		
		for (int i = 0; i < 1000; i++) {
			reimbDao.save(100, "potato", "Other", "For pagination test.");
		}
	}
}
