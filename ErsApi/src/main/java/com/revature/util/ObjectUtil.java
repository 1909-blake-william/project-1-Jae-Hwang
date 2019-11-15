package com.revature.util;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtil {
	public static final ObjectUtil instance = new ObjectUtil();
	
	private ObjectMapper om;
	private Logger log;
	
	private ObjectUtil() {
		om = new ObjectMapper();
		log = Logger.getRootLogger();
	};
	
	public ObjectMapper getOm() {
		if (om == null) {
			om = new ObjectMapper();
		}
		return om;
	}
	
	public Logger getLog() {
		if (log == null) {
			log = Logger.getRootLogger();
		}
		return log;
	}
}
