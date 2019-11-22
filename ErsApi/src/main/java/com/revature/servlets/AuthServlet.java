package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.UserDao;
import com.revature.models.User;
import com.revature.util.ObjectUtil;

public class AuthServlet extends HttpServlet {

	private UserDao userDao = UserDao.currentImplementation;
	private ObjectMapper om = ObjectUtil.instance.getOm();
	private Logger log = LogManager.getRootLogger();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("uri = " + req.getRequestURI());

		if ("/ERSProject/auth/login".equals(req.getRequestURI())) {
			User credentials = (User) om.readValue(req.getReader(), User.class);
			User loggedInUser = userDao.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());

			if (loggedInUser == null) {
				log.trace("User Credential does not match. 401");
				resp.setStatus(401); // Unauthorized status code
				return;
			} else {
				log.trace("User Credential match, creating and sending back the Session. 201");
				resp.setStatus(201);
				req.getSession().setAttribute("user", loggedInUser);
				resp.getWriter().write(om.writeValueAsString(loggedInUser));
				return;
			}
		} else if ("/ERSProject/auth/logout".equals(req.getRequestURI())) {
			resp.setStatus(202);
			log.trace("Attempting to setting user on session to null. 202 [Does not work]");
			HttpSession session = req.getSession();
			session.setAttribute("user", null);
			session.invalidate();
			return;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if ("/ERSProject/auth/session-user".equals(req.getRequestURI())) {
			log.trace("Sending current user Session 200");
			resp.setStatus(200);
			String json = om.writeValueAsString(req.getSession().getAttribute("user"));
			resp.getWriter().write(json);
		}
	}
}
