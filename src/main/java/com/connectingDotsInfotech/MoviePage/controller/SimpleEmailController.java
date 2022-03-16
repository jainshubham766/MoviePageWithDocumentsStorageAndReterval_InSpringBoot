package com.connectingDotsInfotech.MoviePage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleEmailController {

	@Autowired
	private JavaMailSender sender;

	@RequestMapping("/simpleemail")
	@ResponseBody
	String home() {
		try {
			sendEmail();
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}

	private void sendEmail() throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo("set-your-recipient-email-here@gmail.com");
		helper.setText("How are you?");
		helper.setSubject("Hi");

		sender.send(message);
	}
}

//https://www.quickprogrammingtips.com/spring-boot/how-to-send-email-from-spring-boot-applications.html