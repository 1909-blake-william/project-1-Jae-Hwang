package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.UserDao;
import com.revature.models.User;
import com.revature.util.ObjectUtil;

public class UserServlet extends HttpServlet {
	
	private UserDao userDao = UserDao.currentImplementation;
	private ObjectMapper om = ObjectUtil.instance.getOm();
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("/ERSProject/users".equals(req.getRequestURI())) {
			List<User> users;
			
			users = userDao.findAll();
			
			String json = om.writeValueAsString(users);

			resp.addHeader("content-type", "application/json");
			resp.getWriter().write(json);
		}
	}
}
