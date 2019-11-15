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
import com.revature.models.User;
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
		super.service(req, resp);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("uri = " + req.getRequestURI());

		List<Reimbursement> reimbs;

		String username = req.getParameter("username");
		String strId = req.getParameter("id");
		String strStatus = req.getParameter("status");
		String strType = req.getParameter("type");

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

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("Reimbursement doPost");
		System.out.println("uri = " + req.getRequestURI());
		System.out.println("url = " + req.getRequestURL());
		
		String author = req.getParameter("author");
		String amountStr = req.getParameter("amount");
		double amount = Double.parseDouble(amountStr);
		String type = req.getParameter("type");
		String desc = req.getParameter("desc");
		
		if (author == null || amountStr == null || type == null || desc == null) {
			resp.setStatus(422);
			return;
		}
		System.out.println(amountStr + ", " + author + ", " + type + ", " + desc);
		System.out.println("Handling Post Request");
		boolean result = reimbDao.save(amount, author, type, desc);
		System.out.println(result);
		
		if (result) {
			resp.setStatus(201);	
		} else {
			resp.setStatus(400);
		}
		
	}
}
