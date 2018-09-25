package com.hd.service.gh.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.OrderMapper;
import com.hd.util.PageData;


/** 订单Service
 * @author vijay
 */
@Service("orderService")
public class OrderService implements OrderMapper {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listOrders(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.orderlistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OrderMapper.findById", pd);
	}

	/**编辑订单信息
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("OrderMapper.editById", pd);
	}
	
	/**修改订单地址ID(addressId)
	 * @param pd
	 * @throws Exception
	 */
	public void editOrderAddId(PageData pd) throws Exception {
		dao.update("OrderMapper.editOrderAddId", pd);
	}
	

}
