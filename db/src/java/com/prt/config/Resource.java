/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.config;

import com.google.gson.Gson;
import com.prt.utils.DBConfig;
import com.prt.utils.DBProcess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author P-ratt
 */
@Path("/repository")
public class Resource {

	public Resource() {
	}

	@Path("select/user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postSelectUserCredentials(String supplied) {
		try {
			Gson gson = new Gson();
			String username = gson.fromJson(supplied, String.class);
			Connection conn = DBConfig.getCurrent().getConnection();
			if (conn != null) {
				String query = "SELECT U.USERNAME, P.PASSWORD, P.SALT FROM USERS U JOIN PASSWORD P ON U.PASSID = P.ID WHERE U.USERNAME = ?";
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setString(1, username);
				ResultSet set = stmt.executeQuery();
				ArrayList<String[]> resultList = new ArrayList<>();
				while (set.next()) {
					resultList.add(new String[]{"USERNAME", set.getString("USERNAME")});
					resultList.add(new String[]{"PASSWORD", set.getString("PASSWORD")});
					resultList.add(new String[]{"SALT", set.getString("SALT")});
				}
				set.close();
				stmt.close();
				return gson.toJson(resultList.toArray(new String[resultList.size()][]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Path("select/users")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postSelectUsers() {
		try {
			Gson gson = new Gson();
			Connection conn = DBConfig.getCurrent().getConnection();
			if (conn != null) {
				Statement stmt = conn.createStatement();
				ResultSet set = stmt.executeQuery("SELECT * FROM USERS");
				ArrayList<String[][]> allUsers = new ArrayList<>();
				while (set.next()) {
					ArrayList<String[]> resultList = new ArrayList<>();
					resultList.add(new String[]{"FIRSTNAME", set.getString("FIRSTNAME")});
					resultList.add(new String[]{"LASTNAME", set.getString("LASTNAME")});
					resultList.add(new String[]{"USERNAME", set.getString("USERNAME")});
					resultList.add(new String[]{"ADMIN", set.getString("ADMIN")});
					allUsers.add(resultList.toArray(new String[resultList.size()][]));
				}
				set.close();
				stmt.close();
				return gson.toJson(allUsers.toArray(new String[allUsers.size()][][]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Path("user/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postAddNewUser(String content) {
		Gson gson = new Gson();
		try {
			String[] params = gson.fromJson(content, String[].class);
			DBProcess.DoAddUser(params);
			return gson.toJson(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(false);
	}

	@Path("database/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postAddNewDatabases(String content) {
		Gson gson = new Gson();
		try {
			String[][][][] params = gson.fromJson(content, String[][][][].class);
			DBProcess.DoAddDatabases(params);
			return gson.toJson(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(false);
	}

	@Path("databases")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postGetDatabases() {
		Gson gson = new Gson();
		try {
			return gson.toJson(DBProcess.DoGetDatabases());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(false);
	}

	@Path("database/tables")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postGetDatabaseTables(String contents) {
		Gson gson = new Gson();
		try {
			String id = gson.fromJson(contents, String.class);
			return gson.toJson(DBProcess.DoGetDatabaseTables(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(false);
	}

	@Path("table/columns")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postGetTableColumns(String contents) {
		Gson gson = new Gson();
		try {
			String id = gson.fromJson(contents, String.class);
			return gson.toJson(DBProcess.DoGetTableColumns(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(false);
	}

}
