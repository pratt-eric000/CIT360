/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.google.gson.Gson;
import com.prt.models.Column;
import com.prt.models.Database;
import com.prt.models.Globals;
import com.prt.models.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "databaseController")
@ViewScoped
public class DatabaseController implements Serializable {

	@ManagedProperty("#{globals}")
	private Globals globals;

	public Globals getGlobals() {
		return globals;
	}

	public void setGlobals(Globals globals) {
		this.globals = globals;
	}

	private Database newDatabase;
	private Column newColumn;
	private ArrayList<Database> databases;
	private Column selectedColumn;
	private Database selectedDatabase;
	private Table selectedTable;
	private Table newTable;
	private ArrayList<String> types;

	public ArrayList<String> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}

	public Table getNewTable() {
		return newTable;
	}

	public void setNewTable(Table newTable) {
		this.newTable = newTable;
	}

	public Table getSelectedTable() {
		return selectedTable;
	}

	public void setSelectedTable(Table selectedTable) {
		this.selectedTable = selectedTable;
	}

	public Database getSelectedDatabase() {
		return selectedDatabase;
	}

	public void setSelectedDatabase(Database selectedDatabase) {
		this.selectedDatabase = selectedDatabase;
	}

	public Column getSelectedColumn() {
		return selectedColumn;
	}

	public void setSelectedColumn(Column selectedColumn) {
		this.selectedColumn = selectedColumn;
	}

	public Column getNewColumn() {
		return newColumn;
	}

	public void setNewColumn(Column newColumn) {
		this.newColumn = newColumn;
	}

	public ArrayList<Database> getDatabases() {
		return databases;
	}

	public void setDatabases(ArrayList<Database> databases) {
		this.databases = databases;
	}

	public Database getNewDatabase() {
		return newDatabase;
	}

	public void setNewDatabase(Database newDatabase) {
		this.newDatabase = newDatabase;
	}

	@PostConstruct
	void init() {
		newDatabase = new Database();
		newColumn = new Column();
		newTable = new Table();
		databases = new ArrayList<>();
		types = new ArrayList<>();
		try {
			Gson gson = new Gson();
			//grab all database tables and columns in the database
			types = new ArrayList<>(Arrays.asList(new String[]{
				"INTEGER",
				"VARCHAR",
				"DATE"
			}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addDB() {
		if (newDatabase.getName() != null && !newDatabase.getName().equals("")) {
			globals.handler.get("createdatabase").execute(new String[][]{});
			databases.add(newDatabase);
			PrimeFaces.current().ajax().update("dbform");
			newDatabase = new Database();
			PrimeFaces.current().ajax().update("adddbform");
			PrimeFaces.current().executeScript("PF('adddatabasedlg').hide()");
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "You must add a database name in order to proceed"));
		}
	}

	public void cancelAddDB() {
		newDatabase = new Database();
		PrimeFaces.current().ajax().update("adddbform");
	}

	public void addTable() {
		if (newTable.getName() != null && !newTable.getName().equals("")) {
			newDatabase.getTables().add(newTable);
			PrimeFaces.current().ajax().update("adddbform");
			newTable = new Table();
			PrimeFaces.current().ajax().update("addtblform");
			PrimeFaces.current().executeScript("PF('addtbldlg').hide()");
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "You must add a table name in order to proceed"));
		}
	}

	public void cancelAddTable() {
		newTable = new Table();
		PrimeFaces.current().ajax().update("addtblform");
	}

	public void addColumn() {
		if (newColumn.getType() != null && !newColumn.getType().equals("")) {
			newTable.getColumns().add(newColumn);
			PrimeFaces.current().ajax().update("addtblform");
			newColumn = new Column();
			PrimeFaces.current().ajax().update("addcolform");
			PrimeFaces.current().executeScript("PF('addcolumndlg').hide()");
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a data type before proceeding"));
		}
	}

	public void cancelAddColumn() {
		newColumn = new Column();
		PrimeFaces.current().ajax().update("addcolform");
	}

	public void updateSelectedColumn(Column selected) {
		selectedColumn = selected;
		PrimeFaces.current().ajax().update("detailsform");
		PrimeFaces.current().ajax().update("editcolform");
	}

	public void updateSelectedDatabase(Database selected) {
		selectedDatabase = selected;
		PrimeFaces.current().ajax().update("viewtblsform");
	}

	public void updateSelectedTable(Table selected) {
		selectedTable = selected;
		PrimeFaces.current().ajax().update("viewcolsform");
	}

}
