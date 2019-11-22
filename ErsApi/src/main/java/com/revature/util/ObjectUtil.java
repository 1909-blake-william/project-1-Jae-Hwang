package com.revature.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtil {
	public static final ObjectUtil instance = new ObjectUtil();
	
	private ObjectMapper om;
	
	private ObjectUtil() { };
	
	public ObjectMapper getOm() {
		if (om == null) {
			om = new ObjectMapper();
		}
		return om;
	}
}
