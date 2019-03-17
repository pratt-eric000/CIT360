/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erc.prt;

import java.util.ArrayList;

/**
 *
 * @author P-ratt
 */
public class RunThing implements Runnable {

	private final Job job;
	private boolean paused;
	private Thread thread;

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public Job getJob() {
		return job;
	}

	public boolean isFinished() {
		return job.isFinished();
	}

	public int getI() {
		return job.getIteration();
	}

	public String getTname() {
		return job.getName();
	}

	public String[] getHashes() {
		return job.getHashes();
	}

	public ArrayList<String> getHashList() {
		return job.getHashList();
	}

	public void setGoTimmyGo(boolean goTimmyGo) {
		job.setGoTimmyGo(goTimmyGo);
	}

	public RunThing(Job job) {
		this.job = job;
		thread = new Thread(this, job.getName());
	}

	@Override
	public void run() {
		try {
			job.doTheThings();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopThread() {
		try {
			job.setGoTimmyGo(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pauseThread() {
		try {
			synchronized (thread) {
				thread.wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
