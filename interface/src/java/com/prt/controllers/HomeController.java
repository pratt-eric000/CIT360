/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.prt.models.Globals;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "homeController")
@ViewScoped
public class HomeController implements Serializable {

	@ManagedProperty("globals")
	private Globals globals;

	public Globals getGlobals() {
		return globals;
	}

	public void setGlobals(Globals globals) {
		this.globals = globals;
	}

	@PostConstruct
	void init() {

	}
}
