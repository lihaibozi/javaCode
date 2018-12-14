package com.kin207.zn.gh.model;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import common.plugin.mybatis.Db;
import common.plugin.mybatis.Model;
import common.plugin.mybatis.Table;

@Table("t_patient_info")
public class PatientEntity extends Model<PatientEntity>{
	public static Logger log = Logger.getLogger(PatientEntity.class);
	
	/**
	 * 根据时间段查询患者信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> findByTime(String begin,String end) {
			String sql = "SELECT a.doctorArea,a.doctorName,a.doctorNickName,"
					+ " a.doctorWxId,a.dotorHos,b.patientAddress,b.patientAge,"
					+ " b.patientCardNo,b.patientName,b.patientPhoneNumber,"
					+ " b.patientSex,b.transferId FROM t_doctor a "
					+ " JOIN t_patient_info b ON a.openId = b.openId WHERE "
					+ " b.createTime >= '"+begin+"'"
					+ " AND b.createTime <= '"+ end+"'";
			return  Db.find(sql, null);
	}
}
