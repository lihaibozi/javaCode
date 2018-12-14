package com.hd.service.gh;

import java.util.List;

import com.hd.entity.Page;
import com.hd.util.PageData;


/** 商品信息接口类
 * @author lihaibo
 * 修改时间：2015.11.2
 */
public interface ShopManager {
	
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> shopList (Page page)throws Exception;
	
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**修改类型
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**保存类型
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 获取所有类型
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listShop(PageData pd)throws Exception;
	
	/**通过名称获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception;
	
	/**通过code获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception;
	
	/**删除类型
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**批量删除类型
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] USER_IDS)throws Exception;
	
	
}
