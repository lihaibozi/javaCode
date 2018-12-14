package com.hd.controller.gh;

import javax.annotation.Resource;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.HospitalManager;
import com.hd.util.AppUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;
import com.hd.util.StringUtil;


/** 
 * 类名称：医院信息维护
 * 创建人：lihaibo
 * 修改时间：2018年11月22日
 * @version
 */
@Controller
@RequestMapping(value="/hospital")
public class HospitalController extends BaseController {
	
	String menuUrl = "hospital/hospital.do"; //人员地址(权限用)
	@Resource(name="hospitalService")
	private HospitalManager hospitalService;
	@Resource(name="hDepartmentService")
	private HdepartmentManager hDepartmentService;
	
	/**显示科室信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/hospital")
	public ModelAndView hospitalList(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	hospitalList = hospitalService.hospitalList(page);		//分页列出人员信息列表
			mv.setViewName("gh/hospital/hospital_list");
			mv.addObject("hospitalList", hospitalList);
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
			hospitalService.delete(pd);
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
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				hospitalService.deleteAll(ArrayDATA_IDS);
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
		List<PageData> listDeparts = hDepartmentService.departments(pd);
		mv.setViewName("gh/hospital/hospital_edit");
		mv.addObject("listDeparts",listDeparts);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
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
		pd = hospitalService.findById(pd);	//根据ID读取
		mv.setViewName("gh/hospital/hospital_edit");
		mv.addObject("msg", "edit");
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
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="address",required=false)String address ,
			@RequestParam(value="level",required=false)String level ,
			@RequestParam(value="firstSpell",required=false)  String  firstSpell,
			@RequestParam(value="contactPerson",required=false)  String  contactPerson,
			@RequestParam(value="contactPhone",required=false)  String  contactPhone
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		SimpleDateFormat sdf = new SimpleDateFormat("SSS");
		String code =  "00"+sdf.format(new Date())+new Random().nextInt(10);
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			pd.put("code", code);
			pd.put("name", name.trim());
			pd.put("address", StringUtils.isEmpty(address)?"":address.trim());
			pd.put("level", StringUtils.isEmpty(level)?"":level.trim());
			pd.put("firstSpell", StringUtils.isEmpty(firstSpell)?"":firstSpell);
			pd.put("contactPerson", StringUtils.isEmpty(contactPerson)?"":contactPerson.trim());
			pd.put("contactPhone", StringUtils.isEmpty(contactPhone)?"":contactPhone.trim());
			hospitalService.save(pd);
		}
		map.put("result", "ok");
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="address",required=false)String address ,
			@RequestParam(value="level",required=false)String level ,
			@RequestParam(value="firstSpell",required=false)  String  firstSpell,
			@RequestParam(value="contactPerson",required=false)  String  contactPerson,
			@RequestParam(value="contactPhone",required=false)  String  contactPhone
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("id", id);
			pd.put("name", name.trim());	
			pd.put("address", StringUtils.isEmpty(address)?"":address.trim());
			pd.put("level", StringUtils.isEmpty(level)?"":level.trim());
			pd.put("firstSpell", StringUtils.isEmpty(firstSpell)?"":firstSpell);
			pd.put("contactPerson", StringUtils.isEmpty(contactPerson)?"":contactPerson.trim());
			pd.put("contactPhone", StringUtils.isEmpty(contactPhone)?"":contactPhone.trim());
			hospitalService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**判断医院名称是不是存在
	 * @return
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(hospitalService.findByName(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
}
