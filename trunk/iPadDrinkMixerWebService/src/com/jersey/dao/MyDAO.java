package com.jersey.dao;

import java.util.HashMap;
import java.util.Map;

import com.jersey.model.TestModel;

public enum MyDAO {
	instance;
	
	private Map<String, TestModel> contentProvider = new HashMap<String, TestModel>();
	
	private MyDAO() {
		
		TestModel todo = new TestModel("1", "Learn REST");
		todo.setDescription(" This is my description. ");
		contentProvider.put("1", todo);
		todo = new TestModel("2", "Do something");
		todo.setDescription(" This is my second description. ");
		contentProvider.put("2", todo);
		
	}
	public Map<String, TestModel> getModel(){
		return contentProvider;
	}
}
