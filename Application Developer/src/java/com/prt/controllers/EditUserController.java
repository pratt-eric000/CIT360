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
import com.prt.utils.RestUtil;
import java.io.Serializable;
import java.util.List;
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
@ManagedBean(name = "editUserController")
@ViewScoped
public class EditUserController implements Serializable {

	@ManagedProperty("#{global}")
	private Globals global;
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
			//get all roles
			Gson gson = new Gson();
			roles = (List<Role>) gson.fromJson(RestUtil.post(global.dturl + "repository/select/roles", null), List.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	public String saveEdit() {
		try {
			Gson gson = new Gson();
			String result = gson.fromJson(RestUtil.post(global.dturl + "repository/user/edit", gson.toJson(global.userToEdit)), String.class);
			if (result != null && result.equalsIgnoreCase("true")) {
				global.userToEdit = null;
				return "/app/admin/users.xhtml?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was an error saving the user"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
