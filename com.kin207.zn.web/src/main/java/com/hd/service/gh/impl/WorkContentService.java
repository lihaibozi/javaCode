package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.WorkContentManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("workContentService")
public class WorkContentService implements WorkContentManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> workcontentList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("WorkContentMapper.workcontentlistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WorkContentMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WorkContentMapper.edit", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("WorkContentMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("WorkContentMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("WorkContentMapper.deleteAll", ids);
	}

	public List<PageData> workcontents(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkContentMapper.workcontents",pd);
	}

	public List<PageData> getDeps(PageData pd)  throws Exception{
		return (List<PageData>) dao.findForList("WorkContentMapper.getDeps",pd);
	}

	public List<PageData> getworkcontentBydepHos(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkContentMapper.getworkcontentBydepHos",pd);
	}

	public List<PageData> getworkcontents(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkContentMapper.getworkcontents",pd);
	}

	public List<PageData> findByScheduleId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("WorkContentMapper.findByScheduleId", pd);
	}

	public void deleteFilePath(PageData pd) throws Exception {
		dao.update("WorkContentMapper.delFilePath", pd);
	}

	@Override
	public List<PageData> researchWrokContent(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkContentMapper.researchWrokContent", page);
	}

	@Override
	public void addOpinion(PageData pd) throws Exception {
		dao.update("WorkContentMapper.addOpinion", pd);
	}

	@Override
	public List<PageData> zkWrokContent(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkContentMapper.zkWrokContent", page);
	}
}
