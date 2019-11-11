package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class ReimbursementDaoSQL implements ReimbursementDao {

	private Logger log = Logger.getRootLogger();
	ConnectionUtil connectionUtil = ConnectionUtil.instance;

	private Reimbursement extractReimbursement (ResultSet rs) throws SQLException {
		int reimb_id = rs.getInt("reimb_id");
		double reimb_amount = rs.getDouble("reimb_amount");
		String reimb_author = rs.getString("reimb_author");
		int reimb_status = rs.getInt("reimb_status_id");
		int reimb_type = rs.getInt("reimb_type_id");
		Reimbursement result = new Reimbursement(reimb_id, reimb_amount, reimb_author, reimb_status, reimb_type);
		return result;
	}
	
	@Override
	public int save(Reimbursement reimb) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Reimbursement> findAll() {

		log.trace("attempting to find all reimbs");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_id";

			PreparedStatement ps = c.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}

			return reimbs;

		} catch (SQLException e) {
			log.debug("connection failed");
			// e.printStackTrace();
			return null;
		}

	}

	@Override
	public Reimbursement findById(int reimbId) {
		
		log.trace("attempting to find a reimb by id");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_id "
					+ "WHERE reimb_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, reimbId);
			
			ResultSet rs = ps.executeQuery();
			Reimbursement reimb = null;
			if (rs.next()) {
				reimb = extractReimbursement(rs);
			}

			return reimb;

		} catch (SQLException e) {
			log.debug("connection failed");
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Reimbursement> findByAuthorId(int authorId) {
		
		log.trace("attempting to find reimbs by author");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_id "
					+ "WHERE reimb_author = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, authorId);
			
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}

			return reimbs;

		} catch (SQLException e) {
			log.debug("connection failed");
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Reimbursement> findByTypeId(int typeId) {

		log.trace("attempting to find reimbs by type");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_id "
					+ "WHERE reimb_type_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, typeId);
			
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}

			return reimbs;

		} catch (SQLException e) {
			log.debug("connection failed");
			// e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Reimbursement> findByStatusId(int statusId) {

		log.trace("attempting to find reimbs by status");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_id "
					+ "WHERE reimb_status_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, statusId);
			
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}

			return reimbs;

		} catch (SQLException e) {
			log.debug("connection failed");
			// e.printStackTrace();
			return null;
		}
	}

}
