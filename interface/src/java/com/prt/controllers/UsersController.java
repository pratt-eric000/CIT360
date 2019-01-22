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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "usersController")
@ViewScoped
public class UsersController implements Serializable {

	@ManagedProperty("#{globals}")
	private Globals globals;
	private ArrayList<User> users;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User newUser) {
		this.user = newUser;
	}

	public Globals getGlobals() {
		return globals;
	}

	public void setGlobals(Globals globals) {
		this.globals = globals;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	@PostConstruct
	void init() {
		try {
			user = new User();
			users = new ArrayList<>();
			Gson gson = new Gson();
			//grab all the current users in the database
			String[][][] results = gson.fromJson(RestUtil.post("http://localhost:8080/db/webresources/repository/select/users", null), String[][][].class);
			if (results != null && results.length > 0) {
				for (String[][] row : results) {
					User user = new User();
					for (String[] col : row) {
						if (col[0].equalsIgnoreCase("firstname")) {
							user.setFirstname(col[1]);
						} else if (col[0].equalsIgnoreCase("lastname")) {
							user.setLastname(col[1]);
						} else if (col[0].equalsIgnoreCase("username")) {
							user.setUsername(col[1]);
						} else if (col[0].equalsIgnoreCase("admin")) {
							user.setAdmin(col[1].equals("1"));
						}
					}
					users.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addUser() {
		try {
			Gson gson = new Gson();
			//decrypt password
			byte[] salt = new byte[16];
			Random random = new Random();
			random.nextBytes(salt);
			String saltStr = new String(Base64.getEncoder().encode(salt), "UTF-8");
			String encrypted = EncryptionHelper.encrypt(user.getPassword(), salt);

			String[] params = new String[]{encrypted, saltStr, user.getFirstname(), user.getLastname(), user.getUsername(), user.isAdmin() ? "1" : "0"};
			String result = gson.fromJson(RestUtil.post("http://localhost:8080/db/webresources/repository/user/add", gson.toJson(params)), String.class);
			if (result != null && result.equalsIgnoreCase("true")) {
				//message saying success
				init();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User successfully created"));
				PrimeFaces.current().executeScript("PF('adduserdlg').hide()");
			} else {
				//message saying error
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was an error creating the user"));
			}
			PrimeFaces.current().ajax().update("usersform");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelAddUser() {
		user.setAdmin(false);
		user.setFirstname("");
		user.setLastname("");
		user.setUsername("");
		PrimeFaces.current().ajax().update("adduserform");
	}

	public void startEdit(User user) {
		this.user = user;
		PrimeFaces.current().ajax().update("edituserform");
	}

	public void editUser() {

	}
}
