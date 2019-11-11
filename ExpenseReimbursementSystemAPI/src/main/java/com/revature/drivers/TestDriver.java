package com.revature.drivers;

import java.util.List;

import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

public class TestDriver {
	
	static ConnectionUtil connectionUtil = ConnectionUtil.instance;
	static ReimbursementDao ReimbDao = ReimbursementDao.currentImplementation;

	public static void main(String[] args) {
		
		connectionUtil.setConnection();
		
		List<Reimbursement> reimbs = ReimbDao.findAll();
		reimbs.forEach( reimb -> {
			System.out.println(reimb);
		});
	}
}
