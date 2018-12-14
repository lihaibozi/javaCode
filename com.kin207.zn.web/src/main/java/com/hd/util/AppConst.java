package com.hd.util;



import java.util.Properties;


public class AppConst {

	public static final String XINGE_ID_PREFIX = "test_usense_xinge_pushid_";
	public static final String HUANXIN_ID_PREFIX="test_usense_huanxin_id_";
	//分页参数
	public static int DEFAULT_PAGE_NUM = 1;
	public static int DEFAULT_PAGE_SIZE = 10;
	public static int MOBILE_PAGE_SIZE = 15;
	
	//当前访问的URL
	public static final int APP_IMAGE_TYPE_TWEET = 1;//动态图片
	public static final int APP_IMAGE_TYPE_ACTIVITY = 2;//活动图片
	public static final int APP_IMAGE_TYPE_QUESTION = 3;//意见反馈图片
	public static final int APP_IMAGE_TYPE_GOODS = 4;//商品图片
	
	
	//图片文件夹
	//图片上传路径
	public static String EMPLOYEE_PHOTO_PATH ="/mnt/images/";
	public static String EMPLOYEE_PHOTO_DEFAULT ="https://jzxcx.bydsfy.com/";
	public static final String FOLDER_NEWS_LOGO = "h5image/news/logo";
	public static final String FOLDER_NEWS_VIDEO = "h5image/news/video";
	public static final String FOLDER_NEWS_SHARE_IMAGE = "h5image/news/share";
	public static final String FOLDER_NEWS_CONTENT_IMAGE = "h5image/news/content";
	public static final String FOLDER_RECOMMENT_IMG = "h5image/news/recomment";
	public static final String FOLDER_ANALYSIS_VIDEO_IMAGE = "h5image/analysis/videoImg";
	public static final String FOLDER_APP_START_IMAGE = "h5image/appStartImage";
	
	public static final String IMAGE_NEWS_CONTENT_DEFAULT = "assets/images/defaultImg/newsDetail.jpg";
	
	public static final int PUBLISH_YES = 1;
	public static final int PUBLISH_NO = 0;
	
	public static final int WIDTH_THUMB_IMAGE = 400;
	public static final int HEIGHT_THUMB_IMAGE = 300;
	
	public static final int AGENCY_ORDER_TYPE = 0;
	public static final int DAOYOU_ORDER_TYPE = 1;
	public static final int FANYI_ORDER_TYPE = 2;
	public static final int FOOD_ORDER_TYPE = 3;
	public static final int JIEJI_ORDER_TYPE = 4;
	public static final int VIP_ORDER_TYPE = 5;
	
	

	//从配置文件读取
	public static int ORDER_DAOYOU_TYPE;
	public static int ORDER_FANYI_TYPE;
	public static int ORDER_AGENCY_TYPE;
	public static int ORDER_FOOD_TYPE;
	public static int ORDER_JIEJI_TYPE;
	public static int ORDER_VIP_TYPE;
	
	public static void initFromProperties(Properties properties){
		ORDER_DAOYOU_TYPE = Integer.parseInt(properties.getProperty("ORDER_DAOYOU_TYPE"));
		ORDER_FANYI_TYPE = Integer.parseInt(properties.getProperty("ORDER_FANYI_TYPE"));
		ORDER_AGENCY_TYPE = Integer.parseInt(properties.getProperty("ORDER_AGENCY_TYPE"));
		ORDER_FOOD_TYPE = Integer.parseInt(properties.getProperty("ORDER_FOOD_TYPE"));
		ORDER_JIEJI_TYPE = Integer.parseInt(properties.getProperty("ORDER_JIEJI_TYPE"));
		ORDER_VIP_TYPE = Integer.parseInt(properties.getProperty("ORDER_VIP_TYPE"));
	}

}
