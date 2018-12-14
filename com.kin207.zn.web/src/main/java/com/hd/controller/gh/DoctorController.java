package com.hd.controller.gh;

import javax.annotation.Resource;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.DoctorManager;
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.HospitalManager;
import com.hd.util.AppUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;

import jodd.util.StringUtil;

/** 
 * 类名称：医生信息维护
 * 创建人：lihaibo
 * 修改时间：2018年9月21日
 * @version
 */
@Controller
@RequestMapping(value="/doctor")
public class DoctorController extends BaseController {
	
	String menuUrl = "doctor/doctorList.do"; //医生地址(权限用)
	@Resource(name="doctorService")
	private DoctorManager doctorService;
	@Resource(name="hospitalService")
	private HospitalManager hospitalService;
	@Resource(name="hDepartmentService")
	private HdepartmentManager hDepartmentService;
	
	/**显示医生列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/doctorList")
	public ModelAndView listDoctors(Page page){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	doctorList = doctorService.doctorList(page);		//列出医生信息列表
			mv.setViewName("gh/doctor/doctor_list");
			mv.addObject("doctorList", doctorList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面
	 * @return
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> listHospital = hospitalService.hospitals(pd);
		mv.setViewName("gh/doctor/doctor_edit");
		mv.addObject("listHospital",listHospital);
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
		pd = doctorService.findById(pd);	//根据ID读取
		List<PageData> listHospital = hospitalService.hospitals(pd);
		List<PageData> listDepartment = hDepartmentService.getDepartments(pd);
		mv.setViewName("gh/doctor/doctor_edit");
		mv.addObject("listHospital",listHospital);
		mv.addObject("listDepartment",listDepartment);
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
	@RequestMapping(value="/save")
	public ModelAndView save(
			HttpServletRequest request,
			@RequestParam(value="doctorName",required=false)  String  doctorName ,
			@RequestParam(value="workId",required=false)String workId,
			@RequestParam(value="age",required=false)String age,
			@RequestParam(value="sex",required=false)String sex,
			@RequestParam(value="cardNo",required=false)String cardNo,
			@RequestParam(value="cellhone",required=false)String cellhone,
			@RequestParam(value="proTitle",required=false)String proTitle,
			@RequestParam(value="duty",required=false)String duty,
			@RequestParam(value="departmentId",required=false)String departmentId,
			@RequestParam(value="hospitalId",required=false)String hospitalId
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("doctorName", doctorName.trim());								
			pd.put("workId", StringUtil.isEmpty(workId)?"":workId.trim());						   
			pd.put("age", StringUtil.isEmpty(age)?"":age.trim());					
			pd.put("sex", sex);						   
			pd.put("cardNo", StringUtil.isEmpty(cardNo)?"":cardNo.trim());	
			pd.put("cellhone", StringUtil.isEmpty(cellhone)?"":cellhone.trim());	
			pd.put("proTitle", StringUtil.isEmpty(proTitle)?"":proTitle.trim());	
			pd.put("duty", StringUtil.isEmpty(duty)?"":duty.trim());								
			pd.put("departmentId", departmentId);						   
			pd.put("hospitalId", hospitalId);					
			doctorService.save(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
			doctorService.delete(pd);
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
				doctorService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			}
		return AppUtil.returnObject(pd, map);
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
			@RequestParam(value="doctorName",required=false)  String  doctorName ,
			@RequestParam(value="workId",required=false)String workId,
			@RequestParam(value="age",required=false)String age,
			@RequestParam(value="sex",required=false)String sex,
			@RequestParam(value="cardNo",required=false)String cardNo,
			@RequestParam(value="cellhone",required=false)String cellhone,
			@RequestParam(value="proTitle",required=false)String proTitle,
			@RequestParam(value="duty",required=false)String duty,
			@RequestParam(value="departmentId",required=false)String departmentId,
			@RequestParam(value="hospitalId",required=false)String hospitalId
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("id", id.trim());
			pd.put("doctorName", doctorName);								
			pd.put("workId", StringUtil.isEmpty(workId)?"":workId.trim());						   
			pd.put("age", StringUtil.isEmpty(age)?"":age);					
			pd.put("sex", sex);						   
			pd.put("cardNo", StringUtil.isEmpty(cardNo)?"":cardNo.trim());	
			pd.put("cellhone", StringUtil.isEmpty(cellhone)?"":cellhone.trim());	
			pd.put("proTitle", StringUtil.isEmpty(proTitle)?"":proTitle.trim());	
			pd.put("duty", StringUtil.isEmpty(duty)?"":duty.trim());								
			pd.put("departmentId", departmentId);						   
			pd.put("hospitalId", hospitalId);			
			doctorService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/firstPage")
	public ModelAndView firstPage(Page page){

		ModelAndView mv = this.getModelAndView();
		try{
			mv.setViewName("gh/doctor/firstPage");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
}
