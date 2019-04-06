/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.prt.models.Globals;
import com.prt.utils.RestUtil;
import java.io.Serializable;
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
@ManagedBean(name = "editRoleController")
@ViewScoped
public class EditRoleController implements Serializable {

	@ManagedProperty("#{global")
	private Globals global;

	public Globals getGlobal() {
		return global;
	}

	public void setGlobal(Globals global) {
		this.global = global;
	}

	@PostConstruct
	void init() {

	}

	public String saveEdit() {
		try {
			Gson gson = new Gson();
			String result = gson.fromJson(RestUtil.post(global.dturl + "repository/role/edit", gson.toJson(global.roleToEdit)), String.class);
			if (result != null && result.equalsIgnoreCase("true")) {
				return "/app/admin/roles.xhtml?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was an error saving the edit of this role"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
