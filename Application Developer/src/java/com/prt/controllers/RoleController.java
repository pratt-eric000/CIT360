/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.prt.models.Globals;
import com.prt.models.Role;
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
@ManagedBean(name = "roleController")
@ViewScoped
public class RoleController implements Serializable {

	@ManagedProperty("#{global}")
	private Globals global;
	private List<Role> roles;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@PostConstruct
	void init() {
		try {
			Gson gson = new Gson();
			roles = (List<Role>) gson.fromJson(RestUtil.post(global.dturl + "repository/select/roles", null), List.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}
}
