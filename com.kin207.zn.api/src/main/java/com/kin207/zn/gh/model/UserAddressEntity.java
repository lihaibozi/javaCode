package com.kin207.zn.gh.model;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import common.plugin.mybatis.Db;
import common.plugin.mybatis.Model;
import common.plugin.mybatis.Table;


@Table("t_gh_address")
public class UserAddressEntity extends Model<UserAddressEntity>{
	public static Logger log = Logger.getLogger(UserAddressEntity.class);
	
	/**
	 * 根据用户查询用户的地址
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> findByUserId(String userId) {
			String sql = "select * from t_gh_address where userId = #{userId} ";
			return Db.find(sql, "userId", userId);
	}
}
