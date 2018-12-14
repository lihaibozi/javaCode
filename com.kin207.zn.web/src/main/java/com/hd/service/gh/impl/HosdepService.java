package com.hd.service.gh.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hd.dao.DaoSupport;
import com.hd.entity.Page;
import com.hd.service.gh.HosdepManager;
import com.hd.service.gh.PatientManager;
import com.hd.util.PageData;


/** 科室信息
 * @author lihaibo
 * 修改时间：2018.10.26
 */
@Service("hosdepService")
public class HosdepService implements HosdepManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hosdepList(Page page)throws Exception{
		return (List<PageData>) dao.findForList("HosdepMapper.hosdeplistPage", page);
	}

	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HosdepMapper.findById", pd);
	}
	
	/**修改数据
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("HosdepMapper.edit", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("HosdepMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("HosdepMapper.deleteById", pd);
	}
	
	/**批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception{
		dao.delete("HosdepMapper.deleteAll", ids);
	}

	public List<PageData> hosdeps(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HosdepMapper.hosdeps",pd);
	}

	public List<PageData> getDeps(PageData pd)  throws Exception{
		return (List<PageData>) dao.findForList("HosdepMapper.getDeps",pd);
	}

	@Override
	public List<PageData> gethosdepBydepHos(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HosdepMapper.gethosdepBydepHos",pd);
	}

	@Override
	public List<PageData> gethosdeps(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HosdepMapper.gethosdeps",pd);
	}
}
