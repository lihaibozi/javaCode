package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.RepairRegisterManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("repairRegisterService")
public class RepairRegisterService implements RepairRegisterManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> repairRegisterList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("RepairRegisterMapper.repairRegisterlistPage", page);
	}											 

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RepairRegisterMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("RepairRegisterMapper.edit", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("RepairRegisterMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("RepairRegisterMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("RepairRegisterMapper.deleteAll", ids);
	}

	public List<PageData> repairRegisters(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RepairRegisterMapper.repairRegisters",pd);
	}

	public List<PageData> getMemberAndPhone(PageData pd)  throws Exception{
		return (List<PageData>) dao.findForList("RepairRegisterMapper.getMemberAndPhone",pd);
	}

	public List<PageData> getTypes(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RepairRegisterMapper.getTypes",pd);
	}

	public List<PageData> getStatus(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RepairRegisterMapper.getStatus",pd);
	}

	public List<PageData> getMembersByDepId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RepairRegisterMapper.getMembersByDepId",pd);
	}

	public String getMembersById(String id) throws Exception {
		return  (String)dao.findForObject("RepairRegisterMapper.getMembersById",id);
	}

	@Override
	public List<PageData> getDepartment(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RepairRegisterMapper.getDepartment",pd);
	}

	@Override
	public void deleteFilePath(PageData pd) throws Exception {
		dao.update("RepairRegisterMapper.delFilePath", pd);
	}
}
