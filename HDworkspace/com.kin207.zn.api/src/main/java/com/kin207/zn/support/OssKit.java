package com.kin207.zn.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.Base64;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.ObjectMetadata;
import com.kin207.zn.FileService;
import com.kin207.zn.SavedFile;

public class OssKit {
	
	public static String ACCESS_KEY_ID;
	public static String SECRET_ACCESS_KEY;
	public static String ENDPOINT;
	public static String BUCKET_NAME;
	public static String BUCKET_DOMAIN;
	public static String IMG_DOMAIN;
	
	//public static String CONTENT_TYPE_IMAGE = "image/jpeg";
	
	public static void init(String accessKeyId, String secretAccessKey, String endpoint, String bucket, String bucketDomain, String imgDomain)
	{
		ACCESS_KEY_ID = accessKeyId;
		SECRET_ACCESS_KEY = secretAccessKey;
		ENDPOINT = endpoint;
		BUCKET_NAME = bucket;
		BUCKET_DOMAIN = bucketDomain.endsWith("/") ? bucketDomain : (bucketDomain + "/");
		IMG_DOMAIN = imgDomain.endsWith("/") ? imgDomain : (imgDomain + "/");
	}

	public static String upload(String key, String filename)
    {
    	key = key.startsWith("/") ? key.replaceFirst("/", "") : key;
		String bucketName = BUCKET_NAME;
    	
    	File file = new File(filename);
        
		try {
			OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);

	        ObjectMetadata objectMeta = new ObjectMetadata();
	        objectMeta.setContentLength(file.length());
	        //objectMeta.setContentType(CONTENT_TYPE_IMAGE);
	        
			InputStream input = new FileInputStream(file);
			client.putObject(bucketName, key, input, objectMeta);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return IMG_DOMAIN + key;
		}
		return IMG_DOMAIN + key;
    }
    public static void delete(String aliyunObjectLink)
    {
    	if (aliyunObjectLink == null || aliyunObjectLink.trim().isEmpty()) 
    	{
			return;
		}
    	try {
    		String key = extractKey(aliyunObjectLink);
        	OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        	client.deleteObject(BUCKET_NAME, key);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
    }

	public static void delete(List<String> urls)
	{
		if (urls == null || urls.isEmpty()) 
		{
			return;
		}
		for (String url : urls) 
		{
			delete(url);
		}
	}
    
	
	/**
	 * 获取阿里云oss表单上传属性
	 * @return {accessKeyId, policy, signature, bucketHost}
	 */
	public static Map<String, Object> getFormUploadAttrs()
	{
		Map<String, Object> attrs = new HashMap<String, Object>();
		attrs.put("accessKeyId", OssKit.ACCESS_KEY_ID);
		
		Map<String, Object> policy = new HashMap<String, Object>();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String expiration = DateUtil.formatIso8601Date(cal.getTime());
		policy.put("expiration", expiration);
		
		List<Object> conditions = new ArrayList<Object>();
		Map<String, Object> bucket = new HashMap<String, Object>();
		bucket.put("bucket", OssKit.BUCKET_NAME);
		conditions.add(bucket);
		
		policy.put("conditions", conditions);
		
//		String policyJson = JsonKit.toJson(policy);//修改内容
		String policyJson =  JSON.toJSONString(policy);
		String policyBase64 = Base64.encodeToString(policyJson);
		attrs.put("policy", policyBase64);
		
		String signature = Base64.encodeToString(HmacSha1.encrypt(policyBase64, OssKit.SECRET_ACCESS_KEY));
		attrs.put("signature", signature);
		
		attrs.put("bucketHost", OssKit.BUCKET_DOMAIN);
		
		return attrs;
	}

	public static String generateFileKey(String folder, String filePostFix) 
	{
		SavedFile savedFile = FileService.gemerateSavedFile(folder, filePostFix);
		String key = FileService.getFileKey(savedFile);
		return key;
	}
	
    public static String extractKey(String aliyunObjectLink)
    {
    	if (aliyunObjectLink.startsWith(BUCKET_DOMAIN)) 
    	{
			return aliyunObjectLink.replace(BUCKET_DOMAIN, "");
		}

    	return aliyunObjectLink.replace(IMG_DOMAIN, "");
    }
}
