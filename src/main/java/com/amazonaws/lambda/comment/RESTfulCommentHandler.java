package com.amazonaws.lambda.comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.mail.MessagingException;

import com.amazonaws.lambda.comment.data.EscapedRequest;
import com.amazonaws.lambda.comment.data.Request;
import com.amazonaws.lambda.comment.data.Response;
import com.amazonaws.lambda.comment.data.SuccessResponce;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RESTfulCommentHandler implements RequestHandler<Request, Response> {
	private static String password = System.getenv("BOTL_DATABASE_PASSWORD");
	private static String username = System.getenv("BOTL_DATABASE_USERNAME");
	private static String endpoint = System.getenv("BOTL_DATABASE_ENDPOINT");
	private static String port = System.getenv("BOTL_DATABASE_PORT");
	final static String INSERT_QUERY = "INSERT INTO comments (ApplicationFK, Comment, Name, Email) VALUES (?, ?, ?, ?)";
	final static String GET_WATCHERS_QUERY = "SELECT Refrence, Address, Description, URL, "
			+ "CONCAT('[', GROUP_CONCAT(CONCAT('{\"Name\": \"', comm.Name, '\", \"Comment\": \"', comm.Comment, '\"}') SEPARATOR ', '), ']') as Comments, "
			+ "GROUP_CONCAT(comm.Email Separator ';') as Emails "
			+ "FROM applications LEFT JOIN comments comm ON ApplicationID = comm.ApplicationFK "
			+ "WHERE ApplicationID = ?; ";
	
    @Override
    public Response handleRequest(Request input, Context context) {
        context.getLogger().log("BODY: " + input.getBody() + "\n");
        
        EscapedRequest req = input.parse();
       
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://" + endpoint + ":" + port + "?useSSL=false", 
					username, 
					password);
	    	conn.setCatalog("botl");
	    	
			int watchers = updateWatchers(req, conn);
	        context.getLogger().log("INFO: number of watchers is " + watchers + "\n");
			
			writeToDatabase(req, conn);
			
			
			
		} catch (SQLException | MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        
		
		
        return new SuccessResponce();
    }

	private void writeToDatabase(EscapedRequest req, Connection conn) throws SQLException {
    	PreparedStatement ps = conn.prepareStatement(INSERT_QUERY);
    	ps.setInt(1, req.getApplication());
    	ps.setString(2, req.getComment());
    	ps.setString(3, req.getName());
    	if(req.hasEmail())
    		ps.setString(4, req.getEmailString());
    	else
    		ps.setNull(4, Types.VARCHAR);
    	ps.executeUpdate();
	}



	private int updateWatchers(EscapedRequest req, Connection conn) throws SQLException, MessagingException {
		PreparedStatement ps = conn.prepareStatement(GET_WATCHERS_QUERY);
		ps.setInt(1, req.getApplication());
		ResultSet rs = ps.executeQuery();
		rs.next();
		String emailsString = rs.getString("Emails");
		if(emailsString == null) {
			return 0;
		}
		String[] emails = emailsString.split(";");
		EmailDispacter.dispatch(emails);
		
		
		return emails.length;
	}
	
}
