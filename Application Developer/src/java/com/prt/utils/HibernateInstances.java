/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.utils;

import com.prt.hibernate.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author P-ratt
 */
public class HibernateInstances {

	private HibernateUtil hibernateUtil;

	public Session getSession() throws Exception {
		try {
			return hibernateUtil.getSessionFactory().openSession();
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
}
