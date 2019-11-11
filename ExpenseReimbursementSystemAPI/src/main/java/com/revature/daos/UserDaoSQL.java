package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDaoSQL implements UserDao {

	private Logger log = Logger.getRootLogger();
	ConnectionUtil connectionUtil = ConnectionUtil.instance;

	User extractUser(ResultSet rs) throws SQLException {
		int id = rs.getInt("ers_user_id");
		String rsUsername = rs.getString("ers_username");
		String rsPassword = rs.getString("ers_password");
		String rsEmail = rs.getString("user_email");
		int rsRole = rs.getInt("user_role_id");
		return new User(id, rsUsername, rsPassword, rsEmail, rsRole);
	}

	@Override
	public int save(User u) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> findAll() {

		log.trace("attempting to find all reimbs");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "SELECT * FROM ers_users ORDER BY ers_user_id";

			PreparedStatement ps = c.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> users= new ArrayList<User>();
			while (rs.next()) {
				users.add(extractUser(rs));
			}

			return users;

		} catch (SQLException e) {
			log.debug("connection failed");
			// e.printStackTrace();
			return null;
		}

	}

	@Override
	public User findById(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		log.trace("attempting to find user credentials");
		try {
			Connection c = connectionUtil.getConnection();

			String sql = "SELECT * FROM ers_users " + "WHERE ers_username = ? AND ers_password = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				User newUser = extractUser(rs);
				return newUser;
			} else {
				return null;
			}

		} catch (Exception e) {

			log.debug("connection failed");

		}
		return null;
	}
}
