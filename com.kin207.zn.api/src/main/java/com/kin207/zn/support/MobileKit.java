package com.kin207.zn.support;

import common.utils.Encryption;

public class MobileKit {

	public static String encryptMobileText(String text)
	{
		/*String base64Text = EncryptUtil.encodeBase64(text);
		return EncryptUtil.encrypt(base64Text, AppConst.MOBILE_ENCRYPT_CODE);*/
		
		return new Encryption().sendJson(text);//new String(EncryptUtil.decode(EncryptUtil.encodeBase64(text), AppConst.MOBILE_ENCRYPT_CODE));
		
		//return text;
	}

	public static String decryptMobileText(String encryptText)
	{
		/*byte[] decryptData = EncryptUtil.decode(encryptText, AppConst.MOBILE_ENCRYPT_CODE);
		return EncryptUtil.decodeBase64(decryptData);*/
		//return new String(EncryptUtil.decode(encryptText, AppConst.MOBILE_ENCRYPT_CODE));
		return new Encryption().receiveJson(encryptText);
		//return encryptText;
	}
	
	
	public static void main(String[] args) {
		String json = "山东费拉达斯家里附近撒的；拉德斯基分";
		
		String encrypt = encryptMobileText(json);
		System.err.println("密文：" + encrypt);
		
		String origin = decryptMobileText(encrypt);
		System.err.println("明文：" + origin);
		
	}
}
