package com.hd.service.gh.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.JzDoctorManager;
import com.hd.util.PageData;


/** 商品信息
 * @author lihaibo
 * 修改时间：2018.9.22
 */
@Service("jzdoctorService")
public class JzDoctorService implements JzDoctorManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> doctorList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("JzDoctorMapper.doctorlistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("JzDoctorMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("JzDoctorMapper.edit", pd);
	}
}
