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
import org.hibernate.Query;
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

	@Path("select/users")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postSelectUsers(String supplied) {
		try {
			Gson gson = new Gson();
			Session session = HibernateInstances.getCurrent().getSession();
			List<User> users = session.createCriteria(User.class).list();
			return gson.toJson(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Path("select/roles")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postSelectRoles(String supplied) {
		try {
			Gson gson = new Gson();
			Session session = HibernateInstances.getCurrent().getSession();
			List<Role> users = session.createCriteria(Role.class).list();
			return gson.toJson(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Path("user/edit")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postEditUser(String supplied) {
		Gson gson = new Gson();
		try {
			User user = gson.fromJson(supplied, User.class);
			Session session = HibernateInstances.getCurrent().getSession();
			session.beginTransaction();
			String q = ""
					+ "update User set firstName = :firstname, "
					+ "last_name = :lastname, "
					+ "email = :email, "
					+ "phoneNumber = :phonenumber, "
					+ "username = :username "
					+ "where id = :id";
			Query query = session.createQuery(q);
			query.setParameter("firstname", user.getFirstName());
			query.setParameter("lastname", user.getLastName());
			query.setParameter("email", user.getEmail());
			query.setParameter("phonenumber", user.getPhoneNumber());
			query.setParameter("username", user.getUsername());
			query.setParameter("id", user.getId());
			query.executeUpdate();
			session.createQuery("delete from UserRoleXref where userId = :uid").setParameter("uid", user.getId()).executeUpdate();
			session.save(new UserRoleXref(user.getId(), user.getRoleId()));
			session.getTransaction().commit();
			return gson.toJson("true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson("false");
	}

	@Path("user/delete")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postDeleteUser(String supplied) {
		Gson gson = new Gson();
		try {
			User user = gson.fromJson(supplied, User.class);
			Session session = HibernateInstances.getCurrent().getSession();
			session.beginTransaction();
			session.delete(user);
			session.getTransaction().commit();
			return gson.toJson("true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson("false");
	}

	@Path("user/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postAddUser(String supplied) {
		Gson gson = new Gson();
		try {
			User user = gson.fromJson(supplied, User.class);
			Session session = HibernateInstances.getCurrent().getSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			return gson.toJson("true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson("false");
	}

	@Path("role/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postAddRole(String supplied) {
		Gson gson = new Gson();
		try {
			Role role = gson.fromJson(supplied, Role.class);
			Session session = HibernateInstances.getCurrent().getSession();
			session.beginTransaction();
			session.save(role);
			session.getTransaction().commit();
			return gson.toJson("true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson("false");
	}

	@Path("role/edit")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postUpdateRole(String supplied) {
		Gson gson = new Gson();
		try {
			Role role = gson.fromJson(supplied, Role.class);
			Session session = HibernateInstances.getCurrent().getSession();
			session.beginTransaction();
			session.createQuery("update Role set name = :name, desc = :desc where id = :id")
					.setParameter("name", role.getName())
					.setParameter("desc", role.getDesc())
					.setParameter("id", role.getId())
					.executeUpdate();
			session.getTransaction().commit();
			return gson.toJson("true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson("false");
	}

	@Path("role/delete")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postDeleteRole(String supplied) {
		Gson gson = new Gson();
		try {
			Role role = gson.fromJson(supplied, Role.class);
			Session session = HibernateInstances.getCurrent().getSession();
			session.beginTransaction();
			session.delete(role);
			session.getTransaction().commit();
			return gson.toJson("true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson("false");
	}

}
