package com.kin207.zn.gh.model;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import common.plugin.mybatis.Db;
import common.plugin.mybatis.Model;
import common.plugin.mybatis.Table;

@Table("t_doctor_info")
public class DoctorEntity extends Model<DoctorEntity>{
	public static Logger log = Logger.getLogger(DoctorEntity.class);
	
	/**
	 * 根据用户openId查询用户信息
	 * @param orderId
	 * @return
	 */
	public static DoctorEntity findByOpenId(String openId){
		String sql = "SELECT * FROM t_doctor_info u WHERE u.openId = #{openId} limit 1 ";
		return  Db.findFirst(DoctorEntity.class, sql, "openId", openId);
	}
}
