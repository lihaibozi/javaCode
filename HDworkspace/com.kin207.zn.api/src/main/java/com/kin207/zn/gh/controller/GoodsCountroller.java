package com.kin207.zn.gh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kin207.zn.gh.service.GoodsService;

/**
 * 用户商品模块API
 * @author KinFeng
 */
@Controller
@RequestMapping("/gh/goods")
public class GoodsCountroller {

	@Autowired
	private GoodsService goodsService;
	/**
	 * 获取商品信息
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/getGoods")
	@ResponseBody
	public String checkPackages(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="shopId",required=false)  String  shopId ) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<Map<String,Object>> goods = goodsService.getAllGoods();
    	map.put("goods", goods);
    	return new Gson().toJson(map); 
			
	}

}
