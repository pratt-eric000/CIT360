/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.prt.models.Globals;
import com.prt.models.User;
import com.prt.utils.EncryptionHelper;
import com.prt.utils.RestUtil;
import java.io.Serializable;
import java.util.Base64;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

	@ManagedProperty("#{globals}")
	private Globals globals;

	private String username;
	private String password;

	public Globals getGlobals() {
		return globals;
	}

	public void setGlobals(Globals globals) {
		this.globals = globals;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@PostConstruct
	void init() {

	}

	public String validate() {
		try {
			Gson gson = new Gson();
			String[][] results = gson.fromJson(RestUtil.post("http://localhost:8080/db/webresources/repository/select/user", gson.toJson(username)), String[][].class);
			if (results != null && results.length > 0) {
				User user = new User();
				for (String[] result : results) {
					if (result[0].equalsIgnoreCase("USERNAME")) {
						user.setUsername(result[1]);
					} else if (result[0].equalsIgnoreCase("PASSWORD")) {
						user.setPassword(result[1]);
					} else if (result[0].equalsIgnoreCase("SALT")) {
						user.setSalt(result[1]);
					}
				}
				String compare = EncryptionHelper.encrypt(password, Base64.getDecoder().decode(user.getSalt()));
				if (compare != null && compare.equals(user.getPassword())) {
					globals.loggedin = true;
//					PrimeFaces.current().ajax().update("");
					return "/user/home.xhtml?faces-redirect=true";
				}
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication Error", "The username or password entered was incorrect. Please try again"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
