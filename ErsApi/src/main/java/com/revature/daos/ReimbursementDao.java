package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDao {
	
	public final ReimbursementDao currentImplementation = new ReimbursementDaoSQL();
	
	boolean save(Reimbursement reimb);
	
	boolean save(double amount, String username, String type, String desc);
	
	List<Reimbursement> findAll ();
	
	Reimbursement findById(int reimbId);
	
	List<Reimbursement> findByAuthorId(int userId);
	
	List<Reimbursement> findByAuthor(String author);
	
	
	List<Reimbursement> findByTypeId(int typeId);
	
	List<Reimbursement> findByStatusId(int statusId);
	
	boolean update(int reimbId, int statusId, int resolver);
	
}
