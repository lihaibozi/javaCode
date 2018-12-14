package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.PatientManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("hDepartmentService")
public class HdepartmentService implements HdepartmentManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> departmentList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("HdepartmentMapper.departmentlistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HdepartmentMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("HdepartmentMapper.edit", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("HdepartmentMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("HdepartmentMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("HdepartmentMapper.deleteAll", ids);
	}

	public List<PageData> departments(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HdepartmentMapper.departments",pd);
	}

	public List<PageData> getDeps(PageData pd)  throws Exception{
		return (List<PageData>) dao.findForList("HdepartmentMapper.getDeps",pd);
	}

	@Override
	public List<PageData> getDepartmentBydepHos(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HdepartmentMapper.getDepartmentBydepHos",pd);
	}

	@Override
	public List<PageData> getDepartments(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HdepartmentMapper.getDepartments",pd);
	}

	@Override
	public Object finByHosAndDep(PageData pd) throws Exception {
		return (PageData)dao.findForObject("HdepartmentMapper.finByHosAndDep", pd);
	}
}
