/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcexample;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author P-ratt
 */
public class CalculatorView extends JFrame {

	private JButton one = new JButton("1");
	private JButton two = new JButton("2");
	private JButton three = new JButton("3");
	private JButton four = new JButton("4");
	private JButton five = new JButton("5");
	private JButton six = new JButton("6");
	private JButton seven = new JButton("7");
	private JButton eight = new JButton("8");
	private JButton nine = new JButton("9");
	private JButton zero = new JButton("0");
	private JButton plus = new JButton("+");
	private JButton minus = new JButton("-");
	private JButton multiply = new JButton("*");
	private JButton divide = new JButton("/");
	private JButton decimal = new JButton(".");
	private JButton calculate = new JButton("Calculate");
	private JButton restart = new JButton("CE");
	private JLabel result = new JLabel();

	private JPanel main = new JPanel();

	public CalculatorView() {
		main.setLayout(new BorderLayout());
		JPanel operators = new JPanel(new GridLayout(6, 1));
		JPanel numbers = new JPanel(new GridLayout(4, 3));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 500);

		numbers.add(one);
		numbers.add(two);
		numbers.add(three);
		numbers.add(four);
		numbers.add(five);
		numbers.add(six);
		numbers.add(seven);
		numbers.add(eight);
		numbers.add(nine);
		numbers.add(zero);
		numbers.add(decimal);

		operators.add(restart);
		operators.add(plus);
		operators.add(minus);
		operators.add(multiply);
		operators.add(divide);
		operators.add(calculate);

		main.add(numbers, BorderLayout.CENTER);
		main.add(operators, BorderLayout.EAST);

		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new FlowLayout());

		resultPanel.add(result);

		main.add(resultPanel, BorderLayout.NORTH);

		this.add(main);
	}

	public void addCalculationListeners(ActionListener listenForCalcButton) {
		one.addActionListener(listenForCalcButton);
		two.addActionListener(listenForCalcButton);
		three.addActionListener(listenForCalcButton);
		four.addActionListener(listenForCalcButton);
		five.addActionListener(listenForCalcButton);
		six.addActionListener(listenForCalcButton);
		seven.addActionListener(listenForCalcButton);
		eight.addActionListener(listenForCalcButton);
		nine.addActionListener(listenForCalcButton);
		zero.addActionListener(listenForCalcButton);
		plus.addActionListener(listenForCalcButton);
		minus.addActionListener(listenForCalcButton);
		multiply.addActionListener(listenForCalcButton);
		divide.addActionListener(listenForCalcButton);
		decimal.addActionListener(listenForCalcButton);
	}

	public void addCalculateListener(ActionListener listener) {
		calculate.addActionListener(listener);
	}

	public void addResetListener(ActionListener listener) {
		restart.addActionListener(listener);
	}

	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void setResultText(String display) {
		result.setText(display);
	}

	public String getResultText() {
		return result.getText();
	}

}
