/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erc.prt;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author P-ratt
 */
public class Job extends Thread {

	private String name;
	private int iteration;
	private boolean finished = false;
	private String[] hashes = new String[0];
	private ArrayList<String> hashList = new ArrayList<>();
	private boolean goTimmyGo;
	private int hashCount = -1;

	public int getHashCount() {
		return hashCount;
	}

	public void setHashCount(int hashCount) {
		this.hashCount = hashCount;
	}

	public String getTName() {
		return name;
	}

	public void setTName(String name) {
		this.name = name;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String[] getHashes() {
		return hashes;
	}

	public void setHashes(String[] hashes) {
		this.hashes = hashes;
	}

	public ArrayList<String> getHashList() {
		return hashList;
	}

	public void setHashList(ArrayList<String> hashList) {
		this.hashList = hashList;
	}

	public boolean isGoTimmyGo() {
		return goTimmyGo;
	}

	public void setGoTimmyGo(boolean goTimmyGo) {
		this.goTimmyGo = goTimmyGo;
	}

	public Job(String name) {
		this.name = name;
		goTimmyGo = true;
	}

	public Job(String name, int hashCount) {
		this.hashCount = hashCount;
		this.name = name;
		goTimmyGo = true;
	}

	public void doTheThings() {
		finished = false;
		try {
			Random random = new Random();
			byte[] things = new byte[16];
			byte[] salt = new byte[16];
			hashList = new ArrayList<>();
			for (iteration = 0; iteration < hashCount; iteration++) {
				if (!goTimmyGo) {
					break;
				}
				random.nextBytes(things);
				random.nextBytes(salt);
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
				PBEKeySpec spec = new PBEKeySpec(new String(things, "UTF-8").toCharArray(), salt, 10000, 256);
				SecretKey key = factory.generateSecret(spec);
				hashList.add(new String(Base64.getEncoder().encode(key.getEncoded()), "UTF-8"));
			}
			if (goTimmyGo) {
				while (hashList.size() > hashCount) {
					hashList.remove(hashList.size() - 1);
				}
				hashes = hashList.toArray(new String[hashList.size()]);
			}
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		finished = true;
	}
}
