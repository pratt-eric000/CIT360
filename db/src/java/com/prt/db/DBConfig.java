/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author P-ratt
 */
public class DBConfig {

	public static Connection getNewConnection() throws Exception {
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		Class.forName(driver);
		String url = "jdbc:drby:mainDB;create=true";
		Connection c = DriverManager.getConnection(url);
		return c;
	}
}
