package com.amazonaws.lambda.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentPOJO {
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Comment")
	private String comment;

	@JsonProperty("Name")
	public String getName() {
		return name;
	}
	
	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("Comment")
	public String getComment() {
		return comment;
	}
	
	@JsonProperty("Comment")
	public void setComment(String comment) {
		this.comment = comment;
	}
}
