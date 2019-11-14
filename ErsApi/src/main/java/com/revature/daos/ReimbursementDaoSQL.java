package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementDaoSQL implements ReimbursementDao {

	private Logger log = Logger.getRootLogger();
	private ConnectionUtil connectionUtil = ConnectionUtil.instance;
	
	// SQL statement using so many joins
	private String selectReimb = "SELECT reimb_id,"
									+ " reimb_amount," 
									+ " reimb_submitted,"
									+ " reimb_resolved,"
									+ " reimb_description,"
									+ " auth.ers_username author,"
									+ " res.ers_username resolver,"
									+ " reimb_type,"
									+ " reimb_status"
								+ " FROM ers_reimbursement"
								+ " JOIN ers_users auth ON reimb_author = auth.ers_user_id"
								+ " FULL JOIN ers_users res ON reimb_resolver = res.ers_user_id"
								+ " JOIN ers_reimbursement_status status USING (reimb_status_id)"
								+ " JOIN ers_reimbursement_type type USING (reimb_type_id)";

	private Reimbursement extractReimbursement (ResultSet rs) throws SQLException {
		int id = rs.getInt("reimb_id");
		double amount = rs.getDouble("reimb_amount");
		Timestamp submitted = rs.getTimestamp("reimb_submitted");
		Timestamp resolved = rs.getTimestamp("reimb_resolved");
		String desc = rs.getString("reimb_description");
		String author = rs.getString("author");
		String resolver = rs.getString("resolver");
		String status = rs.getString("reimb_status");
		String type = rs.getString("reimb_type");
		Reimbursement result = new Reimbursement(id, amount, submitted, resolved, desc, author, resolver, status, type);
		return result;
	}
	
	@Override
	public boolean save(Reimbursement reimb) {
		try {
			Connection c = connectionUtil.getConnection();

			CallableStatement cs = c.prepareCall("CALL regist_reimbursement(?, ?, ?)");
			cs.setDouble(1, reimb.getReimbAmount());
			cs.setString(2, reimb.getReimbAuthor());
			cs.setString(3, reimb.getReimbType());
			
			boolean result = cs.execute();
			
			c.commit();
			
			return result;
			
		} catch (SQLException e) {
			log.debug("Request Failed");
			e.printStackTrace();
			
			try {
				log.debug("Attempting to rollback");
				connectionUtil.getConnection().rollback();
			} catch (SQLException e1) {
				log.debug("Connection failed");
				e1.printStackTrace();
			}
			return false;
		}
	}
	
	@Override
	public boolean save(double amount, String username, String type, String desc) {
		try {
			Connection c = connectionUtil.getConnection();

			CallableStatement cs = c.prepareCall("CALL regist_reimbursement(?, ?, ?, ?)");
			cs.setDouble(1, amount);
			cs.setString(2, username);
			cs.setString(3, type);
			cs.setString(4, desc);
			
			cs.execute();
			
			c.commit();
			
			return true;
			
		} catch (SQLException e) {
			log.debug("Request Failed");
			e.printStackTrace();
			
			try {
				log.debug("Attempting to rollback");
				connectionUtil.getConnection().rollback();
			} catch (SQLException e1) {
				log.debug("Connection failed");
				e1.printStackTrace();
			}
			
			return false;
		}
	}

	@Override
	public List<Reimbursement> findAll() {

		log.trace("attempting to find all reimbs");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = selectReimb
					+ " ORDER BY reimb_id";
			
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

			String sql = selectReimb
					+ " WHERE reimb_id = ? ORDER BY reimb_id";
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

			String sql = selectReimb
					+ " WHERE reimb_author = ? ORDER BY reimb_id";
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
	
	public List<Reimbursement> findByAuthor(String author) {
		try {
			Connection c = connectionUtil.getConnection();

			String sql = selectReimb
					+ " WHERE auth.ers_username = ? ORDER BY reimb_id";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, author);
			
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

			String sql = selectReimb
					+ " WHERE reimb_type_id = ? ORDER BY reimb_id";
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

			String sql = selectReimb
					+ " WHERE reimb_status_id = ? ORDER BY reimb_id";
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

	@Override
	public void update(int userId, int statusId, int resolver) {
//		UPDATE ers_reimbursement SET
//		reimb_resolved = CURRENT_TIMESTAMP,
//		reimb_resolver = 2,
//		reimb_status_id = 4
//		WHERE reimb_id = 2;
		log.trace("attempting to find reimbs by status");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "UPDATE ers_reimbursement SET"
					+ " reimb_resolved = CURRENT_TIMESTAMP,"
					+ " reimb_resolver = 2,";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, statusId);
			
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}


		} catch (SQLException e) {
			log.debug("connection failed");
			// e.printStackTrace();
		}
	}

}
