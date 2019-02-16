/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcexample;

/**
 *
 * @author P-ratt
 */
public class Multiply implements Operator {

	@Override
	public String performOperation(String s1, String s2) {
		if (s1.contains(".") || s2.contains(".")) {
			return String.valueOf(Double.parseDouble(s1) * Double.parseDouble(s2));
		} else {
			return String.valueOf(Integer.parseInt(s1) * Integer.parseInt(s2));
		}
	}

}
