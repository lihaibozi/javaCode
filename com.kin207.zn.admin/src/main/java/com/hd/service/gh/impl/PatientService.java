package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.PatientManager;
import com.hd.util.PageData;


/** 病人信息
 * @author lihaibo
 * 修改时间：2018.10.13
 */
@Service("patientService")
public class PatientService implements PatientManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> patientList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("PatientMapper.patientlistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PatientMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PatientMapper.edit", pd);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("PatientMapper.save", pd);
	}

	@Override
	public List<PageData> patientList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PatientMapper.listPatient",pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("PatientMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("PatientMapper.deleteAll", ids);
	}
}
