/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.models;

import java.io.Serializable;

/**
 *
 * @author P-ratt
 */
public class RoleScreenXref implements Serializable {

	private int id;
	private int roleId;
	private int screenId;
	private int defaultScreen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public int getDefaultScreen() {
		return defaultScreen;
	}

	public void setDefaultScreen(int defaultScreen) {
		this.defaultScreen = defaultScreen;
	}
}
