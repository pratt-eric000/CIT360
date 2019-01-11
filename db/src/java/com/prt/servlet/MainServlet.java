/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author P-ratt
 */
public class MainServlet extends HttpServlet {

	private Connection conn;

	@Override
	public void init() throws ServletException {
		InitialContext cxt = null;
		DataSource ds = null;
		try {
			cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/mainDB");
		} catch (NamingException e) {
		}
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Statement stmnt;
		try {
			stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("SELECT * FROM test_table");
		} catch (SQLException ex) {
			Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
