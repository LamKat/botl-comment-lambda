package com.amazonaws.lambda.comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.comment.data.Request;
import com.amazonaws.lambda.comment.data.Response;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RESTfulCommentHandlerTest {

    private static Request input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new Request();
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    //@Test
    public void testRESTfulCommentHandler() {
        RESTfulCommentHandler handler = new RESTfulCommentHandler();
        Context ctx = createContext();

        Response output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
    
    @Test
    public void t() {
    	String refrence = "ref";
    	String address = "add";
    	String description = "desc";
    	String url = "url";
    	List<CommentPOJO> comments = new ArrayList<CommentPOJO>();
    	CommentPOJO cp = new CommentPOJO();
    	cp.setName("namey");
    	cp.setComment("commenty");
    	comments.add(cp);
		String commentsHTML = comments.stream()
				.map(c -> {
						return String.format("<tr><td>%s</td><td>%s</td></tr>", c.getName(), c.getComment());
					})
				.collect(Collectors.joining());
		
		
		
		System.out.println(String.format(
				  "<p>%s</p>"
				+ "<p>%s</p>"
				+ "<p><a href=\"%s\">View on LPA website</a></p>"
				+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%%\">"
					+ "<tr>"
						+ "<th>Name</th><th>Comment</th>"
					+ "</tr>"
					+ "%s"
				+ "</table>", address, description, url.replace("&", "&amp;"), commentsHTML));
    }
}
