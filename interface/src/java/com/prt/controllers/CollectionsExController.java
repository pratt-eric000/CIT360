/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.controllers;

import com.prt.models.Globals;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
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
@ManagedBean(name = "collectionsController")
@ViewScoped
public class CollectionsExController implements Serializable {

	@ManagedProperty("#{globals}")
	private Globals globals;
	private Set<String> set;
	private List<String> list;
	private Map<String, String> map;
	private Queue<String> queue;
	private TreeSet treeset;
	private String listitem;
	private String setitem;
	private String mapkey;
	private String mapvalue;
	private String queueitem;
	private String treesetitem;

	public String getListitem() {
		return listitem;
	}

	public void setListitem(String listitem) {
		this.listitem = listitem;
	}

	public String getSetitem() {
		return setitem;
	}

	public void setSetitem(String setitem) {
		this.setitem = setitem;
	}

	public String getMapkey() {
		return mapkey;
	}

	public void setMapkey(String mapkey) {
		this.mapkey = mapkey;
	}

	public String getMapvalue() {
		return mapvalue;
	}

	public void setMapvalue(String mapvalue) {
		this.mapvalue = mapvalue;
	}

	public String getQueueitem() {
		return queueitem;
	}

	public void setQueueitem(String queueitem) {
		this.queueitem = queueitem;
	}

	public String getTreesetitem() {
		return treesetitem;
	}

	public void setTreesetitem(String treesetitem) {
		this.treesetitem = treesetitem;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Queue<String> getQueue() {
		return queue;
	}

	public void setQueue(Queue<String> queue) {
		this.queue = queue;
	}

	public TreeSet getTreeset() {
		return treeset;
	}

	public void setTreeset(TreeSet treeset) {
		this.treeset = treeset;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Globals getGlobals() {
		return globals;
	}

	public void setGlobals(Globals globals) {
		this.globals = globals;
	}

	@PostConstruct
	void init() {
		list = new ArrayList<>();
		set = new HashSet<>();
		map = new HashMap<>();
		queue = new LinkedList<>();
		treeset = new TreeSet();
	}

	public void addListItem() {
		list.add(listitem);
		listitem = "";
	}

	public void addSetItem() {
		set.add(setitem);
		setitem = "";
	}

	public void addMapItem() {
		if (mapkey != null && !mapkey.equals("") && mapvalue != null && !mapvalue.equals("")) {
			map.put(mapkey, mapvalue);
			mapkey = "";
			mapvalue = "";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please fill in both fields to add map item"));
		}
	}

	public void addQueueItem() {
		queue.add(queueitem);
		queueitem = "";
	}

	public void addTreesetItem() {
		treeset.add(treesetitem);
		treesetitem = "";
	}

	public List<String> getSetValues() {
		List<String> result = new ArrayList<>();
		result.addAll(set);
		return result;
	}

	public List<Map.Entry<String, String>> getMapEntries() {
		Set<Map.Entry<String, String>> results = map.entrySet();
		return new ArrayList<>(results);
	}

	public void printList(Object listobject) {
		StringBuilder output = new StringBuilder();
		if (listobject instanceof ArrayList) {
			String separator = "";
			for (String item : list) {
				output.append(separator).append(item);
				separator = ", ";
			}
		} else if (listobject instanceof HashSet) {
			String separator = "";
			for (String item : set) {
				output.append(separator).append(item);
				separator = ", ";
			}
		} else if (listobject instanceof HashMap) {
			Iterator<String> keys = map.keySet().iterator();
			String separator = "";
			while (keys.hasNext()) {
				String key = keys.next();
				String value = map.get(key);
				output.append(separator).append("Map Key: ").append(key).append("\tMap Value: ").append(value);
				separator = ", ";
			}
		} else if (listobject instanceof LinkedList) {
			String separator = "";
			while (queue.size() > 0) {
				String item = queue.poll();
				output.append(separator).append(item);
				separator = ", ";
			}
		} else if (listobject instanceof TreeSet) {
			Iterator<String> treeList = treeset.iterator();
			String separator = "";
			while (treeList.hasNext()) {
				String item = treeList.next();
				output.append(separator).append(item);
				separator = ", ";
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", output.toString()));
	}
}
