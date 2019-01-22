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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "usersController")
@ViewScoped
public class UsersController implements Serializable {

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
		try {
			Gson gson = new Gson();
			//grab all the current users in the database
			String[][][] results = gson.fromJson(RestUtil.post("http://localhost:8080/db/webresources/repository/select/users", null), String[][][].class);
			if (results != null && results.length > 0) {
				for (String[][] row : results) {
					for (String[] col : row) {

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
