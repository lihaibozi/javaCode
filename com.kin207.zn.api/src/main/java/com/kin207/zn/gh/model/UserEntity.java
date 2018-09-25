package com.kin207.zn.gh.model;

import java.util.Map;

import org.apache.log4j.Logger;

import common.plugin.mybatis.Db;
import common.plugin.mybatis.Model;
import common.plugin.mybatis.Table;

@Table("t_gh_user")
public class UserEntity extends Model<UserEntity>{
	public static Logger log = Logger.getLogger(UserEntity.class);
	
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	public static int saveUser(UserEntity user){
		try {
			user.save();
		} catch (Exception e) {
			log.error("regist :" +user.attrSet());
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 根据用户openId查询用户信息
	 * @param orderId
	 * @return
	 */
	public static UserEntity findByOpenId(String openId){
		String sql = "SELECT * FROM t_gh_user u WHERE u.openId = #{openId} limit 1 ";
		return  Db.findFirst(UserEntity.class, sql, "openId", openId);
	}
	
	/**
	 * 根据用户主键ID查询用户信息
	 * @param orderId
	 * @return
	 */
	public static UserEntity findById(String id){
		String sql = "SELECT * FROM t_gh_user WHERE id = #{id} limit 1 ";
		return  Db.findFirst(UserEntity.class, sql, "id", id);
	}
	
	
	
	
}
