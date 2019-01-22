/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.prt.models.Globals;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "databaseController")
@ViewScoped
public class DatabaseController {

	@ManagedProperty("#{globals}")
	private Globals globals;

	public Globals getGlobals() {
		return globals;
	}

	public void setGlobals(Globals globals) {
		this.globals = globals;
	}

	@PostConstruct
	void init() {
		try {
			Gson gson = new Gson();
			//grab all database tables and columns in the database
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
