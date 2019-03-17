/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;

/**
 *
 * @author P-ratt
 */
public class Waiter implements Runnable {

	private final Job job;
	private int time = 3;
	private boolean done = false;

	public boolean isDone() {
		return done;
	}

	public Job getJob() {
		return job;
	}

	public Waiter(Job job) {
		this.job = job;
	}

	@Override
	public void run() {
		synchronized (job) {
			try {
				System.out.println("Job [" + job.getJobName() + "] is ready to be performed");
				job.wait();
				System.out.println("Job [" + job.getJobName() + "] was successfully performed");
				done = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void doOtherThings() throws Exception {
		System.out.println("Time required to perform [" + job.getJobName() + "] has increased");
		time += 3;
		Thread.sleep(500);
		for (int i = 0; i < 3; i++) {
			System.out.print(".");
			Thread.sleep(1000);
		}
		System.out.println("Be sure to perform your task soon.");
	}

	public void performTask() throws Exception {
		System.out.print("Starting to perform task");
		for (int i = 0; i < time; i++) {
			System.out.print(".");
			Thread.sleep(1000);
		}
		synchronized (job) {
			job.notify();
			time = 0;
		}
	}

}
