package com.amazonaws.lambda.comment;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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
	
	private Message msg;
	private Session session;
	
	public EmailDispacter() throws MessagingException {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);
		properties.setProperty("mail.smtps.auth","true");
		session = Session.getDefaultInstance(properties);
		
		
		msg = new MimeMessage(session);
		
		msg.setFrom(new InternetAddress(FROM));
	}
	
	public void setRecipients(List<String> emails) throws MessagingException {
		InternetAddress[] recipients = new InternetAddress[emails.size()];
		int i = 0;
		for(String email: emails) {
			recipients[i++] = new InternetAddress(email);
		}
		
		msg.addRecipients(Message.RecipientType.BCC, recipients);
	}
	
	public void buildMessage(
			String refrence, String address, String description, String url, List<CommentPOJO> comments) 
					throws MessagingException {
		
		
		msg.setSubject(String.format("BOTL Project: New comment for %s", refrence));
		
		String commentsHTML = comments.stream()
				.map(c -> {
						return String.format("<tr><td>%s</td><td>%s</td></tr>", c.getName(), c.getComment());
					})
				.collect(Collectors.joining());
		
		String messageHTML = String.format(
				  "<p>%s</p>"
				+ "<p>%s</p>"
				+ "<p><a href=\"%s\">View on LPA website</a></p>"
				+ "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%%\">"
					+ "<tr>"
						+ "<th>Name</th><th>Comment</th>"
					+ "</tr>"
					+ "%s"
				+ "</table>", address, description, url.replace("&", "&amp;"), commentsHTML);
		
		msg.setContent(messageHTML, "text/html");
	}
	
	public void dispatch() throws MessagingException {
		
		
		SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
        t.connect(HOST, USER, PASSWORD);
        t.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Response: " + t.getLastServerResponse());
        t.close();
	}
}
