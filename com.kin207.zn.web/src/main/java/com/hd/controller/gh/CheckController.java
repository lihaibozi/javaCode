package com.hd.controller.gh;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.CheckManager;
import com.hd.service.gh.DoctorManager;
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.WorkTypeManager;
import com.hd.service.gh.impl.HospitalService;
import com.hd.util.AppUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;


/** 
 * 类名称：科室信息维护
 * 创建人：lihaibo
 * 修改时间：2018年10月26日
 * @version
 */
@Controller
@RequestMapping(value="/check")
public class CheckController extends BaseController {
	
	String menuUrl = "check/check.do"; //科室地址(权限用)
	@Resource(name="checkService")
	private CheckManager checkService;
	@Resource(name="hospitalService")
	private HospitalService hospitalService;
	@Resource(name="hDepartmentService")
	private HdepartmentManager hDepartmentService;
	@Resource(name="doctorService")
	private DoctorManager doctorService;
	@Resource(name="worktypeService")
	private WorkTypeManager worktypeService;
	
	/**显示科室信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/check")
	public ModelAndView checkList(
			Page page,@RequestParam(value="type",required=false) String type ){

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			pd.put("type", type);
			List<PageData>	checkList = checkService.checkList(page);		//分页列出科室信息列表
			mv.setViewName("gh/check/check_list");
			mv.addObject("type",type);
			mv.addObject("checkList", checkList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**批量审核通过
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/passAll")
	@ResponseBody
	public Object passAll() throws Exception {
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			String type = pd.getString("type");
			pd.put("type", type);
			pd.put("reason", "通过");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				pd.put("ids", ArrayDATA_IDS);
				checkService.passAll(pd);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			}
		return AppUtil.returnObject(pd, map);
	}
	
	/**批量删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/disPassAll")
	@ResponseBody
	public Object disPassAll() throws Exception {
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			String type = pd.getString("type");
			String reason = pd.getString("reason");
			pd.put("type", type);
			pd.put("reason", reason);
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				pd.put("ids", ArrayDATA_IDS);
				checkService.disPassAll(pd);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			}
		return AppUtil.returnObject(pd, map);
	}

}
