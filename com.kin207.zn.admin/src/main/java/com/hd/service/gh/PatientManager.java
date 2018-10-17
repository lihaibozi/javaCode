package com.hd.service.gh;

import java.util.List;

import com.hd.entity.Page;
import com.hd.util.PageData;


/** 病人信息接口类
 * @author lihaibo
 * 修改时间：2018.10.13
 */
public interface PatientManager {
	
	/**病人信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> patientList (Page page)throws Exception;
	
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

	List<PageData> patientList(PageData pd) throws Exception;

	public void delete(PageData pd) throws Exception;

	public void deleteAll(String[] ids) throws Exception;
	
}
