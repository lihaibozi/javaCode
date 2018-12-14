package com.hd.controller.gh;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.GoodsManager;
import com.hd.service.gh.ShopManager;
import com.hd.service.gh.TypeManager;
import com.hd.util.AppUtil;
import com.hd.util.FileUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;
import com.hd.util.Tools;

/** 
 * 类名称：菜品管理
 * 创建人:李海波
 * 修改时间：2018年4月17日
 * @version
 */
@Controller
@RequestMapping(value="/goods")
public class GoodsController extends BaseController {
	
	String menuUrl = "goods/goodsList.do"; //菜单地址(权限用)
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	
	@Resource(name="typeService")
	private TypeManager typeService;
	@Resource(name="shopService")
	private ShopManager shopService;
	
	/**显示菜品列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/goodsList")
	public ModelAndView listUsers(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keyword = pd.getString("keyword");							//检索条件 关键词
			if(null != keyword && !"".equals(keyword)){
				pd.put("keyword", keyword.trim());
			}
			page.setPd(pd);
			List<PageData>	goodsList = goodsService.goodsList(page);		//列出菜单列表
			
			mv.setViewName("gh/goods/goods_list");
			mv.addObject("goodsList", goodsList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
			@RequestParam(value="image",required=false) MultipartFile file,
			@RequestParam(value="name",required=false)  String  name ,
			@RequestParam(value="typeCode",required=false)  String typeCode,
			@RequestParam(value="remark",required=false)String remark,
			@RequestParam(value="shopId",required=false)String shopId,
			@RequestParam(value="pric",required=false) String pric
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增图片");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
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
			pd.put("name", name);								
			pd.put("typeCode", typeCode);						
			pd.put("remark", remark);
			pd.put("shopId", shopId);
			pd.put("createTime", Tools.date2Str(new Date()));	
			pd.put("pric", pric);						//活动开始时间			
			goodsService.save(pd);
		}
		map.put("result", "ok");
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
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除图片");
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			pd = this.getPageData();
			File f = new File(pd.getString("image"));
		    f.delete();
		    goodsService.delete(pd);
		}
		out.write("success");
		out.close();
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
			@RequestParam(value="image",required=false) MultipartFile file,
			@RequestParam(value="name",required=false)  String  name ,
			@RequestParam(value="remark",required=false)String remark,
			@RequestParam(value="shopId",required=false)String shopId,
			@RequestParam(value="pric",required=false) String pric
			) throws Exception{

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改图片");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
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
			pd.put("name", name);								
			pd.put("remark", remark);						   
			pd.put("pric", pric);
			pd.put("shopId", shopId);
			goodsService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
		List<PageData> typeList = typeService.listType(pd);
		List<PageData> shopList = shopService.listShop(pd);
		mv.setViewName("gh/goods/goods_edit");
		mv.addObject("shopList",shopList);
		mv.addObject("typeList",typeList);
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
		pd = goodsService.findById(pd);	//根据ID读取
		List<PageData> typeList = typeService.listType(pd);
		List<PageData> shopList = shopService.listShop(pd);
		mv.setViewName("gh/goods/goods_edit");
		mv.addObject("shopList",shopList);
		mv.addObject("typeList",typeList);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**判断名称是不是存在
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
			if(goodsService.findByName(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
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
			String DATA_IDS = pd.getString("IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				pathList = goodsService.getAllById(ArrayDATA_IDS);
				for(int i=0;i<pathList.size();i++){
					File f = new File(pathList.get(i).getString("image"));
				    f.delete();
				}
				goodsService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			}
		return AppUtil.returnObject(pd, map);
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
}
