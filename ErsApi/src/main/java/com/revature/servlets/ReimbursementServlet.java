package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.util.ObjectUtil;

public class ReimbursementServlet extends HttpServlet {

	private ReimbursementDao reimbDao = ReimbursementDao.currentImplementation;
	private ObjectMapper om = ObjectUtil.instance.getOm();
	private Logger log = LogManager.getRootLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		System.out.println("uri = " + req.getRequestURI());

		List<Reimbursement> reimbs;

		String username = req.getParameter("username");
		String strId = req.getParameter("id");
		String strStatus = req.getParameter("status");
		String strType = req.getParameter("type");

		if (username != null) {
			resp.setStatus(200);
			log.trace("Getting reimbursements under user: " + username + ", 200");
			reimbs = reimbDao.findByAuthor(username);
		} else if (strId != null) {
			resp.setStatus(200);
			log.trace("Getting reimbursement with id: " + strId + ", 200");
			reimbs = new ArrayList<>();
			int reimbId = Integer.valueOf(strId);
			reimbs.add(reimbDao.findById(reimbId));
		} else {
			resp.setStatus(200);
			log.trace("Getting all reimbursements, 200");
			reimbs = reimbDao.findAll();
		}

		String json = om.writeValueAsString(reimbs);

		resp.addHeader("content-type", "application/json");
		resp.getWriter().write(json);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		System.out.println("Reimbursement doPut");
		System.out.println("uri = " + req.getRequestURI());
		System.out.println("url = " + req.getRequestURL());
		
		String idStr = req.getParameter("id");
		int id = Integer.parseInt(idStr);
		String statusStr = req.getParameter("status");
		int status = Integer.parseInt(statusStr);
		String resolverStr = req.getParameter("resolver");
		int resolver = Integer.parseInt(resolverStr);
		
		if (idStr == null || statusStr == null || resolverStr == null) {
			log.trace("Invalid Put request, 422");
			resp.setStatus(422);
			return;
		}
		
		boolean result = reimbDao.update(id, status, resolver);
		
		if (result) {
			log.trace("Update Successful, 204");
			resp.setStatus(204);	
		} else {
			log.trace("Information does not match, 400");
			resp.setStatus(400);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		System.out.println("Reimbursement doPost");
		System.out.println("uri = " + req.getRequestURI());
		System.out.println("url = " + req.getRequestURL());
		
		String author = req.getParameter("author");
		String amountStr = req.getParameter("amount");
		double amount = Double.parseDouble(amountStr);
		String type = req.getParameter("type");
		String desc = req.getParameter("desc");
		
		if (author == null || amountStr == null || type == null || desc == null) {
			log.trace("Invalid Post request, 422");
			resp.setStatus(422);
			return;
		}
		boolean result = reimbDao.save(amount, author, type, desc);
		
		if (result) {
			log.trace("Insert Successful, 201");
			resp.setStatus(201);	
		} else {
			log.trace("Invalid input for Insert, 400");
			resp.setStatus(400);
		}
		
	}
}
