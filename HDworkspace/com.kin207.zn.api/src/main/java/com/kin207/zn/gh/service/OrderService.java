package com.kin207.zn.gh.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kin207.zn.gh.model.OrderEntity;

@Transactional
@Service
public class OrderService {
	/**
	 * 订单数据
	 * @return
	 */
	public List<Map<String,Object>> getOrders(String userId){
		 List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		List<Map<String,Object>> orders = OrderEntity.findOrderByUserId(userId);
		if(orders!=null&&!"".equals(orders)){
		for(Map<String, Object> order :orders){
			 String qt = order.get("goods").toString();
			 order.remove("goods");
			 order.put("goods",  JSON.parse(qt));
			 orderList.add(order);
		 }
		}
		return orderList;
	}
}
