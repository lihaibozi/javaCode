package com.hd.controller.gh;

import javax.annotation.Resource;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.HosdepManager;
import com.hd.service.gh.impl.HospitalService;
import com.hd.util.AppUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;

import jodd.util.StringUtil;
import net.sf.json.JSONArray;

/** 
 * 类名称：科室信息维护
 * 创建人：lihaibo
 * 修改时间：2018年10月26日
 * @version
 */
@Controller
@RequestMapping(value="/hosdep")
public class HosdepController extends BaseController {
	
	String menuUrl = "hosdep/hosdep.do"; //科室地址(权限用)
	@Resource(name="hosdepService")
	private HosdepManager hosdepService;
	@Resource(name="hospitalService")
	private HospitalService hospitalService;
	@Resource(name="hDepartmentService")
	private HdepartmentManager hDepartmentService;
	
	/**显示科室信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/hosdep")
	public ModelAndView hosdepList(Page page){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	hosdepList = hosdepService.hosdepList(page);		//分页列出科室信息列表
			mv.setViewName("gh/hosdep/hosdep_list");
			mv.addObject("hosdepList", hosdepList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 返回医院下科室信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getDeps")
	@ResponseBody
	public String getMemberAndPhone(HttpServletRequest request,
			@RequestParam(value="id",required=false)  String  id ) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		pd.put("id", id);
		List<PageData> list = hosdepService.getDeps(pd);				//执行查询
	    return list.size()>0?JSONArray.fromObject(list).toString():"";
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
			hosdepService.delete(pd);
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
				hosdepService.deleteAll(ArrayDATA_IDS);
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
		pd.put("depHos", 1); //查询本院的科室
		pd.put("hosId", 1); //查询联盟医院
		List<PageData> listHospital = hospitalService.hospitals(pd);
		List<PageData> listDepartment = hDepartmentService.departments(pd);
		mv.setViewName("gh/hosdep/hosdep_edit");
		mv.addObject("listHospital",listHospital);
		mv.addObject("listDepartment",listDepartment);
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
		pd.put("depHos", 1); //查询本院的可是
		pd.put("hosId", 1); //查询联盟医院
		PageData pdd = hosdepService.findById(pd);	//根据ID读取
		List<PageData> listHospital = hospitalService.hospitals(pd);
		List<PageData> listDepartment = hDepartmentService.departments(pd);
		mv.setViewName("gh/hosdep/hosdep_edit");
		mv.addObject("msg", "edit");
		mv.addObject("listHospital", listHospital);
		mv.addObject("pd", pdd);
		mv.addObject("listDepartment", listDepartment);
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
			@RequestParam(value="hosId",required=false) String hosId,
			@RequestParam(value="depId",required=false) String depId
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			pd.put("hosId", hosId);
			pd.put("depId", depId);
			hosdepService.save(pd);
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
			@RequestParam(value="hosId",required=false) String hosId,
			@RequestParam(value="depId",required=false) String depId
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("id", id);
			pd.put("hosId", hosId);
			pd.put("depId",depId);
			hosdepService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
}
