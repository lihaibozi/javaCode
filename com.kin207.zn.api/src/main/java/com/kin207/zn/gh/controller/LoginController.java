package com.kin207.zn.gh.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.kin207.zn.AppConst;
import com.kin207.zn.gh.model.DoctorEntity;
import com.kin207.zn.gh.model.PatientEntity;
import com.kin207.zn.gh.util.AesCbcUtil;
import com.kin207.zn.gh.util.HttpRequest;
import com.kin207.zn.support.MobileParam;

import common.plugin.mybatis.Db;


@Controller
@RequestMapping("/gh/login")
public class LoginController {

    @RequestMapping("/login")
    @ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response) {
    	MobileParam mobileParam = (MobileParam) request.getAttribute(AppConst.MOBILE_KEY_PARAM);
    	String encryptedData = mobileParam.getPara("encryptedData");
    	String iv = mobileParam.getPara("iv");
    	String code = mobileParam.getPara("code");
    	Map<String, Object> map = new HashMap<String, Object>();
    	 
		// 登录凭证不能为空
		if (code == null || code.length() == 0) {
			map.put("status", 0);
			map.put("msg", "code 不能为空");
			return new Gson().toJson(map);
		}
 
		// 小程序唯一标识 (在微信小程序管理后台获取)
		String wxspAppid = "wx3a319dd3c2d52d47";
		// 小程序的 app secret (在微信小程序管理后台获取)
		String wxspSecret = "107b1b7eaa39f8f2808f0cead075fcae";
		// 授权（必填）
		String grant_type = "authorization_code";
 
		//////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
		//////////////// ////////////////
		// 请求参数
		String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type="
				+ grant_type;
		// 发送请求
		String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
		try {
			sr = new String(sr.getBytes("ISO-8859-1"), "UTF-8");
			// 解析相应内容（转换成json对象）
			JSONObject json = new JSONObject(sr);
			// 获取会话密钥（session_key）
			String session_key = json.get("session_key").toString();
			// 用户的唯一标识（openid）
			//String openid = (String) json.get("openid");
			
			//////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
			String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
			if (null != result && result.length() > 0) {
				JSONObject userInfoJSON = new JSONObject(result);
				Map<String,Object> userInfo = new HashMap<String,Object>();
				userInfo.put("openId", userInfoJSON.get("openId"));
				DoctorEntity doctor= DoctorEntity.findByOpenId(String.valueOf(userInfoJSON.get("openId")));
				if(doctor!=null){
					//修改数据的昵称信息
					
					doctor.set("doctorNickName", userInfoJSON.get("nickName"));
					doctor.update();
					
					map.put("userInfo", userInfo);	
					if("".equals(doctor.get("doctorName"))||doctor.get("doctorName")==null){
						map.put("result", 2);
						map.put("msg", "资料尚未维护");
					}else{
						if(Integer.parseInt(String.valueOf(doctor.get("isInUse")))==0){
							map.put("result", 1);
							map.put("msg", "登录成功");
						}else{
							map.put("result", 3);
							map.put("msg", "被禁用");
						}
					}
				}else{
					DoctorEntity d = new DoctorEntity();
					d.set("openId", userInfoJSON.get("openId"));
					d.set("doctorNickName", userInfoJSON.get("nickName"));
					d.set("createTime", new Date());
					d.save();
					map.put("result", 2);	
					map.put("userInfo", userInfo);
				}
			} else {
				map.put("result", 0);
				map.put("msg", "解密失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Gson().toJson(map);
	}
}
