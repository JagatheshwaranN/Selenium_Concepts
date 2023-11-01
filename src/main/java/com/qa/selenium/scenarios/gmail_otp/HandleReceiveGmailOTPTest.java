package com.qa.selenium.scenarios.gmail_otp;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.AndTerm;
import jakarta.mail.search.FromStringTerm;
import jakarta.mail.search.SearchTerm;
import jakarta.mail.search.SubjectTerm;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class HandleReceiveGmailOTPTest {

	public final String emailHost = "smtp.gmail.com";
	public final int smtpPort = 587;
	public final String imapHost = "imap.gmail.com";
	public final int imapPort = 993;

	@Test(priority = 1)
	public void sendOTP(String senderEmail, String senderPassword, String recipientEmail, String subject, String content) {
		Properties smtpProperties = new Properties();
		smtpProperties.put("mail.smtp.auth", "true");
		smtpProperties.put("mail.smtp.starttls.enable", "true");
		smtpProperties.put("mail.smtp.host", emailHost);
		smtpProperties.put("mail.smtp.port", smtpPort);

		Session smtpSession = Session.getInstance(smtpProperties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {
			Message message = new MimeMessage(smtpSession);
			message.setFrom(new InternetAddress(senderEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject(subject);

			BodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(content);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Email sent successfully.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 2)
	public String retrieveOTP(String recipientEmail, String recipientPassword, String senderEmail, String subject, String folderType) {
		try {
			Store store = connectToMailbox(recipientEmail, recipientPassword);
			Folder folder = openFolder(store, folderType);
			Message[] messages = searchEmails(folder, senderEmail, subject);

			if (messages.length < 1) {
				throw new Exception("No OTP received");
			} else {
				Message latestMessage = messages[messages.length - 1];
				displayEmailDetails(latestMessage);
				return extractOTPFromMessage(latestMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private Store connectToMailbox(String email, String password) throws MessagingException {
		Properties imapProperties = new Properties();
		imapProperties.put("mail.imap.host", imapHost);
		imapProperties.put("mail.imap.port", imapPort);
		imapProperties.put("mail.imap.ssl.enable", "true");

		Session imapSession = Session.getInstance(imapProperties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});

		Store store = imapSession.getStore("imap");
		store.connect(imapHost, email, password);
		return store;
	}

	private Folder openFolder(Store store, String folderName) throws MessagingException {
		Folder folder = store.getFolder(folderName);
		folder.open(Folder.READ_ONLY);
		return folder;
	}

	private Message[] searchEmails(Folder folder, String senderEmail, String subject) throws MessagingException {
		SearchTerm searchTerm = new AndTerm(new FromStringTerm(senderEmail), new SubjectTerm(subject));
		return folder.search(searchTerm);
	}

	private void displayEmailDetails(Message message) throws MessagingException {
		System.out.println("Subject: " + message.getSubject());
		System.out.println("From: " + message.getFrom()[0]);
		System.out.println("Date: " + message.getSentDate());
	}

	private String extractOTPFromMessage(Message message) throws MessagingException, IOException {
		if (message.isMimeType("text/plain")) {
			return extractOTPFromText(message.getContent().toString());
		} else if (message.isMimeType("multipart/*")) {
			return extractOTPFromMultipart((MimeMultipart) message.getContent());
		}
		return "";
	}

	private String extractOTPFromText(String text) {
		String otpRegex = "\\D+";
		String[] otpText = text.split(otpRegex);
		if (otpText.length >= 3) {
			return otpText[2];
		}
		return "";
	}

	private String extractOTPFromMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		for (int i = 0; i < mimeMultipart.getCount(); i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				return extractOTPFromText(bodyPart.getContent().toString());
			} else if (bodyPart.isMimeType("text/html")) {
				return extractOTPFromText((String) bodyPart.getContent());
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				return extractOTPFromMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return "";
	}

}
