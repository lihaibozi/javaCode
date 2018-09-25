package com.kin207.zn.support;


public class MobileResult extends JsonResult<MobileResult>{

	private MobileResult()
	{
		super();
	}
	
	public static MobileResult create()
	{
		MobileResult result = new MobileResult();
		return result;
	}
	
	public String success(String msgCode, String msg)
	{
		return super.success(msgCode, msg);
		//return MobileKit.encryptMobileText(super.success(msgCode, msg));// kinfeng modify 加密使用
	}
	
	
	public String fail(String msgCode, String msg) 
	{
		return super.fail(msgCode, msg);
		//return MobileKit.encryptMobileText(super.fail(msgCode, msg));// kinfeng modify 加密使用
	}
	public String exits(String msgCode, String msg) 
	{
		return super.exits(msgCode, msg);
		//return MobileKit.encryptMobileText(super.fail(msgCode, msg));// kinfeng modify 加密使用
	}
	
}
