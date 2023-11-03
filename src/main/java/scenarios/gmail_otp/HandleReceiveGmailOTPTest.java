package scenarios.gmail_otp;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.AndTerm;
import jakarta.mail.search.FromStringTerm;
import jakarta.mail.search.SearchTerm;
import jakarta.mail.search.SubjectTerm;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class HandleReceiveGmailOTPTest {

	// Defines the IMAP host as "imap.gmail.com" for handling Gmail IMAP configuration
	public final String imapHost = "imap.gmail.com";

	// Defines the IMAP port as 993 for handling the port configuration during IMAP connections
	public final int imapPort = 993;

	@Test(priority = 1)
	public void mockReadOTPEmail() {
		// Print the OTP retrieved from the specified email parameters
		System.out.println(retrieveOTP("test004@gmail.com", "abc@xyz", "test001@gmail.com", "Secure Code", "Inbox"));
	}

	public String retrieveOTP(String recipientEmail, String recipientPassword, String senderEmail, String subject, String folderType) {
		try {
			// Connects to the mailbox using the recipient's email and password
			Store store = connectToMailbox(recipientEmail, recipientPassword);

			// Opens the specified folder in the mailbox
			Folder folder = openFolder(store, folderType);

			// Searches for emails based on the sender's email and subject
			Message[] messages = searchEmails(folder, senderEmail, subject);

			// Checks if there are no messages received
			if (messages.length < 1) {
				// Throws an exception if no OTP is received
				throw new Exception("No OTP received");
			} else {
				// Retrieves the latest message from the array
				Message latestMessage = messages[messages.length - 1];

				// Displays details of the latest email message
				displayEmailDetails(latestMessage);

				// Extracts the OTP from the latest message and returns it
				return extractOTPFromMessage(latestMessage);
			}
		} catch (Exception e) {
			// Prints the stack trace if an exception occurs
			e.printStackTrace();
			// Returns an empty string in case of an exception
			return "";
		}

	}

	private Store connectToMailbox(String email, String password) throws MessagingException {
		// Sets up the properties for the IMAP connection
		Properties imapProperties = new Properties();
		// Sets the IMAP host
		imapProperties.put("mail.imap.host", imapHost);
		// Sets the IMAP port
		imapProperties.put("mail.imap.port", imapPort);
		// Enables SSL for the connection
		imapProperties.put("mail.imap.ssl.enable", "true");

		// Establishes an IMAP session using the provided properties and authenticator
		Session imapSession = Session.getInstance(imapProperties, new Authenticator() {
			// Retrieves the email and password for authentication
			protected PasswordAuthentication getPasswordAuthentication() {
				// Authenticates the email and password
				return new PasswordAuthentication(email, password);
			}
		});

		// Gets the store for the IMAP session
		Store store = imapSession.getStore("imap");

		// Connects to the mailbox using the IMAP host, email, and password
		store.connect(imapHost, email, password);

		// Returns the connected store
		return store;
	}


	private Folder openFolder(Store store, String folderName) throws MessagingException {
		// Gets the specific folder from the store
		Folder folder = store.getFolder(folderName);

		// Opens the folder in read-only mode
		folder.open(Folder.READ_ONLY);

		// Returns the opened folder
		return folder;
	}


	private Message[] searchEmails(Folder folder, String senderEmail, String subject) throws MessagingException {
		// Creates a search term based on the sender's email and the subject of the email
		SearchTerm searchTerm = new AndTerm(new FromStringTerm(senderEmail), new SubjectTerm(subject));

		// Searches for messages in the folder based on the search term
		return folder.search(searchTerm);
	}

	private void displayEmailDetails(Message message) throws MessagingException {
		// Prints the subject of the message
		System.out.println("Subject: " + message.getSubject());

		// Prints the sender of the message
		System.out.println("From: " + message.getFrom()[0]);

		// Prints the date the message was sent
		System.out.println("Date: " + message.getSentDate());
	}


	private String extractOTPFromMessage(Message message) throws MessagingException, IOException {
		// Check if the message is of type 'text/plain'
		if (message.isMimeType("text/plain")) {
			// Extracts the OTP from the text content of the message
			return extractOTPFromText(message.getContent().toString());
		}
		// Check if the message is of type 'multipart/*'
		else if (message.isMimeType("multipart/*")) {
			// Extracts the OTP from the multipart content of the message
			return extractOTPFromMultipart((MimeMultipart) message.getContent());
		}
		// Returns an empty string if the message is not of 'text/plain' or 'multipart/*' type
		return "";
	}


	private String extractOTPFromText(String text) {
		// Regular expression to match non-digit characters
		String otpRegex = "\\D+";
		// Split the text using the regular expression
		String[] otpText = text.split(otpRegex);
		// Check if the array length is at least 3
		if (otpText.length >= 2) {
			// Extract and return the third element from the array as the OTP
			return otpText[1];
		}
		// Returns an empty string if the array length is less than 2
		return "";
	}

	private String extractOTPFromMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		// Iterate through the mimeMultipart to extract the OTP
		for (int i = 0; i < mimeMultipart.getCount(); i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			// Check if the body part is of MIME type text/plain
			if (bodyPart.isMimeType("text/plain")) {
				// Extract and return the OTP from the text body part
				return extractOTPFromText(bodyPart.getContent().toString());
			}
			// Check if the body part is of MIME type text/html
			else if (bodyPart.isMimeType("text/html")) {
				// Extract and return the OTP from the HTML body part
				return extractOTPFromText((String) bodyPart.getContent());
			}
			// Check if the body part content is another MimeMultipart
			else if (bodyPart.getContent() instanceof MimeMultipart) {
				// Recursive call to extract the OTP from the inner MimeMultipart
				return extractOTPFromMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		// Returns an empty string if no OTP is found
		return "";
	}

}
