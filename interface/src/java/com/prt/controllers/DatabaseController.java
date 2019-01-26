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
import com.prt.utils.RestUtil;
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
			String[][][] results = gson.fromJson(RestUtil.post("http://localhost:8080/db/webresources/repository/databases", null), String[][][].class);
			if (results != null && results.length > 0) {
				for (String[][] row : results) {
					Database database = new Database();
					for (String[] col : row) {
						if (col[0].equalsIgnoreCase("ID")) {
							database.setId(col[1]);
						} else if (col[0].equalsIgnoreCase("NAME")) {
							database.setName(col[1]);
						} else if (col[0].equalsIgnoreCase("DESC")) {
							database.setDescription(col[1]);
						}
						//with database ID, I can search for the tables in it
						String[][][] tblResults = gson.fromJson(RestUtil.post("http://localhost:8080/db/webresources/repository/database/tables", gson.toJson(new String[]{database.getId()})), String[][][].class);
						if (tblResults != null && tblResults.length > 0) {
							for (String[][] tblrow : tblResults) {
								Table table = new Table();
								for (String[] tblcol : tblrow) {
									if (tblcol[0].equalsIgnoreCase("ID")) {
										table.setId(tblcol[1]);
									} else if (tblcol[0].equalsIgnoreCase("NAME")) {
										table.setName(tblcol[1]);
									} else if (tblcol[0].equalsIgnoreCase("DESC")) {
										table.setDescription(tblcol[1]);
									}
									//with table ID, I can search for the columns related to the table
									String[][][] colResults = gson.fromJson(RestUtil.post("http://localhost:8080/db/webresources/repository/table/columns", gson.toJson(new String[]{table.getId()})), String[][][].class);
									if (colResults != null && colResults.length > 0) {
										for (String[][] colRow : colResults) {
											Column column = new Column();
											for (String[] colcol : colRow) {
												if (colcol[0].equalsIgnoreCase("ID")) {
													column.setId(colcol[1]);
												} else if (colcol[0].equalsIgnoreCase("CHAR_LENGTH")) {
													column.setCharLength(Integer.parseInt(colcol[1]));
												} else if (colcol[0].equalsIgnoreCase("DESC")) {
													column.setDescription(colcol[1]);
												} else if (colcol[0].equalsIgnoreCase("AUTO_INCREMENTED")) {
													column.setIsautoincremented(colcol[1].equals("1"));
												} else if (colcol[0].equalsIgnoreCase("PRIMARY_KEY")) {
													column.setIsprimarykey(colcol[1].equals("1"));
												} else if (colcol[0].equalsIgnoreCase("NAME")) {
													column.setName(colcol[1]);
												} else if (colcol[0].equalsIgnoreCase("TYPE")) {
													column.setType(colcol[1]);
												} else if (colcol[0].equalsIgnoreCase("NOT_NULL")) {
													column.setNotnull(colcol[1].equals("1"));
												}
											}
											table.getColumns().add(column);
										}
									}
								}
								database.getTables().add(table);
							}
						}
					}
					databases.add(database);
				}
			}
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
