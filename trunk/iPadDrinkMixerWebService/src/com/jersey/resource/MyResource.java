package com.jersey.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.jersey.dao.MyDAO;
import com.jersey.model.TestModel;


public class MyResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	
	public MyResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	//Application integration 		
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public TestModel getTodo() {
		TestModel todo = MyDAO.instance.getModel().get(id);
		if(todo==null)
			throw new RuntimeException("Get: Todo with " + id +  " not found");
		return todo;
	}
	
	// For the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public TestModel getTodoHTML() {
		TestModel todo = MyDAO.instance.getModel().get(id);
		if(todo==null)
			throw new RuntimeException("Get: Todo with " + id +  " not found");
		return todo;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTodo(JAXBElement<TestModel> todo) {
		TestModel c = todo.getValue();
		return putAndGetResponse(c);
	}
	
	@DELETE
	public void deleteTodo() {
		TestModel c = MyDAO.instance.getModel().remove(id);
		if(c==null)
			throw new RuntimeException("Delete: Todo with " + id +  " not found");
	}
	
	private Response putAndGetResponse(TestModel todo) {
		Response res;
		if(MyDAO.instance.getModel().containsKey(todo.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		MyDAO.instance.getModel().put(todo.getId(), todo);
		return res;
	}
}
