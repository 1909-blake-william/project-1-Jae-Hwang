package com.revature.dispatchers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Dispatcher {

	boolean support(HttpServletRequest req);
	
	void execute(HttpServletRequest req, HttpServletResponse resp);
}
