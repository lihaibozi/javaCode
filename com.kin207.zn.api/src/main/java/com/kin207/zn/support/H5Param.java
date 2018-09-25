package com.kin207.zn.support;

import com.kin207.zn.AppConst;




public class H5Param {

	private Integer userId;
	private String os;
	private Double appVersion;
	
	public static H5Param build(String userId, String os, String appVersion) 
	{
		Integer userIdInt = userId == null ? null : Integer.valueOf(userId);
		Double appVersionDouble = explainToDoubleVersion(appVersion);
		
		return new H5Param(userIdInt, os, appVersionDouble);
	}
	
	/**
	 * @return 3.1.1 返回 3.11
	 */
	private static Double explainToDoubleVersion(String version) 
	{
		if (version == null || version.trim().isEmpty()) 
		{
			return null;
		}
		String[] splits = version.split("\\.");
		StringBuilder versionBuilder = new StringBuilder();
		for (int i = 0; i < splits.length; i++) 
		{
			String str = splits[i];
			versionBuilder.append(str);
			if (i == 0) 
			{
				versionBuilder.append(".");
			}
		}
		return Double.valueOf(versionBuilder.toString());
	}

	private H5Param(Integer userId, String os, Double appVersion)
	{
		this.userId = userId;
		this.os = os;
		this.appVersion = appVersion;
	}
	
	public Integer getUserId()
	{
		return userId;
	}
	
	public boolean isLogin()
	{
		return getUserId() != null;
	}
	
	public boolean isAndroid()
	{
		return this.os != null && this.os.equals(AppConst.APP_SYSTEM_OPERATION_ANDROID);
	}
	
	public boolean isVersionGe31()
	{
		return this.appVersion != null && this.appVersion >= 31;
	}

	
}
