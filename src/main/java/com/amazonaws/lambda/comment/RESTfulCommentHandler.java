package com.amazonaws.lambda.comment;

import com.amazonaws.lambda.comment.data.Request;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RESTfulCommentHandler implements RequestHandler<Request, String> {
//	private static String password = System.getenv("BOTL_DATABASE_PASSWORD");
//	private static String username = System.getenv("BOTL_DATABASE_USERNAME");
//	private static String endpoint = System.getenv("BOTL_DATABASE_ENDPOINT");
//	private static String port = System.getenv("BOTL_DATABASE_PORT");
	
	
    @Override
    public String handleRequest(Request input, Context context) {
        context.getLogger().log("comment: " + input.getComment() + "\n");
        context.getLogger().log("application: " + input.getApplication() + "\n");
        context.getLogger().log("name: " + input.getName() + "\n");
        context.getLogger().log("email: " + input.getEmail() + "\n");

        return "Hello from Lambda!";
    }

}
