/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.prt.models.Globals;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author P-ratt
 */
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig fc) throws ServletException {
		//do nothing yet
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		Globals userManager = (session != null) ? (Globals) session.getAttribute("globals") : null;
		if (userManager == null || !userManager.loggedin) {
			res.sendRedirect(req.getContextPath() + "/login.xhtml");
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		//do nothing yet
	}

}
