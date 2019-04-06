/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.prt.models.Globals;
import com.prt.models.User;
import com.prt.utils.RestUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

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
		} catch (Exception e) {
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
}
