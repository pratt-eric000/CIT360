/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadsexample;

import erc.prt.OddBallObject;
import erc.prt.RunThing;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author P-ratt
 */
public class ThreadsExample {

	/**
	 * @param args the command line arguments
	 * @throws java.lang.Exception
	 */
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		Thread t1 = new Thread(new RunThing("Thread-1"));
		t1.start();

		Thread.sleep(500);
		String menu = "While we are doing the things, what would you like to?";
		while (OddBallObject.hashes.length == 0) {
			System.out.println(menu);
			System.out.print("\n1)\t\tSwim\n2)\t\tJump\n3)\t\tCry\n\nAnswer> ");
			input.nextInt();
			menu = "What else would you like to do?";
		}
		System.out.println(Arrays.toString(OddBallObject.hashes));
	}

}
