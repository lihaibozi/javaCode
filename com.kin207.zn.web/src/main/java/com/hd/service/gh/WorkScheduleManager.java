package com.hd.service.gh;

import java.util.List;

import com.hd.entity.Page;
import com.hd.util.PageData;


/** 科室信息接口类
 * @author lihaibo
 * 修改时间：2018.10.26
 */
public interface WorkScheduleManager {
	
	/**科室信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> workscheduleList (Page page)throws Exception;
	
	
	public List<PageData> workschedules(PageData pd) throws Exception ;
	
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

	public List<PageData> getDeps(PageData pd) throws Exception;


	public List<PageData> getworkscheduleBydepHos(PageData pd) throws Exception;
	
	public List<PageData> getworkschedules(PageData pd)  throws Exception;

	public void setFinish(PageData pd) throws Exception;
	
}
