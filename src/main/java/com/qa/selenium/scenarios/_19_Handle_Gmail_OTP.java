package com.qa.selenium.scenarios;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.AndTerm;
import jakarta.mail.search.FromStringTerm;
import jakarta.mail.search.SearchTerm;
import jakarta.mail.search.SubjectTerm;

public class _19_Handle_Gmail_OTP {

	@Test(priority = 1, enabled = true)
	private void mockSendOTPEmail() throws Exception {
		sendEmail("test004@gmail.com", "abc@xyz", "test001@gmail.com", "Your Secure OTP",
				"Your secure OTP for login is 879245");
	}

	@Test(priority = 2, enabled = true)
	private void mockReadOTPEmail() {
		readEmail("test001@gmail.com", "abc@xyz", "test004@gmail.com", "Your Secure OTP", "Inbox");
	}

	private static void sendEmail(String username, String password, String to, String subject, String mailContent) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);

			// Create a multipart message body
			MimeMultipart multipart = new MimeMultipart();

			// Create a text part
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(mailContent);
			multipart.addBodyPart(textPart);

			// Set the content of the message
			message.setContent(multipart);

			Transport.send(message);

			System.out.println("Email sent successfully.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private static void readEmail(String username, String password, String sender, String subject, String folderType) {

		// Gmail account credentials
		String subjectKeywords = subject;
		String folderName = folderType;

		// Gmail IMAP server settings
		String imapHost = "imap.gmail.com";
		int imapPort = 993;

		// Set properties
		Properties props = new Properties();
		props.put("mail.imap.host", imapHost);
		props.put("mail.imap.port", imapPort);
		props.put("mail.imap.ssl.enable", "true");

		// Create session
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Connect to the mailbox
			Store store = session.getStore("imap");
			store.connect(imapHost, username, password);

			// Open the specified folder
			Folder folder = store.getFolder(folderName);
			folder.open(Folder.READ_ONLY);

			// Create a search term for unread messages from the specified sender and
			// subject
			SearchTerm searchTerm = new AndTerm(new FromStringTerm(sender), new SubjectTerm(subjectKeywords));

			// Search for messages matching the search term
			Message[] messages = folder.search(searchTerm);

			// Read and print content of matching unread messages
			for (Message message : messages) {
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Date: " + message.getSentDate());

				// Read message content
				try {
					String content = getTextFromMessage(message);
					System.out.println("Content:\n" + content);
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("-------------------------");
			}

			// Close resources
			folder.close(false);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		int count = mimeMultipart.getCount();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result.append(bodyPart.getContent());
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result.append(html);
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
			}
		}
		return result.toString();
	}
}
