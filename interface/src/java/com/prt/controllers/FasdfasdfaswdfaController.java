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
@ManagedBean(name = "fasdfasdfaswdfaController")
@ViewScoped
public class FasdfasdfaswdfaController implements Serializable {

	private String ugh;

	public String getUgh() {
		return ugh;
	}

	public void setUgh(String ugh) {
		this.ugh = ugh;
	}

	@PostConstruct
	void init() {
		ugh = "stupid";
	}
}
