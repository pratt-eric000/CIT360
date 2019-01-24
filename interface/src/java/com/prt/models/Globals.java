/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.models;

import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "globals")
@SessionScoped
public class Globals implements Serializable, CRUD {

	public boolean loggedin = false;
	public HashMap<String, CRUD> handler;

	public boolean isLoggedin() {
		return loggedin;
	}

	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}

	@PostConstruct
	void init() {
		handler = new HashMap<>();
		handler.put("createtable", new CreateTable());
		handler.put("createdatabase", new CreateDatabase());
		handler.put("insert", new Insert());
		handler.put("delete", new Delete());
		handler.put("drop", new Drop());
		handler.put("update", new Update());
	}

	@Override
	public String execute(String[][] params) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
