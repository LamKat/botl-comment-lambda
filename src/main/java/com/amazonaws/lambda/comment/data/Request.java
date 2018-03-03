package com.amazonaws.lambda.comment.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {

	@JsonProperty("body")
	private String body;

	@JsonProperty("body")
	public String getBody() {
		return body;
	}
	@JsonProperty("body")
	public  void setBody(String body) {
		this.body = body;
	}

	public EscapedRequest parse() {
	    Map<String, String> params = new HashMap<String, String>();
	    for (String param : body.split("&")) {
	    	String[] keyPair = param.split("=");
	        String key = keyPair[0];
	        String value = keyPair[1];
	        params.put(key, value);
	    }
	    
		return new EscapedRequest(params);
	}
}
