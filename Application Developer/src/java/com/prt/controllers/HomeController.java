/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.prt.models.Globals;
import com.prt.models.Screen;
import com.prt.utils.HibernateInstances;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "homeController")
@ViewScoped
public class HomeController implements Serializable {

	@ManagedProperty("#{global}")
	private Globals global;
	private Screen screen;

	public Globals getGlobal() {
		return global;
	}

	public void setGlobal(Globals global) {
		this.global = global;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	@PostConstruct
	void init() {
		try {
			//using selected screen ID, get the necessary screen content
			Session session = HibernateInstances.getCurrent().getSession();
			screen = ((List<Screen>) session.createQuery("from Screen where id = :id").setParameter("id", global.selectedScreen).list()).get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
