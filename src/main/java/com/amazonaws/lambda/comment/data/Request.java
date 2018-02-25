package com.amazonaws.lambda.comment.data;

public class Request {
	private String comment;
	private String application;
	private String name;
	private String email;
	
	public String getComment() {
		return comment;
	}
	public  void setComment(String comment) {
		this.comment = comment;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
