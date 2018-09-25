package com.kin207.zn.support.phoneMsg;

import org.apache.log4j.Logger;

import com.kin207.zn.AppConst;

import properties.EmailMsg;

/**
 * 手机短信
 */
public class PhoneMsgService {
	private static Logger log = Logger.getLogger(PhoneMsgService.class);
	private static final String CAPTCHA_PLACE_HOLDER = "#0#";
	
	/**
	 * 发送验证码－重置密码
	 * @param mobileNo  手机号码
	 * @param captcha   验证码
	 */
	public static String sendResetPassCaptcha(String mobileNo, String captcha, String language) 
	{
/*		String content = EmailMsg.get(EmailMsg.SMS_RESET_PASS, language);
		content = content.replaceFirst(CAPTCHA_PLACE_HOLDER, captcha);*/
		String content = AppConst.SMS_SEND_CAPTCHA +" "+captcha;
		try {
			return PhoneMsgSender.sendSms(mobileNo, content);
		} catch (Exception e) {
			log.error("PhoneMsgService -> sendResetPassCaptcha :"+"发送验证码失败" +e);
		}
		return null;
	}
	
	
	/**
	 * 发送商务信息
	 * @param mobileNo  手机号码
	 */
	public static String sendMessage(String mobileNo ) {
		try {
			return PhoneMsgSender.batchSend(mobileNo);
		} catch (Exception e) {
			log.error("PhoneMsgService -> sendMessage :"+"发送短信失败" +e);
		}
		return null;
	}
	/**
	 * 发送验证码－绑定手机号
	 * @param mobileNo  手机号码
	 * @param captcha   验证码
	 */
	public static void sendBindPhoneCaptcha(String mobileNo, String captcha, String language) 
	{
		String content = EmailMsg.get(EmailMsg.SMS_BIND_PHONE, language);
		content = content.replaceFirst(CAPTCHA_PLACE_HOLDER, captcha);
		try {
			PhoneMsgSender.sendSms(mobileNo, content);
		} catch (Exception e) {
			log.error("PhoneMsgService -> sendBindPhoneCaptcha :"+"绑定手机号失败" +e);
		}
	}

}
