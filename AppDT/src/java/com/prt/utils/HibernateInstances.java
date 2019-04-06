/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.utils;

import com.prt.hibernate.HibernateUtil;
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
		Session session = hibernateUtil.getSessionFactory().openSession();
		//check to see if default user is created. If not create it
		checkForDefaultUser(session);
		return session;
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
		List<User> admin = session.createQuery("SELECT * FROM USERS WHERE USERNAME = :username").setParameter("username", "admin").list();
		if (admin == null || admin.isEmpty()) {
			try {
				//create user since it doesn't exist
				byte[] salt = new byte[16];
				Random random = new Random();
				random.nextBytes(salt);
				String saltStr = new String(Base64.getEncoder().encode(salt), "UTF-8");
				String password = EncryptionHelper.encrypt("admin", salt);
				String query = "INSERT INTO PASSWORDS (PASSWORD, SALT) VALUES (:password, :salt)";
				session.createQuery(query).setParameter("password", password).setParameter("salt", saltStr).executeUpdate();
				List lastIdList = session.createQuery("SELECT LAST_INSERT_ID()").list();
				String id = lastIdList.get(0).toString();
				query = "INSERT INTO USERS (FIRST_NAME, USERNAME, PASSWORD_ID) VALUES (:firstname, :username, :passwordid)";
				session.createQuery(query).setParameter("firstname", "admin").setParameter("username", "admin").setParameter("passwordid", id).executeUpdate();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
