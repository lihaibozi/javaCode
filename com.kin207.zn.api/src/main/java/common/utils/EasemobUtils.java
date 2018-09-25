package common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kin207.zn.AppConst;

import common.utils.httpclient.comm.Constants;
import common.utils.httpclient.comm.HTTPMethod;
import common.utils.httpclient.comm.Roles;
import common.utils.httpclient.httpclient.utils.HTTPClientUtils;
import common.utils.httpclient.httpclient.vo.ClientSecretCredential;
import common.utils.httpclient.httpclient.vo.Credential;
import common.utils.httpclient.httpclient.vo.EndPoints;

public class EasemobUtils {
	
	public static final Logger log = Logger.getLogger(EasemobUtils.class);
	
	private static final JsonNodeFactory factory = new JsonNodeFactory(false);
	 // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
    		Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);
	
	/**
	 * grant_type”: “client_credentials”,”client_id”: “{app的client_id}”,”client_secret”: “{app的client_secret}
	 */
	public static void token() {
		ObjectNode dataNode = factory.objectNode();
		dataNode.put("grant_type", "client_credentials");
		dataNode.put("client_id", Constants.APP_CLIENT_ID);
		dataNode.put("client_secret", Constants.APP_CLIENT_SECRET);
		
		ObjectNode objNode = HTTPClientUtils.sendHTTPRequest(EndPoints.TOKEN_APP_URL, credential, dataNode, HTTPMethod.METHOD_POST);
		log.info("token" +objNode.toString() );
	}
	
	/**
	 *  注册环信
	 * @param username
	 * @param password
	 * @param nickname
	 */
	public static boolean regist(int userId, String nickname) {
		
		String username = generateUserName(userId);
		String password = generatePassword(userId);
		
		ObjectNode dataNode = factory.objectNode();
		dataNode.put("username", username);
		dataNode.put("password", password);
		dataNode.put("nickname",nickname);
		ObjectNode objNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataNode, HTTPMethod.METHOD_POST);
		int statusCode = objNode.get("statusCode").asInt();
		if(statusCode == 401) {
			token();
			regist(userId, nickname);
		}
		
		log.info(objNode.toString());
		if(statusCode == 200) {
			return true;
		} 
		return false;
	}
	/**
	 *  修改环信昵称
	 * @param username
	 * @param password
	 * @param nickname
	 */
	public static boolean updateNickName(int userId, String nickname) {
		
		String username = generateUserName(userId);
		String password = generatePassword(userId);
		
		ObjectNode dataNode = factory.objectNode();
		dataNode.put("username", username);
		dataNode.put("password", password);
		dataNode.put("nickname",nickname);
		ObjectNode objNode = HTTPClientUtils.sendHTTPRequest(HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"+username), credential, dataNode, HTTPMethod.METHOD_PUT);
		int statusCode = objNode.get("statusCode").asInt();
		if(statusCode == 401) {
			token();
			regist(userId, nickname);
		}
		
		log.info(objNode.toString());
		if(statusCode == 200) {
			return true;
		} 
		return false;
	}
	/**
	 * 批量用户注册环信
	 * @param userList [{userId, nickName}]
	 */
	public static void registBatch(List<Map<String,Object>> users) {
		
		if (users == null || users.isEmpty()) 
		{
			return;
		}

		List<Map<String, Object>> userList = new ArrayList<Map<String,Object>>();
		
		for (Map<String, Object> map : users) 
		{
			Map<String, Object> user = new HashMap<String, Object>();
	
			int userId = (int)map.get("id");
			user.put("username", generateUserName(userId));
			user.put("password", generatePassword(userId));
			
			String nickName = (String)map.get("nickName");
			nickName = getValidNickName(userId, nickName);
			user.put("nickname", nickName);
			userList.add(user);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(userList);
			ObjectNode objNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, json, HTTPMethod.METHOD_POST);
			int statusCode = objNode.get("statusCode").asInt();
			log.info("EasemobUtils registBatch 批量注册环信结果" + statusCode + " " + objNode.toString());
		} catch (JsonProcessingException e) {
			log.error("EasemobUtils registBatch" +e.getMessage() );
		}
	}
	
	public static String generatePassword(int userId) 
	{
		return EncryptUtil.encrptMD5(AppConst.HUANXIN_ID_PREFIX + userId, "UTF-8").toUpperCase();
	}

	public static String generateUserName(int userId) 
	{
		return AppConst.HUANXIN_ID_PREFIX + userId;
	}

	private static String getValidNickName(int userId, String nickName) 
	{
		return nickName == null || "".equals(nickName.trim()) ? "account_" + userId : nickName;
	}
	
}
