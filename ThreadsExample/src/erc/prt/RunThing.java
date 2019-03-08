/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erc.prt;

/**
 *
 * @author P-ratt
 */
public class RunThing implements Runnable {

	private Thread t;
	private String tname;

	public RunThing(String name) {
		tname = name;
	}

	@Override
	public void run() {
		try {
			OddBallObject.doTheThings(tname);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
