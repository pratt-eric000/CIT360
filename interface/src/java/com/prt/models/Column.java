/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.models;

/**
 *
 * @author P-ratt
 */
public class Column {

	private String name;
	private String description;
	private String label;
	private String type;
	private boolean notnull;
	private boolean isprimarykey;
	private String foreignkey;
	private String primarykey;
	private int charLength;
	private boolean isautoincremented;

	public boolean isIsautoincremented() {
		return isautoincremented;
	}

	public void setIsautoincremented(boolean isautoincremented) {
		this.isautoincremented = isautoincremented;
	}

	public int getCharLength() {
		return charLength;
	}

	public void setCharLength(int charLength) {
		this.charLength = charLength;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNotnull() {
		return notnull;
	}

	public void setNotnull(boolean notnull) {
		this.notnull = notnull;
	}

	public boolean isIsprimarykey() {
		return isprimarykey;
	}

	public void setIsprimarykey(boolean isprimarykey) {
		this.isprimarykey = isprimarykey;
	}

	public String getForeignkey() {
		return foreignkey;
	}

	public void setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
	}

	public String getPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}
}
