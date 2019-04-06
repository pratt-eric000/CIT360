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

	public String prepareEditUser(User user) {
		try {
			global.userToEdit = user;
			return "/app/admin/edituser.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String deleteUser(User user) {
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
		return null;
	}
}
