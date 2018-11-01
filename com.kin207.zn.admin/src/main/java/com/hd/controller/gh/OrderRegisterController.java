package com.hd.controller.gh;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.HmemberManager;
import com.hd.service.gh.OrderRegisterManager;
import com.hd.util.AppUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;

import jodd.util.ObjectUtil;
import net.sf.json.JSONArray;

/** 
 * 类名称：接单人登记
 * 创建人：lihaibo
 * 修改时间：2018年10月27日
 * @version
 */
@Controller
@RequestMapping(value="/orderRegister")
public class OrderRegisterController extends BaseController {
	
	String menuUrl = "orderRegister/orderRegister.do"; //登记地址(权限用)
	@Resource(name="hDepartmentService")
	private HdepartmentManager hDepartmentService;
	@Resource(name="hMemberService")
	private HmemberManager hMemberService;
	@Resource(name="orderRegisterService")
	private OrderRegisterManager orderRegisterService;
	
	/**显示科室信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/orderRegister")
	public ModelAndView orderRegisterList(Page page){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	orderRegisterList = orderRegisterService.orderRegisterList(page);		//分页列出接单人登记信息列表
			mv.setViewName("gh/orderRegister/orderRegister_list");
			mv.addObject("orderRegisterList", orderRegisterList);
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
			orderRegisterService.delete(pd);
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
				orderRegisterService.deleteAll(ArrayDATA_IDS);
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
		//维修状态
		List<PageData> listStatus = orderRegisterService.getStatus(pd);
		mv.addObject("listStatus", listStatus);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.setViewName("gh/orderRegister/orderRegister_edit");
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageData pdd = orderRegisterService.findById(pd);	//根据ID读取
		Object finishDate = pdd.get("finishDate");
		Object arriveDate = pdd.get("arriveDate");
		if(finishDate!=null){
			String finishTime = sdf.format(finishDate);
			pdd.put("finishDate", finishTime);
		}
		if(arriveDate!=null){
			String arriveTime = sdf.format(arriveDate);
			pdd.put("arriveDate", arriveTime);
		}
		//select下拉框数据
		//维修状态
		List<PageData> listStatus = orderRegisterService.getStatus(pd);
		mv.addObject("listStatus", listStatus);
		mv.setViewName("gh/orderRegister/orderRegister_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pdd);
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
			@RequestParam(value="department",required=false) String department,
			@RequestParam(value="repairStaff",required=false) String repairStaff,
			@RequestParam(value="cellPhone",required=false) String  cellPhone ,
			@RequestParam(value="assetNumber",required=false) String assetNumber,
			@RequestParam(value="assetSn",required=false) String  assetSn ,
			@RequestParam(value="content",required=false) String content,
			@RequestParam(value="orderPeople",required=false) String  orderPeople ,
			@RequestParam(value="assistants",required=false) String  assistants ,
			@RequestParam(value="type",required=false) String type,
			@RequestParam(value="status",required=false) String  status 
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddSSS");
		String serialNumber = sdf.format(new Date())+new Random().nextInt(10);
		String userName = Jurisdiction.getUsername();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			pd.put("registerPeople", userName.trim());
			pd.put("registerDate", new Date());
			pd.put("modifyPeople", userName.trim());
			pd.put("modifyDate", new Date());
			pd.put("serialNumber", serialNumber.trim());
			pd.put("department", department.trim());
			pd.put("repairStaff",repairStaff.trim());
			pd.put("cellPhone",cellPhone.trim());
			pd.put("assetNumber",assetNumber.trim());
			pd.put("assetSn",assetSn.trim());
			pd.put("content",content.trim());
			pd.put("orderPeople",orderPeople.trim());
			pd.put("assistants",assistants.trim());
			pd.put("type",type.trim());
			pd.put("status",status.trim());
			orderRegisterService.save(pd);
			//保存成功后插入数据到t_order_register表
			pd = new PageData();
			pd.put("serialNumber", serialNumber);
			pd.put("status", status);
			pd.put("isToRepair", 0);
			pd.put("isReplace", 0);
			pd.put("isStartBack", 0);
			pd.put("modifyPeople", userName);
			pd.put("modifyDate", new Date());
			orderRegisterService.save(pd);
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
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="repairContent",required=false) String repairContent,
			@RequestParam(value="repairProcess",required=false) String  repairProcess ,
			@RequestParam(value="isToRepair",required=false) String isToRepair,
			@RequestParam(value="isReplace",required=false) String  isReplace ,
			@RequestParam(value="isStartBack",required=false) String isStartBack,
			@RequestParam(value="arriveDate",required=false) String  arriveDate ,
			@RequestParam(value="finishDate",required=false) String  finishDate 
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String userName = Jurisdiction.getUsername();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("id", id.trim());
			pd.put("status", status.trim());
			pd.put("repairContent", repairContent.trim());
			pd.put("repairProcess", repairProcess.trim());
			pd.put("isToRepair",isToRepair.trim());
			pd.put("isReplace",isReplace.trim());
			pd.put("isStartBack",isStartBack.trim());
			pd.put("arriveDate",arriveDate);
			pd.put("finishDate",finishDate);	
			pd.put("modifyPeople", userName.trim());
			pd.put("modifyDate", new Date());
			orderRegisterService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	

	/**
	 * 返回科室下成员信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getMemberAndPhone")
	@ResponseBody
	public String getMemberAndPhone(HttpServletRequest request,
			@RequestParam(value="id",required=false)  String  id ) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		pd.put("id", id);
		List<PageData> list = orderRegisterService.getMemberAndPhone(pd);				//执行查询
	    return list.size()>0?JSONArray.fromObject(list).toString():"";
	}
	
}