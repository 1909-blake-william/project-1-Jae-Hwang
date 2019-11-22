package com.revature.dispatchers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.util.Exceptions;
import com.revature.util.Json;

public class ReimbursementDispatcher implements Dispatcher {

	private final Logger log = LogManager.getRootLogger();
	private final ReimbursementDao reimbDao = ReimbursementDao.currentImplementation;
	private final DispatcherChain dispatcher = DispatcherChain.getInstance();

	private int selected = 0;

	private static final ReimbursementDispatcher instance = new ReimbursementDispatcher();

	private ReimbursementDispatcher() {
	}

	public static ReimbursementDispatcher getInstance() {
		return instance;
	}

	@Override
	public boolean support(HttpServletRequest req) {
		selected = 0;
		return isFindAll(req) 
				|| isFindByAuthor(req) 
				|| isInsert(req) 
				|| isUpdate(req) 
				|| isFindAllPage(req)
				|| isFindByAuthorPage(req);
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		log.trace("Selected: " + selected);
		switch (selected) {
		case 1:
			handleFindAll(req, resp);
			break;

		case 2:
			handleFindByAuthor(req, resp);
			break;

		case 3:
			handleInsert(req, resp);
			break;

		case 4:
			handleUpdate(req, resp);
			break;

		case 5:
			handleFindAllPage(req, resp);
			break;

		case 6:
			handleFindByAuthorPage(req, resp);
			break;

		default:
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			;
			break;
		}
	}
	
	//
	// -- find all 1
	public boolean isFindAll(HttpServletRequest req) {
		if ("/ERSProject/fc/reimbursements".equals(req.getRequestURI()) && "GET".equals(req.getMethod())
				&& req.getParameter("username") == null
				&& req.getParameter("page") == null) {
			selected = 1;
			log.trace("Request supports Find All");
			return true;
		}
		return false;
	}

	public boolean handleFindAll(HttpServletRequest req, HttpServletResponse resp) {
		log.trace("Handling Find All");
		List<Reimbursement> reimbs;
		reimbs = reimbDao.findAll();
		if (reimbs != null) {
			if (reimbs.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
				return true;
			}
			resp.addHeader("content-type", "application/json");
			try {
				resp.getOutputStream().write(Json.write(reimbs));
				resp.setStatus(HttpServletResponse.SC_OK);
				return true;
			} catch (IOException e) {
				Exceptions.generalException(e);
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
		}
		return false;
	}

	//
	// -- find by Author 2
	public boolean isFindByAuthor(HttpServletRequest req) {
		if ("/ERSProject/fc/reimbursements".equals(req.getRequestURI()) && "GET".equals(req.getMethod())
				&& req.getParameter("username") != null
				&& req.getParameter("page") == null) {
			selected = 2;
			log.trace("Request supports Find by Author");
			return true;
		}
		return false;
	}

	public boolean handleFindByAuthor(HttpServletRequest req, HttpServletResponse resp) {
		log.trace("Handling Find by Author");
		List<Reimbursement> reimbs;
		String username = req.getParameter("username");

		reimbs = reimbDao.findByAuthor(username);

		if (reimbs != null) {
			if (reimbs.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
				return true;
			}
			resp.addHeader("content-type", "application/json");
			try {
				resp.getOutputStream().write(Json.write(reimbs));
				resp.setStatus(HttpServletResponse.SC_OK);
				return true;
			} catch (IOException e) {
				Exceptions.generalException(e);
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
		}
		return false;
	}

	//
	// -- Insert 3
	public boolean isInsert(HttpServletRequest req) {
		if ("/ERSProject/fc/reimbursements".equals(req.getRequestURI()) && "POST".equals(req.getMethod())) {
			selected = 3;
			log.trace("Request supports Insert");
			return true;
		}
		return false;
	}

	public boolean handleInsert(HttpServletRequest req, HttpServletResponse resp) {
		log.trace("Handling Insert");
		String author = req.getParameter("author");
		String amountStr = req.getParameter("amount");
		double amount = Double.parseDouble(amountStr);
		String type = req.getParameter("type");
		String desc = req.getParameter("desc");

		if (reimbDao.save(amount, author, type, desc)) {
			resp.setStatus(HttpServletResponse.SC_CREATED);
			return true;
		}

		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return false;
	}

	//
	// -- Update 4
	public boolean isUpdate(HttpServletRequest req) {
		if ("/ERSProject/fc/reimbursements".equals(req.getRequestURI()) && "PUT".equals(req.getMethod())
				&& req.getParameter("id") != null && req.getParameter("status") != null
				&& req.getParameter("resolver") != null) {
			selected = 4;
			log.trace("Request supports Update");
			return true;
		}
		return false;
	}

	public boolean handleUpdate(HttpServletRequest req, HttpServletResponse resp) {
		log.trace("Handling Update");
		String idStr = req.getParameter("id");
		int id = Integer.parseInt(idStr);
		String statusStr = req.getParameter("status");
		int status = Integer.parseInt(statusStr);
		String resolverStr = req.getParameter("resolver");
		int resolver = Integer.parseInt(resolverStr);

		if (reimbDao.update(id, status, resolver)) {
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return true;
		}
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return false;
	}
	
	// 
	// find all with pagination 5
	public boolean isFindAllPage(HttpServletRequest req) {
		if ("/ERSProject/fc/reimbursements".equals(req.getRequestURI()) && "GET".equals(req.getMethod())
				&& req.getParameter("username") == null
				&& req.getParameter("page") != null) {
			selected = 5;
			log.trace("Request supports Find All with page");
			return true;
		}
		return false;
	}
	public boolean handleFindAllPage(HttpServletRequest req, HttpServletResponse resp) {
		log.trace("Handling Find All with Pagination");
		List<Reimbursement> reimbs;
		int page = Integer.parseInt(req.getParameter("page"));
		
		reimbs = reimbDao.findAllPag(page);
		if (reimbs != null) {
			if (reimbs.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
				return true;
			}
			resp.addHeader("content-type", "application/json");
			try {
				resp.getOutputStream().write(Json.write(reimbs));
				resp.setStatus(HttpServletResponse.SC_OK);
				return true;
			} catch (IOException e) {
				Exceptions.generalException(e);
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
		}
		return false;
	}
	
	//
	// -- find by Author 6
	public boolean isFindByAuthorPage(HttpServletRequest req) {
		if ("/ERSProject/fc/reimbursements".equals(req.getRequestURI()) && "GET".equals(req.getMethod())
				&& req.getParameter("username") != null
				&& req.getParameter("page") != null) {
			selected = 6;
			log.trace("Request supports Find by Author with page");
			return true;
		}
		return false;
	}

	public boolean handleFindByAuthorPage(HttpServletRequest req, HttpServletResponse resp) {
		log.trace("Handling Find by Author with Page with page");
		List<Reimbursement> reimbs;
		String username = req.getParameter("username");
		int page = Integer.parseInt(req.getParameter("page"));

		reimbs = reimbDao.findByAuthorPag(username, page);

		if (reimbs != null) {
			if (reimbs.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
				return true;
			}
			resp.addHeader("content-type", "application/json");
			try {
				resp.getOutputStream().write(Json.write(reimbs));
				resp.setStatus(HttpServletResponse.SC_OK);
				return true;
			} catch (IOException e) {
				Exceptions.generalException(e);
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
		}
		return false;
	}
}
