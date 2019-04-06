/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.config;

import com.google.gson.Gson;
import com.prt.models.Password;
import com.prt.models.Role;
import com.prt.models.User;
import com.prt.models.UserRoleXref;
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
			String query = "from User where username = :username";
			Session session = HibernateInstances.getCurrent().getSession();
			List<User> result = (List<User>) session.createQuery(query).setParameter("username", username).list();
			if (result.size() > 0) {
				User user = result.get(0);
				query = "from Password where id = :pid";
				List<Password> passwords = (List<Password>) session.createQuery(query).setParameter("pid", user.getPasswordId()).list();
				Password password = passwords.get(0);
				user.setPassword(password.getPassword());
				user.setSalt(password.getSalt());
				UserRoleXref urx = ((List<UserRoleXref>) session.createQuery("from UserRoleXref where userId = :uid").setParameter("uid", user.getId()).list()).get(0);
				Role role = ((List<Role>) session.createQuery("from Role where id = :id").setParameter("id", urx.getRoleId()).list()).get(0);
				user.setRole(role.getName());
				user.setRoleId(role.getId());
				return gson.toJson(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
