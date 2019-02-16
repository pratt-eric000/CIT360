/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "dbController")
@SessionScoped
public class DB {

	private Members member;
	private HibernateUtil helper;
	private Session session;
	private String username;
	private List<Members> members = new ArrayList<>();
	private String firstname;
	private String lastname;
	private String email;
	private String password;

	@PostConstruct
	void init() {
		getAllMembers();
	}

	public Members getMember() {
		return member;
	}

	public void setMember(Members member) {
		this.member = member;
	}

	public List<Members> getMembers() {
		return members;
	}

	public void setMembers(List<Members> members) {
		this.members = members;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addMember() {
		member = new Members(username, firstname, lastname, email, password);
		session = helper.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(member);
		session.getTransaction().commit();
		session.close();
		members.clear();
		getAllMembers();
		username = "";
		firstname = "";
		lastname = "";
		email = "";
		password = "";
	}

	public void getAllMembers() {
		session = helper.getSessionFactory().openSession();
		members = session.createCriteria(Members.class).list();
	}

	public void removeMember(Members member) {
		session = helper.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(member);
		session.getTransaction().commit();
		members.clear();
		getAllMembers();
	}
}
