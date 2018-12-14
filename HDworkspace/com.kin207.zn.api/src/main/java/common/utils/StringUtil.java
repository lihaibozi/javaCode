package common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import jodd.util.RandomStringUtil;

/**
 * 字符串工具类
 * @author wujun
 */
public class StringUtil {

	//邮件正则表达式
	private final static String email_reg = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		
	public static final String RAND_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	public static final String RAND_NUM_STR = "1234567890";
	public static final String BLANK_STR = "";
	public static final String NULL_STR = "NULL";
	
	public static String randStr(int len){
		return RandomStringUtil.random(len,RAND_STR);
	}
	
	public static String randNum(int len) {
		return RandomStringUtil.random(len, RAND_NUM_STR);
	}
	
	public static String concat(String...strs){
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static boolean isBlankOrNull(String str){
		if(str == null){
			return true;
		}
		if(BLANK_STR.equals(str.trim())){
			return true;
		}
		if(NULL_STR.equalsIgnoreCase(str.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * 友好时间串
	 * @param time
	 * @return
	 */
	public static String friendlyDate(Date time){
	    int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);
	    if(ct < 3600){
	    	return Math.max(ct / 60,1) + "分钟前";
	    }else if(ct >= 3600 && ct < 86400){
	    	return ct/3600 + "小时前";
	    }else if(ct >= 86400 && ct < 2592000){
	    	int day = ct / 86400 ;   
	    	return (day>1)? day + "天前" : "昨天";
	    }else{
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	return sdf.format(time);
	    }
	}
	
	/**
	 * 手机号规则验证
	 */
	public static boolean isMobileNumber(String mobiles) {
	    return Pattern
	            .compile("^((13[0-9])|14[0-9]|(15[0-9,\\D])|(18[0-9,\\D]))\\d{8}")
	            .matcher(mobiles).matches();
	}
	
	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || email.trim().length()==0){
			return false;
		}
		Pattern emailer = Pattern.compile(email_reg);
	    return emailer.matcher(email).matches();
	}
	

	/**
	 * 随机创建唯一字符串
	 */
	public static String randomUniqueStr() {
		return UUID.randomUUID().toString() + new Random().nextInt(1000);
	}

	public static String uniqueStrWithDateTime(){
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		String conector = "_";
		String timeDesc = DateUtil.dateFormat("yyyyMMddHHmmss", new Date());
		sb.append(timeDesc)
		  .append(conector)
		  .append(System.currentTimeMillis())
		  .append(conector)
		  .append(random.nextInt(10000))
		  .append(conector)
		  .append(random.nextInt(10000))
		  .append(conector)
		  .append(random.nextInt(10000));
		return sb.toString();
	}
	
	/**
	 * 生成新的文件名
	 */
	public static String appendExtraToFileName(String fileName, String extra) {
		int index = fileName.lastIndexOf(".");
		String name = fileName.substring(0, index);
		String ext = fileName.substring(index);
		return new StringBuilder().append(name).append(extra).append(ext).toString();
	}
	
	/**
	 * 给文件名添加随即字符串
	 */
	public static String generateNewName(String fileName) {
		Random random = new Random();
		String subName = new StringBuffer().append(random.nextInt(1000))
										   .append("_")
										   .append(random.nextInt(1000))
										   .append("_")
										   .append(random.nextInt(1000))
										   .toString();
		return appendExtraToFileName(fileName, subName);
	}
	
	public static String join(Iterator<?> iter,String sep){
		if(iter == null){
			return "";
		}
		if(sep == null){
			sep = "";
		}
		StringBuilder sb = new StringBuilder();
		while(iter.hasNext()){
			sb.append(jodd.util.StringUtil.toSafeString(iter.next())).append(sep);
		}
		return sb.substring(0, sb.length()-sep.length());
	}
	
	public static double convertToDouble(String value,double defaultValue){
		if(jodd.util.StringUtil.isBlank(value)){
			return defaultValue;
		}
		double tmp = defaultValue;
		try{
			tmp = Double.valueOf(value);
		}catch(Exception e){
			tmp = defaultValue;
		}
		return tmp;
	}

	/**
	 * 把以逗号分隔的字符串拆分成一个字符串数组
	 * @param keys  "key1, key2, key3"
	 * @param split ","
	 * @return ["key1", "key2", "key3"]
	 */
	public static String[] splitToArray(String keys, String split) {
		if (keys == null) {
			return null;
		}
		
		String[] array = keys.split(split);
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}
	
	public static int paseInt(String number){
		if(number!=null ){
			if(number.contains(".")){
				String result = number.substring(0, number.indexOf("."));
				return Integer.parseInt(result);
			}else{
				return Integer.parseInt(number); 
			}
		}
		return 0;
	}
}
