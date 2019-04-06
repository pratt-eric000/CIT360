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
@ManagedBean(name = "roleController")
@ViewScoped
public class RoleController implements Serializable {

	@ManagedProperty("#{global}")
	private Globals global;
	private List<Role> roles;
	private List<User> users;
	private String roleName;
	private String roleDesc;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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

	public void addNewRole() {
		try {
			Role role = new Role();
			role.setName(roleName);
			role.setDesc(roleDesc);
			Gson gson = new Gson();
			String result = gson.fromJson(RestUtil.post(global.dturl + "repository/role/add", gson.toJson(role)), String.class);
			if (result != null && result.equalsIgnoreCase("true")) {
				roleName = "";
				roleDesc = "";
				init();
				PrimeFaces.current().ajax().update("roleform");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The new role has been successfully added"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem saving the new role"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String editRole(Role role) {
		global.roleToEdit = role;
		return "/app/admin/editrole.xhtml?faces-redirect=true";
	}

	public void deleteRole(Role role) {
		try {
			Gson gson = new Gson();
			String result = gson.fromJson(RestUtil.post(global.dturl + "repository/role/delete", gson.toJson(role)), String.class);
			if (result != null && result.equalsIgnoreCase("true")) {
				roles.remove(role);
				PrimeFaces.current().ajax().update("roleform");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The new role has been successfully deleted"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was a problem deleting the selected role"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
