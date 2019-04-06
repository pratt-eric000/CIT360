/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.models;

import java.io.Serializable;

/**
 *
 * @author P-ratt
 */
public class ActionLink implements Serializable {

	private int id;
	private int fieldActionId;
	private String href;
	private int fieldId;
	private int columnId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFieldActionId() {
		return fieldActionId;
	}

	public void setFieldActionId(int fieldActionId) {
		this.fieldActionId = fieldActionId;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
}
