/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcexample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author P-ratt
 */
public class CalculatorController {

	private CalculatorView view;
	private CalculatorModel model;

	public CalculatorController(CalculatorView view, CalculatorModel model) {
		this.view = view;
		this.model = model;

		this.view.addCalculationListener(new CalculateListener());
	}

	class CalculateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int firstNumber, secondNumber = 0;

			try {
				firstNumber = view.getFirstNumber();
				secondNumber = view.getSecondNumber();
				model.addTwoNumbers(firstNumber, secondNumber);

				view.setCalcSolution(model.getCalculation());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}
