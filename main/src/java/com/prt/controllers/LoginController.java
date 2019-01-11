/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

	private String username;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@PostConstruct
	void init() {

	}

	public String validate() {
		try {
			System.out.println("Testing");
			return "/user/home.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
