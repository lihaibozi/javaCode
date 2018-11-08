package com.hd.controller.gh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.AddressManager;
import com.hd.service.gh.OrderMapper;
import com.hd.util.AppUtil;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;


/** 
 * 说明：订单控制层
 * 创建人：kinfeng
 */
@Controller
@RequestMapping(value="/order")
public class OrderController extends BaseController {
	
	String menuUrl = "order/list.do"; //菜单地址(权限用)
	@Resource(name="orderService")
	private OrderMapper orderService;
	@Resource(name="adderssService")
	private AddressManager adderssService; 
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表order");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");//检索条件
		String keywords1 = pd.getString("keywords1");//检索条件
		String keywords3 = pd.getString("keywords3");//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != keywords1 && !"".equals(keywords1)){
			pd.put("keywords1", keywords1.trim());
		}
		if(null != keywords3 && !"".equals(keywords3)){
			pd.put("keywords3", keywords3.trim());
		}
		page.setPd(pd);
		List<PageData> orderList = orderService.listOrders(page);
		mv.setViewName("/gh/order/order_list");
		mv.addObject("pd", pd);	
		mv.addObject("orderList", orderList);
		mv.addObject("QX", Jurisdiction.getHC());//按钮权限
		return mv;
	}
	/**列表(进行中)
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/unlist")
	public ModelAndView unlist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表order");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");//检索条件
		String keywords1 = pd.getString("keywords1");//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null != keywords1 && !"".equals(keywords1)){
			pd.put("keywords1", keywords1.trim());
		}
		pd.put("keywords3",1);
		page.setPd(pd);
		List<PageData> orderList = orderService.listOrders(page);
		mv.setViewName("/gh/order/uorder_list");
		mv.addObject("pd", pd);	
		mv.addObject("orderList", orderList);
		mv.addObject("QX", Jurisdiction.getHC());//按钮权限
		return mv;
	}
	/**
	 * 修改
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public Object edit(){
		String username = Jurisdiction.getUsername();
		logBefore(logger, username+"订单结束");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		//页面ajax传过来的备份文件完整路径
		try {
			orderService.edit(pd);
			pd.put("msg", "ok");
		} catch (Exception e) {
			pd.put("msg", "no");
			e.printStackTrace();
		} 
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 去订单地址页面
	 */
	@RequestMapping(value="/goOrderAddress")
	public ModelAndView goOrderAddress()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		if(adderssService.findById(pd)!=null){
			pd = adderssService.findById(pd);
		}
		mv.addObject("pd", pd);
		mv.setViewName("gh/order/order_address");
		mv.addObject("msg", "editAdd");
		return mv;
	}
	
	/**
	 * 修改订单地址
	 
	@RequestMapping(value="/editAdd")
	public ModelAndView editAdd() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改订单地址");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "editAdd")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData data = adderssService.findById(pd);
		if(data==null || !pd.getString("recipients").equals(data.getString("recipients")) ||
				!pd.getString("phone").equals(data.getString("phone")) ||
				!pd.getString("address").equals(data.getString("address")) ||
				!pd.getString("detailedAddress").equals(data.getString("detailedAddress"))){
			pd.put("createTime", DateUtil.getTime());
			pd.put("userId", pd.get("userId"));
			adderssService.save(pd);
			PageData userAddData = new PageData();
			userAddData.put("userId", pd.get("userId"));
			userAddData = adderssService.findLastAddByUserId(userAddData);
			PageData orderData = new PageData();
			orderData.put("addressId", userAddData.get("id"));
			orderData.put("id", pd.get("orderId"));
			orderService.editOrderAddId(orderData);
		}else{
			adderssService.edit(pd);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	*/
	
}
