package com.hd.service.gh.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.DoctorManager;
import com.hd.service.gh.ShopManager;
import com.hd.util.PageData;


/** 商品信息
 * @author lihaibo
 * 修改时间：2018.9.22
 */
@Service("doctorService")
public class DoctorService implements DoctorManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> doctorList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("DoctorMapper.doctorlistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DoctorMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DoctorMapper.edit", pd);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("DoctorMapper.save", pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("DoctorMapper.delete", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("DoctorMapper.deleteAll", ids);
	}

	@Override
	public List<PageData> getDoctorByDep(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DoctorMapper.getDoctorByDep", pd);
	}
}
