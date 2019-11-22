package com.revature.util;

import java.io.InputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
	
	private static final ObjectMapper om = ObjectUtil.instance.getOm();
	
	public static byte[] write(Object o) {
		try {
			return om.writeValueAsBytes(o);
		} catch (JsonProcessingException e) {
			Exceptions.generalException(e);
		}
		return null;
	}
	
	public static Object read(InputStream is, Class<?> clazz) {
		try {
			return om.readValue(is, clazz);
		} catch (Exception e) {
			Exceptions.generalException(e);
		}
		return null;
	}
}
