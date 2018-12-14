package com.kin207.zn.support;




public class MobileSession {

	private Integer userId;
	
	public static MobileSession build(Integer userId) 
	{
		return new MobileSession(userId);
	}
	
	private MobileSession(Integer userId)
	{
		this.userId = userId;
	}
	
	public Integer getUserId()
	{
		return userId;
	}
	
	public boolean isLogin()
	{
		return getUserId() != null;
	}

}
