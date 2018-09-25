package com.kin207.zn.support;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HmacSha1 {

	private static final String MAC_NAME = "HmacSHA1";    
    private static final String ENCODING = "UTF-8";    
      
    /* 
     * 展示了一个生成指定算法密钥的过程 初始化HMAC密钥  
     * @return  
     * @throws Exception 
     *  
      public static String initMacKey() throws Exception { 
      //得到一个 指定算法密钥的密钥生成器 
      KeyGenerator KeyGenerator keyGenerator =KeyGenerator.getInstance(MAC_NAME);  
      //生成一个密钥 
      SecretKey secretKey =keyGenerator.generateKey(); 
      return null; 
      } 
     */  
      
    /**  
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名  
     * @param encryptText 被签名的字符串  
     * @param encryptKey  密钥  
     * @return  
     * @throws Exception  
     */    
    public static byte[] encrypt(String encryptText, String encryptKey)     
    {           
		try {
			byte[] data = encryptKey.getBytes(ENCODING);
	        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);   
	        Mac mac = Mac.getInstance(MAC_NAME);   
	        mac.init(secretKey);    
	        byte[] text = encryptText.getBytes(ENCODING);    
	        return mac.doFinal(text);
		} catch (Exception e) {
			e.printStackTrace();
		}  
        return null; 
    }
}
