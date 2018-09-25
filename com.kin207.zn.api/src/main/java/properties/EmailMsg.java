package properties;

import java.util.Properties;

import com.kin207.zn.support.LanguageKit;

/**
 * Email消息
 * @author kinfeng
 */
public class EmailMsg{

	private static Properties properties;
	
	public static String get(String key, String language){
		String postFix = LanguageKit.underlinePostfixOf(language);
		String realKey = key + postFix;
		return get(realKey);
	}

	private static String get(String key){
		return properties.getProperty(key);
	}

	public static void initFromProperties(Properties properties) {
		EmailMsg.properties = properties;
	}

	public static final String SMS_RESET_PASS = "sms_reset_pass";//短信－重置密码内容
	public static final String EMAIL_RESET_PASS_FROM = "email_reset_pass_from";
	public static final String EMAIL_RESET_PASS_SUBJECT = "email_reset_pass_subject";
	public static final String EMAIL_RESET_PASS_CONTENT = "email_reset_pass_content";
	public static final String EMAIL_REGIST_FROM = "email_regist_from";
	public static final String EMAIL_REGIST_SUBJECT = "email_regist_subject";
	public static final String EMAIL_REGIST_CONTENT = "email_regist_content";
	
	public static final String SMS_BIND_PHONE = "sms_bind_phone";//短信－绑定手机号
	
}
