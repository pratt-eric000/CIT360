/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.models;

import com.google.gson.Gson;
import java.io.Serializable;

/**
 *
 * @author P-ratt
 */
public class CreateTable implements Serializable, CRUD {

	@Override
	public String execute(String[][] params) {
		try {
			Gson gson = new Gson();
			//code to insert table record
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
