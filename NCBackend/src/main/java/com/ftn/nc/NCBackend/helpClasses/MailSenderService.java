package com.ftn.nc.NCBackend.helpClasses;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

public class MailSenderService {
	
	@Async
	public static void sendEmail(JavaMailSender mailSender, Environment environment, String subjectInfo, String poruka, String email) throws MessagingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false);
		helper.setFrom(environment.getProperty("spring.mail.username"));
		helper.setTo(email);
		helper.setSubject(subjectInfo);
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(poruka,"UTF-8","html");
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);
			
		mailSender.send(message);
	}

}
