package common.utils;

import com.kin207.zn.AppConst;

/**
 * H5分页参数
 * @author wujun
 */
public class PageParam {
	/* 初始页数 */
	public static final int FIRST_PAGE = 1;
	
	private int start;
	private int limit;
	private int pageNum;
	
	private PageParam(int start, int limit, int pageNum){
		this.start = start;
		this.limit = limit;
		this.pageNum = pageNum;
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public static PageParam buildH5(Integer pageNum){
		return build(pageNum, AppConst.PAGE_SIZE_TEN);
	}
	
	public static PageParam build(Integer pageNum, Integer pageSize){
		if (pageNum == null || pageNum <= 0)
			pageNum = 1;
		
		if (pageSize == null || pageSize <= 0)
			pageSize = AppConst.PAGE_SIZE_TEN;
		
		int start = (pageNum - 1) * pageSize;
		
		PageParam pageParam = new PageParam(start, pageSize, pageNum);
		return pageParam;
	}
	
}
