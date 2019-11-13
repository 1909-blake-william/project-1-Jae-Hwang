package com.revature.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtil {
	public static final ObjectUtil instance = new ObjectUtil();
	
	private ObjectMapper om;
	
	private ObjectUtil() {
		om = new ObjectMapper();
	};
	
	public ObjectMapper getOm() {
		if (om == null) {
			om = new ObjectMapper();
		}
		return om;
	}
}
