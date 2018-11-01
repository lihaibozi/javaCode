package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.HdepartmentManager;
import com.hd.service.gh.HmemberManager;
import com.hd.service.gh.PatientManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("hMemberService")
public class HmemberService implements HmemberManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> memberList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("HmemberMapper.memberlistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HmemberMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("HmemberMapper.edit", pd);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("HmemberMapper.save", pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("HmemberMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("HmemberMapper.deleteAll", ids);
	}

	@Override
	public List<PageData> getOrders(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HmemberMapper.getOrders",pd);
	}

}
