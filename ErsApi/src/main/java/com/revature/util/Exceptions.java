package com.revature.util;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Exceptions {
	
	private static Logger log = LogManager.getRootLogger();
	
	public static void logSQLException(SQLException e) {
		log.warn("SQL Message: {}", e.getMessage());
		log.warn("Error Code: {}", e.getErrorCode());
		log.warn("SQL State: {}", e.getSQLState());
		log.warn("Stack Trace: ", e);
	}
	
	public static void generalException(Exception e) {
		log.warn("Stack Trace: {}", e);
	}
};
