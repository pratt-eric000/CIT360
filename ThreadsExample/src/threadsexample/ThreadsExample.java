/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadsexample;

import erc.prt.Job;
import erc.prt.RunThing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
		Map<Integer, String> choiceMap;

		int threadNum = 1;
		ArrayList<RunThing> things = new ArrayList<>();
		Scanner input = new Scanner(System.in);
		String menu = "Welcome to the hash generator!\n\n"
				+ "For your first iteration, how many hashes would you like for Thread-1? ";
		System.out.print(menu);
		int answer = input.nextInt();

		Job job = new Job("Thread-1", answer);
		RunThing thing = new RunThing(job);
		new Thread(thing, "Thread-1").start();

		things.add(thing);

		String defaultmenu = "What would you like to do now?\n\n"
				+ "1)\t\tCheck for finished hashes\n"
				+ "2)\t\tCreate another hash list\n"
				+ "3)\t\tView current jobs\n"
				+ "0)\t\tExit Application\n\n"
				+ "> ";

		Map<Integer, String> defaultMap = new HashMap<>();
		defaultMap.put(1, "check");
		defaultMap.put(2, "create");
		defaultMap.put(3, "working");
		defaultMap.put(0, "exit");

		menu = "\nThread-" + threadNum + " is currently gathering hashes and will be ready shortly. " + defaultmenu;
		choiceMap = defaultMap;
		answer = -1;
		while (choiceMap.get(answer) == null || choiceMap.get(answer) != null && !choiceMap.get(answer).equals("exit")) {
			System.out.print(menu);
			answer = input.nextInt();
			String result = choiceMap.get(answer);
			if (result.equals("check")) {
				ArrayList<RunThing> finished = new ArrayList<>();
				for (RunThing t : things) {
					if (t.isFinished()) {
						finished.add(t);
					}
				}
				if (finished.size() > 0) {
					choiceMap = new HashMap<>();
					menu = "The following jobs have finished. Which would you like to view?\n\n";
					for (int i = 0; i < finished.size(); i++) {
						RunThing t = finished.get(i);
						menu += (i + 1) + ")\t\t" + t.getTname() + "\n";
						choiceMap.put((i + 1), t.getTname());
					}
					menu += "\n> ";
				} else {
					menu = "It looks like no jobs have finished yet.\n\n" + defaultmenu;
					choiceMap = defaultMap;
				}
			} else if (result.equals("create")) {
				menu = "How many hashes would you like for Thread-" + ++threadNum + "? ";
				System.out.println(menu);
				answer = input.nextInt();
				Job newJob = new Job("Thread-" + threadNum, answer);
				RunThing newThing = new RunThing(newJob);
				new Thread(newThing, "Thread-" + threadNum).start();
				things.add(newThing);
				menu = defaultmenu;
				answer = -1;
			} else if (result.equals("working")) {
				ArrayList<RunThing> working = new ArrayList<>();
				Map<RunThing, Integer> iterationMap = new HashMap<>();
				Map<RunThing, String> nameMap = new HashMap<>();
				for (RunThing t : things) {
					if (!t.isFinished()) {
						working.add(t);
						iterationMap.put(t, t.getI());
						nameMap.put(t, t.getTname());
//						t.pauseThread();
					}
				}
				if (working.size() > 0) {
					choiceMap = new HashMap<>();
					menu = "Which thread would you like to know about?\n\n";
					menu += "\t\tThread\t\tIteration\n";
					for (int i = 0; i < working.size(); i++) {
						RunThing runThing = working.get(i);
						menu += (i + 1) + ")\t\t" + nameMap.get(runThing) + "\t\t" + iterationMap.get(runThing) + "\n";
						choiceMap.put(i + 1, "check" + nameMap.get(runThing));
					}
					menu += "\n> ";
				} else {
					menu = "It looks like all jobs have finished.\n\n" + defaultmenu;
					choiceMap = defaultMap;
				}
			} else if (result.startsWith("Thread-")) {
				for (RunThing t : things) {
					if (t.getTname().equals(result)) {
						System.out.println("Hash Results:");
						System.out.println(Arrays.toString(t.getHashes()));
						menu = "\n\n" + defaultmenu;
						choiceMap = defaultMap;
						break;
					}
				}
			} else if (result.contains("checkThread-")) {
				for (RunThing t : things) {
					String threadName = result.substring(result.indexOf("Thread-"));
					if (t.getTname().equals(threadName)) {
						menu = "Thread actions for " + threadName + ":\n\n"
								+ "1) Stop job\n"
								+ "2) See unfinished results\n"
								+ (t.isPaused() ? "3) Resume job\n" : "3) Pause job\n")
								+ "4) Back\n"
								+ "0) Exit program\n\n";
						choiceMap = new HashMap<>();
						choiceMap.put(1, "stop" + threadName);
						choiceMap.put(2, "see" + threadName);
						choiceMap.put(3, (t.isPaused() ? "resume" : "pause") + threadName);
						choiceMap.put(4, "back");
						choiceMap.put(5, "exit");
						break;
					}
				}
			} else if (result.contains("stopThread-") || result.contains("seeThread-") || result.contains("pauseThread-") || result.contains("resumeThread-")) {
				String action = result.substring(0, result.indexOf("Thread-"));
				String thread = result.substring(result.indexOf("Thread-"));
				RunThing rt = null;
				for (RunThing t : things) {
					if (t.getTname().equals(thread)) {
						rt = t;
					}
				}
				if (rt != null) {
					switch (action) {
						case "stop":
							rt.setGoTimmyGo(false);
							System.out.println(rt.getTname() + " has been cancelled");
							break;
						case "see":
							String[] hashes = rt.getHashList().toArray(new String[rt.getHashList().size()]);
							System.out.println("Results:\n" + Arrays.toString(hashes) + "");
							break;
						case "pause":
							rt.setPaused(true);
							break;
						case "resume":
							rt.setPaused(false);
							break;
					}
				}
				menu = "\n\n" + defaultmenu;
				choiceMap = defaultMap;
			} else if (result.equals("back")) {
				menu = "\n\n" + defaultmenu;
				choiceMap = defaultMap;
			}
		}
		for (RunThing thing1 : things) {
			thing1.stopThread();
		}
	}

}
