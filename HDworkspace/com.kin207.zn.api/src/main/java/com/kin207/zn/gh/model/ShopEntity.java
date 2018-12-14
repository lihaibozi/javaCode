package com.kin207.zn.gh.model;

import java.util.List;
import java.util.Map;

import common.plugin.mybatis.Db;
import common.plugin.mybatis.Model;
import common.plugin.mybatis.Table;

/**
 * 店面实体类
 * @author KinFeng
 */
@Table("t_gh_shop")
public class ShopEntity  extends Model<ShopEntity>{
	
	/**
	 * 根据店面信息
	 * @return
	 */
	public static List<Map<String,Object>> findAllShop(){
		String sql = "SELECT * FROM t_gh_shop";
		return  Db.find(sql, null);
	}
	
}
