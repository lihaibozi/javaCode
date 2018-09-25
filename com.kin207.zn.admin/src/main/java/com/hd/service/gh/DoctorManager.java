package com.hd.service.gh;

import java.util.List;

import com.hd.entity.Page;
import com.hd.util.PageData;


/** 商品信息接口类
 * @author lihaibo
 * 修改时间：2018.9.22
 */
public interface DoctorManager {
	
	/**医生信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> doctorList (Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	
}
