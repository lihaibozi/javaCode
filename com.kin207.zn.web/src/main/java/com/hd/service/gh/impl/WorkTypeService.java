package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.PatientManager;
import com.hd.service.gh.WorkTypeManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("worktypeService")
public class WorkTypeService implements WorkTypeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> worktypeList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("WorkTypeMapper.worktypelistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WorkTypeMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WorkTypeMapper.edit", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("WorkTypeMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("WorkTypeMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("WorkTypeMapper.deleteAll", ids);
	}

	public List<PageData> worktypes(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkTypeMapper.worktypes",pd);
	}

	public List<PageData> getDeps(PageData pd)  throws Exception{
		return (List<PageData>) dao.findForList("WorkTypeMapper.getDeps",pd);
	}

	@Override
	public List<PageData> getworktypeBydepHos(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkTypeMapper.getworktypeBydepHos",pd);
	}

	@Override
	public List<PageData> getworktypes(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkTypeMapper.getworktypes",pd);
	}

	@Override
	public List<PageData> getWorkTypeByDep(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkTypeMapper.getWorkTypeByDep",pd);
	}
}
