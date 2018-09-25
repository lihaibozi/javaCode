package com.hd.service.gh.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.ShopManager;
import com.hd.util.PageData;


/** 商品信息
 * @author KinFeng
 * 修改时间：2015.11.2
 */
@Service("shopService")
public class ShopService implements ShopManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> shopList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("ShopMapper.shoplistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ShopMapper.findById", pd);
	}
	
	
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ShopMapper.save", pd);
	}
	 
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ShopMapper.edit", pd);
	}
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ShopMapper.delete", pd);
	}
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] IDS)throws Exception{
		dao.delete("ShopMapper.deleteAll", IDS);
	}
	
	/**类型列表(全部)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listShop(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("ShopMapper.listShop", pd);
	}
	/**
	 * 根据名称查数据
	 */
	public PageData findByName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ShopMapper.findByName", pd);
		
	}
    
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ShopMapper.findByCode", pd);
		
	}

	
}
