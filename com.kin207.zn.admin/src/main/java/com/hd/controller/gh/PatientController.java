package com.hd.controller.gh;

import javax.annotation.Resource;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.PatientManager;
import com.hd.util.AppUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;

/** 
 * 类名称：病人信息列表和展示
 * 创建人：lihaibo
 * 修改时间：2018年10月13日
 * @version
 */
@Controller
@RequestMapping(value="/patient")
public class PatientController extends BaseController {
	
	String menuUrl = "patient/patientList.do"; //病人地址(权限用)
	@Resource(name="patientService")
	private PatientManager patientService;
	
	/**显示病人列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/patientList")
	public ModelAndView patientList(Page page){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	patientList = patientService.patientList(page);		//分页列出病人信息列表
			List<PageData>	patientAllList = patientService.patientList(pd);	
			if(patientList.size()>0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Multimap<String,Integer> multimap = ArrayListMultimap.create();
				for(PageData p :patientAllList){
					String time = String.valueOf(p.get("createTime")).substring(0,10);
					String birthTime = String.valueOf(p.get("patientBirth")).substring(0,10);
					Long millions = new Date().getTime()-sdf.parse(birthTime).getTime();
					int age = (int) (millions/1000/24/60/60/365);
					multimap.put(time,age);
				}
				for(PageData p :patientList){
					String time = String.valueOf(p.get("createTime")).substring(0,10);
					p.put("patientCount", multimap.get(time).size());
					Collection<Integer> ages = multimap.get(time);
					int total = 0;
					for(Integer age:ages){
						total += age;
					}
					p.put("avgAge", total/ages.size());
				}
			}
			
			mv.setViewName("gh/patient/patient_list");
			mv.addObject("patientList", patientList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	

	/**删除
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return;
		} 
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			pd = this.getPageData();
		    patientService.delete(pd);
		}
		out.write("success");
		out.close();
	}
	
	/**批量删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			List<PageData> pdList = new ArrayList<PageData>();
			List<PageData> pathList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				patientService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			}
		return AppUtil.returnObject(pd, map);
	}
	
	/**去新增页面
	 * @return
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("gh/patient/patient_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 新增
	 * @param file
	 * @param name
	 * @param typeCode
	 * @param remark
	 * @param pric
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(
			@RequestParam(value="patientName",required=false)String patientName,
			@RequestParam(value="patientSex",required=false)  String  patientSex ,
			@RequestParam(value="patientCardNo",required=false)  String patientCardNo,
			@RequestParam(value="patientPhoneNumber",required=false)String patientPhoneNumber,
			@RequestParam(value="patientBirth",required=false) String patientBirth
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		Page page = new Page();
		List<PageData>	patientList = patientService.patientList(page);	
		String hosNumber = "ZY001";
		if(patientList.size()>0){
			hosNumber =  (String) patientList.get(0).get("hosNumber");
			int number = Integer.parseInt(hosNumber.substring(2))+1;
			hosNumber = "ZY"+String.format("%03d", number);
			for(PageData pageData:patientList){
				String name = pageData.getString("patientName");
				if(name.equals(patientName)){
					patientName = patientName+hosNumber;
				}
			}
		}
		
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			pd.put("patientName", patientName.trim());
			pd.put("hosNumber", hosNumber.trim());
			pd.put("patientSex", patientSex.trim());	
			pd.put("patientCardNo", patientCardNo.trim());						
			pd.put("patientPhoneNumber", patientPhoneNumber.trim());						   
			pd.put("patientBirth", patientBirth.trim());								
			pd.put("lastYDate", new Date());
			pd.put("createTime", new Date());
			patientService.save(pd);
		}
		map.put("result", "ok");
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**去修改页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = patientService.findById(pd);	//根据ID读取
		mv.setViewName("gh/patient/patient_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 修改
	 * @param request
	 * @param id
	 * @param file
	 * @param name
	 * @param remark
	 * @param pric
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			HttpServletRequest request,
			@RequestParam(value="id",required=false)  String  id ,
			@RequestParam(value="patientName",required=false)  String  patientName ,
			@RequestParam(value="patientSex",required=false)String patientSex,
			@RequestParam(value="patientCardNo",required=false)String patientCardNo,
			@RequestParam(value="patientPhoneNumber",required=false)String patientPhoneNumber,
			@RequestParam(value="patientBirth",required=false)String patientBirth
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("id", id);
			pd.put("patientName", patientName);								
			pd.put("patientSex", patientSex);						   
			pd.put("patientCardNo", patientCardNo);					
			pd.put("patientPhoneNumber", patientPhoneNumber);						   
			pd.put("patientBirth", patientBirth);				
			patientService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	

	
	
	
}
