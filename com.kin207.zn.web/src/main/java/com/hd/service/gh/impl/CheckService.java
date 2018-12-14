package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.CheckManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("checkService")
public class CheckService implements CheckManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> checkList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("CheckMapper.checklistPage", page);
	}

	@Override
	public void passAll(PageData pd) throws Exception {
		 dao.update("CheckMapper.passAll",pd);
	}

	@Override
	public void disPassAll(PageData pd) throws Exception {
		dao.update("CheckMapper.disPassAll",pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CheckMapper.findById", pd);
	}
}
