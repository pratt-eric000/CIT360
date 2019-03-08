/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcexample;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author P-ratt
 */
public class CalculatorModelTest {

	public CalculatorModelTest() {
	}

	@Test
	public void testAddEmptyOpperand() {
		CalculatorModel model = new CalculatorModel();
		model.addOpperand("+", "");
		String[] display = model.getDisplay();
		String[] expected = new String[]{""};
		assertArrayEquals(expected, display);
	}

	@Test
	public void testMultipleOpperands() {
		CalculatorModel model = new CalculatorModel();
		model.addOpperand("5");
		model.addOpperand("+");
		model.addOpperand("4");
		model.addOpperand("-");
		model.addOpperand("*");
		model.addOpperand("2");
		model.calculate();
		String expected = "13";
		String actual = model.getDisplay()[0];
		assertEquals(expected, actual);
	}

	@Test
	public void testCalculateOrderOfOpperations() {
		CalculatorModel model = new CalculatorModel();
		model.addOpperand("5");
		model.addOpperand("+");
		model.addOpperand("6");
		model.addOpperand("*");
		model.addOpperand("3");
		model.calculate();
		assertEquals("23", model.getAnswer());
	}

	@Test
	public void testAddOpperandsToExistingResult() {
		CalculatorView view = new CalculatorView();
		CalculatorView view2 = new CalculatorView();
		CalculatorModel model = new CalculatorModel();
		CalculatorModel model2 = new CalculatorModel();
		CalculatorController controller = new CalculatorController(view, model);
		CalculatorController controller2 = new CalculatorController(view2, model2);
		model.addOpperand("5");
		model.addOpperand("+");
		model.addOpperand("6");
		model.addOpperand("*");
		model.addOpperand("3");
		model.calculate();
		String display = controller.parseDisplay(model.getDisplay());
		model.addOpperand("-", display);
		display = controller.parseDisplay(model.getDisplay());
		assertEquals("23  -", display);
		model.addOpperand("4", display);
		display = controller.parseDisplay(model.getDisplay());
		assertEquals("23  -  4", display);
		model.calculate();
		model2.addOpperand("5");
		model2.addOpperand("+");
		model2.addOpperand("6");
		model2.addOpperand("*");
		model2.addOpperand("3");
		model2.addOpperand("-");
		model2.addOpperand("4");
		model2.calculate();
		controller2.parseDisplay(model2.getDisplay());
		assertEquals(model2.getAnswer(), model.getAnswer());
	}

	@Test
	public void testResetCalculator() {
		CalculatorModel model = new CalculatorModel();
		model.addOpperand("5");
		model.addOpperand("+");
		model.addOpperand("6");
		model.addOpperand("*");
		model.addOpperand("3");
		model.calculate();
		model.resetCalculator();
		ArrayList<String> opperations = model.getOperations();
		StringBuilder numbers = model.getNumbers();
		assertArrayEquals(new String[]{}, opperations.toArray(new String[opperations.size()]));
		assertEquals(0, numbers.length());
	}

	@Test
	public void testNotSameResult() {
		CalculatorView view = new CalculatorView();
		CalculatorModel model = new CalculatorModel();
		CalculatorController controller = new CalculatorController(view, model);
		model.addOpperand("8");
		model.addOpperand("4");
		model.addOpperand("-");
		model.addOpperand("2");
		model.addOpperand("*");
		model.addOpperand("7");
		model.saveOperations();
		model.resetCalculator();
		model.addOpperand("8");
		model.addOpperand("8");
		model.addOpperand("4");
		model.addOpperand("-");
		model.addOpperand("2");
		model.addOpperand("*");
		model.addOpperand("7");
		assertNotSame(model.getOperations(), model.getSavedOperations());
	}

	@Test
	public void testListsNotNull() {
		CalculatorModel model = new CalculatorModel();
		assertNotNull(model.getOperations());
		assertNotNull(model.getSavedOperations());
		assertNotNull(model.getHelper());
	}

}
