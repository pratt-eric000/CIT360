/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcexample;

import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author P-ratt
 */
public class CalculatorView extends JFrame {

	private JTextField firstNumber = new JTextField(10);
	private JLabel additionLabel = new JLabel("+");
	private JTextField secondNumber = new JTextField(10);
	private JButton calcButton = new JButton("Calculate");
	private JTextField calcSolution = new JTextField(10);

	public CalculatorView() {
		JPanel calcPanel = new JPanel();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 200);
		calcPanel.add(firstNumber);
		calcPanel.add(additionLabel);
		calcPanel.add(secondNumber);
		calcPanel.add(calcButton);
		calcPanel.add(calcSolution);

		this.add(calcPanel);
	}

	public int getFirstNumber() {
		return Integer.parseInt(firstNumber.getText());
	}

	public int getSecondNumber() {
		return Integer.parseInt(secondNumber.getText());
	}

	public int calcSolution() {
		return Integer.parseInt(calcSolution.getText());
	}

	public void setCalcSolution(int solution) {
		calcSolution.setText(Integer.toString(solution));
	}

	public void addCalculationListener(ActionListener listenForCalcButton) {
		calcButton.addActionListener(listenForCalcButton);
	}

	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

}
