/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.prt.models.Globals;
import com.prt.models.User;
import com.prt.utils.RestUtil;
import java.io.Serializable;
import java.util.List;
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
@ManagedBean(name = "userController")
@ViewScoped
public class UserController implements Serializable {

	@ManagedProperty("#{global}")
	private Globals global;
	private List<User> users;
	private User newUser;

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Globals getGlobal() {
		return global;
	}

	public void setGlobal(Globals global) {
		this.global = global;
	}

	@PostConstruct
	void init() {
		try {
			Gson gson = new Gson();
			users = (List<User>) gson.fromJson(RestUtil.post(global.dturl + "repository/select/users", null), List.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	public void startAddNewUser() {
		newUser = new User();
	}

	public String prepareEditUser(User user) {
		global.userToEdit = user;
		return "/app/admin/edituser.xhtml?faces-redirect=true";
	}

	public void deleteUser(User user) {
		try {
			Gson gson = new Gson();
			String result = gson.fromJson(RestUtil.post(global.dturl + "repository/user/delete", gson.toJson(user)), String.class);
			if (result != null && result.equalsIgnoreCase("true")) {
				users.remove(user);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The user was successfully removed"));
				PrimeFaces.current().ajax().update("userForm");
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem deleting the user you selected"));
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	public void addNewUser() {
		try {
			Gson gson = new Gson();
			String result = gson.fromJson(RestUtil.post(global.dturl + "repository/user/add", gson.toJson(newUser)), String.class);
			if (result != null && result.equalsIgnoreCase("true")) {
				init();
				PrimeFaces.current().ajax().update("userForm");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The user was successfully added"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem adding the new user"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
