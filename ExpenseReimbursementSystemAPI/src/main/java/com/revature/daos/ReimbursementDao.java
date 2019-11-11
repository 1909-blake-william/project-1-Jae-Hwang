package com.revature.daos;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDao {
	
	public final ReimbursementDao currentImplementation = new ReimbursementDaoSQL();
	
	int save(Reimbursement reimb);
	
	List<Reimbursement> findAll ();
	
	Reimbursement findById(int reimbId);
	
	List<Reimbursement> findByAuthorId(int userId);
	
	List<Reimbursement> findByTypeId(int typeId);
	
	List<Reimbursement> findByStatusId(int statusId);
	
}
