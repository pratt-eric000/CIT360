/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.prt.models.User;
import com.prt.utils.EncryptionHelper;
import com.prt.utils.RestUtil;
import java.io.Serializable;
import java.util.Base64;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

	private String username;
	private String password;

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

	}

	public String validate() {
		try {
			Gson gson = new Gson();
			User user = gson.fromJson(RestUtil.post("http://localhost:8080/data/resources/repository/select/user", gson.toJson(username)), User.class);
			if (user != null) {
				//validate by hashing given password and match it with the password retrieved from the database
				String compare = EncryptionHelper.encrypt(password, Base64.getDecoder().decode(user.getSalt()));
				if (compare != null && compare.equals(user.getPassword())) {
					return "/app/main/home.xhtml?faces-redirect=true";
				}
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
