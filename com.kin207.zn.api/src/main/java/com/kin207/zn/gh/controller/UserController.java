package com.kin207.zn.gh.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kin207.zn.gh.model.UserAddressEntity;
import com.kin207.zn.gh.model.UserEntity;
import com.kin207.zn.support.phoneMsg.PhoneMsgService;

import common.plugin.mybatis.Db;
import common.utils.StringUtil;

/**
 * 用户模块API
 * @author KinFeng
 */
@Controller
@RequestMapping("/gh/user")
public class UserController {
	private static String baseUrl = "gh/user/";
	public static Logger log = Logger.getLogger(UserController.class);
	
    /**
	 * 重置密码－1：发送验证码
	 * @param param
	 * @return
	 */
	@RequestMapping("/sendCaptcha")
	@ResponseBody
	public String sendCaptcha(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String account = request.getParameter("account");
		String captcha = StringUtil.randNum(6);
		if (StringUtil.isMobileNumber(account)) {
			// 发短信
			PhoneMsgService.sendResetPassCaptcha(account, captcha, null);
		}
		map.put("validTime", 60);
		map.put("captcha", captcha);
		return new Gson().toJson(map); 
	}
	/**
	 * 注册
	 */
    @RequestMapping("/regist")
    @ResponseBody
	public String regist(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="account",required=false) String phone ,
			@RequestParam(value="shopId",required=false) String shopId ,
			@RequestParam(value="nickName",required=false) String nickName ,
			@RequestParam(value="openId",required=false)String openId,
			@RequestParam(value="photo",required=false)String photo,    //用户微信头像
			@RequestParam(value="gender",required=false) String gender) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	UserEntity user = new UserEntity();
    	user.set("shopId", shopId);
    	user.set("openId", openId);
    	user.set("nickName", nickName);
    	user.set("account", phone);
    	user.set("photo", photo);
    	user.set("gender", gender);
    	user.set("createTime", new Date());
    	try {
			Db.save(user);
			map.put("result", 1);
		} catch (Exception e) {
			map.put("result", 0);
			e.printStackTrace();
		}
		return new Gson().toJson(map);
	}
    
    
    /**
	 * 保存地址
	 */
    @RequestMapping("/saveAddress")
    @ResponseBody
	public String saveAddress(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="userId",required=false)String userId,
			@RequestParam(value="openId",required=false)String openId,
			@RequestParam(value="recipients",required=false)String recipients,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="detailedAddress",required=false)String detailedAddress,
			@RequestParam(value="address",required=false)String address) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		if(!StringUtils.hasText(userId)){
    			UserEntity user = UserEntity.findByOpenId(openId);
    			if(user != null)
    				userId = user.getId().toString();
    		}
    		UserAddressEntity ua = new UserAddressEntity();	
        	ua.set("userId", userId)//用户ID
        		.set("recipients", recipients)//收件人
        		.set("phone", phone)//手机
        		.set("createTime", new Date())
        		.set("detailedAddress", detailedAddress)//详细地址
        		.set("address", address).save();//地址
		} catch (Exception e) {
			log.error("saveAddress :"+ e.getMessage());
		}
    	map.put("result", true);
    	map.put("userId", userId);
		return new Gson().toJson(map);
	} 
    
	/**
	 * 地址列表
	 */
	@RequestMapping("/userAddress")
	@ResponseBody
	public String userAddress(HttpServletRequest request, HttpServletResponse response){
			String userId = request.getParameter("userId");
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> address= UserAddressEntity.findByUserId(userId);
			if(address!=null){
				map.put("addressList", "address");
			}
			return new Gson().toJson(map);
	}
	
	/**
	 * 前往个人中心
	 */
	@RequestMapping("/goUserCenter")
	@ResponseBody
	public String goUserAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="openId",required=false)String openId) {
			Map<String, Object> map = new HashMap<String, Object>();
			UserEntity user = UserEntity.findByOpenId(openId);
			if(user!=null){
				map.put("userId", user.get("id"));
				map.put("account", user.get("account"));
				map.put("shopId", user.get("shopId"));
				map.put("nickName", user.get("nickName"));
				map.put("photo", user.get("photo"));
				map.put("openId", user.get("openId"));
			}
		
			return new Gson().toJson(map);
	}
   
}
