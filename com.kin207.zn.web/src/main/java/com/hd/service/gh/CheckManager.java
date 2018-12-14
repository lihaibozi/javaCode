package com.hd.service.gh;

import java.util.List;

import com.hd.entity.Page;
import com.hd.util.PageData;


/** 科室信息接口类
 * @author lihaibo
 * 修改时间：2018.10.26
 */
public interface CheckManager {
	
	/**科室信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> checkList (Page page)throws Exception;

	public void passAll(PageData pd) throws Exception;

	public void disPassAll(PageData pd) throws Exception;

	public PageData findById(PageData pd) throws Exception;
	
}
