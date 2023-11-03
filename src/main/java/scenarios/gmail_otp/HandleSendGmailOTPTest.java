package scenarios.gmail_otp;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.testng.annotations.Test;

import java.util.Properties;

public class HandleSendGmailOTPTest {

	// SMTP host for sending emails
    public final String emailHost = "smtp.gmail.com";

    // Port for SMTP communication
    public final int smtpPort = 587;

    @Test(priority = 1)
    public void mockSendOTPEmail() {
        // Send the OTP email with the provided email parameters
        sendOTP("test001@gmail.com", "abc@xyz", "test004@gmail.com", "Secure Code",
                "Your secure OTP for login is 110123");
    }


    public void sendOTP(String senderEmail, String senderPassword, String recipientEmail, String subject, String content) {
        // SMTP properties setup
        Properties smtpProperties = new Properties();
		// Set SMTP authentication to true
        smtpProperties.put("mail.smtp.auth", "true");
		// Enable TLS for secure connection
        smtpProperties.put("mail.smtp.starttls.enable", "true");
		// Set the SMTP host
        smtpProperties.put("mail.smtp.host", emailHost);
		// Set the SMTP port
        smtpProperties.put("mail.smtp.port", smtpPort);

		// Establishes a session using SMTP properties and authenticates the sender's credentials
		Session smtpSession = Session.getInstance(smtpProperties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// Authenticate the sender
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {
            // Create message
            Message message = new MimeMessage(smtpSession);
			// Set the sender's email address
            message.setFrom(new InternetAddress(senderEmail));
			// Set the recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			// Set the email subject
            message.setSubject(subject);

            // Create body part for the text
            BodyPart textBodyPart = new MimeBodyPart();
			// Set the content of the email
            textBodyPart.setText(content);

			// Creates a new MIME multipart object for handling multiple body parts of the message
			Multipart multipart = new MimeMultipart();
			// Adds the text body part to the multipart content
			multipart.addBodyPart(textBodyPart);

			// Set content of the message
            message.setContent(multipart);

			// Send the email
            Transport.send(message);

			// Print success message
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
			// Print stack trace in case of exception
            e.printStackTrace();
        }
    }

}
