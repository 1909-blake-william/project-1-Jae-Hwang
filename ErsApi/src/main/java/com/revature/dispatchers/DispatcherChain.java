package com.revature.dispatchers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DispatcherChain implements Dispatcher, Registry {
	
	private static Logger log = LogManager.getRootLogger();
	private static ReimbursementDispatcher reimbDispatcher = ReimbursementDispatcher.getInstance();

	private static List<Dispatcher> registry = new ArrayList<>();
	
	private static final DispatcherChain instance = new DispatcherChain();

	private DispatcherChain() {
		register(reimbDispatcher);
	}
	
	public static DispatcherChain getInstance() {
		return instance;
	}

	@Override
	public void register(Dispatcher dispatcher) {
		registry.add(dispatcher);
	}

	@Override
	public boolean support(HttpServletRequest req) {
		log.trace("URI : " + req.getRequestURI());
		log.trace("Method : " + req.getMethod());
		log.trace("Number of Registered Dispatchers: " + registry.size());
		
		for (Dispatcher dispatcher : registry) {
			if (dispatcher.support(req)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		for (Dispatcher dispatcher : registry) {
			if (dispatcher.support(req)) {
				dispatcher.execute(req, resp);
				return;
			}
		}
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}
}
