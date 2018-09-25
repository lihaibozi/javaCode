package com.kin207.zn.gh.controller;

import java.util.Date;
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
import com.kin207.zn.gh.model.OrderDetailEntity;
import com.kin207.zn.gh.model.OrderEntity;
import com.kin207.zn.gh.service.OrderService;

import common.plugin.mybatis.Db;

@Controller
@RequestMapping("/gh/shop")
public class OrderCountroller {
	@Autowired
	private OrderService orderService;
	/**
	 * 获取订单信息
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/getOrders")
	@ResponseBody
	public String getShops(HttpServletRequest request, HttpServletResponse response
			) {
    	String userId = request.getParameter("userId");
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<Map<String,Object>> orders = orderService.getOrders(userId);
    	map.put("orders", orders);
    	return new Gson().toJson(map); 
	}
    
    
	/**
	 * 订单保存
	 */
    @RequestMapping("/save")
    @ResponseBody
	public String regist(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="ordrNo",required=false) String ordrNo ,
			@RequestParam(value="shopId",required=false) String shopId ,
			@RequestParam(value="userId",required=false) String userId ,
			@RequestParam(value="status",required=false)String status,
			@RequestParam(value="type",required=false)String type,   
			@RequestParam(value="price",required=false)String price,  
			@RequestParam(value="goodIds",required=false)String goodIds, 
			@RequestParam(value="addressId",required=false) String addressId) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	OrderEntity order = new OrderEntity();
    	String[] goodId = null;
    	if(goodIds!=""){
    		goodId = goodIds.split(",");
    	}
    	order.set("shopId", shopId);
    	order.set("ordrNo", ordrNo);
    	order.set("userId", userId);
    	order.set("status", status);
    	order.set("type", type);
    	order.set("price", price);
    	order.set("addressId", addressId);
    	order.set("createTime", new Date());
    	try {
			int result = Db.save(order);
			if(result==1&&goodId!=null){
				for(int i =0;i<goodId.length;i++){
					OrderDetailEntity od =new OrderDetailEntity();
					od.set("ordrNo", ordrNo);
					od.set("goodId", goodId[i]);
					od.set("createTime", new Date());
					od.save();
				}
			}
			map.put("result", 1);
		} catch (Exception e) {
			map.put("result", 0);
			e.printStackTrace();
		}
		return new Gson().toJson(map);
	}
}
