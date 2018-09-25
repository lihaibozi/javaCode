package com.kin207.zn.gh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kin207.zn.gh.model.ShopEntity;
import com.kin207.zn.support.MobileParam;

@Controller
@RequestMapping("/gh/shop")
public class ShopCountroller {
	/**
	 * 获取个人订单
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/getShops")
	@ResponseBody
	public String getShops(HttpServletRequest request, HttpServletResponse response,MobileParam param
			) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<Map<String,Object>> shops = ShopEntity.findAllShop();
    	map.put("shops", shops);
    	return new Gson().toJson(map); 
	}
}
