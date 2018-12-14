package com.kin207.zn;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import common.utils.DateUtil;
import common.utils.StringUtil;

public class BaseController extends Controller{

	/**
	 * 读取session中的userId
	 */
	public int getSessionUserId()
	{
		return AppSession.getUserId(getSession());
	}
	
	/**
	 * 读取移动端传输的userId
	 */
	public int getMobileUserId() 
	{
		return getParaToInt("_userId");
	}
	
	
	/**
	 * 将参数转为Date
	 * 默认格式为 yyyy-MM-dd HH:mm:ss
	 */
	public Date getParaToDateTime(String paraName)
	{
		return getParaToDateTime(paraName, DateUtil.REG_YYMMDD_HHMMSS);
	}
	
	/**
	 * 将参数转为Date
	 */
	public Date getParaToDateTime(String paraName, String patterm)
	{
		String date = getPara(paraName);
		if(date != null && !"".equals(date))
		{
			return DateUtil.getDateOf(date, patterm);
		}
		return null;
	}
	
	/**
	 * 将日期＋时间参数转为Date
	 * 默认格式为 yyyy-MM-ddHH:mm
	 */
	public Date getParasToDate(String paramDate, String paramTime)
	{
		return getParasToDate(paramDate, paramTime, "yyyy-MM-ddHH:mm");
	}
	
	public Date getParasToDate(String paramDate, String paramTime, String format)
	{
		String dateStr = getPara(paramDate);
		String timeStr = getPara(paramTime);
		Date dateTime = DateUtil.getDateOf(dateStr + timeStr, format);
		return dateTime;
	}
	
	
	
	public double getParaToDouble(String paraName)
	{
        String value = getPara(paraName);
        return Double.valueOf(value);
    }
    
    public double getParaToDouble(String paraName, double defaultValue)
    {
        String value = getPara(paraName);
        if(value == null || "".equals(value.trim()))
        {
            return defaultValue;
        }
        double tmp = defaultValue;
        try{
            tmp = Double.valueOf(value);
        }catch(Exception e){
            tmp = defaultValue;
        }
        return tmp;
    }
    

	
	
	/**
	 * 分页查询
	 */
	public Page<Record> page(String select, String where)
	{
		return page(select, where, new Object[0]);
	}
	
	/**
	 * 分页查询
	 */
	public Page<Record> page(String select, String where, Object...params)
	{
		Integer pageNumber = getParaToInt("pageNum");
		pageNumber = pageNumber == null ? AppConst.DEFAULT_PAGE_NUM : pageNumber;
		
		Integer pageSize = getParaToInt("pageSize");
		pageSize = pageSize == null ? AppConst.DEFAULT_PAGE_SIZE : pageSize;
		
		Page<Record> page = Db.paginate(pageNumber, pageSize, select, where, params);
		
		setAttr("list", page.getList());
		setAttr("pageNum", page.getPageNumber());
		setAttr("pageSize", page.getPageSize());
		setAttr("totalRow", page.getTotalRow());
		setPageUrl();
		return page;
	}
	
	private void setPageUrl() 
	{
		String pageUrl = getRequest().getRequestURL().toString();
		String params = getRequest().getQueryString();
		if(params != null && params.trim().length() > 0)
		{
			pageUrl = StringUtil.concat(pageUrl, "?", params);
		}
		setAttr("page_url", pageUrl);
	}
	
	
	
	/**
     * 另一种分页方法
     */
	public List<Record> nextPageDesc(String sql, Object[] params)
	{
		int nextId = Integer.MAX_VALUE;
		return nextPage(sql, nextId, params);
	}
	
	public List<Record> loadMoreDesc(String sql, Object... params)
	{
		int nextId = Integer.MAX_VALUE;
		return nextPage(sql, nextId, params);
	}
	
	public List<Record> nextPageAsc(String sql, Object[] params)
	{
		int nextId = Integer.MIN_VALUE;
		return nextPage(sql, nextId, params);
	}
	
	public List<Record> loadMoreAsc(String sql, Object...params)
	{
		int nextId = Integer.MIN_VALUE;
		return nextPage(sql, nextId, params);
	}
	
	/**
	 * 加载下一页
	 */
	private List<Record> nextPage(String sql, Integer nextId, Object...params)
	{
		if(getPara("nextId") != null)
		{
			nextId = getParaToInt("nextId");
		}
		int pageSize = AppConst.MOBILE_PAGE_SIZE;
		if (getPara("pageSize") != null) 
		{
			pageSize = getParaToInt("pageSize");
		}
		
		sql = sql.replace("??", nextId.toString());
		StringBuilder sqlBuilder = new StringBuilder(sql);
		sqlBuilder.append(" limit ").append(pageSize);
		
		List<Record> list = Db.find(sqlBuilder.toString(), params);
		
		if (list != null && !list.isEmpty()) 
		{
			nextId = list.get(list.size() - 1).getInt("id");
		}
		setAttr("nextId", nextId);
		setAttr("pageSize", pageSize);
		setAttr("list", list);
		return list;
	}
	
	public String buildWhere(String whereSql, Map<String, Object> whereParams, String[] whereConditions, List<Object> buildedParams,  Object...originParams)
	{
		
		if (originParams != null && originParams.length > 0) 
		{
			for (Object param : originParams) 
			{
				buildedParams.add(param);
			}
		}
		
		StringBuilder whereBuilder = new StringBuilder().append(whereSql);
		if(whereParams != null && !whereParams.isEmpty())
		{
			for(int i = 0; i < whereConditions.length; i++)
			{
				if(whereParams.containsKey(whereConditions[i]) && whereParams.get(whereConditions[i]) != null && !whereParams.get(whereConditions[i]).equals(""))
				{
					whereBuilder.append(" ").append(whereConditions[i]).append(" ");
					buildedParams.add(whereParams.get(whereConditions[i]));
				}
			}
		}
		whereSql = whereBuilder.toString();
		return whereSql;
	}
	/**
	 * 返回请求成功信息
	 */
	public void renderSuccess()
	{
		setAttr(AppConst.STATUS_CODE_KEY, AppConst.STATUC_SUCCESS);
		setAttr(AppConst.STATUS_CODE_MSG, AppConst.STATUC_SUCCESS_MSG);
		renderJson();
	}
	

}
