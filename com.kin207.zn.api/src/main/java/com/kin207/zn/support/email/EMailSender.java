package com.kin207.zn.support.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class EMailSender {
	private static Logger log = Logger.getLogger(EMailSender.class);
	private static final String host = "smtp.ym.163.com";
	private static final String user = "secretary@ikin207.com";
	private static final String password = ">Secretary2016";

	public static int send(String from, String to, String subject, String content) 
	{
		int retCode = -1;
		
		System.setProperty("java.net.preferIPv4Stack", "true");  

		Properties props = new Properties();
		props.put("mail.smtp.host", host); // 指定SMTP服务器
		props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证

		try {
			Session mailSession = Session.getDefaultInstance(props);
			//mailSession.setDebug(true);
			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from)); // 发件人
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 收件人
			message.setSubject(subject); // 邮件主题
			// 指定邮箱内容及ContentType和编码方式
			message.setContent(content, "text/html;charset=utf-8");
			// 指定邮件发送日期
			message.setSentDate(new Date());
			message.saveChanges();
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			retCode = 1;
		} catch (Exception e) {
			log.error("EMailSender send" + e.getMessage());
			retCode = -1;
		}
		return retCode;
	}

}
