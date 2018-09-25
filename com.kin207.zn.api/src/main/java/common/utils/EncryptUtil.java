package common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.apache.log4j.Logger;


public class EncryptUtil {
	
	private static Logger log = Logger.getLogger(EncryptUtil.class);

	public static String encrypt(String text, String key) 
	{
		char[] keyChars = key.toCharArray();
		char[] contents = text.toCharArray();
		
		StringBuffer sb = new StringBuffer();
		for(int i=0,j=0; i<contents.length; i++,j++) 
		{
			if(j>=keyChars.length) 
			{
				j = 0;
			}
			sb.append((char)(contents[i]^keyChars[j]));
		}
		
		return sb.toString();
	}
	
	
	public static String decrypt(String data, String key) 
	{
		if(data == null || "".equals(data))
		{
			return "";
		}
		
		char[] encodeChars = data.toCharArray();
		char[] keyChars = key.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i = 0, j = 0; i < encodeChars.length; i++, j++) 
		{
			if(j >= keyChars.length) 
			{
				j = 0;
			}
			
			sb.append((char)(encodeChars[i]^keyChars[j]));
		}
		return sb.toString();
	}
	
	public static String urlEncode(String str)
	{
		try {
			return URLEncoder.encode(str, "utf8");
		} catch (UnsupportedEncodingException e) {
			log.error("EncryptUtil urlEncode"+e.getMessage());
		}
		
		return null;
	}
	
	public static String urlDecode(String str)
	{
		try {
			return URLDecoder.decode(str, "utf8");
		} catch (UnsupportedEncodingException e) {
			log.error("EncryptUtil urlDecode :" +e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 简单异或加密
	 * @param code 325
	 */
	public static byte[] decode(String str, int code) 
	{
		byte[] charArray = null;
		try {		
			charArray = str.getBytes("UTF-8");
			for (int i = 0; i < charArray.length; i++) {
				charArray[i] = (byte) (charArray[i] ^ code);
			}
			return charArray;
		} catch (Exception e) {
			log.error("EncryptUtil decode :" +e.getMessage());
		}
		return null;
	}
	
	public static String decodeBase64(byte[] s)
	{
		byte[] encodeBase64 = Base64.decode(s);
		try {
			return new String(encodeBase64, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("EncryptUtil decodeBase64 :" +e.getMessage());
		}
		return null;
	}
	
	public static String decodeBase64(String text)
	{
		return Base64.decodeToString(text);
	}


	public static String encodeBase64(byte[] data) 
	{
		return Base64.encodeToString(data);
	}
	
	public static String encodeBase64(String data) 
	{
		return Base64.encodeToString(data);
	}
	
	public final static String encrptMD5(String text, String charset)
	{
		 if(text == null){
			 return null;
		 }
		 char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
				    'A', 'B', 'C', 'D', 'E', 'F' };  
		  try {  
			   byte[] strTemp = text.getBytes(charset);  
			   MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
			   mdTemp.update(strTemp);  
			   byte[] md = mdTemp.digest();  
			   int j = md.length;  
			   char str[] = new char[j * 2];  
			   int k = 0;  
			   for (int i = 0; i < j; i++) {  
			    byte byte0 = md[i];  
			    str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
			    str[k++] = hexDigits[byte0 & 0xf];  
			   }  
			   return new String(str);  
		  } catch (Exception e) {  
			  return null;  
		  }
	 }
	
}
