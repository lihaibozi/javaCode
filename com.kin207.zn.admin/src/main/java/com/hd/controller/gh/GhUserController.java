package com.hd.controller.gh;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hd.controller.base.BaseController;
import com.hd.entity.Page;
import com.hd.service.gh.AddressManager;
import com.hd.service.gh.GhUserManager;
import com.hd.util.Jurisdiction;
import com.hd.util.PageData;

/** 
 * 类名称：用户管理
 * 创建人：kinfeng
 * 修改时间：2018年4月17日
 * @version
 */
@Controller
@RequestMapping(value="/ghUser")
public class GhUserController extends BaseController {
	
	String menuUrl = "ghUser/userList.do"; //菜单地址(权限用)
	@Resource(name="ghUserService")
	private GhUserManager ghUserService;
	@Resource(name="adderssService")
	private AddressManager adderssService;	
	/**显示用户列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/userList")
	public ModelAndView listUsers(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String keywords = pd.getString("keywords");							//检索条件 关键词
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			page.setPd(pd);
			List<PageData>	userList = ghUserService.userList(page);		//列出会员列表
			
			mv.setViewName("gh/ghuser/user_list");
			mv.addObject("userList", userList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**用户地址列表页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goUserAddress")
	public ModelAndView userAddList(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
        String keywords = pd.getString("keywords");//检索条件 关键词
        if(null != keywords && !"".equals(keywords)){
            pd.put("keywords", keywords.trim());
        }
        page.setPd(pd);
        List<PageData> addList = adderssService.addressList(page);
        mv.addObject("addList", addList);
		mv.addObject("pd", pd);//放入视图容器
		mv.setViewName("gh/ghuser/address_list");
		return mv;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
}
