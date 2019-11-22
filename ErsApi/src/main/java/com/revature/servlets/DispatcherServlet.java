package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dispatchers.DispatcherChain;

public class DispatcherServlet extends HttpServlet {

	private static final DispatcherChain dispatcher = DispatcherChain.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (dispatcher.support(req)) {
			dispatcher.execute(req, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (dispatcher.support(req)) {
			dispatcher.execute(req, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (dispatcher.support(req)) {
			dispatcher.execute(req, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}
}
