package com.jersey.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestModel {

	private String id;
	private String summary;
	private String description;
	
	public TestModel(){
		
	}
	public TestModel (String id, String summary){
		this.id = id;
		this.summary = summary;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
