package properties;

import java.util.Properties;

import javax.annotation.PostConstruct;

import com.kin207.zn.AppConst;
import com.kin207.zn.SpringKit;

/**
 * 属性配置文件读取
 * @author wujun
 */
public class PropertiesReader {

	private String clientMsgConfigLocation;
	private String emailMsgConfigLocation;
	private String appConstConfigLocation;
	
	@PostConstruct
	public void read(){
		//应用常量配置
		Properties appConstProperties = SpringKit.loadClasspathProperties(appConstConfigLocation);
		AppConst.initFromProperties(appConstProperties);
		
		//客户端消息配置
		Properties clientMsgProperties = SpringKit.loadClasspathProperties(clientMsgConfigLocation);
		ClientMsg.initFromProperties(clientMsgProperties);
		
		//邮件／短信内容配置
		Properties emailMsgProperties = SpringKit.loadClasspathProperties(emailMsgConfigLocation);
		EmailMsg.initFromProperties(emailMsgProperties);
		
		SpringKit.testReadProperties(emailMsgProperties, EmailMsg.EMAIL_RESET_PASS_CONTENT);
	}

	
	public String getClientMsgConfigLocation() {
		return clientMsgConfigLocation;
	}
	public void setClientMsgConfigLocation(String clientMsgConfigLocation) {
		this.clientMsgConfigLocation = clientMsgConfigLocation;
	}

	public String getEmailMsgConfigLocation() {
		return emailMsgConfigLocation;
	}
	public void setEmailMsgConfigLocation(String emailMsgConfigLocation) {
		this.emailMsgConfigLocation = emailMsgConfigLocation;
	}

	public String getAppConstConfigLocation() {
		return appConstConfigLocation;
	}
	public void setAppConstConfigLocation(String appConstConfigLocation) {
		this.appConstConfigLocation = appConstConfigLocation;
	}

}
