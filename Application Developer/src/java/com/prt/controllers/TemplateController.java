/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.prt.models.Globals;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "templateController")
@ViewScoped
public class TemplateController {

	@ManagedProperty("#{global}")
	private Globals global;

	public Globals getGlobal() {
		return global;
	}

	public void setGlobal(Globals global) {
		this.global = global;
	}

	public String logout() {
		global.setLoggedIn(false);
		return "/app/login.xhtml?faces-redirect=true";
	}
}
