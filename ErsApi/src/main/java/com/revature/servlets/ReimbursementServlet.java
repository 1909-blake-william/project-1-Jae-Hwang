package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.util.ObjectUtil;

public class ReimbursementServlet extends HttpServlet {
	
	private ReimbursementDao reimbDao = ReimbursementDao.currentImplementation;
	private ObjectMapper om = ObjectUtil.instance.getOm();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getRequestURL());
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json");
		// TODO Auto-generated method stub
		super.service(req, resp);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("uri = " + req.getRequestURI());
		
		List<Reimbursement> reimbs;
		
		String username = req.getParameter("username");
		String strId = req.getParameter("id");
		
		if (username != null) {
			reimbs = reimbDao.findByAuthor(username);
		} else if (strId != null) {
			reimbs = new ArrayList<>();
			int reimbId = Integer.valueOf(strId);
			reimbs.add(reimbDao.findById(reimbId));
		} else {
			reimbs = reimbDao.findAll();
		}
		
		String json = om.writeValueAsString(reimbs);

		resp.addHeader("content-type", "application/json");
		resp.getWriter().write(json);
	}
}
