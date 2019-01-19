/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.Random;

/**
 *
 * @author P-ratt
 */
public class DBConfig {

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String URL = "jdbc:drby:mainDB;create=true;user=mainuser;password=1qaz!QAZ2wsx@WSX";
	private Connection connection = null;

	public Connection getConnection() throws Exception {
		if (connection == null) {
			connection = getNewConnection();
		}
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public DBConfig() {
	}

	private static Connection getNewConnection() throws Exception {
		Class.forName(DRIVER);
		Connection c = DriverManager.getConnection(URL);
		//check basic tables to see that they exist
		try {
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
				byte[] salt = new byte[16];
				Random random = new Random();
				random.nextBytes(salt);
				String saltStr = new String(Base64.getEncoder().encode(salt), "UTF-8");
				String admin = EncryptionHelper.encrypt("admin", salt);
				query = "INSERT INTO PASSWORD (PASSWORD, SALT) VALUES (?, ?)";
				PreparedStatement insert = c.prepareStatement(query);
				insert.setString(1, admin);
				insert.setString(2, saltStr);
				insert.executeQuery();
				query = "SELECT ID FROM PASSWORD";
				set = stmt.executeQuery(query);
				String id = null;
				while (set.next()) {
					id = set.getString("ID");
				}
				if (id != null) {
					query = "INSERT INTO USERS (FIRSTNAME, USERNAME, PASSID) VALUES (?, ?, ?)";
					insert = c.prepareStatement(query);
					insert.setString(1, "admin");
					insert.setString(2, "admin");
					insert.setString(3, id);
					insert.executeQuery();
				}
				insert.close();
				stmt.close();
			}
			set.close();
			stmt.close();
		} catch (Exception e) {
			//create table if no table is found
			String query = "CREATE TABLE USERS("
					+ "ID INT NOT NULL GENERATED (START WITH 1, INCREMENT BY 1) PRIMARY KEY, "
					+ "FIRSTNAME VARCHAR(1024) NOT NULL, "
					+ "LASTNAME VARCHAR(1024), "
					+ "USERNAME VARCHAR(1024) NOT NULL, "
					+ "PASSID INT NOT NULL)";
			Statement stmt = c.createStatement();
			stmt.executeQuery(query);
			query = "CREATE TABLE PASSWORD("
					+ "ID INT NOT NULL GENERATED (START WITH 1, INCREMENT BY 1) PRIMARY KEY, "
					+ "PASSWORD VARCHAR(1024) NOT NULL, "
					+ "SALT VARCHAR(1024) NOT NULL)";
			stmt.executeQuery(query);

			byte[] salt = new byte[16];
			Random random = new Random();
			random.nextBytes(salt);
			String saltStr = new String(Base64.getEncoder().encode(salt), "UTF-8");
			String admin = EncryptionHelper.encrypt("admin", salt);

			query = "INSERT INTO PASSWORD (PASSWORD, SALT) VALUES (?, ?)";
			PreparedStatement insert = c.prepareStatement(query);
			insert.setString(1, admin);
			insert.setString(2, saltStr);
			insert.executeQuery();
			query = "SELECT ID FROM PASSWORD";
			ResultSet set = stmt.executeQuery(query);
			String id = null;
			while (set.next()) {
				id = set.getString("ID");
			}
			if (id != null) {
				query = "INSERT INTO USERS (FIRSTNAME, USERNAME, PASSID) VALUES (?, ?, ?)";
				insert = c.prepareStatement(query);
				insert.setString(1, "admin");
				insert.setString(2, "admin");
				insert.setString(3, id);
				insert.executeQuery();
			}
			insert.close();
			stmt.close();
		}
		return c;
	}

	public static DBConfig getCurrent() {
		return InstanceHolder.INSTANCE;
	}

	private static class InstanceHolder {

		private static final DBConfig INSTANCE = new DBConfig();
	}
}
