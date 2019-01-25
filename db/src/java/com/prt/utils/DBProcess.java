/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author P-ratt
 */
public class DBProcess {

	public static void DoAddUser(String[] params) throws Exception {
		Connection conn = DBConfig.getCurrent().getConnection();
		if (conn != null) {
			DoAddUser(conn, params);
		}
	}

	public static void DoAddUser(Connection c, String[] params) {
		try {
			String query = "INSERT INTO PASSWORD (PASSWORD, SALT) VALUES (?, ?)";
			PreparedStatement insert = c.prepareStatement(query);
			insert.setString(1, params[0]);
			insert.setString(2, params[1]);
			insert.execute();
			query = "SELECT ID FROM PASSWORD WHERE PASSWORD = ?";
			PreparedStatement stmt = c.prepareStatement(query);
			stmt.setString(1, params[0]);
			ResultSet set = stmt.executeQuery();
			String id = null;
			while (set.next()) {
				id = set.getString("ID");
			}
			if (id != null) {
				query = "INSERT INTO USERS (FIRSTNAME, LASTNAME, USERNAME, PASSID, ADMIN) VALUES (?, ?, ?, ?, ?)";
				insert = c.prepareStatement(query);
				insert.setString(1, params[2]);
				insert.setString(2, params[3]);
				insert.setString(3, params[4]);
				insert.setString(4, id);
				insert.setString(5, params[5]);
				insert.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void DoAddDatabases(String[][][] params) throws Exception {
		Connection conn = DBConfig.getCurrent().getConnection();
		if (conn != null) {

		}
	}
}
