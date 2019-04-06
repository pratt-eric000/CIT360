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
public class FieldAction implements Serializable {

	private int id;
	private int fieldId;
	private int actionId;
	private int actionLinkId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public int getActionLinkId() {
		return actionLinkId;
	}

	public void setActionLinkId(int actionLinkId) {
		this.actionLinkId = actionLinkId;
	}
}
