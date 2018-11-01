package com.kin207.zn.gh.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.kin207.zn.AppConst;
import com.kin207.zn.gh.model.PatientEntity;
import com.kin207.zn.support.MobileParam;

import common.plugin.mybatis.Db;

@Controller
@RequestMapping("/gh/patient")
public class PatientInfoController {
	/**
	 * 患者信息保存
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/save")
    @ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response) {
    	MobileParam mobileParam = (MobileParam) request.getAttribute(AppConst.MOBILE_KEY_PARAM);
    	Map<String, Object> map = new HashMap<String, Object>();
    	PatientEntity patient = new PatientEntity();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	patient.set("transferId", sdf.format(new Date())+new Random().nextInt(10));
    	patient.set("openId", mobileParam.getPara("openId").trim());
    	patient.set("patientName",mobileParam.getPara("patientName").trim());
    	patient.set("patientSex",  mobileParam.getPara("patientSex").trim());
    	patient.set("patientAge",  mobileParam.getPara("patientAge").trim());
    	patient.set("patientCardNo",  mobileParam.getPara("patientCardNo").trim());
    	patient.set("patientPhoneNumber",  mobileParam.getPara("patientPhoneNumber").trim());
    	patient.set("patientAddress",  mobileParam.getPara("patientAddress").trim());
    	patient.set("createTime", new Date());
    	try {
			int result = Db.save(patient);
			map.put("result", result);
		} catch (Exception e) {
			map.put("result", 0);
			e.printStackTrace();
		}
		return new Gson().toJson(map);
	}

    /**
	 * 查询某一段时间内患者及转诊医生信息
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/getPatients")
    @ResponseBody
	public String getPatients(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="startTime",required=false)  String startTime ,
			@RequestParam(value="endTime",required=false) String endTime) {
    	MobileParam mobileParam = (MobileParam) request.getAttribute(AppConst.MOBILE_KEY_PARAM);
    	Map<String, Object> map = new HashMap<String, Object>();
    	String begin = mobileParam.getPara("startTime")+" 00:00:01";
    	String end = mobileParam.getPara("endTime")+" 23:59:59";
    	List<Map<String,Object>> list = PatientEntity.findByTime(begin, end);
    	if(list!=null){
    		map.put("patientList", list);
    	}
		return new Gson().toJson(map);
	}
    
}
