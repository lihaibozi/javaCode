package com.hd.service.gh;

import java.util.List;

import com.hd.entity.Page;
import com.hd.util.PageData;


/** 商品信息接口类
 * @author KinFeng
 * 修改时间：2015.11.2
 */
public interface GhUserManager {
	
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> userList (Page page)throws Exception;
	
}
