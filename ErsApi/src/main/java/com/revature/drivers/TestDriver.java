package com.revature.drivers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	static Logger logger = LogManager.getLogger(TestDriver.class);

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
		
		System.out.println();
		System.out.println("Pagination: 1");
		reimbs = reimbDao.findAllPag(1);
		reimbs.forEach( reimb -> {
			System.out.println(reimb);
		});
		
		System.out.println();
		System.out.println("Pagination: 1");
		reimbs = reimbDao.findByAuthorPag("potato", 1);
		reimbs.forEach( reimb -> {
			System.out.println(reimb);
		});
		
		//for (int i = 0; i < 10; i++) {
		//	reimbDao.save(100, "potato", "Other", "For pagination test: " + i);
		//}
		
		System.out.println();
		System.out.println("Row Count: " + reimbDao.getMaxPage());
		System.out.println("Row Count with uid potato: " + reimbDao.getMaxPageAuthor("potato"));
	}
}
