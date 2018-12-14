package com.hd.service.gh.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.AddressManager;
import com.hd.service.gh.GhUserManager;
import com.hd.service.gh.GoodsManager;
import com.hd.util.PageData;


/** 商品信息
 * @author lihaibo
 * 修改时间：2015.11.2
 */
@Service("ghUserService")
public class GhUserService implements GhUserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> userList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("GhUserMapper.userlistPage", page);
	}
	
}
