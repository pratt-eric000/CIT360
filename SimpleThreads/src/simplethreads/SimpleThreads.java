/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplethreads;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
		Runnable runnable = new Waiter(job);
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(runnable);

		System.out.println("The task you have chosen will take an unspecified amount of time to accomplish. We will check back on the task every 3 seconds to see if it is done.");

		try {
			Random rand = new Random();
			int num = (rand.nextInt(60) + 1);
			System.out.println("The time allotted for the task is " + num + " seconds.");
			long init = System.currentTimeMillis() / 1000;
			for (long i = init; i < init + num; i += (System.currentTimeMillis() / 1000) - (init)) {
				System.out.println("Checking...");
				Thread.sleep(2000);
				System.out.println("Still not done...");
				Thread.sleep(500);
			}
			synchronized (job) {
				job.notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
