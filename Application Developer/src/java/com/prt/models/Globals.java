/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.models;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "global")
@SessionScoped
public class Globals {

	private boolean loggedIn;
	public String userRole;
	public int userId;
	public String username;
	public int selectedScreen;
	public User userToEdit;
	public final String dturl = "http://localhost:8080/data/resources/";
	public int selectedRole;
	public Role roleToEdit;

	public Role getRoleToEdit() {
		return roleToEdit;
	}

	public void setRoleToEdit(Role roleToEdit) {
		this.roleToEdit = roleToEdit;
	}

	public User getUserToEdit() {
		return userToEdit;
	}

	public void setUserToEdit(User userToEdit) {
		this.userToEdit = userToEdit;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
