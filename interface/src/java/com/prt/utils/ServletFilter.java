/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author P-ratt
 */
public class ServletFilter extends HttpServlet {

	public void filter(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Test");
	}
}
