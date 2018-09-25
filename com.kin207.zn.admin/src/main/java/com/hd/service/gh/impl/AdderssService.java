package com.hd.service.gh.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.AddressManager;
import com.hd.service.gh.GoodsManager;
import com.hd.util.PageData;


/** 商品信息
 * @author KinFeng
 * 修改时间：2015.11.2
 */
@Service("adderssService")
public class AdderssService implements AddressManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> addressList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("AddressMapper.addresslistPage", page);
	}
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AddressMapper.findById", pd);
	}
}
