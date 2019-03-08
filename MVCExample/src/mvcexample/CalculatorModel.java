/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author P-ratt
 */
public class CalculatorModel {

	private int calcValue;
	private ArrayList<String> operations = new ArrayList<>();
	private Map<String, Operator> helper = new HashMap<>();
	private String prevOperation;
	private StringBuilder numbers;
	private String answer;
	private String[] display;
	private ArrayList<ArrayList<String>> savedOperations = new ArrayList<>();

	CalculatorModel() {
		helper.put("+", new Add());
		helper.put("-", new Subtract());
		helper.put("*", new Multiply());
		helper.put("/", new Divide());

		prevOperation = null;
		numbers = new StringBuilder();
	}

	public ArrayList<ArrayList<String>> getSavedOperations() {
		return savedOperations;
	}

	public void setSavedOperations(ArrayList<ArrayList<String>> savedOperations) {
		this.savedOperations = savedOperations;
	}

	public String[] getDisplay() {
		return display;
	}

	public void setDisplay(String[] display) {
		this.display = display;
	}

	public int getCalcValue() {
		return calcValue;
	}

	public void setCalcValue(int calcValue) {
		this.calcValue = calcValue;
	}

	public ArrayList<String> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<String> operations) {
		this.operations = operations;
	}

	public Map<String, Operator> getHelper() {
		return helper;
	}

	public void setHelper(Map<String, Operator> helper) {
		this.helper = helper;
	}

	public String getPrevOperation() {
		return prevOperation;
	}

	public void setPrevOperation(String prevOperation) {
		this.prevOperation = prevOperation;
	}

	public StringBuilder getNumbers() {
		return numbers;
	}

	public void setNumbers(StringBuilder numbers) {
		this.numbers = numbers;
	}

	public String getAnswer() {
		return answer;
	}

	public void addOpperand(String opperand) {
		addOpperand(opperand, null);
	}

	public void addOpperand(String opperand, String existingDisplay) {
		if (!opperand.equals("+") && !opperand.equals("-") && !opperand.equals("*") && !opperand.equals("/")) {
			numbers.append(opperand);
		} else {
			if (existingDisplay != null && existingDisplay.length() > 0 && numbers.length() == 0 && operations.isEmpty()) {
				numbers.append(existingDisplay);
			}
			if (numbers.length() > 0) {
				operations.add(numbers.toString());
				numbers = new StringBuilder();
				prevOperation = null;
			}
			if (!operations.isEmpty()) {
				if (prevOperation == null) {
					prevOperation = opperand;
				} else {
					operations.remove(operations.size() - 1);
				}
				operations.add(opperand);
			}
		}
		display = new String[operations.size() + 1];
		for (int i = 0; i < operations.size(); i++) {
			display[i] = operations.get(i);
		}
		display[display.length - 1] = numbers.toString();
	}

	public void calculate() {
		try {
			if (numbers.length() > 0) {
				operations.add(numbers.toString());
			}
			while (operations.contains("*") || operations.contains("/")) {
				int pos = -1;
				String operator = null;
				if (operations.contains("*")) {
					operator = "*";
					pos = operations.indexOf("*");
				} else if (operations.contains("/")) {
					operator = "/";
					pos = operations.indexOf("/");
				}
				String prev = operations.get(pos - 1);
				String next = operations.get(pos + 1);
				String newNum = helper.get(operator).performOperation(prev, next);
				for (int i = 0; i < 3; i++) {
					operations.remove(pos - 1);
				}
				operations.add(pos - 1, newNum);
			}

			answer = null;
			String prevOp = null;
			for (String operation : operations) {
				if (operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")) {
					prevOp = operation;
				} else if (!operation.equals(".")) {
					if (prevOp != null) {
						answer = helper.get(prevOp).performOperation(answer, operation);
						prevOp = null;
					} else {
						answer = operation;
					}
				}
			}
			numbers = new StringBuilder();
			operations = new ArrayList<>();
			display = new String[]{answer};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetCalculator() {
		operations = new ArrayList<>();
		numbers = new StringBuilder();
		display = new String[]{};
	}

	public void saveOperations() {
		savedOperations.add(operations);
	}
}
