package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.PatientManager;
import com.hd.service.gh.WorkScheduleManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("workScheduleService")
public class WorkScheduleService implements WorkScheduleManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> workscheduleList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("WorkScheduleMapper.workschedulelistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WorkScheduleMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WorkScheduleMapper.edit", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("WorkScheduleMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("WorkScheduleMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("WorkScheduleMapper.deleteAll", ids);
	}

	public List<PageData> workschedules(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkScheduleMapper.workschedules",pd);
	}

	public List<PageData> getDeps(PageData pd)  throws Exception{
		return (List<PageData>) dao.findForList("WorkScheduleMapper.getDeps",pd);
	}

	public List<PageData> getworkscheduleBydepHos(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkScheduleMapper.getworkscheduleBydepHos",pd);
	}

	public List<PageData> getworkschedules(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkScheduleMapper.getworkschedules",pd);
	}
	
	public void setFinish(PageData pd) throws Exception {
		dao.update("WorkScheduleMapper.setFinish", pd);
	}

	public List<PageData> findFinished(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkScheduleMapper.findFinished",pd);
	}
	
	public void setNotify(PageData pd) throws Exception {
		dao.update("WorkScheduleMapper.setNotify", pd);
	}
}
