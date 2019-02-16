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
public class CalculatorModel {

	private int calcValue;

	public void addTwoNumbers(int firstNumber, int secondNumber) {
		calcValue = firstNumber + secondNumber;
	}

	public int getCalculation() {
		return calcValue;
	}
}
