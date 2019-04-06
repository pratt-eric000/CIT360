/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.utils;

import com.prt.hibernate.HibernateUtil;
import com.prt.models.Password;
import com.prt.models.User;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import org.hibernate.Session;

/**
 *
 * @author P-ratt
 */
public class HibernateInstances {

	private HibernateUtil hibernateUtil;

	public Session getSession() throws Exception {
		try {
			Session session = hibernateUtil.getSessionFactory().openSession();
			//check to see if default user is created. If not create it
			checkForDefaultUser(session);
			return session;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HibernateInstances() {
	}

	public static HibernateInstances getCurrent() {
		return InstanceHolder.INSTANCE;
	}

	private static class InstanceHolder {

		private static final HibernateInstances INSTANCE = new HibernateInstances();
	}

	public void checkForDefaultUser(Session session) {
		List<User> users = session.createCriteria(User.class).list();
		if (users == null || users.isEmpty()) {
			try {
				//create user since it doesn't exist
				byte[] salt = new byte[16];
				Random random = new Random();
				random.nextBytes(salt);
				String saltStr = new String(Base64.getEncoder().encode(salt), "UTF-8");
				String pword = EncryptionHelper.encrypt("admin", salt);
				Password password = new Password(pword, saltStr);
				session.beginTransaction();
				session.save(password);
				User user = new User("admin", "", "", "", "admin", 1, "", "");
				session.save(user);
				session.getTransaction().commit();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
