package com.hd.service.gh;

import java.util.List;

import com.hd.entity.Page;
import com.hd.util.PageData;


/** 
 * 订单接口类
 * @author vijay
 */
public interface OrderMapper {
	
	/**订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listOrders(Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	

	/**编辑订单信息
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**修改订单地址ID(addressId)
	 * @param pd
	 * @throws Exception
	 */
	public void editOrderAddId(PageData pd) throws Exception;
	
	
}
