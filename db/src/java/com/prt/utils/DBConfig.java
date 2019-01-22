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

	private Connection getNewConnection() throws Exception {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection c = DriverManager.getConnection("jdbc:derby:mainDB;create=true;user=app;password=derby");
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
				insert.execute();
				query = "SELECT ID FROM PASSWORD";
				set = stmt.executeQuery(query);
				String id = null;
				while (set.next()) {
					id = set.getString("ID");
				}
				if (id != null) {
					query = "INSERT INTO USERS (FIRSTNAME, USERNAME, PASSID, ADMIN) VALUES (?, ?, ?, 0)";
					insert = c.prepareStatement(query);
					insert.setString(1, "admin");
					insert.setString(2, "admin");
					insert.setString(3, id);
					insert.execute();
				}
				insert.close();
				stmt.close();
			} else {
				query = "UPDATE USERS SET ADMIN=1 WHERE FIRSTNAME='Admin'";
				stmt.execute(query);
			}
			set.close();
			stmt.close();
		} catch (Exception e) {
			//create table if no table is found
			String query = "CREATE TABLE USERS ("
					+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
					+ "FIRSTNAME VARCHAR(1024) NOT NULL, "
					+ "LASTNAME VARCHAR(1024), "
					+ "USERNAME VARCHAR(1024) NOT NULL, "
					+ "PASSID INTEGER NOT NULL, "
					+ "ADMIN INTEGER DEFAULT 0) ";
			Statement stmt = c.createStatement();
			stmt.execute(query);
			query = "CREATE TABLE PASSWORD("
					+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
					+ "PASSWORD VARCHAR(1024) NOT NULL, "
					+ "SALT VARCHAR(1024) NOT NULL)";
			stmt.execute(query);

			byte[] salt = new byte[16];
			Random random = new Random();
			random.nextBytes(salt);
			String saltStr = new String(Base64.getEncoder().encode(salt), "UTF-8");
			String admin = EncryptionHelper.encrypt("admin", salt);

			query = "INSERT INTO PASSWORD (PASSWORD, SALT) VALUES (?, ?)";
			PreparedStatement insert = c.prepareStatement(query);
			insert.setString(1, admin);
			insert.setString(2, saltStr);
			insert.execute();
			query = "SELECT ID FROM PASSWORD";
			ResultSet set = stmt.executeQuery(query);
			String id = null;
			while (set.next()) {
				id = set.getString("ID");
			}
			if (id != null) {
				query = "INSERT INTO USERS (FIRSTNAME, USERNAME, PASSID, ADMIN) VALUES (?, ?, ?, 1)";
				insert = c.prepareStatement(query);
				insert.setString(1, "admin");
				insert.setString(2, "admin");
				insert.setString(3, id);
				insert.execute();
			}
			insert.close();
			stmt.close();
		}
		c.commit();
		return c;
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
