/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplethreads;

import java.util.Scanner;
import simple.Job;
import simple.Waiter;

/**
 *
 * @author P-ratt
 */
public class SimpleThreads {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Please tell me the task that was assigned to you: ");
		String task = input.nextLine();
		Job job = new Job(task);
		Waiter waiter = new Waiter(job);
		new Thread(waiter, "Dishes").start();

		String defaultmenu = "What would you like to do next?\n\n"
				+ "1)\t" + job.getJobName() + "\n"
				+ "2)\tDo Other Things\n"
				+ "3)\tQuit\n\n"
				+ "> ";
		try {
			Thread.sleep(500);
			int choice = -1;
			while (choice != 3 && !waiter.isDone()) {
				System.out.print("\n\n" + defaultmenu);
				choice = input.nextInt();
				switch (choice) {
					case 1:
						waiter.performTask();
						break;
					case 2:
						waiter.doOtherThings();
						break;
				}
				Thread.sleep(500);
			}
			System.out.println("Thank you for doing the things");
		} catch (Exception e) {
		}

	}

}
