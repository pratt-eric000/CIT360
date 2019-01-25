/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.prt.models.Animal;
import com.prt.models.Cat;
import com.prt.models.Dog;
import com.prt.models.Globals;
import com.prt.models.Goat;
import com.prt.models.Panda;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author P-ratt
 */
@ManagedBean(name = "applicationController")
@ViewScoped
public class ApplicationControllerExController implements Serializable {

	@ManagedProperty("#{globals}")
	private Globals globals;
	HashMap<String, Animal> animalHandler;

	public HashMap<String, Animal> getAnimalHandler() {
		return animalHandler;
	}

	public void setAnimalHandler(HashMap<String, Animal> animalHandler) {
		this.animalHandler = animalHandler;
	}

	public Globals getGlobals() {
		return globals;
	}

	public void setGlobals(Globals globals) {
		this.globals = globals;
	}

	public List<String> getAnimals() {
		List<String> animals = new ArrayList<>();
		animals.addAll(animalHandler.keySet());
		return animals;
	}

	@PostConstruct
	void init() {
		animalHandler = new HashMap<>();
		animalHandler.put("Dog", new Dog());
		animalHandler.put("Cat", new Cat());
		animalHandler.put("Goat", new Goat());
		animalHandler.put("Panda", new Panda());
	}

	public void makeNoise(String animal) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Hello", animalHandler.get(animal).makeNoise()));
	}
}
