package com.kin207.zn.gh.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
	public String regist(HttpServletRequest request, HttpServletResponse response) {
    	MobileParam mobileParam = (MobileParam) request.getAttribute(AppConst.MOBILE_KEY_PARAM);
    	Map<String, Object> map = new HashMap<String, Object>();
    	PatientEntity patient = new PatientEntity();
    	patient.set("transferId", 123);
    	patient.set("openId", mobileParam.getPara("openId"));
    	patient.set("patientName",mobileParam.getPara("patientName"));
    	patient.set("patientSex",  mobileParam.getPara("patientSex"));
    	patient.set("patientAge",  mobileParam.getPara("patientAge"));
    	patient.set("patientCardNo",  mobileParam.getPara("patientCardNo"));
    	patient.set("patientPhoneNumber",  mobileParam.getPara("patientPhoneNumber"));
    	patient.set("patientAddress",  mobileParam.getPara("patientAddress"));
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
    
}
