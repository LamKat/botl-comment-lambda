package com.amazonaws.lambda.comment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RESTfulCommentHandler implements RequestHandler<Object, String> {
	private static String password = System.getenv("BOTL_DATABASE_PASSWORD");
	private static String username = System.getenv("BOTL_DATABASE_USERNAME");
	private static String endpoint = System.getenv("BOTL_DATABASE_ENDPOINT");
	private static String port = System.getenv("BOTL_DATABASE_PORT");
	
	
    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);

        return "Hello from Lambda!";
    }

}
