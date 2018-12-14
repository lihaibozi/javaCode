package com.kin207.zn.support.email;

import com.kin207.zn.SpringKit;

import properties.EmailMsg;


public class EmailService {

	private static final String PLACE_HOLDER_CAPTCHA = "#0#";
	
	/**
	 * 重置密码－发邮件
	 */
	public static void sendResetPassCaptcha(String account, String captcha, String language) 
	{
		String from = EmailMsg.get(EmailMsg.EMAIL_RESET_PASS_FROM, language);
		String subject = EmailMsg.get(EmailMsg.EMAIL_RESET_PASS_SUBJECT, language);
		String configLocation = EmailMsg.get(EmailMsg.EMAIL_RESET_PASS_CONTENT, language);
		
		String content = SpringKit.loadContentFrom(configLocation);
		content = content.replaceFirst(PLACE_HOLDER_CAPTCHA, captcha);
		
		EMailSender.send(from, account, subject, content);
	}
	/**
	 * 注册－发邮件
	 */
	public static void sendRegistInfo(String account, String password, String language) 
	{
		String from = EmailMsg.get(EmailMsg.EMAIL_REGIST_FROM, language);
		String subject = EmailMsg.get(EmailMsg.EMAIL_REGIST_SUBJECT, language);
		String configLocation = EmailMsg.get(EmailMsg.EMAIL_REGIST_CONTENT, language);
		
		String content = SpringKit.loadContentFrom(configLocation);
		content = content.replaceFirst(PLACE_HOLDER_CAPTCHA, password);
		
		EMailSender.send(from, account, subject, content);
	}
}
