package com.hd.controller.gh;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.hd.controller.base.BaseController;
import com.hd.controller.system.user.UserController;
import com.hd.entity.Page;
import com.hd.service.gh.HStatusManager;
import com.hd.service.gh.HTypeManager;
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.HmemberManager;
import com.hd.service.gh.OrderRegisterManager;
import com.hd.service.gh.RepairRegisterManager;
import com.hd.util.AppUtil;
import com.hd.util.DateUtil;
import com.hd.util.FileUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.ObjectExcelView;
import com.hd.util.PageData;
import com.hd.util.StringUtil;

import net.sf.json.JSONArray;

/** 
 * 类名称：维修登记
 * 创建人：lihaibo
 * 修改时间：2018年10月27日
 * @version
 */
@Controller
@RequestMapping(value="/repairRegister")
public class RepairRegisterController extends BaseController {
	
	String menuUrl = "repairRegister/repairRegister.do"; //维修登记地址(权限用)
	@Resource(name="repairRegisterService")
	private RepairRegisterManager repairRegisterService;
	@Resource(name="hDepartmentService")
	private HdepartmentManager hDepartmentService;
	@Resource(name="hMemberService")
	private HmemberManager hMemberService;
	@Resource(name="orderRegisterService")
	private OrderRegisterManager orderRegisterService;
	@Resource(name="userController")
	private UserController userController;
	@Resource(name="hTypeService")
	private HTypeManager hTypeService;
	@Resource(name="hStatusService")
	private HStatusManager hStatusService;
	
	/**显示科室信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/repairRegister")
	public ModelAndView repairRegisterList(Page page){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");
			String startTime = pd.getString("startTime");
			String endTime = pd.getString("endTime");
			String orderPeople = pd.getString("orderPeople");
			String status = pd.getString("status");
			//检索条件 关键词
			if(StringUtils.isNotEmpty(keyword)){
				pd.put("keyword", keyword.trim());
			}
			if(StringUtils.isNotEmpty(startTime)){
				pd.put("startTime", startTime+" 00:00:01");
			}
			if(StringUtils.isNotEmpty(endTime)){
				pd.put("endTime", endTime+" 23:59:59");
			}
			if(StringUtils.isNotEmpty(orderPeople)){
				pd.put("orderPeople", orderPeople.trim());
			}
			if(StringUtils.isNotEmpty(status)){
				pd.put("status", status);
			}
			page.setPd(pd);
			List<PageData>	repairRegisterList = repairRegisterService.repairRegisterList(page);		//分页列出维修登记信息列表
			String fileName = "";
			for(PageData pageData:repairRegisterList){
				String fileUrl = (String) pageData.get("image");
				if(fileUrl!=null&&!StringUtils.isEmpty(fileUrl)){
					fileName = fileUrl.substring(fileUrl.lastIndexOf("/")).replace("/", "");
					pageData.put("fileName", fileName);
				}
				Object assistants = pageData.get("assistants");
				String assMembers = "";
				if(assistants!=null){
					String str = (String) assistants;
					String[] ids = str.split(",");
					for(String id:ids){
						String name = repairRegisterService.getMembersById(id);
						if(assMembers==""){
							assMembers += name;
						}else{
							assMembers += ","+name;
						}
					}
				}
				pageData.put("assistants", assMembers);
			}
			List<PageData> listOrders = hMemberService.getOrders(pd);
			List<PageData> listStatus = hStatusService.statuss(pd);
			mv.setViewName("gh/repairRegister/repairRegister_list");
			mv.addObject("repairRegisterList", repairRegisterList);
			mv.addObject("pd", pd);
			mv.addObject("listOrders",listOrders);
			mv.addObject("listStatus",listStatus);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**删除文件
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/delFile")
	public void delFile(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			return;
		} 
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			pd = this.getPageData();
			if(userController.delFile(pd.getString("fileName"))){
				repairRegisterService.deleteFilePath(pd);
			}
		}
		out.write("success");
		out.close();
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
			repairRegisterService.delete(pd);
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
			if(null != DATA_IDS && !StringUtils.isEmpty(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				orderRegisterService.deleteAll(ArrayDATA_IDS);
				repairRegisterService.deleteAll(ArrayDATA_IDS);
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
		//select下拉框数据
		//科室信息
		List<PageData> listDepartments = hDepartmentService.departments(pd);
		//接单人信息
		List<PageData> listOrders = hMemberService.getOrders(pd);
		//维修类别
		List<PageData> listTypes = repairRegisterService.getTypes(pd);
		//维修状态
		List<PageData> listStatus = repairRegisterService.getStatus(pd);
		//所有人员
		List<PageData> members = hMemberService.getAll(pd);
		mv.addObject("listTypes", listTypes);
		mv.addObject("members", members);
		mv.addObject("listStatus", listStatus);
		mv.addObject("listOrders", listOrders);
		mv.addObject("listDeparts", listDepartments);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.setViewName("gh/repairRegister/repairRegister_edit");
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
		PageData pdd = repairRegisterService.findById(pd);	//根据ID读取
		//select下拉框数据
		//科室信息
		List<PageData> listDepartments = hDepartmentService.departments(pd);
		//接单人信息
		List<PageData> listOrders = hMemberService.getOrders(pd);
		//维修类别
		List<PageData> listTypes = repairRegisterService.getTypes(pd);
		//维修状态
		List<PageData> listStatus = repairRegisterService.getStatus(pd);
		//根据科室查询报修人员
		List<PageData> members = repairRegisterService.getMembersByDepId(pd);
		//得到所有协助人员
		String assistantsString = (String) pdd.get("assistants");
		String fileUrl = (String) pdd.get("image");
		if(fileUrl!=null&&!StringUtils.isEmpty(fileUrl)){
			String fileName = "";
			fileName = fileUrl.substring(fileUrl.lastIndexOf("/")).replace("/", "");
			pdd.put("fileName", fileName);
		}
		String[] assistants ={};
		if(assistantsString!=null){
			 assistants = assistantsString.split(",");
			 List<Map<String,String>> listAssistants = new ArrayList<Map<String,String>>();
				for(String str:assistants){
					Map<String,String> map = new HashMap<String,String>();
					map.put("id", str);
					listAssistants.add(map);
				}
				mv.addObject("assistants", listAssistants);
		}
		mv.addObject("members", members);
		mv.addObject("listTypes", listTypes);
		mv.addObject("listStatus", listStatus);
		mv.addObject("listOrders", listOrders);
		mv.addObject("listDeparts", listDepartments);
		mv.setViewName("gh/repairRegister/repairRegister_edit");
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
			@RequestParam(value="status",required=false) String  status,
			@RequestParam(value="image",required=false) MultipartFile file 
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增图片");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddssSSS");
		String serialNumber = checkSerial(sdf);
		String userName = Jurisdiction.getUsername();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			try {
				//上传图片
				String imgurl = FileUtil.upload(file,this.getRequest()/*, param.getPara("file")*/);
				if(imgurl == null || "1".equals(imgurl)){
					logger.error("goods: image upload fail");
				}else if( "2".equals(imgurl)){
					logger.error("goods: save()image type is error");
				}else{
					pd.put("image", imgurl);
				}
			} catch (Exception e) {
				logger.error("BannerController: image upload fail");
				e.printStackTrace();
			}
			pd.put("registerPeople", userName);
			pd.put("registerDate", new Date());
			pd.put("modifyPeople", userName);
			pd.put("modifyDate", new Date());
			pd.put("serialNumber", serialNumber);
			pd.put("department", department);
			pd.put("repairStaff",StringUtils.isEmpty(repairStaff)?"":repairStaff);
			pd.put("cellPhone",StringUtils.isEmpty(cellPhone)?"":cellPhone.trim());
			pd.put("assetNumber",StringUtils.isEmpty(assetNumber)?"":assetNumber.trim());
			pd.put("assetSn",StringUtils.isEmpty(assetSn)?"":assetSn.trim());
			pd.put("content",StringUtils.isEmpty(content)?"":content.trim());
			pd.put("orderPeople",orderPeople);
			pd.put("assistants",assistants);
			pd.put("type",type);
			pd.put("status",status);
			repairRegisterService.save(pd);
			//保存成功后插入数据到
			pd = new PageData();
			pd.put("serialNumber", serialNumber);
			pd.put("repairContent", StringUtils.isEmpty(content)?"":content.trim());
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
	
	public String checkSerial(SimpleDateFormat sdf) throws Exception{
		String serialNumber = sdf.format(new Date())+new Random().nextInt(10);
		PageData pd = repairRegisterService.getDataBySerial(serialNumber);
		if(pd!=null){
			checkSerial(sdf);
		}
		return serialNumber;
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
			@RequestParam(value="department",required=false) String department,
			@RequestParam(value="repairStaff",required=false) String repairStaff,
			@RequestParam(value="cellPhone",required=false) String  cellPhone ,
			@RequestParam(value="assetNumber",required=false) String assetNumber,
			@RequestParam(value="assetSn",required=false) String  assetSn ,
			@RequestParam(value="content",required=false) String content,
			@RequestParam(value="orderPeople",required=false) String  orderPeople ,
			@RequestParam(value="assistants",required=false) String  assistants ,
			@RequestParam(value="type",required=false) String type,
			@RequestParam(value="status",required=false) String  status,
			@RequestParam(value="image",required=false) MultipartFile file 
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String userName = Jurisdiction.getUsername();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			try {
				//上传图片
				String imgurl = FileUtil.upload(file,this.getRequest()/*, param.getPara("file")*/);
				if(imgurl == null || "1".equals(imgurl)){
					logger.error("BannerController: image upload fail");
				}else if( "2".equals(imgurl)){
					logger.error("BannerController: save()image type is error");
				}else{
					pd.put("image", imgurl);
				}
			} catch (Exception e) {
				logger.error("BannerController: image upload fail");
				e.printStackTrace();
			}
			pd.put("id", id);
			pd.put("modifyPeople", userName);
			pd.put("modifyDate", new Date());
			pd.put("department", department);
			pd.put("repairStaff",StringUtils.isEmpty(repairStaff)?"":repairStaff);
			pd.put("cellPhone",StringUtils.isEmpty(cellPhone)?"":cellPhone.trim());
			pd.put("assetNumber",StringUtils.isEmpty(assetNumber)?"":assetNumber.trim());
			pd.put("assetSn",StringUtils.isEmpty(assetSn)?"":assetSn.trim());
			pd.put("content",StringUtils.isEmpty(content)?"":content.trim());
			pd.put("orderPeople",orderPeople);
			pd.put("assistants",assistants);
			pd.put("type",type);
			pd.put("status",status);					   		
			repairRegisterService.edit(pd);				//执行修改数据库
			PageData pageData = repairRegisterService.findById(pd);
			String serialNumber = pageData.getString("serialNumber");
			pd = new PageData();
			pd.put("serialNumber", serialNumber);
			pd.put("status", status);
			orderRegisterService.editStatus(pd);
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
		List<PageData> list = repairRegisterService.getMemberAndPhone(pd);				//执行查询
	    return list.size()>0?JSONArray.fromObject(list).toString():"";
	}
	

	/**
	 * 返回科室下成员信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getDepartment")
	@ResponseBody
	public String getDepartment(HttpServletRequest request,
			@RequestParam(value="id",required=false)  String  id ) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		pd.put("id", id);
		List<PageData> list = repairRegisterService.getDepartment(pd);				//执行查询
	    return list.size()>0?JSONArray.fromObject(list).toString():"";
	}
	
	/**导出用户信息到EXCEL
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
				String keyword = pd.getString("keyword");
				String startTime = pd.getString("startTime");
				String endTime = pd.getString("endTime");
				String orderPeople = pd.getString("orderPeople");
				String status = pd.getString("status");
				//检索条件 关键词
				if(StringUtils.isNotEmpty(keyword)){
					pd.put("keyword", keyword.trim());
				}
				if(StringUtils.isNotEmpty(startTime)){
					pd.put("startTime", startTime);
				}
				if(StringUtils.isNotEmpty(endTime)){
					pd.put("endTime", endTime);
				}
				if(StringUtils.isNotEmpty(orderPeople)){
					pd.put("orderPeople", orderPeople.trim());
				}
				if(StringUtils.isNotEmpty(orderPeople)){
					pd.put("status", status);
				}
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				List<PageData> listTitles = hTypeService.types(pd);
				List<PageData> repairList = repairRegisterService.repairRegisterLists(pd);
				List<PageData> varList = new ArrayList<PageData>();
				List<String> days = new ArrayList<String>();
				titles.add("时间");
				for(PageData pg:listTitles){
					titles.add(pg.getString("type"));
				}
				dataMap.put("titles", titles);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String sqlStartTime = "";
				String sqlEndTime ="";
				if(repairList.size()>0){
					sqlStartTime = sdf.format(repairList.get(repairList.size()-1).get("registerDate"));
					sqlEndTime = sdf.format(repairList.get(0).get("registerDate"));
				}
				if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
					days = DateUtil.getDays(startTime, endTime);
				}else if(StringUtils.isNotEmpty(startTime)&&StringUtils.isEmpty(endTime)){
					days = DateUtil.getDays(startTime, sqlEndTime);
				}else if(StringUtils.isEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
					days = DateUtil.getDays(sqlStartTime , endTime);
				}else{
					days = DateUtil.getDays(sqlStartTime,sqlEndTime);
				}
				Multimap<String,PageData> multiMap = ArrayListMultimap.create();
				//首先按时间统计
				for(PageData pageData :repairList){
					multiMap.put(sdf.format(pageData.get("registerDate")), pageData);
					multiMap.put(pageData.getString("type"), pageData);
				}
				
				for(String str :days){
					PageData vpd = new PageData();
					Collection<PageData> collection  = multiMap.get(str);
					Multimap<String,Object> typeMul = ArrayListMultimap.create();
					//某个时间下统计某类型的集合数据
					for(PageData pg:collection){
						typeMul.put(pg.getString("type"), pg.get("id"));
					}
					for(int i=0;i<titles.size();i++){
						int ii = i+1;
						if(titles.get(i).equals("时间")){
							vpd.put("var"+ii, str);	
						}else{
							vpd.put("var"+ii, String.valueOf(typeMul.get(titles.get(i)).size()));
						}
					}
					varList.add(vpd);
				}
				
				PageData vpd = new PageData();
				for(int i=0;i<titles.size();i++){
					int jj = i+1;
					if(titles.get(i).equals("时间")){
						vpd.put("var"+jj, "总计");
					}else{
						vpd.put("var"+jj, String.valueOf(multiMap.get(titles.get(i)).size()));
					}
				}
				varList.add(vpd);
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
}
