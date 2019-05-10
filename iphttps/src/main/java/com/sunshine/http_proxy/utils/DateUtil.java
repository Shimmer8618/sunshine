/****************************************************************************
 Module: 日期转换处理
 Version: 1.0
 Author:  likt		
 Company: excel
 Description:
 *****************************************************************************/

package com.sunshine.http_proxy.utils;

import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期转换工具
 */
public class DateUtil {
	public static final String DATE_DIVISION = "-";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String TIME_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";

	public static final String TIME_PATTERN_DEFAULT_NO_SS = "yyyy-MM-dd HH:mm";
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd";
	/**
	 * yyyyMMdd
	 */
	public static final String DATA_PATTERN_YYYYMMDD = "yyyyMMdd";
	/**
	 * HH:mm:ss
	 */
	public static final String TIME_PATTERN_HHMMSS = "HH:mm:ss";

	/**
	 * yyyy-MM-dd HH-mm-ss
	 */
	public static final String TIME_PATTERN = "yyyy-MM-dd HH-mm-ss";

	/**
	 * yyyyMMddHHmmss
	 */
	public static final String TIME_PATTERN_DEFAULT_STR = "yyyyMMddHHmmss";

	public static final String TIME_PATTERN_SSSS_STR = "yyyyMMddHHmmssSSSS";

	public static final int SECOND = 1000;
	public static final int MINUTE = 60 * SECOND;
	public static final int HOUR = 60 * MINUTE;
	public static final long DAY = 24l * HOUR;

	/**
	 * Return the current date
	 * 
	 * @return － DATE<br>
	 */
	public static Date now() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return currDate;
	}

	public static Timestamp nowTimestamp() {
		Calendar cal = Calendar.getInstance();
		return new Timestamp(cal.getTimeInMillis());
	}
	/**
	 * 给一个时间加上N分种或减去N分种后得到一个新的日期
	 *
	 * @param startDate
	 *            需要增加的日期时间
	 * @param addnos
	 *            添加的分钟数，可以是正数也可以是负数
	 * @return 操作后的日期
	 */
	public static Date addMinute(Date startDate, int addnos) {
		if (startDate == null) {
			return null;
		}
		Calendar cc = Calendar.getInstance();
		cc.setTime(startDate);
		cc.add(Calendar.MINUTE, addnos);
		return cc.getTime();
	}
	/**
	 * Return the current date string
	 * 
	 * @return － 产生的日期字符串<br>
	 */
	public static String nowString() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return formatDate(currDate);
	}

	/**
	 * Return the current date in the specified format
	 * 
	 * @param strFormat
	 * @return
	 */
	public static String nowString(String pattern) {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return format(currDate, pattern);
	}

	public static Timestamp parseTimestampDefault(String dateValue) {
		return new Timestamp(parse(dateValue, TIME_PATTERN_DEFAULT).getTime());
	}

	public static Timestamp parseTimestamp(String dateValue) {
		return new Timestamp(parse(dateValue, TIME_PATTERN_HHMMSS).getTime());
	}

	public static Timestamp parseTimestampYYMMDD(String dateValue) {
		return new Timestamp(parse(dateValue, DATE_PATTERN_DEFAULT).getTime());
	}
	
	public static Timestamp parseTimestamp(String dateValue,String partten) {
		return new Timestamp(parse(dateValue, partten).getTime());
	}

	public static Timestamp parseTimeNoStamp(String dateValue) {
		if(null==dateValue||dateValue.trim().length()==0) {
			dateValue=getNowDateString();	
		}
		return new Timestamp(parse(dateValue, DATE_PATTERN_DEFAULT).getTime());

	}

	public static String getNowDateString() {
		return format(new Date(),DATE_PATTERN_DEFAULT);
	}

	/**
	 * Parse a string and return a date value
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String dateValue) {
		return parse(dateValue, DATE_PATTERN_DEFAULT);
	}

	/**
	 * Parse a strign and return a datetime value
	 * 
	 * @param dateValue
	 * @return
	 */
	public static Date parseTime(String dateValue) {
		return parse(dateValue, TIME_PATTERN_DEFAULT);
	}

	/**
	 * Parse a string and return the date value in the specified format
	 * 
	 * @param strFormat
	 * @param dateValue
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date parse(String dateValue, String pattern) {
		if (StringUtils.isEmpty(dateValue))
			return null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

		try {
			return dateFormat.parse(dateValue);
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public static String formatDate(Date d) {
		return format(d, DATE_PATTERN_DEFAULT);
	}

	public static String formatDate(Timestamp d) {
		return format(d, DATE_PATTERN_DEFAULT);
	}

	
	
	public static String formatDateddmmss(Date d) {
		return format(d, TIME_PATTERN_DEFAULT);
	}
	
	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串(只有数字)。
	 * 
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public static String formatTimeTargetStr(Date t) {
		String timestr = null;
		timestr = format(t, TIME_PATTERN_DEFAULT_STR);
		return timestr;
	}

	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串(只有数字)。
	 * 
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public String formatTimeStr(Date t) {
		String timestr = null;
		synchronized (this) {
			timestr = format(t, TIME_PATTERN_DEFAULT_STR);
		}
		return timestr;
	}

	/**
	 * 豪秒 将Timestamp 类型的日期转换为系统参数定义的格式的字符串(只有数字)。
	 * 
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public String formatTimeSSSSStr(Date t) {
		String timestr = null;
		synchronized (this) {
			timestr = format(t, TIME_PATTERN_SSSS_STR);
		}
		return timestr;
	}

	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public static String formatTime(Date t) {
		return format(t, TIME_PATTERN_DEFAULT);
	}

	/**
	 * 将Date类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param aTs_Datetime
	 * @param as_Pattern
	 * @return
	 */
	public static String format(Date d, String pattern) {
		if (d == null)
			return null;

		SimpleDateFormat dateFromat = new SimpleDateFormat(pattern);
		return dateFromat.format(d);
	}
	
	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param t
	 * @param pattern
	 * @return
	 */
	public static String format(Timestamp t, String pattern) {
		if (t == null)
			return null;

        Date date = new Date(t.getTime());
        
        return DateUtil.format(date, pattern);
	}
	
	/**
	 * 将Timestamp类型的日期转换Date类型。
	 * 
	 * @param t
	 * @return
	 */
	public static Date timestamp2Date(Timestamp t) {
		if (t == null)
			return null;

        return new Date(t.getTime());
	}

	/**
	 * 取得指定日期N天后的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, days);

		return cal.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date1
	 *            start
	 * @param date2
	 *            end
	 * @return
	 */
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的月数 ,请用 后面参数-前面参数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int MonthsBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH);
		cal.setTime(date2);
		int year2 = cal.get(Calendar.YEAR);
		int month2 = cal.get(Calendar.MONTH);

		int m = (year2 - year1) * 12 + (month2 - month1);

		return m;
	}

	/**
	 * 计算开始时间到月末与结束时间到月初之和
	 * 
	 * @param StarDate
	 * @param EndDate
	 * @return day
	 */
	public static int getDayByStarDateEndDate(Date StarDate, Date EndDate) {
		int dayStar = daysBetween(StarDate, getFirstDateOfNextMonth(StarDate));

		int dayEnd = daysBetween(getFirstDateOfCurrentMonth(EndDate), EndDate);

		int day = dayStar + dayEnd;
		if (day > 30) {
			day = 30;
		}
		return day;
	}

	/**
	 * 计算两个相同天相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int DaysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int day1 = cal.get(Calendar.DATE);
		cal.setTime(date2);
		int day2 = cal.get(Calendar.DATE);

		int d = day2 - day1;
		if (d > 30) {
			d = 30;
		}
		if (d < -30) {
			d = -30;
		}
		return d;
	}

	/**
	 * 判断两个日期是否为同月
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean booleanBetweenDate(Date date1, Date date2) {
		boolean falg = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int month1 = cal.get(Calendar.MONTH);
		cal.setTime(date2);
		int month2 = cal.get(Calendar.MONTH);
		int m = month2 - month1;
		if (m == 0) {
			falg = true;
		}
		return falg;
	}

	/**
	 * 计算两个相同月相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int MonthCountBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH);
		cal.setTime(date2);
		int year2 = cal.get(Calendar.YEAR);
		int month2 = cal.get(Calendar.MONTH);
		int m = month2 - month1;
		int y = year2 - year1;

		return y * 12 + m;
	}

	public static Date getDateBeforTwelveMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.MONTH, -12);

		return cal.getTime();

	}

	/**
	 * 传入时间字符串,加一天后返回Date
	 * 
	 * @param date
	 *            时间 格式 YYYY-MM-DD
	 * @return
	 */
	public static Date addDate(String date) {
		if (date == null)
			return null;

		Date tmpDate = parse(date, DATE_PATTERN_DEFAULT);

		Calendar cal = Calendar.getInstance();
		cal.setTime(tmpDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}


	/**
	 * 得到某个日期的星期
	 * 
	 * @param dt
	 * @return Day of the week (0 - sunday)
	 */
	public static int getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return w;
	}

	/**
	 * 得某(月，季度，上半年，下半年，全年)第1天日期
	 */
	public static Date getCurrentMonFirstDate(int month) {
		Calendar cal = Calendar.getInstance();
		if (month == 13) {
			cal.setTime(now());
			cal.set(Calendar.DAY_OF_YEAR, 1);
		} else if (month == 14 || month == 17) {
			cal.set(Calendar.MONTH, 5);
			cal.set(Calendar.DATE, 1);
		} else if (month == 15 || month == 19) {
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.DATE, 1);
		} else if (month == 16) {
			cal.set(Calendar.MONTH, 2);
			cal.set(Calendar.DATE, 1);
		} else if (month == 18) {
			cal.set(Calendar.MONTH, 8);
			cal.set(Calendar.DATE, 1);
		} else {
			cal.set(Calendar.MONTH, (month - 1));
			cal.set(Calendar.DATE, 1);
		}
		return cal.getTime();
	}

	/**
	 * 得某(月，季度，上半年，下半年，全年)最后1天日期
	 */
	public static Date getCurrentMouLastDate(int month) {
		Calendar cal = Calendar.getInstance();
		if (month == 13) {
			cal.setTime(now());
			cal.set(Calendar.DAY_OF_YEAR, 1);
			cal.roll(Calendar.DAY_OF_YEAR, -1);
		} else if (month == 14 || month == 17) {
			cal.set(Calendar.MONTH, 5);
			cal.set(Calendar.DATE, 1);
			cal.roll(Calendar.DATE, -1);
		} else if (month == 15 || month == 19) {
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.DATE, 1);
			cal.roll(Calendar.DATE, -1);
		} else if (month == 16) {
			cal.set(Calendar.MONTH, 2);
			cal.set(Calendar.DATE, 1);
			cal.roll(Calendar.DATE, -1);
		} else if (month == 18) {
			cal.set(Calendar.MONTH, 8);
			cal.set(Calendar.DATE, 1);
			cal.roll(Calendar.DATE, -1);
		} else {
			cal.set(Calendar.MONTH, (month - 1));
			cal.set(Calendar.DATE, 1);
			cal.roll(Calendar.DATE, -1);
		}
		return cal.getTime();
	}

	/**
	 * 得到当月前几个月的列表 yyyy-mm-01
	 * 
	 * @return
	 */
	public static List<String> getBeforeMonth(int number) {
		List<String> list = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		for (int i = 0; i < number; i++) {
			String yearMonth = new String();

			yearMonth = year + "-" + (month + 1);
			list.add(yearMonth);
			if (month == 0) {
				year--;
				month = 11;
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
			} else {
				month--;
				cal.set(Calendar.MONTH, month);
			}
		}
		return list;
	}

	/**
	 * 查出某月的最后一天
	 * 
	 * @param date
	 *            Date
	 * @return lastDayofMonthStr
	 */
	public static String getLastDayofMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		long lastTime = cal.getTimeInMillis() - (1000 * 3600 * 24);
		cal.setTimeInMillis(lastTime);
		return format(cal.getTime(), DATE_PATTERN_DEFAULT);
	}

	/**
	 * 查出上月的最后一天
	 * 
	 * @param date
	 *            Date
	 * @return lastDayofMonthStr
	 */
	public static String getLastDayofMonthBeforeDate(Date date) {
		if (date == null) {
			date = now();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		long lastTime = cal.getTimeInMillis() - (1000 * 3600 * 24);
		cal.setTimeInMillis(lastTime);
		return format(cal.getTime(), DATE_PATTERN_DEFAULT);
	}

	/**
	 * 返回当前日期
	 * 
	 * @return Date
	 * */
	public static Date getCurrentDate() {
		return parseDate(formatDate(now()));
	}

	/**
	 * 返回当前日期
	 * 
	 * @return Date
	 * */
	public static String getCurrentDateStr() {
		return format(getCurrentDate(), DATE_PATTERN_DEFAULT);
	}
	/**
	 * 返回当前日期
	 * 
	 * @return Date
	 * */
	public static String getCurrentDateStr2() {
		return format(getCurrentDate(), TIME_PATTERN_DEFAULT);
	}
	/**
	 * 生成字符日期类型，无分隔符
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static String parseDate(Date date) {
		String name = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Integer year = cal.get(Calendar.YEAR);
		Integer month = cal.get(Calendar.MONTH) + 1;
		Integer day = cal.get(Calendar.DATE);
		String syear = year.toString();
		String smonth;
		String sday;
		if (month.intValue() < 10)
			smonth = "0" + month.toString();
		else
			smonth = month.toString();

		if (day.intValue() < 10)
			sday = "0" + day.toString();
		else
			sday = day.toString();
		name = syear + smonth + sday;

		return name;
	}

	/**
	 * 根据指定日期月份加n且取下个月,得到个新日期
	 * 
	 * @param date
	 * @return date
	 */
	public static Date getDateForMonthAddNew(java.util.Date date, int month) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(java.util.Calendar.YEAR), c.get(java.util.Calendar.MONTH)
				+ month, c.get(java.util.Calendar.DATE));
		Date nextDate = c.getTime();
		return nextDate;
	}

	/**
	 * 得到某天当月第一天日期
	 * 
	 * @param dateOfMonth
	 * @return
	 */
	public static Date getFirstDateOfCurrentMonth(Date dateOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOfMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 得到某天当月某一天号
	 * 
	 * @param dateOfMonth
	 * @param theDay
	 *            到某天
	 * @return
	 */
	public static Date getTheDateOfCurrentMonth(Date dateOfMonth, int theDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOfMonth);
		cal.set(Calendar.DAY_OF_MONTH, theDay);

		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 得到某天下个月第一天日期
	 * 
	 * @param dateOfMonth
	 * @return
	 */
	public static Date getFirstDateOfNextMonth(Date dateOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOfMonth);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 得到某天下下个月第一天日期
	 * 
	 * @param dateOfMonth
	 * @return
	 */
	public static Date getFirstDateOfNextTwoMonth(Date dateOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOfMonth);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month + 2);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 取得某日m个月后得日子
	 * 
	 * @param beforeDate
	 * @param m
	 * @return afterDate
	 */
	public static Date getDateAfterCountMonth(Date beforeDate, int m) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beforeDate);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month + m);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 取得某日m个月后得日子(改进版)
	 * 
	 * @param beforeDate
	 * @param m
	 * @return afterDate
	 */
	public static Date getDateAfterCountMonthByCal(Date beforeDate, int m) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(beforeDate);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		// 判断是否为月末
		boolean isLastDay = isLastDayOfMonth(beforeDate);
		if (isLastDay) {
			// 是 返回m+1个月初
			cal.set(Calendar.MONTH, month + m + 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
		} else {
			// 否
			cal.set(Calendar.MONTH, month + m);
			Date newDate = parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));

			Calendar newCal = Calendar.getInstance();
			newCal.setTime(newDate);
			int newDay = newCal.get(Calendar.DAY_OF_MONTH);
			// 判断 m 个后,相同号是否存在，如1月30日之后的1个月没有30日
			if (newDay == day) {
				// 如果存在，直接返回
				return newDate;
			} else {
				// 如果不存在，说明已经被推到下个月了（3月2日），直接该月初,如3月1日
				return getFirstDateOfCurrentMonth(newDate);
			}
		}

	}

	/**
	 * 取得某日m个月前得日子
	 * 
	 * @param beforeDate
	 * @param m
	 * @return afterDate
	 */
	public static Date getDateBeforeCountMonth(Date beforeDate, int m) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beforeDate);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month - m);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 取得某年第1天时期
	 * 
	 * @param
	 * @param
	 * @return startDate
	 */
	public static String getDateStratYearMonthDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now());
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return format(cal.getTime(), DATE_PATTERN_DEFAULT);
	}

	/**
	 * 取得某年下一年第1天时期
	 * 
	 * @param
	 * @param
	 * @return endDate
	 */
	public static String getDateNextYearMonthDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now());
		int year = cal.get(Calendar.YEAR);
		cal.set(Calendar.YEAR, year + 1);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return format(cal.getTime(), DATE_PATTERN_DEFAULT);
	}

	/**
	 * 根据计算日期，取得下个季度的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfNextSeason(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int returnMonth = 0;
		int returnYear = year;
		switch (month) {
		case 0:
			returnMonth = 3;
			break;
		case 1:
			returnMonth = 3;
			break;
		case 2:
			returnMonth = 3;
			break;
		case 3:
			returnMonth = 6;
			break;
		case 4:
			returnMonth = 6;
			break;
		case 5:
			returnMonth = 6;
			break;
		case 6:
			returnMonth = 9;
			break;
		case 7:
			returnMonth = 9;
			break;
		case 8:
			returnMonth = 9;
			break;
		case 9:
			returnMonth = 0;
			returnYear = year + 1;
			break;
		case 10:
			returnMonth = 0;
			returnYear = year + 1;
			break;
		case 11:
			returnMonth = 0;
			returnYear = year + 1;
			break;
		}
		cal.set(Calendar.MONTH, returnMonth);
		cal.set(Calendar.YEAR, returnYear);
		cal.set(Calendar.DATE, 1);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 得到某天的上一年的同天日期
	 * 
	 * @param dateOfMonth
	 * @return
	 */
	public static Date getDateOfPreviousYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		cal.set(Calendar.YEAR, year - 1);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 得到某天的年初日期
	 * 
	 * @param dateOfMonth
	 * @return
	 */
	public static Date getFirstYearDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 取得上个月的同天日期
	 */
	public static Date getDateOfPreviousMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	/**
	 * 判断给定日期是否为月末的一天
	 * 
	 * @param date
	 * @return true:是|false:不是
	 */
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否月初
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * yyyy-MM-dd HH-mm-ss
	 * 
	 * @return
	 */

	/**
	 * 取得某日m个月后月初的日子
	 * 
	 * @param beforeDate
	 * @param m
	 * @return afterDate
	 */
	public static Date getBeginDateAfterCountMonth(Date beforeDate, int m) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beforeDate);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month + m);
		cal.set(Calendar.DATE, 1);
		return parseDate(format(cal.getTime(), DATE_PATTERN_DEFAULT));
	}

	public static String getDateMinute() {
		return nowString(TIME_PATTERN);
	}

	/**
	 * 取得当前日期的下季度第一天
	 * 
	 * @param args
	 */
	public static Date getNextQuarterFirst(Date nowDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		int month = cal.get(Calendar.MONTH);
		int times = 3 - month % 3;
		return getDateAfterCountMonth(nowDate, times);

	}

	/**
	 * @Description TODO(获取当天剩余分钟数)
	 * @Author lvhaosir 2019/2/14 19:19
	 * @Param
	 * @return
	 */
	public static long getDayRemaining(String claimGoodTime) {
		// 传入的时间为 YYYY-MM-dd 需改变为 yyyyMMdd
		claimGoodTime = claimGoodTime.replaceAll("-","");
		// 使用预定的 日期+ 235959  减去 当前时间
		String newYmd = claimGoodTime + "235959";
		String ymdhms = DateUtil.formatTimeTargetStr(new Date());
		System.out.println(ymdhms);
		System.out.println(" 净菜自提预定时间 >>> "+newYmd);
		System.out.println(" 净菜自提当前时间 >>> "+ymdhms);
		// 预定时间
		Date newYmdData = DateUtil.parse(newYmd, "yyyyMMddHHmmss");
		Date ymdhmsData = DateUtil.parse(ymdhms, "yyyyMMddHHmmss");
		long diff = newYmdData.getTime() - ymdhmsData.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
		long time = hours * 60 + minutes;
		System.out.println(time);
		return time;
	}

	/**
	 * @Description TODO(获取传入时间与当前时间相差的小时数)
	 * @Author lvhaosir 2019/4/2 17:43
	 * @param startTime
	 */
	public static long getDayRemainHours(Timestamp startTime) {

		// System.out.println(" 传入的时间为 >>> "+startTime);
		Timestamp nowTimestamp = DateUtil.nowTimestamp();
		// System.out.println(" 当前时间为 >>> "+nowTimestamp);
		// 预定时间

		long diff = startTime.getTime() - nowTimestamp.getTime();
		if (diff > 0) {
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			return hours;
		}
		// long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		// System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
		// long time = hours * 60 + minutes;
		// System.out.println(time);
		return -1;
	}

	/*public static void main(String[] args) {

		String offWorkDate = "2019-03-15-00:00";
		System.out.println(offWorkDate.substring(0,10));




	}*/

	public static List<String> getResultTableName(Date date1, Date date2) {
		List<String> list = new ArrayList<String>();
		int daysBetween = daysBetween(date1, date2);
		if (daysBetween < 0) {
			return list;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String result = "";
		//判断两个日期是否为同月,如果是直接返回当前月。
		boolean b = booleanBetweenDate(date1, date2);
		if (b) {
			if (month < 10) {
				result = "" + year + 0 + month;
			} else {
				result = "" + year + month;
			}
			list.add(result);
			return list;
		}

		int MonthsBetween = MonthsBetween(date1, date2);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		for (int i = 0; i <= MonthsBetween; i++) {
			if (month <= 12) {
				if ((month + i) < 10) {
					result = "" + year + 0 + (month + i);
				} else {
					result = "" + year + (month + i);
				}
			} else {
				if ((month + i - 12) < 10) {
					result = "" + (year + 1) + (month + i - 12);
				} else {
					result = "" + (year + 1) + (month + i - 12);
				}
			}
			list.add(result);
		}
		return list;
	}
	
	/**
	 * 得到当天的后二天的日期
	 * 
	 * @param day 
	 * @return
	 */
	public static String getCurrentDateAfterDay(int day) {
		Calendar cal = Calendar.getInstance();
		long lastTime = cal.getTimeInMillis() + (1000 * 3600 * 24 * day);
		cal.setTimeInMillis(lastTime);
		return format(cal.getTime(), DATE_PATTERN_DEFAULT);
	}
	
	/**
	 * 获取当前系统时间的星期数。
	 * <p>
	 * 结果显示格式根据系统语言环境确定，如zh_CN时显示为星期一
	 * </p>
	 * 
	 * @return
	 */
	public static String getLocaleDayOfWeek() {
		Locale usersLocale = Locale.getDefault();
		DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
		String weekdays[] = dfs.getWeekdays();
		return weekdays[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)];
	}
	
	/**
	 * 获取友好时间
	 * @param datetime 格式：2012-8-21 17:53:20
	 * @return
	 */
	public static String getFriendTime(Date datetime) { // 传入的时间格式必须类似于2012-8-21 17:53:20这样的格式

		if(datetime == null){
			return "";
		}
		String interval = null;

		Date d1 = datetime;

		// 用现在距离1970年的时间间隔new
		// Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
		long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒

		if (time / 1000 < 10 && time / 1000 >= 0) {
			// 如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
			interval = "刚刚";
			return interval;
		} else if (time / 1000 < 60 && time / 1000 > 0) {
			// 如果时间间隔小于60秒则显示多少秒前
			int se = (int) ((time % 60000) / 1000);
			interval = se + "秒前";
			return interval;
		} else if (time / 60000 < 60 && time / 60000 > 0) {
			// 如果时间间隔小于60分钟则显示多少分钟前
			int m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟
			interval = m + "分钟前";
			return interval;
		} else if (time / 3600000 < 24 && time / 3600000 >= 0) {
			// 如果时间间隔小于24小时则显示多少小时前
			int h = (int) (time / 3600000);// 得出的时间间隔的单位是小时
			interval = h + "小时前";
			return interval;
		} else if (time / 3600000 >= 24 && time / 3600000 < 48) {
			// 如果时间间隔大于24个小时，小于48个小时
			interval = "昨天";
			return interval;
		} else if (time / 3600000 >= 48 && time / 3600000 < 72) {
			// 如果时间间隔大于48个小时，小于72个小时
			interval = "前天";
			return interval;
		} else {
			// 大于24小时，则显示正常的时间，但是不显示秒
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			interval = sdf.format(datetime);
			return interval;
		}
		
	}
	
	public static String strToDateFormat(String date,String strPattern,String toPattern) throws ParseException {
       SimpleDateFormat formatter = new SimpleDateFormat(strPattern);
       Date newDate= formatter.parse(date);
       formatter = new SimpleDateFormat(toPattern);
       return formatter.format(newDate);
   }
	
	/**
	 * 计算两个日期之间相差的分钟数 (向上取整的整数),请用 后面参数-前面参数
	 * 
	 * @param befDate
	 * @param aftDate
	 * @return min
	 */
	public static int minsBetween(Date befDate, Date aftDate) {
		long befTime = befDate.getTime();
		long aftTime = aftDate.getTime();
		int mins = (int)Math.ceil((aftTime-befTime)/(60*1000.0));
		return mins;
	}
	//取得一个月的第一天
	public static String getFirstDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	/**
	 * 将Date类转换为XMLGregorianCalendar
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar dateToXmlDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
		}
		XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
		dateType.setYear(cal.get(Calendar.YEAR));
		//由于Calendar.MONTH取值范围为0~11,需要加1
		dateType.setMonth(cal.get(Calendar.MONTH)+1);
		dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
		dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
		dateType.setMinute(cal.get(Calendar.MINUTE));
		dateType.setSecond(cal.get(Calendar.SECOND));
		return dateType;
	}

	/**
	 * 将XMLGregorianCalendar转换为Date
	 * @param cal
	 * @return
	 */
	public static Timestamp xmlDate2Date(XMLGregorianCalendar cal){
		return new Timestamp(cal.toGregorianCalendar().getTime().getTime());
	}
	/**
	 * 获取包含任意时间的星期一和星期天的日期
	 * @param d
	 * @return
	 */
	public static Map<String, Object> getWeekday(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String monday = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		String sunday = sdf.format(cal.getTime());
		Map<String, Object> weekday=new HashMap<>();
		weekday.put("monday", monday);
		weekday.put("sunday", sunday);
		return weekday;
	}
	
	/**
	 * 某一月的天数
	 */
	public static int whichMonth(String date) {
		int days = 0;
		if (date.equals(
				(Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1))
						.toString())) {
			days = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("", Locale.ENGLISH);
			sdf.applyPattern("yyyy-MM");
			Calendar calendar = new GregorianCalendar();
			try {
				calendar.setTime(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		return days;
	}

	/**
	 * 查询所有时间、今天0、最近三天3、最近一周7、最近一月30
	 */
	public static String queryDate(Integer days) {
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Date date=new Date();
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.DAY_OF_MONTH, -days);
	    date = calendar.getTime();
	    System.out.println(sdf.format(date));
		return sdf.format(date);
	}
	
	/**
	 * 根据年月查询对应月的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysByYearMonth(int year, int month) {  
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
 
    }
	
	 /** 
     * 获取当月的 天数 
     * */  
 
   public static int getCurrentMonthDay() {  
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.DATE, 1); 
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
 
    }


	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	/**
	 * @Description TODO(获取下个月的时间，具体到 秒)
	 * @Author lvhaosir 2019/3/29 17:04
	 * @param startTime
	 */
	public static Timestamp getNextMonth(Timestamp startTime) {
		String end1 = DateUtil.format(startTime, DateUtil.TIME_PATTERN_HHMMSS);
		System.out.println(startTime);
		Date dateAfterCountMonthByCal = DateUtil.getDateAfterCountMonthByCal(startTime, 1);
		String start1 = DateUtil.format(dateAfterCountMonthByCal, DateUtil.DATE_PATTERN_DEFAULT);
		StringBuilder sb = new StringBuilder(start1);
		sb.append(" ");
		sb.append(end1);
		Timestamp endTime = DateUtil.parseTimestampDefault(sb.toString());
		System.out.println(endTime);
		return endTime;
	}

	/**
	 * 获取指定年月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DATE);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime()) + " 23:59:59";
	}

	public static Timestamp getLastDayOfMonth(Timestamp timestamp) {
		int year = timestamp.getYear()+1900;
		int month = timestamp.getMonth() + 1;
		String lastDayOfMonth = getLastDayOfMonth(year, month);
		System.out.println(lastDayOfMonth);
		return DateUtil.parseTimestampDefault(lastDayOfMonth);
	}


}