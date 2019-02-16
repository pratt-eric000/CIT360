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

		this.view.addCalculationListeners(new CalculateListener());
		this.view.addCalculateListener(new OperationListener());
		this.view.addResetListener(new ResetListener());

	}

	class CalculateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String opperand = e.getActionCommand();
				model.addOpperand(opperand, view.getResultText());
				view.setResultText(model.getDisplay());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	class OperationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.calculate();
				view.setResultText(model.getDisplay());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	class ResetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.resetCalculator();
				view.setResultText(model.getDisplay());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
