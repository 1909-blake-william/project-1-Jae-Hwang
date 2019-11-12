package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDao {
	
	public final ReimbursementDao currentImplementation = new ReimbursementDaoSQL();
	
	void save(Reimbursement reimb);
	
	void save(double amount, String username, String type, String desc);
	
	List<Reimbursement> findAll ();
	
	Reimbursement findById(int reimbId);
	
	List<Reimbursement> findByAuthorId(int userId);
	
	List<Reimbursement> findByTypeId(int typeId);
	
	List<Reimbursement> findByStatusId(int statusId);
	
	void update();
	
}
