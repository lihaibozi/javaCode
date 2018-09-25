package common.utils.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kin207.zn.AppConst;

import common.utils.DateUtil;

public class FriendTime {

	private String language;
	
	private FriendTime(){}
	
	public static FriendTime create(String language)
	{
		FriendTime friendTime = new FriendTime();
		friendTime.language = language;
		return friendTime;
	}
	
	/**
	 * @return 今天／昨天／ yyyy-MM-dd
	 */
	public String socialDate(Date date) 
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DateUtil.setTimeToZero(cal);
		
		Calendar now = Calendar.getInstance();
		DateUtil.setTimeToZero(now);
		
		if (!cal.after(now)) 
		{
			long millis = now.getTimeInMillis() - cal.getTimeInMillis();
			long day = millis / (1000 * 60 * 60 * 24);
			
			if (day == 0) 
			{
				return getDescMap().get(keyToday);
			}
			if (day == 1) 
			{
				return getDescMap().get(keyYesterday);
			}
		}
		
		return DateUtil.dateFormat(date);
	}
	
	/**
	 * @return
	 * 两天内：今天/昨天 hh:mm a
	 * 今年：MM-dd hh:mm a
	 * 其它：yyyy-MM-dd hh:mm a
	 */
	public String matchTime(Date date) 
	{
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(" hh:mm a");
		String hhmm = format.format(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal = DateUtil.setTimeToZero(cal);
		
		Calendar now = Calendar.getInstance();
		now = DateUtil.setTimeToZero(now);
		
		long days = DateUtil.daysBetween(now.getTime(), cal.getTime());
		String mmdd = "";
		if (days <= 0) 
		{
			mmdd = getDescMap().get(keyToday);
		}else if (days <= 1) 
		{
			mmdd = getDescMap().get(keyYesterday);
		}else 
		{
			format.applyPattern("MM-dd");
			mmdd = format.format(date);
		}
		
		String yy = "";
		if (now.get(Calendar.YEAR) != cal.get(Calendar.YEAR)) 
		{
			format.applyPattern("yyyy-");
			yy = format.format(date);
		}
		return new StringBuilder().append(yy).append(mmdd).append(hhmm).toString();
	}
	
	/**
	 * 以友好的方式显示时间
	 * @return xx分钟前／xx小时前／昨天／前天／xx天前／yyyy-MM-dd
	 */
	public String friendlyTime(Date time) 
	{
		if(time == null) 
		{
			return "Unknown";
		}
		
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = DateUtil.getDateFormat();
		
		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int)(ct - lt);
		if(days == 0)
		{
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
			{
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1) + getDescMap().get(keyMinutesAgo);
			}else
			{ 
				ftime = hour + getDescMap().get(keyHoursAgo);
			}
		}else if(days == 1)
		{
			ftime = getDescMap().get(keyYesterday);
		}else if(days >= 2 && days <= 10)
		{ 
			ftime = days + getDescMap().get(keyDaysAgo);			
		}else if(days > 10)
		{			
			ftime = dateFormat.format(time);
		}
		return ftime;
	}
	
	/**
	 * @return 
	 * 今年：MM-dd HH:mm
	 * 其它：yyyy-MM-dd HH:mm
	 */
	public String socialTime(Date date) 
	{
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		
		SimpleDateFormat format = new SimpleDateFormat();
		String yearStr = "";
		String dateStr = null;
		if (year != currentYear) 
		{
			format.applyPattern("yyyy-");
			yearStr = format.format(date);
		}
		if (dateStr == null)
		{
			format.applyPattern("MM-dd");
			dateStr = format.format(date);
		}
		format.applyPattern(" HH:mm");
		String timeStr = format.format(date);

		return new StringBuilder().append(yearStr).append(dateStr).append(timeStr).toString();
	}
	
	
	private static String keyToday = "today";
	private static String keyYesterday = "yesterday";
	private static String keyMinutesAgo = "minutesAgo";
	private static String keyHoursAgo = "hoursAgo";
	private static String keyDaysAgo = "daysAgo";
	private final static Map<String, String> enMap = new HashMap<String, String>();
	static{
		enMap.put(keyToday, "Today");
		enMap.put(keyYesterday, "Yesterday");
		enMap.put(keyMinutesAgo, "minutes ago");
		enMap.put(keyHoursAgo, "hours ago");
		enMap.put(keyDaysAgo, "days ago");
	}
	
	private final static Map<String, String> zhMap = new HashMap<String, String>();
	static{
		zhMap.put(keyToday, "今天");
		zhMap.put(keyYesterday, "昨天");
		zhMap.put(keyMinutesAgo, "分钟前");
		zhMap.put(keyHoursAgo, "小时前");
		zhMap.put(keyDaysAgo, "天前");
	}
	private final static Map<String, String> jpMap = new HashMap<String, String>();
	static{
		jpMap.put(keyToday, "今日");
		jpMap.put(keyYesterday, "昨日");
		jpMap.put(keyMinutesAgo, "分钟前");
		jpMap.put(keyHoursAgo, "小时前");
		jpMap.put(keyDaysAgo, "天前");
	}
	private final static Map<String, String> krMap = new HashMap<String, String>();
	static{
		krMap.put(keyToday, "오늘");
		krMap.put(keyYesterday, "어제");
		krMap.put(keyMinutesAgo, "분 전");
		krMap.put(keyHoursAgo, "시간 전");
		krMap.put(keyDaysAgo, "날전");
	}
	private Map<String, String> getDescMap()
	{
		if (AppConst.CHOOSE_SYSTEM_LANGUAGE.equals("En")) 
		{
			return enMap;
		}
		if (AppConst.CHOOSE_SYSTEM_LANGUAGE.equals("Kor")) 
		{
			return krMap;
		}
		if (AppConst.CHOOSE_SYSTEM_LANGUAGE.equals("Jp")) 
		{
			return  jpMap;
		}
		return zhMap;
		
		
	}
}
