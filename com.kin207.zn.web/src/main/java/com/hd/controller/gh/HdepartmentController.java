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
@RequestMapping(value="/hDepartment")
public class HdepartmentController extends BaseController {
	
	String menuUrl = "hDepartment/hDepartment.do"; //科室地址(权限用)
	@Resource(name="hDepartmentService")
	private HdepartmentManager hDepartmentService;
	@Resource(name="hospitalService")
	private HospitalService hospitalService;
	
	/**显示科室信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/hDepartment")
	public ModelAndView departmentList(Page page){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	departmentList = hDepartmentService.departmentList(page);		//分页列出科室信息列表
			mv.setViewName("gh/department/hDepartment_list");
			mv.addObject("departmentList", departmentList);
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
		List<PageData> list = hDepartmentService.getDeps(pd);				//执行查询
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
			hDepartmentService.delete(pd);
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
				hDepartmentService.deleteAll(ArrayDATA_IDS);
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
		List<PageData> listHospital = hospitalService.hospitals(pd);
		mv.setViewName("gh/department/hDepartment_edit");
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
		PageData pdd = hDepartmentService.findById(pd);	//根据ID读取
		List<PageData> listHospital = hospitalService.hospitals(pd);
		List<PageData> listDepartment = hDepartmentService.getDepartmentBydepHos(pd);
		mv.setViewName("gh/department/hDepartment_edit");
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
			@RequestParam(value="depName",required=false) String depName,
			@RequestParam(value="depPhone",required=false) String  depPhone,
			@RequestParam(value="depHos",required=false) String  depHos,
			@RequestParam(value="principal",required=false) String  principal,
			@RequestParam(value="cellPhone",required=false) String  cellPhone,
			@RequestParam(value="depParent",required=false) String  depParent,
			@RequestParam(value="isWork",required=false) String  isWork
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			pd.put("depName", depName.trim());
			pd.put("depPhone", StringUtil.isEmpty(depPhone)?"":depPhone.trim());
			pd.put("depHos", depHos);
			pd.put("principal", StringUtil.isEmpty(principal)?"":principal.trim());
			pd.put("cellPhone", StringUtil.isEmpty(cellPhone)?"":cellPhone.trim());
			pd.put("depParent", StringUtil.isEmpty(depParent)?"0":depParent);
			pd.put("isWork", StringUtil.isEmpty(isWork)?"0":isWork);
			hDepartmentService.save(pd);
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
			@RequestParam(value="depName",required=false) String depName,
			@RequestParam(value="depPhone",required=false) String  depPhone,
			@RequestParam(value="depHos",required=false) String  depHos,
			@RequestParam(value="principal",required=false) String  principal,
			@RequestParam(value="cellPhone",required=false) String  cellPhone,
			@RequestParam(value="depParent",required=false) String  depParent,
			@RequestParam(value="isWork",required=false) String  isWork
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("id", id);
			pd.put("depName", depName.trim());
			pd.put("depPhone", StringUtil.isEmpty(depPhone)?"":depPhone.trim());
			pd.put("depHos", depHos);
			pd.put("principal", StringUtil.isEmpty(principal)?"":principal.trim());
			pd.put("cellPhone", StringUtil.isEmpty(cellPhone)?"":cellPhone.trim());
			pd.put("depParent", StringUtil.isEmpty(depParent)?"0":depParent);
			pd.put("isWork", StringUtil.isEmpty(isWork)?"0":isWork);
			hDepartmentService.edit(pd);				//执行修改数据库
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
			if(hDepartmentService.finByHosAndDep(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
