/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.prt.models.Globals;
import com.prt.models.Table;
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
@ManagedBean(name = "databaseController")
@ViewScoped
public class DatabaseController implements Serializable {

	@ManagedProperty("#{global}")
	private Globals global;
	private List<Table> tables;

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public Globals getGlobal() {
		return global;
	}

	public void setGlobal(Globals global) {
		this.global = global;
	}

	@PostConstruct
	void init() {
		try {
			Session session = HibernateInstances.getCurrent().getSession();
			tables = session.createCriteria(Table.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startAddDatabase() {

	}
}
