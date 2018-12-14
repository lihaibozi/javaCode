package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.HospitalManager;
import com.hd.service.gh.PatientManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("hospitalService")
public class HospitalService implements HospitalManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hospitalList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("HospitalMapper.hospitallistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HospitalMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("HospitalMapper.edit", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("HospitalMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("HospitalMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("HospitalMapper.deleteAll", ids);
	}

	public List<PageData> hospitals(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HospitalMapper.hospitals",pd);
	}

	/**
	 * 根据名称查数据
	 */
	public PageData findByName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("HospitalMapper.findByName", pd);
		
	}

	public List<PageData> getHos(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("HospitalMapper.hospitals",pd);
	}

	public List<PageData> getHosByDep(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("HospitalMapper.getHosByDep",pd);
	}
}
