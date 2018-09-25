package com.kin207.zn.gh.model;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import common.plugin.mybatis.Db;
import common.plugin.mybatis.Model;
import common.plugin.mybatis.Table;

@Table("t_gh_order")
public class OrderEntity extends Model<OrderEntity>{
	public static Logger log = Logger.getLogger(OrderEntity.class);
	
	/**
	 * 根据用户openId查询用户信息
	 * @param orderId
	 * @return
	 */
	public static List<Map<String,Object>> findOrderByUserId(String userId){
		String sql = "SELECT o.shopId,o.orderNo,o.price,o.`status`,o.type,CONCAT('[', group_concat(JSON_OBJECT('name',g.`name`,'image',g.image,'pric',g.pric) ) ,']')as goods  "
				+ "FROM t_gh_order o,t_gh_order_detail d,t_gh_goods g WHERE o.orderNo = d.orderNo AND d.goodsId = g.id AND o.userId =#{userId} GROUP BY o.id ";
		return  Db.find(sql, "userId",userId);
	}
	
}
