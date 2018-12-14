package properties;

import java.util.Properties;

import com.kin207.zn.support.LanguageKit;

/**
 * 客户端消息
 * @author KinFeng
 *
 */
public class ClientMsg{
	
	public static final String USER_ACCOUNT_EXIST_NO = "10000";//账号不存在
	public static final String USER_ACCOUNT_NO_EXIST_NO = "10001";//账号已存在	
	public static final String USER_PASSWORD_ERROR = "10002";//密码错误
	public static final String USER_ACCOUNT_NULL = "10003";//账号为空
	public static final String USER_ACCOUNT_FORMAT_ERROR = "10004";//账号格式不正确
	public static final String USER_ACCOUNT_EXIST_YES = "10005";//账号已存在
	public static final String USER_REGIST_SUCCESS = "10006";//注册成功
	public static final String USER_RESET_PASSWOR_SUCCESS = "10007";//新密码设置成功
	public static final String USER_INFO_SAVE_SUCCESS = "10008";//用户信息保存成功
	public static final String USER_INFO_SAVE_FAIL = "10038";//用户信息保存失败
	public static final String USER_LOGIN_SUCCESS = "10009";//登录成功
	public static final String USER_REGIST_HUANXIN_FAILED = "10010";//注册环信失败
	public static final String USER_UPDATA_HUANXIN_FAILED = "10026";//注册环信失败
	public static final String NO_PASSWORD_IN_THIRTYPARTY_LOGIN = "10011";//第三方登录没有密码
	public static final String OLD_PASSWORD_NOT_RIGHT = "10012";//旧密码不正确
	public static final String MOBILE_ERROR = "10016";//手机号格式错误
	
	
	public static final String RELIEVE_BIND_QQ = "10018";//QQ解绑失败
	public static final String RELIEVE_BIND_SUCCESS = "10019";//解绑成功
	public static final String RELIEVE_BIND_FAIL = "10020";//当前登录账号不能解绑
	public static final String BIND_SUCCESS = "10021";//账号绑定成功
	
	public static final String USER_NICKNAME_UNVALID = "10017"; //昵称与官方运营号冲突
	public static final String RESULT_SUCCESS_NULL= "10025";//查询结果为空
	
	public static final String MOBILE_BIND_EXITS = "10026";//已经绑定手机
	public static final String BIND_SENSOR_USER_SAME = "10028";//已经绑定SENSOR
	public static final String BIND_SENSOR_EXITS= "10029";//SENSOR已经被别人绑定
	public static final String BIND_SENSOR_NOT_EXITS = "10032";//SENSOR不存在
	
	public static final String UPLOAD_DATA_SUCCESS= "10030";//数据上传成功
	public static final String UPLOAD_DATA_FAIL= "10031";//数据上传失败
	private static Properties properties;
	
	public static String get(String msgCode, String language) {
		String key = msgCode + LanguageKit.underlinePostfixOf(language);
		return get(key);
	}
	
	private static String get(String key){
		return properties.getProperty(key);
	}

	public static void initFromProperties(Properties properties){
		ClientMsg.properties = properties;
	}

}
