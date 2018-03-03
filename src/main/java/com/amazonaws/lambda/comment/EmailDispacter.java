package com.amazonaws.lambda.comment;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class EmailDispacter {
	

	private static String FROM = System.getenv("FROM_EMAIL");
	
	private static String HOST = System.getenv("FROM_EMAIL_SERVER");
	private static String USER = System.getenv("FROM_EMAIL_USER");
	private static String PASSWORD = System.getenv("FROM_EMAIL_PASSWORD");
	
	
	
	
	public static void dispatch(String[] recips) throws MessagingException {
		InternetAddress[] recipients = new InternetAddress[recips.length];
		for(int i = 0; i < recips.length; i++) {
			recipients[i] = new InternetAddress(recips[i]);
		}
		
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);
		properties.setProperty("mail.smtps.auth","true");
		Session session = Session.getDefaultInstance(properties);
		
		
		// Create a default MimeMessage object.
		Message message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress(FROM));
		message.addRecipients(Message.RecipientType.BCC, recipients);
		
		
		
		message.setSubject("This is the Subject Line!");
		message.setContent("<h1>This is actual message</h1>", "text/html");

		
		SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
        t.connect(HOST, USER, PASSWORD);
        t.sendMessage(message, message.getAllRecipients());
        System.out.println("Response: " + t.getLastServerResponse());
        t.close();
		

	}
}
