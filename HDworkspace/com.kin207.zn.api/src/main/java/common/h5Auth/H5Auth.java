package common.h5Auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import common.utils.AESKit;
import common.utils.StringUtil;

/**
 * H5请求处理
 * @author wujun
 */
public class H5Auth {

	private static final int RAND_NUM_LENGTH = 8;
	private static String SECRET_KEY = "1234567812345678";
	public static int SESSION_MINUTES = 600;
	
	public static String split = "____";
	
	public static void init(String mobileSecretKey, int mobileSessionMinutes)
	{
		SECRET_KEY = mobileSecretKey;
		SESSION_MINUTES = mobileSessionMinutes;
	}
	
	/**
	 * 如果创建的token当作URL中的参数，请进行 URLEncode
	 * <br>或者调用 createUrlEncodeToken(int userId)
	 */
	public static String createToken(int userId)
	{
		String data = tokenData(userId);
		
		return AESKit.encrypt(data, SECRET_KEY);
	}
	
	public static String createUrlEncodeToken(int userId)
	{
		String data = tokenData(userId);
		String secret = AESKit.encrypt(data, SECRET_KEY); 
		
		try {
			return URLEncoder.encode(secret, "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer getUserIdFromToken(String token)
	{
		try {
			String data = AESKit.decrypt(token, SECRET_KEY);
			
			String secretUserId = data.substring(RAND_NUM_LENGTH, data.length()); 
			return Integer.valueOf(secretUserId);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * @return userId_effectiveTime
	 */
	private static String tokenData(int userId)
	{
		String randStr = StringUtil.randNum(RAND_NUM_LENGTH); 
		StringBuilder data = new StringBuilder().append(randStr)
												.append(userId);
		return data.toString();
	}
	
	public static void main(String[] args) throws Exception {
		
		String text = "UPXQ14kCVEfRg+bPpCPO3g==";
		String secret = URLEncoder.encode(text, "utf8");
		String orgin = URLDecoder.decode(secret, "utf8");
		System.out.println(secret);
		System.out.println(orgin);
		System.out.println(text);
	}
}
