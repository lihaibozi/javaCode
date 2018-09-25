package com.kin207.zn.gh.model;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import common.plugin.mybatis.Db;
import common.plugin.mybatis.Model;
import common.plugin.mybatis.Table;

@Table("t_gh_goods")
public class GoodsEntity extends Model<GoodsEntity>{
	public static Logger log = Logger.getLogger(GoodsEntity.class);
	
	/**
	 * 根据用户openId查询用户信息
	 * @param orderId
	 * @return
	 */
	public static List<Map<String,Object>> findAllGoods(){
		String sql = "SELECT  g.name,g.pric,g.id,g.image,t.`code` typeCode,t.`name` AS typeName "
				+ "FROM t_gh_goods g LEFT JOIN t_gh_type t  ON g.typeCode = t.`code`";
		return  Db.find(sql, null);
	}
	
	
	
	
}
