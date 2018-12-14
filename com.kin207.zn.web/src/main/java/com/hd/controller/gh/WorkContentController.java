package com.hd.controller.gh;

import javax.annotation.Resource;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.hd.controller.base.BaseController;
import com.hd.controller.system.user.UserController;
import com.hd.entity.Page;
import com.hd.service.gh.DoctorManager;
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.WorkContentManager;
import com.hd.service.gh.WorkScheduleManager;
import com.hd.service.gh.WorkTypeManager;
import com.hd.service.gh.impl.HospitalService;
import com.hd.util.AppUtil;
import com.hd.util.Const;
import com.hd.util.FileUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;
import com.hd.util.Tools;

import jodd.util.StringUtil;
import net.sf.json.JSONArray;

/** 
 * 类名称：科室信息维护
 * 创建人：lihaibo
 * 修改时间：2018年10月26日
 * @version
 */
@Controller
@RequestMapping(value="/workcontent")
public class WorkContentController extends BaseController {
	
	String menuUrl = "workcontent/workcontent.do"; //科室地址(权限用)
	@Resource(name="workContentService")
	private WorkContentManager workContentService;
	@Resource(name="workScheduleService")
	private WorkScheduleManager workScheduleService;
	@Resource(name="hospitalService")
	private HospitalService hospitalService;
	@Resource(name="hDepartmentService")
	private HdepartmentManager hDepartmentService;
	@Resource(name="doctorService")
	private DoctorManager doctorService;
	@Resource(name="worktypeService")
	private WorkTypeManager worktypeService;
	@Resource(name="userController")
	private UserController userController;
	
	/**显示科室信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/workcontent")
	public ModelAndView workcontentList(Page page){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	workcontentList = workContentService.workcontentList(page);		//分页列出科室信息列表
			mv.setViewName("gh/workcontent/workcontent_list");
			mv.addObject("workcontentList", workcontentList);
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
	@RequestMapping(value="/getDoctorAndWorkType")
	@ResponseBody
	public String getDoctorAndWorkType(HttpServletRequest request,
			@RequestParam(value="id",required=false)  String  id ) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> listDoctor = doctorService.getDoctorByDep(pd);
		List<PageData> listWorkType = worktypeService.getWorkTypeByDep(pd);
		map.put("listDoctor", listDoctor);		//执行查询
		map.put("listWorkType", listWorkType);
	    return JSONArray.fromObject(map).toString();
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
			workContentService.delete(pd);
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
				workContentService.deleteAll(ArrayDATA_IDS);
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
		String type = "";	
		mv.setViewName("gh/workcontent/workcontent_add");
		mv.addObject("msg", "save");
		mv.addObject("type",type);
		mv.addObject("schId", pd);
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
		pd = workContentService.findById(pd);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object medicalDate = pd.get("medicalDate");
		Object conferenceTime = pd.get("conferenceTime");
		if(medicalDate!=null){
			pd.put("medicalDate", sdf.format(medicalDate));
		}
		if(conferenceTime!=null){
			pd.put("conferenceTime", sdf.format(conferenceTime));
		}
		String fileUrl = (String) pd.get("courseware");
		if(fileUrl!=null&&!StringUtils.isEmpty(fileUrl)){
			String fileName = "";
			fileName = fileUrl.substring(fileUrl.lastIndexOf("/")).replace("/", "");
			pd.put("fileName", fileName);
		}
		mv.setViewName("gh/workcontent/workcontent_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**去查看页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> workContents = workContentService.findByScheduleId(pd);
		if(workContents!=null){
			for(PageData pageData:workContents){
				String fileUrl = (String) pageData.get("courseware");
				if(fileUrl!=null&&!StringUtils.isEmpty(fileUrl)){
					String fileName = "";
					fileName = fileUrl.substring(fileUrl.lastIndexOf("/")).replace("/", "");
					pageData.put("fileName", fileName);
				}
			}
		}
		
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("gh/workcontent/workcontent_view");
		mv.addObject("pd", pd);
		mv.addObject("workContents", workContents);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="wSchId",required=false) String wSchId,
			@RequestParam(value="patientName",required=false) String patientName,
			@RequestParam(value="patientSex",required=false) String patientSex,
			@RequestParam(value="patientAge",required=false) String patientAge,
			@RequestParam(value="patientCardNo",required=false) String patientCardNo,
			@RequestParam(value="patientCellPhone",required=false) String patientCellPhone,
			@RequestParam(value="medicalResult",required=false) String medicalResult	,
			@RequestParam(value="medicalDate",required=false) String medicalDate,
			@RequestParam(value="operationLevel",required=false) String operationLevel,
			@RequestParam(value="apprise",required=false) String apprise,
			@RequestParam(value="content",required=false) String content,
			@RequestParam(value="joinNumber",required=false) String joinNumber,
			@RequestParam(value="conferenceTime",required=false) String conferenceTime,
			@RequestParam(value="courseware1",required=false) MultipartFile file1 ,
			@RequestParam(value="courseware2",required=false) MultipartFile file2,
			@RequestParam(value="contentType",required=false) Integer contentType
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String userName = Jurisdiction.getUsername();
		pd.put("type", contentType);
		if(contentType==3){
			pd.put("wSchId", wSchId);
			pd.put("patientName", patientName.trim());
			pd.put("patientSex", patientSex);
			pd.put("patientAge", patientAge.trim());
			pd.put("patientCardNo", StringUtils.isEmpty(patientCardNo)?"":patientCardNo);
			pd.put("patientCellPhone", StringUtils.isEmpty(patientCellPhone)?"":patientCellPhone);
			pd.put("operationLevel", StringUtils.isEmpty(operationLevel)?"":operationLevel);
			pd.put("medicalDate", medicalDate);
			pd.put("medicalResult", medicalResult.trim());
			String fileUrl = FileUtil.upload(file1,this.getRequest()); //上传文件
			if(!"1".equals(fileUrl)){
				pd.put("courseware", fileUrl);
			}
		}else if(contentType==4){
			pd.put("wSchId", wSchId);
			pd.put("conferenceTime", conferenceTime);
			pd.put("joinNumber", StringUtils.isEmpty(joinNumber)?"":joinNumber);
			pd.put("content", content.trim());
			pd.put("apprise", StringUtils.isEmpty(apprise)?"":apprise);
			String fileUrl = FileUtil.upload(file2,this.getRequest()); //上传文件
			if(!"1".equals(fileUrl)){
				pd.put("courseware", fileUrl);
			}
		}
		if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			pd.put("fillPeople", userName);
			pd.put("fillDate", new Date());
			workContentService.save(pd);
			//保存完之后更改计划中完成情况
			workScheduleService.setFinish(pd);
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
			@RequestParam(value="patientName",required=false) String patientName,
			@RequestParam(value="patientSex",required=false) String patientSex,
			@RequestParam(value="patientAge",required=false) String patientAge,
			@RequestParam(value="patientCardNo",required=false) String patientCardNo,
			@RequestParam(value="patientCellPhone",required=false) String patientCellPhone,
			@RequestParam(value="medicalResult",required=false) String medicalResult	,
			@RequestParam(value="medicalDate",required=false) String medicalDate,
			@RequestParam(value="operationLevel",required=false) String operationLevel,
			@RequestParam(value="apprise",required=false) String apprise,
			@RequestParam(value="content",required=false) String content,
			@RequestParam(value="joinNumber",required=false) String joinNumber,
			@RequestParam(value="conferenceTime",required=false) String conferenceTime,
			@RequestParam(value="courseware1",required=false) MultipartFile file1,
			@RequestParam(value="courseware2",required=false) MultipartFile file2,
			@RequestParam(value="type",required=false) String type
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("id", id);
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				pd.put("patientName", StringUtils.isEmpty(patientName)?"":patientName.trim());
				pd.put("patientSex", StringUtils.isEmpty(patientSex)?"":patientSex);
				pd.put("patientAge", StringUtils.isEmpty(patientAge)?"":patientAge.trim());
				pd.put("patientCardNo", StringUtils.isEmpty(patientCardNo)?"":patientCardNo);
				pd.put("patientCellPhone", StringUtils.isEmpty(patientCellPhone)?"":patientCellPhone);
				pd.put("operationLevel", StringUtils.isEmpty(operationLevel)?"":operationLevel);
				pd.put("medicalDate", StringUtils.isEmpty(medicalDate)?"":medicalDate);
				pd.put("medicalResult", StringUtils.isEmpty(medicalResult)?"":medicalResult.trim());
				pd.put("conferenceTime", StringUtils.isEmpty(conferenceTime)?"":conferenceTime);
				pd.put("joinNumber", StringUtils.isEmpty(joinNumber)?"":joinNumber);
				pd.put("content", StringUtils.isEmpty(content)?"":content.trim());
				pd.put("apprise", StringUtils.isEmpty(apprise)?"":apprise);
				String fileUrl = "";
				if("4".equals(type)){
					fileUrl = FileUtil.upload(file2,this.getRequest()); //上传文件
				}else if("3".equals(type)){
					fileUrl = FileUtil.upload(file1,this.getRequest()); //上传文件
				}
				if(!"1".equals(fileUrl)){
					pd.put("courseware", fileUrl);
				}
			workContentService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
				workContentService.deleteFilePath(pd);
			}
		}
		out.write("success");
		out.close();
	}
	
}
