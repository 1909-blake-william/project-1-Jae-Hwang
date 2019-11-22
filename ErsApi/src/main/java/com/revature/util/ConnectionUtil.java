package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {

	private Logger log = LogManager.getRootLogger();

	public static final ConnectionUtil instance = new ConnectionUtil();

	private Connection c;

	private ConnectionUtil() {
		super();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		int totalTimeout = 0;
		try {
			log.trace("Checking connection.");
			while (!c.isValid(2000)) {
				log.debug("Disconnected, attempting to reconnect");
				setConnection();
				totalTimeout += 2000;
				if (totalTimeout >= 10000) {
					log.debug("Reconnection failed");
					return null;
				}
				log.debug("Reconnect successful");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			log.trace("Connection not initialized, initializing connection.");
			setConnection(); // if connection not initialized, initialize.
		}
		log.trace("Connection successfully retrieved.");
		return c;
	}
	
	public void setConnection() { // initialize connection.
		log.trace("Trying to connect to the DB");
		try {
			log.trace("Successfully connected to the DB");
			this.c = connect();
			this.c.setAutoCommit(false);
		} catch (SQLException e) {
			log.debug("Unable to connect the the DB");
			e.printStackTrace();
		}
	}

	public static Connection connect() throws SQLException {
		String url = System.getenv("ERS_DB_URL");
		String username = System.getenv("ERS_DB_USERNAME");
		String password = System.getenv("ERS_DB_PASSWORD");
		return DriverManager.getConnection(url, username, password);
	}
	
}
