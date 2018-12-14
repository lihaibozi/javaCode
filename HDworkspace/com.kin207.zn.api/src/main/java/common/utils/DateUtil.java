package common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kin207.zn.support.LanguageKit;
import com.kin207.zn.support.MobileParam;

import common.utils.time.FriendTime;

/**
 * 常用日期函数工具类
 */
public class DateUtil {
	private static Logger log = Logger.getLogger(DateUtil.class);
	public final static String REG_YYMMDD = "yyyy-MM-dd";
	public final static String REG_YYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public final static String REG_YYMMDDMM = "yyyy-MM-dd HH:mm";
	
	public static final Integer MAX_HOUR = 23;
	public static final Integer MAX_MINUTE = 59;
	public static final Integer MAX_SECOND = 59;
	
	public static final Integer MIN_HOUR = 0;
	public static final Integer MIN_MINUTE = 0;
	public static final Integer MIN_SECOND = 0;
	/**
	 * @param reg   日期格式化参数
	 * @param date  日期
	 */
	public static String dateFormat(String reg, Date date)
	{
		DateFormat sdf = getDateFormat(reg);
		return sdf.format(date);
	}
	
	/**
	 * 默认日期格式化  格式为 yyyy-MM-dd
	 */
	public static String dateFormat(Date date)
	{
		if(date == null)
		{
			return "";
		}
		return dateFormat(REG_YYMMDD,date);
	}
	
	public static String dateFormatForTime(Date date)
	{
		return dateFormat(REG_YYMMDD_HHMMSS, date);
	}
	
	
	public static Date firstDayOfMonth(int year, int month) 
	{     
		Calendar cal = Calendar.getInstance();     
		cal.set(Calendar.YEAR, year);     
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DATE));  
		return cal.getTime();  
	}
	  
	public static Date lastDayOfMonth(int year, int month) 
	{     
		Calendar cal = Calendar.getInstance();     
		cal.set(Calendar.YEAR, year);     
		cal.set(Calendar.MONTH, month);  
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DATE));  
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}  
	
	
	
	public static boolean isToday(Date time)
	{
		boolean b = false;
		Date today = new Date();
		DateFormat dateFormat = getDateFormat();
		if(time != null)
		{
			String nowDate = dateFormat.format(today);
			String timeDate = dateFormat.format(time);
			if(nowDate.equals(timeDate))
			{
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * 获取日期格式化对象
	 * @return
	 */
	public static DateFormat getDateFormat()
	{
		return getDateFormat(REG_YYMMDD);
	}
	/**
	 * 获取日期格式化对象
	 * @param reg
	 * @return
	 */
	public static DateFormat getDateFormat(String reg)
	{
		return new SimpleDateFormat(reg);
	}
	
	
	public static int compare(Date first, Date second) 
	{
		if(first == null && second == null){
			return 0;
		}
		if(first == null && second != null){
			return -1;
		}
		if(first != null && second == null){
			return 1;
		}
		return first.compareTo(second);
	}
	
	public static TimeRange getTimeRangeOfDay(int afterDayNum) 
	{
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DAY_OF_YEAR, afterDayNum);
		startCalendar.set(Calendar.HOUR_OF_DAY, startCalendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		startCalendar.set(Calendar.MINUTE, startCalendar.getActualMinimum(Calendar.MINUTE));
		startCalendar.set(Calendar.SECOND, startCalendar.getActualMinimum(Calendar.SECOND));
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DAY_OF_YEAR, afterDayNum);
		endCalendar.set(Calendar.HOUR_OF_DAY, startCalendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		endCalendar.set(Calendar.MINUTE, startCalendar.getActualMaximum(Calendar.MINUTE));
		endCalendar.set(Calendar.SECOND, startCalendar.getActualMaximum(Calendar.SECOND));
		
		return new TimeRange(startCalendar.getTime(), endCalendar.getTime());
	}
	
	public static Date setTime(Date endDate, Integer hour, Integer minute, Integer second)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}
	
	public static Calendar getCalendar(Date date) 
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	
	/**
	 * @param month from 1 to 12
	 * @return
	 */
	public static List<Integer> getDaysBetweenOfMonth(Integer month, Date startDate, Date endDate) 
	{
		List<Integer> days = new ArrayList<Integer>();
		
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		
		int targetMonth = month - 1;
		
		if (startCal.get(Calendar.MONTH) <= targetMonth) 
		{
			while (startCal.get(Calendar.MONTH) <= targetMonth && !startCal.after(endCal)) 
			{
				if(startCal.get(Calendar.MONTH) == targetMonth)
				{
					days.add(startCal.get(Calendar.DAY_OF_MONTH));
				}
				startCal.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		
		return days;
	}
	
	
	/**
	 * @param year
	 * @param month from 1 to 12
	 * @param dayOfMonth
	 */
	public static TimeRange getTimeRangeOfDate(Integer year, Integer month, Integer dayOfMonth) 
	{
		Calendar currentDay = Calendar.getInstance();
		currentDay.set(Calendar.YEAR, year);
		currentDay.set(Calendar.MONTH, month - 1);
		currentDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		currentDay.set(Calendar.HOUR_OF_DAY, currentDay.getActualMaximum(Calendar.HOUR_OF_DAY));
		currentDay.set(Calendar.MINUTE, currentDay.getActualMaximum(Calendar.MINUTE));
		currentDay.set(Calendar.SECOND, currentDay.getActualMaximum(Calendar.SECOND));
		
		Date endTime = currentDay.getTime();
		
		currentDay.set(Calendar.HOUR_OF_DAY, currentDay.getActualMinimum(Calendar.HOUR_OF_DAY));
		currentDay.set(Calendar.MINUTE, currentDay.getActualMinimum(Calendar.MINUTE));
		currentDay.set(Calendar.SECOND, currentDay.getActualMinimum(Calendar.SECOND));
		
		Date startTime = currentDay.getTime();
		
		return new TimeRange(startTime, endTime);
	}
	
	public static TimeRange getTimeRangeOfDate(Date date) 
	{
		Calendar currentDay = Calendar.getInstance();
		currentDay.setTime(date);
		
		setTimeToMax(currentDay);
		Date endTime = currentDay.getTime();
		
		setTimeToZero(currentDay);
		Date startTime = currentDay.getTime();
		
		return new TimeRange(startTime, endTime);
	}
	
	
	
	public static TimeRange getTimeRangeOfToday()
	{
		Calendar currentDay = Calendar.getInstance();
		currentDay.set(Calendar.HOUR_OF_DAY, MIN_HOUR);
		currentDay.set(Calendar.MINUTE, MIN_MINUTE);
		currentDay.set(Calendar.SECOND, MIN_SECOND);
		
		Date startTime = currentDay.getTime();
		
		currentDay.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
		currentDay.set(Calendar.MINUTE, MAX_MINUTE);
		currentDay.set(Calendar.SECOND, MAX_SECOND);
		
		Date endTime = currentDay.getTime();
		
		return new TimeRange(startTime, endTime);
	}
	
	
	/**
	 * @param month 1..12
	 * @return
	 */
	public static TimeRange getTimeRangeOfMonth(int month)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month - 1);
		
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, MIN_HOUR);
		cal.set(Calendar.MINUTE, MIN_MINUTE);
		cal.set(Calendar.SECOND, MIN_SECOND);
		
		Date startTime = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
		cal.set(Calendar.MINUTE, MAX_MINUTE);
		cal.set(Calendar.SECOND, MAX_SECOND);
		
		Date endTime = cal.getTime();
		
		return new TimeRange(startTime, endTime);
	}
	
	/**
	 * @param month 1..12
	 * @return
	 */
	public static TimeRange getTimeRangeOfMonth(int year, int month)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, MIN_HOUR);
		cal.set(Calendar.MINUTE, MIN_MINUTE);
		cal.set(Calendar.SECOND, MIN_SECOND);
		
		Date startTime = cal.getTime();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
		cal.set(Calendar.MINUTE, MAX_MINUTE);
		cal.set(Calendar.SECOND, MAX_SECOND);
		
		Date endTime = cal.getTime();
		
		return new TimeRange(startTime, endTime);
	}
	
	
	public static TimeRange getOfCurrentWeek() 
	{
		Calendar calendar = Calendar.getInstance();
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DAY_OF_MONTH, 7 - dayOfWeek);
		calendar.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
		calendar.set(Calendar.MINUTE, MAX_MINUTE);
		calendar.set(Calendar.SECOND, MAX_SECOND);
		Date end = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		calendar.set(Calendar.HOUR_OF_DAY, MIN_HOUR);
		calendar.set(Calendar.MINUTE, MIN_MINUTE);
		calendar.set(Calendar.SECOND, MIN_SECOND);
		Date start = calendar.getTime();
		return new TimeRange(start, end);
	}
	
	
	public static TimeRange timeRangeOfCurMonth()
	{
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, MIN_HOUR);
		cal.set(Calendar.MINUTE, MIN_MINUTE);
		cal.set(Calendar.SECOND, MIN_SECOND);
		
		Date startTime = cal.getTime();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
		cal.set(Calendar.MINUTE, MAX_MINUTE);
		cal.set(Calendar.SECOND, MAX_SECOND);
		
		Date endTime = cal.getTime();
		
		return new TimeRange(startTime, endTime);
	}
	
	
	/**
	 * 获取当前月
	 */
	public static Integer getCurrentMonth() 
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取当前月中的天 
	 */
	public static Integer getCurrentDay(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前日期的年
	 */
	public static Integer getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);//得到年
	}
	
	/**
	 * 获取当前日期添加指定天数的日期
	 */
	public static String addDay(int days){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		SimpleDateFormat format = new SimpleDateFormat("MM.dd");
		return format.format(cal.getTime());
	}
	
	/**
	 * 获取当前日期添加指定天数的日期
	 */
	public static String addAssDay(int days){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		SimpleDateFormat format = new SimpleDateFormat(REG_YYMMDD);
		return format.format(cal.getTime());
	}
	
	/**
	 * 获取当前指定格式的日期类型
	 */
	public static Date getCurrentDate(){
		SimpleDateFormat format = new SimpleDateFormat(REG_YYMMDD);
		try {
			return format.parse(format.format(new Date()));
		} catch (ParseException e) {
			log.error("DateUtil getCurrentDate()" +e.getMessage());
		}
		return null;
	}
	
	/**
	 * 创建某个月的日历
	 * month 1..12
	 */
	public static List<Calendar> createCalsOf(int year, int month) 
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.HOUR_OF_DAY, MIN_HOUR);
		cal.set(Calendar.MINUTE, MIN_MINUTE);
		cal.set(Calendar.SECOND, MIN_SECOND);
		
		int minDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		List<Calendar> cals = new ArrayList<Calendar>();
		for (int dayOfMonth = minDay; dayOfMonth <= maxDay; dayOfMonth++) 
		{
			Calendar newCal = (Calendar)cal.clone();
			newCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			cals.add(newCal);
		}
		return cals;
	}
	
	/**
	 * 字符串转化为Date
	 * dateStr 格式为： yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDateOf(String dateStr) 
	{
		return getDateOf(dateStr, REG_YYMMDD_HHMMSS);
	}
	public static Date getDate(String dateStr){
		return getDateOf(dateStr, REG_YYMMDDMM);
	}
	/**
	 * 字符串转化为Date
	 */
	public static Date getDateOf(String dateStr, String patterm) {
		SimpleDateFormat format = new SimpleDateFormat(patterm);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			log.error("DateUtil getDateOf" +e.getMessage());
			
		}
		return null;
	}
	/**
	 * Long转化为Date String
	 */
	public static String getDateOfString(long dateStr) {
//		long date = Date.parse(dateStr);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(dateStr);
	}

	
	/**
	 * 获取当天的时间
	 * @param hhmm  格式为，HH:mm，比如： 22:30
	 */
	public static Date getTodayTimeOf(String hhmm)
	{
		if(hhmm == null || "".equals(hhmm))
		{
			return null;
		}
		String[] pies = hhmm.split(":");
		if(pies.length != 2)
		{
			return null;
		}
		int hour = Integer.parseInt(pies[0]);
		int minite = Integer.parseInt(pies[1]);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, hour);
		now.set(Calendar.MINUTE, minite);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}
	
	public static String toHHmm(int millSeconds)
	{
		int minutes = millSeconds / (1000 * 60);
		
		Integer minute = minutes % 60;
		Integer hour = minutes / 60;
		String hourStr = hour < 10 ? "0" + hour.toString() : hour.toString();
		String minuteStr = minute < 10 ? "0" + minute.toString() : minute.toString();
		
		return String.format("%s:%s", hourStr, minuteStr);
	}
	
	public static Date setTimeToZero(Date date) 
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, MIN_HOUR);
		cal.set(Calendar.MINUTE, MIN_MINUTE);
		cal.set(Calendar.SECOND, MIN_SECOND);
		return cal.getTime();
	}
	
	public static Calendar setTimeToZero(Calendar cal) 
	{
		cal.set(Calendar.HOUR_OF_DAY, MIN_HOUR);
		cal.set(Calendar.MINUTE, MIN_MINUTE);
		cal.set(Calendar.SECOND, MIN_SECOND);
		return cal;
	}
	
	public static void setTimeToMax(Calendar cal) 
	{
		cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
		cal.set(Calendar.MINUTE, MAX_MINUTE);
		cal.set(Calendar.SECOND, MAX_SECOND);
		
	}
	
	/**
	 * @return dateA - dateB
	 */
	public static long daysBetween(Date dateA, Date dateB)
	{
		long millis = dateA.getTime() - dateB.getTime();
		
		long days = millis / (1000 * 60 * 60 * 24);
		
		return days;
	}
	
	
	public static Integer calAge(Date birthday) 
	{
		Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("出生时间大于当前时间!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthday);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) 
        {
            if (monthNow == monthBirth) 
            {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else 
            {
                age--;
            }
        }
        return age;
	}

	public static String friendlyTime(Date date,MobileParam param) {
//		return FriendTime.create(LanguageKit.LANGUAGE_DEFAULT).friendlyTime(date);
		return FriendTime.create(param.getPara("sysLanguage")).friendlyTime(date);
	}
	public static String friendlyTime(Date date) {
		return FriendTime.create(LanguageKit.LANGUAGE_DEFAULT).friendlyTime(date);
		//return FriendTime.create(param.getPara("sysLanguage")).friendlyTime(date);
	}
}
