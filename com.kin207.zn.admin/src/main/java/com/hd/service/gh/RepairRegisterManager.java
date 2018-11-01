package com.hd.service.gh;

import java.util.List;

import com.hd.entity.Page;
import com.hd.util.PageData;


/** 科室信息接口类
 * @author lihaibo
 * 修改时间：2018.10.26
 */
public interface RepairRegisterManager {
	
	/**科室信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> repairRegisterList (Page page)throws Exception;
	
	
	public List<PageData> repairRegisters(PageData pd) throws Exception ;
	
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

	public void save(PageData pd) throws Exception;

	public void delete(PageData pd) throws Exception;

	public void deleteAll(String[] ids) throws Exception;

	public List<PageData> getMemberAndPhone(PageData pd)  throws Exception;

	public List<PageData> getTypes(PageData pd) throws Exception;

	public List<PageData> getStatus(PageData pd) throws Exception;

	public List<PageData> getMembersByDepId(PageData pd) throws Exception;

	public String getMembersById(String id) throws Exception;
	
}
