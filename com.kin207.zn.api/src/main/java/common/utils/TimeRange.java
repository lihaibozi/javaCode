package common.utils;

import java.util.Date;

/**
 * 时间范围
 * @author wujun
 */
public class TimeRange {
	
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	
	public TimeRange(){}
	
	public TimeRange(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
