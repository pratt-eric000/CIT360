/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.config;

import com.google.gson.Gson;
import com.prt.models.Password;
import com.prt.models.User;
import com.prt.utils.HibernateInstances;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;

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
			String query = "SELECT * FROM USERS WHERE USERNAME = :username";
			Session session = HibernateInstances.getCurrent().getSession();
			List<User> result = session.createQuery(query).setParameter("username", username).list();
			if (result.size() > 0) {
				User user = result.get(0);
				query = "SELECT * FROM PASSWORDS WHERE ID = :pid";
				List<Password> passwords = session.createQuery(query).setParameter("pid", user.getPasswordId()).list();
				Password password = passwords.get(0);
				user.setPassword(password.getPassword());
				user.setSalt(password.getSalt());
				return gson.toJson(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
