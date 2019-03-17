/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erc.prt;

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
public class OddBallObject {

	public static String[] hashes = new String[0];
	public static boolean finished = false;

	public static void doTheThings(String name, int hashCount) {
		finished = false;
		System.out.println(name + " is asking me to do things.");
		try {
			Random random = new Random();
			byte[] things = new byte[16];
			byte[] salt = new byte[16];
			ArrayList<String> hashList = new ArrayList<>();
			for (int i = 0; i < hashCount; i++) {
				random.nextBytes(things);
				random.nextBytes(salt);
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
				PBEKeySpec spec = new PBEKeySpec(new String(things, "UTF-8").toCharArray(), salt, 10000, 256);
				SecretKey key = factory.generateSecret(spec);
				hashList.add(new String(Base64.getEncoder().encode(key.getEncoded()), "UTF-8"));
			}
			while (hashList.size() > hashCount) {
				hashList.remove(hashList.size() - 1);
			}
			hashes = hashList.toArray(new String[hashList.size()]);
			System.out.println("\n" + name + " has finished building the hashes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finished = true;
	}

}
