/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.prt.models.Globals;
import com.prt.models.RoleScreenXref;
import com.prt.models.User;
import com.prt.utils.EncryptionHelper;
import com.prt.utils.HibernateInstances;
import com.prt.utils.RestUtil;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

	@ManagedProperty("#{global}")
	private Globals global;

	public Globals getGlobal() {
		return global;
	}

	public void setGlobal(Globals global) {
		this.global = global;
	}

	private String username;
	private String password;
	private String screen;

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@PostConstruct
	void init() {
		screen = "default.xhtml";
	}

	public String validate() {
		try {
			Gson gson = new Gson();
			User user = gson.fromJson(RestUtil.post("http://localhost:8080/data/resources/repository/select/user", gson.toJson(username)), User.class);
			if (user != null) {
				//validate by hashing given password and match it with the password retrieved from the database
				String compare = EncryptionHelper.encrypt(password, Base64.getDecoder().decode(user.getSalt()));
				if (compare != null && compare.equals(user.getPassword())) {
					global.setLoggedIn(true);
					global.userId = user.getId();
					global.username = user.getUsername();
					global.userRole = user.getRole();
					Session session = HibernateInstances.getCurrent().getSession();
					List<RoleScreenXref> roleScreens = (List<RoleScreenXref>) session.createQuery("from RoleScreenXref where roleId = :rid").setParameter("rid", user.getRoleId()).list();
					if (roleScreens != null && !roleScreens.isEmpty()) {
						global.selectedScreen = roleScreens.get(0).getId();
						if (user.getRole().equalsIgnoreCase("admin")) {
							screen = "admin/home.xhtml";
						} else {
							screen = "user/program.xhtml";
						}
					}
					return "/app/" + screen + "?faces-redirect=true";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
