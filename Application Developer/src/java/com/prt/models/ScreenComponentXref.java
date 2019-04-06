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
public class ScreenComponentXref implements Serializable {

	private int id;
	private int screenId;
	private int componentId;
	private int locationId;
	private String gridCoordinates;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getGridCoordinates() {
		return gridCoordinates;
	}

	public void setGridCoordinates(String gridCoordinates) {
		this.gridCoordinates = gridCoordinates;
	}
}
