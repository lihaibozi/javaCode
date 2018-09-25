package properties;

import java.util.Properties;

import javax.annotation.PostConstruct;

import com.hd.kits.SpringKit;
import com.hd.util.AppConst;


public class PropertiesReader {
	private String appConstConfigLocation;
/*	
	@PostConstruct
	public void read()
	{
		//应用常量配置
		Properties appConstProperties = SpringKit.loadClasspathProperties(appConstConfigLocation);
		AppConst.initFromProperties(appConstProperties);
		
	}
*/
	@PostConstruct
	public void read()
	{
		//应用常量配置
		Properties appConstProperties = SpringKit.loadClasspathProperties(appConstConfigLocation);
		AppConst.initFromProperties(appConstProperties);
		
	}
	public String getAppConstConfigLocation() {
		return appConstConfigLocation;
	}

	public void setAppConstConfigLocation(String appConstConfigLocation) {
		this.appConstConfigLocation = appConstConfigLocation;
	}


}
