package com.hd.controller.gh;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.JzDoctorManager;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;

/** 
 * 类名称：医生信息维护
 * 创建人：lihaibo
 * 修改时间：2018年9月21日
 * @version
 */
@Controller
@RequestMapping(value="/jzdoctor")
public class JzDoctorController extends BaseController {
	
	String menuUrl = "jzdoctor/jzdoctorList.do"; //医生地址(权限用)
	@Resource(name="jzdoctorService")
	private JzDoctorManager jzdoctorService;
	
	/**显示医生列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/jzdoctorList")
	public ModelAndView listjzdoctors(Page page){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	jzdoctorList = jzdoctorService.doctorList(page);		//列出医生信息列表
			mv.setViewName("gh/jzdoctor/jzdoctor_list");
			mv.addObject("jzdoctorList", jzdoctorList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		pd = jzdoctorService.findById(pd);	//根据ID读取
		mv.setViewName("gh/jzdoctor/jzdoctor_edit");
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
			@RequestParam(value="doctorNickName",required=false)  String  doctorNickName ,
			@RequestParam(value="doctorName",required=false)String doctorName,
			@RequestParam(value="doctorWxId",required=false)String doctorWxId,
			@RequestParam(value="dotorHos",required=false)String dotorHos,
			@RequestParam(value="doctorArea",required=false)String doctorArea,
			@RequestParam(value="isInUse",required=false)String isInUse
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("id", id.trim());
			pd.put("doctorNickName", StringUtils.isEmpty(doctorNickName)?"":doctorNickName.trim());								
			pd.put("doctorName", StringUtils.isEmpty(doctorName)?"":doctorName.trim());						   
			pd.put("doctorWxId", StringUtils.isEmpty(doctorWxId)?"":doctorWxId.trim());					
			pd.put("dotorHos", StringUtils.isEmpty(dotorHos)?"":dotorHos.trim());						   
			pd.put("doctorArea", StringUtils.isEmpty(doctorArea)?"":doctorArea.trim());	
			pd.put("isInUse", isInUse.trim());	
			pd.put("modifyDate", new Date());				
			jzdoctorService.edit(pd);				//执行修改数据库
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
			mv.setViewName("gh/jzdoctor/firstPage");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
}
