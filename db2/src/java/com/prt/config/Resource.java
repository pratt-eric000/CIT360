/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.config;

import com.google.gson.Gson;
import com.prt.utils.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
				String[] results = new String[3];
				while (set.next()) {
					results[0] = set.getString("USERNAME");
					results[1] = set.getString("PASSWORD");
					results[2] = set.getString("SALT");
				}
				set.close();
				stmt.close();
				return gson.toJson(results);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
