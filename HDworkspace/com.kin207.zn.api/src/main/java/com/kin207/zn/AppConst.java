package com.kin207.zn;

import java.util.Properties;



public class AppConst {
	//区分国际版
	public static Integer APP_FOR_LOCAL;
	//员工默认头像
	public static String EMPLOYEE_PHOTO_DEFAULT;
	//员工上传头像路径
	public static String EMPLOYEE_PHOTO_PATH;
	public static  String SMS_SEND_CAPTCHA;//短信－发送验证码内容
	//分页参数
	public static int DEFAULT_PAGE_NUM = 1;
	public static int DEFAULT_PAGE_SIZE = 10;
	public static int MOBILE_PAGE_SIZE = 15;
	//员工基本信息--登录类型
	public static final int EMPLOYEE_LOGIN_TYPE_WEIXIN = 1;//微信
	//PBC项目通用参数
	public static final Integer PBC_COMMON_PARAM_ZERO = 0;
	public static final Integer PBC_COMMON_PARAM_ONE = 1;
	public static final Integer PBC_COMMON_PARAM_TWO = 2;
	public static final Integer PBC_COMMON_PARAM_THREE = 3;
	public static final Integer PBC_COMMON_PARAM_FOUR = 4;
	public static final Integer PBC_COMMON_PARAM_FIVE = 5;
	//任务表--任务状态
	public static final Integer TASK_STATUS_NOT_ACCEPT = 0;//待接受
	//public static final Integer TASK_STATUS_ACCEPT = 1;//已接受
	public static final Integer TASK_STATUS_CHECK = 1;//待审核
	public static final Integer TASK_STATUS_PROCESSING = 2;//进行中
	public static final Integer TASK_STATUS_ASSESS = 3;//待评价
	public static final Integer TASK_STATUS_REJECT = 4;//驳回
	public static final Integer TASK_STATUS_FINISH = 5;//已完成
	public static final Integer TASK_STATUS_CANCEL = 6;//取消
	
	public static final Integer ROLE_INFO_TYPE_COMMON = 0;//普通管理员
	public static final Integer ROLE_INFO_TYPE_LEADER = 1;//部门负责人
	public static final Integer ROLE_INFO_TYPE_ADMIN = 2;//超级管理员
	public static final String ROLE_INFO_NAME_COMMON = "普通管理员";
	
	//企业月度考核设置信息 -- 考核时间
	public static final Integer DEFAULT_EVA_BEGIN_DAY = 1;//默认考核开始日
	public static final Integer DEFAULT_EVA_END_DAY = 5;//默认考核结束日
	//推送消息表 -- 临时字段
	public static final String MESSAGE_CONTENT_EXTRA_AGREE = "已同意";//已同意新同事加入企业
	public static final String STATUS_CODE_KEY = "code";//"statusCode";
	public static final int STATUC_SUCCESS = 200;//成功代码
	public static final String STATUS_CODE_MSG = "code";//"statusCode";
	public static final String STATUC_SUCCESS_MSG = "OK";//成功信息
	public static String CHOOSE_SYSTEM_LANGUAGE; //选择语言版本如：default：中文/En：英文
	public static String APP_URL; //项目部署URL，例如：www.ikin207.com
	public static final int MOBILE_ENCRYPT_CODE = 325;
	public static String GRANT_TYPE = "authorization_code";
	//信鸽用户名前缀
	public static String XINGE_ID_PREFIX;
	//环信用户名前缀
	public static String HUANXIN_ID_PREFIX;
	//通用参数
	public static final int DELETE_YES = 1;//已删除
	public static final int DELETE_NO = 0;//未删除
	//移动端返回参数
	public static final String MOBILE_KEY_CODE = "code";
	public static final String MOBILE_KEY_MSG = "msg";
	public static final String MOBILE_KEY_MSG_CODE = "msgCode";
	public static final int MOBILE_CODE_SUCCESS = 200;
	public static final int MOBILE_CODE_FAIL = 300;
	public static final int MOBILE_CODE_EXITS = 301;
	public static final String MOBILE_KEY_PARAM = "mobileParam";
	public static final String MOBILE_KEY_USER_ID = "clientId";
	public static final String MOBILE_KEY_TOKEN = "token";
	public static final String MOBILE_KEY_LANGUAGE = "sysLanguage";
	public static final String MOBILE_KEY_LANGUAGE_CH = "CH";
	public static final String MOBILE_KEY_LANGUAGE_EN = "EN";
	public static final String MOBILE_KEY_OS = "os";
	public static final String MOBILE_KEY_APP_VERSION = "appVersion";
	public static final String APP_SYSTEM_OPERATION_ANDROID = "Android";
	//账户类型
	public static final String ACCOUNT_TYPE_PHONE = "PHONE";
	public static final String ACCOUNT_TYPE_EMAIL = "EMAIL";
	public static final String ACCOUNT_TYPE_QQ = "QQ";
	public static final String ACCOUNT_TYPE_WEIXIN = "WECHAT";
	public static final String ACCOUNT_TYPE_WEIBO = "SINAWEIBO";
	//用户信息常量，默认年龄／性别／左右手
	public static final int USER_AGE_DEFAULT = 25;
	public static final int USER_BIRTHDAY_MONTH = 8;
	public static final int USER_BIRTHDAY_DAY = 15;
	public static final int USER_AGE_MIN = 6;
	public static final int USER_AGE_MAX = 80;
	public static final String USER_HAND_LEFT = "L";
	public static final String USER_HAND_RIGHT = "R";
	public static final String USER_GENDAR_MALE = "M";
	public static final String USER_GENDAR_FEMALE = "F";
	public static final String USER_GENDAR_DEFAULT = USER_GENDAR_MALE;
	public static final int USER_HEIGHT_DEFAULT_MALE = 170; //身高，170cm
	public static final int USER_HEIGHT_DEFAULT_FEMALE = 160; //身高，160cm
	public static final int USER_WEIGHT_DEFAULT_MALE = 65;  //体重，65kg
	public static final int USER_WEIGHT_DEFAULT_FEMALE = 49;  //体重，49kg
	public static final int APP_MESSAGE_STAR = 1;//点赞
	public static final int APP_MESSAGE_REPLY = 2;//评论
	public static final int APP_MESSAGE_ATTENTION = 3;//关注
	public static final int APP_MESSAGE_NOTICE = 4;//通知
	public static final int APP_MESSAGE_TWEET_HOT = 5;//上热门推送
	public static final int APP_MESSAGE_REPORTS_DEAL = 21;//举报处理之后成为的反馈信息
	public static final int APP_MESSAGE_DELETE_TWEET = 22;//删除不合法的动态通知用户
	public static final int APP_MESSAGE_SUGGESTION_REPLY = 23;//回复意见反馈
	//公共消息类型
	public static final int MESSAGE_CONTENT_TYPE_TASK = 1;//任务消息
	public static final int MESSAGE_CONTENT_TYPE_STAR = 2;//点赞消息
	public static final int MESSAGE_CONTENT_TYPE_COMPANY = 3;//企业消息
	public static final int MESSAGE_CONTENT_TYPE_SYSTEM = 4;//系统消息
	public static final int APP_IMAGE_TWEET = 1;//动态
	public static final int APP_IMAGE_ACTIVITY = 2;//活动
	public static final int APP_IMAGE_QUESTION = 3;//意见反馈的图片
	public static final int APP_STAR_TWEET = 1;//动态点赞
	//每页面记录数
	public static final int PAGE_SIZE = 20;
	public static final int PAGE_SIZE_TEN = 10;
	public static final int PAGE_SIZE_FIVE = 5;
	//默认页数
	public static final int DEFAULT_PAGE_NUMBER = 1;
	public static final int MYSQL_START_INDEX = 0;
	public static final int STATUS_YES = 1;
	public static final int STATUS_NO = 0;
	//默认推送人
	public static final String MESSAGE_CREATOR_SYS = "系统";
	public static int LIMIT_ONE_THOUSAND = 1000;
	public static String UNVALID_NICK_NAME_LIST = ""; //非法昵称列表
	//官方运营号
	public static int PUBLIC_ACCOUNT_ID;
	public static String PUBLIC_ACCOUNT;
	//网球等级评定系数
	public static int TENNIS_RATING_RATIO;
	//微信参数
	public static String APPID ;
	public static String SECRET ;
	public static String PARTNER ;
	public static String PARTNERKEY ;
	//微信退款使用的SSL文件路径
	public static String SSLFILEPATH;
	/**
	 * 微信支付交易类型
	 */
	public static String trade_type = "JSAPI";
	
	public static String signType = "MD5";
	
	public static void initFromProperties(Properties properties){
		APP_URL = properties.getProperty("APP_URL");
		EMPLOYEE_PHOTO_DEFAULT = properties.getProperty("EMPLOYEE_PHOTO_DEFAULT");
		EMPLOYEE_PHOTO_PATH = properties.getProperty("EMPLOYEE_PHOTO_PATH");
		/**信鸽／环信常量读取**/
		XINGE_ID_PREFIX = properties.getProperty("XINGE_ID_PREFIX");
		HUANXIN_ID_PREFIX = properties.getProperty("HUANXIN_ID_PREFIX");
		long accessIdAn = Long.valueOf(properties.getProperty("Xinge_access_id_android"));
		String secretKeyAn = properties.getProperty("Xinge_secret_key_android");
		long accessIdIos = Long.valueOf(properties.getProperty("Xinge_access_id_ios"));
		String secretKeyIos = properties.getProperty("Xinge_secret_key_ios");
		int envIos = Integer.valueOf(properties.getProperty("Xinge_env_ios"));
		/**官方运营号**/
		PUBLIC_ACCOUNT_ID = Integer.valueOf(properties.getProperty("PUBLIC_ACCOUNT_ID", "0"));
		PUBLIC_ACCOUNT = properties.getProperty("PUBLIC_ACCOUNT");
		CHOOSE_SYSTEM_LANGUAGE = properties.getProperty("CHOOSE_SYSTEM_LANGUAGE");//选择语言版本如：default：中文/En：英文 ;		
		SMS_SEND_CAPTCHA = properties.getProperty("SMS_SEND_CAPTCHA");//短信－发送验证码
		/**网球等级评定系数**/
		TENNIS_RATING_RATIO = Integer.valueOf(properties.getProperty("TENNIS_RATING_RATIO"));
		APP_FOR_LOCAL = Integer.valueOf(properties.getProperty("APP_FOR_LOCAL"));
		APPID = properties.getProperty("APPID");//微信公众平台ID
		SECRET= properties.getProperty("SECRET");//微信公众平台SECRET
		PARTNER = properties.getProperty("PARTNER");//微信公众平台商户ID
		PARTNERKEY= properties.getProperty("PARTNERKEY");//微信公众平台商户key
		SSLFILEPATH = properties.getProperty("SSLFILEPATH");//微信SSL路径
	}

}