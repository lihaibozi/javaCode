package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.OrderRegisterManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("orderRegisterService")
public class OrderRegisterService implements OrderRegisterManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> orderRegisterList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("OrderRegisterMapper.orderRegisterlistPage", page);
	}											 

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrderRegisterMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OrderRegisterMapper.edit", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("OrderRegisterMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("OrderRegisterMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("OrderRegisterMapper.deleteAll", ids);
	}

	public List<PageData> orderRegisters(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderRegisterMapper.orderRegisters",pd);
	}

	public List<PageData> getMemberAndPhone(PageData pd)  throws Exception{
		return (List<PageData>) dao.findForList("OrderRegisterMapper.getMemberAndPhone",pd);
	}

	public List<PageData> getTypes(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderRegisterMapper.getTypes",pd);
	}

	public List<PageData> getStatus(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderRegisterMapper.getStatus",pd);
	}

	public List<PageData> getMembersByDepId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderRegisterMapper.getMembersByDepId",pd);
	}

	public void editStatus(PageData pd) throws Exception {
		dao.update("OrderRegisterMapper.editStatus", pd);
	}
}
