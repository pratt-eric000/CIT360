/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

/**
 *
 * @author P-ratt
 */
public class DBConfig {

	private Connection connection = null;
	private ArrayList<String> tables = new ArrayList<>();

	public Connection getConnection() throws Exception {
		if (connection == null) {
			connection = getNewConnection();
		}
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public ArrayList<String> getTables() throws Exception {
		if (tables.isEmpty()) {
			if (connection == null) {
				getNewConnection();
			}
			Statement stmt = connection.createStatement();
			ResultSet set = stmt.executeQuery("SELECT * FROM SYS.SYSTABLES");
			while (set.next()) {
				tables.add(set.getString("TABLENAME"));
			}
			set.close();
			stmt.close();
		}
		return tables;
	}

	private Connection getNewConnection() throws Exception {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection c = DriverManager.getConnection("jdbc:derby:mainDB;create=true;user=app;password=derby");
		//check basic tables to see that they exist
		try {
			String checkExisting = "SELECT * FROM SYS.SYSTABLES";
			Statement stmnt = c.createStatement();
			ResultSet rset = stmnt.executeQuery(checkExisting);
			tables = new ArrayList<>();
			while (rset.next()) {
				tables.add(rset.getString("TABLENAME"));
			}
			System.out.println(tables);
			rset.close();
			stmnt.close();
			if (!tables.contains("USERS")) {
				String query = "CREATE TABLE USERS ("
						+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
						+ "FIRSTNAME VARCHAR(1024) NOT NULL, "
						+ "LASTNAME VARCHAR(1024), "
						+ "USERNAME VARCHAR(1024) NOT NULL, "
						+ "PASSID INTEGER NOT NULL, "
						+ "ADMIN INTEGER DEFAULT 0) ";
				Statement stmt = c.createStatement();
				stmt.execute(query);
				stmt.close();
			}
			if (!tables.contains("PASSWORD")) {
				Statement stmt = c.createStatement();
				String query = "CREATE TABLE PASSWORD("
						+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
						+ "PASSWORD VARCHAR(1024) NOT NULL, "
						+ "SALT VARCHAR(1024) NOT NULL)";
				stmt.execute(query);
				stmt.close();
			}

			String query = "SELECT * FROM USERS";
			Statement stmt = c.createStatement();
			ResultSet set = stmt.executeQuery(query);
			boolean found = false;
			while (set.next()) {
				found = true;
				break;
			}
			if (!found) {
				//add a default user if no user is found
				byte[] salt = generateSalt();
				String saltStr = new String(Base64.getEncoder().encode(salt), "UTF-8");
				String admin = EncryptionHelper.encrypt("admin", salt);

				DBProcess.DoAddUser(c, new String[]{admin, saltStr, "admin", "", "admin", "1"});
			} else {
				query = "UPDATE USERS SET ADMIN=1 WHERE USERNAME='admin'";
				stmt.execute(query);
			}
			set.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.commit();
		return c;
	}

	private byte[] generateSalt() {
		byte[] salt = new byte[16];
		Random random = new Random();
		random.nextBytes(salt);
		return salt;
	}

	private DBConfig() {
	}

	public static DBConfig getCurrent() {
		return InstanceHolder.INSTANCE;
	}

	private static class InstanceHolder {

		private static final DBConfig INSTANCE = new DBConfig();
	}
}
