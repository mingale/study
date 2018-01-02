package spring.mvc.bookstore.controller;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailHandler {

	private JavaMailSender mailSender;
	private MimeMessage msg;
	private MimeMessageHelper msgHelper;

	public EmailHandler() {
	}

	public EmailHandler(JavaMailSender mailSender) {
		try {
			this.mailSender = mailSender;
			msg = this.mailSender.createMimeMessage();
			msgHelper = new MimeMessageHelper(msg, true, "UTF-8");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setSubject(String subject) throws MessagingException {
		msgHelper.setSubject(subject);
	}

	public void setText(String htmlContent) throws MessagingException {
		msgHelper.setText(htmlContent, true);
	}

	public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
		msgHelper.setFrom(email, name);
	}

	public void setTo(String email) throws MessagingException {
		msgHelper.setTo(email);
	}

	public void addInline(String contentId, DataSource dataSource) throws MessagingException {
		msgHelper.addInline(contentId, dataSource);
	}

	public void send() {
		mailSender.send(msg);
	}

	// 인증키 랜덤 문자
	public String randomKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 6; i += 1) {
			int rIndex = rnd.nextInt(2);

			switch (rIndex) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				break; // A-Z
			case 1:
				key.append(rnd.nextInt(10));
				break; // 0-9
			}
		}

		return key.toString(); // StringBuffer -> String
	}

}
