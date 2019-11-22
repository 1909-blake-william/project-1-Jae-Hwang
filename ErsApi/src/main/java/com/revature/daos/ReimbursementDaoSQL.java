package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementDaoSQL implements ReimbursementDao {

	private Logger log = LogManager.getRootLogger();
	private ConnectionUtil connectionUtil = ConnectionUtil.instance;

// @formatter:off
	// Window > Pref > Java > Code Style > Formatter > Edit > On/Off Tags
	
	// SQL statement using so many joins
	/*
	private static final String SELECT_REIMB = 
			"SELECT reimb_id," 
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
	*/
	
	// SQL statement using so many joins AND Pagination
	// PLEASE REMEMBER TO SET 2 VALUES
	
	private static final String SELECT_COLUMNS = 
								"SELECT"
								+ " reimb_id,"
							    + " reimb_amount,"
							    + " reimb_submitted,"
							    + " reimb_resolved,"
							    + " reimb_description,"
							    + " auth.ers_username author,"
							    + " res.ers_username resolver,"
							    + " reimb_type,"
							    + " reimb_status";
	private static final String SELECT_FROM = " FROM ers_reimbursement";
	
	private static final String SELECT_JOINS =
							  " JOIN ers_users auth ON reimb_author = auth.ers_user_id"
							+ " FULL JOIN ers_users res ON reimb_resolver = res.ers_user_id"
							+ " JOIN ers_reimbursement_status status USING (reimb_status_id)"
							+ " JOIN ers_reimbursement_type type USING (reimb_type_id)";
	
	private static final String SELECT_FROM_PAGE_FIND_ALL =
							" FROM"     
							+ " (SELECT reimb.*,row_number()"
							+ " over (ORDER BY reimb.reimb_id ASC) line_number"
							+ " FROM ers_reimbursement reimb)";
	
	private static final String SELECT_FROM_PAGE_BY_AUTHOR =
							" FROM"     
							+ " (SELECT reimb.*,row_number()"
							+ " over (ORDER BY reimb.reimb_id ASC) line_number"
							+ " FROM ers_reimbursement reimb"
	  						+ " JOIN ers_users auth ON reimb_author = auth.ers_user_id"
							+ " WHERE auth.ers_username = ?)";
	
	private static final String SELECT_FROM_PAGE_END = 
							  " WHERE line_number BETWEEN ? AND ?  ORDER BY line_number";
// @formatter:on

	private Reimbursement extractReimbursement(ResultSet rs) throws SQLException {
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

			String sql = SELECT_COLUMNS + SELECT_FROM + SELECT_JOINS + " ORDER BY reimb_id";

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
	public List<Reimbursement> findAllPag(int page) {

		log.trace("attempting to find all reimbs WITH PAGE! : " + page);
		try {
			Connection c = connectionUtil.getConnection();

			String sql = SELECT_COLUMNS + SELECT_FROM_PAGE_FIND_ALL + SELECT_JOINS + SELECT_FROM_PAGE_END;

			PreparedStatement ps = c.prepareStatement(sql);
			int rows = page * 10;
			ps.setInt(1, rows - 9);
			ps.setInt(2, rows);

			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<Reimbursement>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}

			return reimbs;

		} catch (SQLException e) {
			log.debug("connection failed");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Reimbursement findById(int reimbId) {

		log.trace("attempting to find a reimb by id");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = SELECT_COLUMNS + SELECT_FROM + SELECT_JOINS 
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

			String sql = SELECT_COLUMNS + SELECT_FROM + SELECT_JOINS 
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

			String sql = SELECT_COLUMNS + SELECT_FROM + SELECT_JOINS 
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
	public List<Reimbursement> findByAuthorPag(String author, int page) {
		try {
			Connection c = connectionUtil.getConnection();

			int rows = page * 10;
			
			String sql = SELECT_COLUMNS + SELECT_FROM_PAGE_BY_AUTHOR + SELECT_JOINS + SELECT_FROM_PAGE_END;
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, author);
			ps.setInt(2, rows - 9);
			ps.setInt(3, rows);

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

			String sql = SELECT_COLUMNS + SELECT_FROM + SELECT_JOINS 
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

			String sql = SELECT_COLUMNS + SELECT_FROM + SELECT_JOINS 
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
	public boolean update(int reimbId, int statusId, int resolver) {
//		UPDATE ers_reimbursement SET
//		reimb_resolved = CURRENT_TIMESTAMP,
//		reimb_resolver = 2,
//		reimb_status_id = 4
//		WHERE reimb_id = 2;
		log.trace("attempting to update reimbs by status");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "UPDATE ers_reimbursement SET" + " reimb_resolved = CURRENT_TIMESTAMP,"
					+ " reimb_resolver = ?," + " reimb_status_id = ?" + " WHERE reimb_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, resolver);
			ps.setInt(2, statusId);
			ps.setInt(3, reimbId);

			ps.executeQuery();
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

}
