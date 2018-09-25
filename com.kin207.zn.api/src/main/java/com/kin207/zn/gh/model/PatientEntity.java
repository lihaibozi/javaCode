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
}
