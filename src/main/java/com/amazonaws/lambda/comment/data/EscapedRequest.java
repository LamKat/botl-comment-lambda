package com.amazonaws.lambda.comment.data;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.owasp.encoder.Encode;

public class EscapedRequest {
	private static final Function<Map<String, String>, String> GET_COMMENT = p -> p.get("comment");
	private static final Function<Map<String, String>, String> GET_APPLICATION = p -> p.get("application");
	private static final Function<Map<String, String>, String> GET_NAME = p -> p.get("name");
	private static final Function<Map<String, String>, String> GET_EMAIL = p -> p.get("email");
	private static final Function<String, String> NEWLINE_ENCODE = s -> s.replaceAll("(\r\n|\n)", "<br />");
	
	
	private String comment;
	private Integer application;
	private String name;
	private Optional<InternetAddress> email;

	public EscapedRequest(Map<String, String> params) {
		Optional<Map<String, String>> opParams = Optional.of(params);
		this.comment = opParams.map(GET_COMMENT)
				.map(this::urlDecode)
				.map(Encode::forHtmlAttribute)
				.map(NEWLINE_ENCODE)
				.orElseThrow(() -> new IllegalArgumentException("Missing comment"));
		
		this.application = opParams.map(GET_APPLICATION)
				.map(Integer::parseInt)
				.orElseThrow(() -> new IllegalArgumentException("Missing application id"));
		
		this.name = opParams.map(GET_NAME)
				.map(this::urlDecode)
				.map(Encode::forHtmlAttribute)
				.map(NEWLINE_ENCODE)
				.orElseThrow(() -> new IllegalArgumentException("Missing name"));
		
		this.email = opParams.map(GET_EMAIL)
				.map(this::urlDecode)
				.map(this::emailStringToInternetAddress);
	}

	public Integer getApplication() {
		return this.application;
	}

	public String getComment() {
		return this.comment;
	}

	public String getName() {
		return this.name;
	}

	public boolean hasEmail() {
		return this.email.isPresent();
	}

	public String getEmailString() {
		return this.email
				.map(InternetAddress::getAddress)
				.orElseThrow(() -> new RuntimeException("Trying to get empty email"));
	}

	private InternetAddress emailStringToInternetAddress(String email) {
		InternetAddress addr = null;
	    try {
	        addr = new InternetAddress(email);
	        addr.validate();
	    } catch (AddressException e) {
	        throw new IllegalArgumentException("Invalid email adress", e);
	    }
	    return addr;
	}
	
	private String urlDecode(String encodedString) {
		try {
			return URLDecoder.decode(encodedString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
