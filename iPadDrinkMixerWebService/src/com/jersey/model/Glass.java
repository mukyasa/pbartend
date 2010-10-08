package com.jersey.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Glass {

	private String name;
	private int id;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
