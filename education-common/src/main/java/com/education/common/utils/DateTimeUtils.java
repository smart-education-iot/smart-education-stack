package com.education.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期及时间工具，日期时间计算及日期时间格式化
 * @author jamesli
 * @version 1.0 
 * @date 2015/5/13 9:24
 */
public class DateTimeUtils {
    /**
     * Year
     */
    public static final int YEAR_UNIT = 0;
    /**
     * Month
     */
    public static final int MONTH_UNIT = 1;
    /**
     * Day
     */
    public static final int DAY_UNIT = 2;
    /**
     * Houre
     */
    public static final int HOURE_UNIT = 3;
    /**
     * Minute
     */
    public static final int MINUTE_UNIT = 4;
    /**
     * yyyy-MM-dd HH:mm:ss 格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm 格式
     */
    public static final String DEFAULT_DATE_TIME_HHmm_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH 格式
     */
    public static final String DEFAULT_DATE_TIME_HH_FORMAT_PATTERN = "yyyy-MM-dd HH";
    /**
     * yyyy-MM-dd 格式
     */
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    /**
     * HH:mm:ss 格式
     */
    public static final String DEFAULT_TIME_FORMAT_PATTERN = "HH:mm:ss";
    /**
     * HH:mm 格式
     */
    public static final String DEFAULT_TIME_HHmm_FORMAT_PATTERN = "HH:mm";
    /**
     * 年
     * <p>可以通过DateTime.now().get(DateTime.YEAR_FIELD)来获取当前时间的年</p>
     */
    public static final int YEAR_FIELD = java.util.Calendar.YEAR;
    /**
     * 月
     * <p>可以通过DateTime.now().get(DateTime.MONTH_FIELD)来获取当前时间的月</p>
     */
    public static final int MONTH_FIELD = java.util.Calendar.MONTH;
    /**
     * 日
     * <p>可以通过DateTime.now().get(DateTime.DAY_FIELD)来获取当前时间的日</p>
     */
    public static final int DAY_FIELD = java.util.Calendar.DATE;
    /**
     * 小时 <p>可以通过DateTime.now().get(DateTime.HOUR_FIELD)来获取当前时间的小时</p>
     */
    public static final int HOUR_FIELD = java.util.Calendar.HOUR_OF_DAY;
    /**
     * 分钟 <p>可以通过DateTime.now().get(DateTime.MINUTE_FIELD)来获取当前时间的分钟</p>
     */
    public static final int MINUTE_FIELD = java.util.Calendar.MINUTE;
    /**
     * 秒
     * <p>可以通过DateTime.now().get(DateTime.SECOND_FIELD)来获取当前时间的秒</p>
     */
    public static final int SECOND_FIELD = java.util.Calendar.SECOND;
    /**
     * 毫秒 <p>可以通过DateTime.now().get(DateTime.MILLISECOND_FIELD)来获取当前时间的毫秒</p>
     */
    public static final int MILLISECOND_FIELD = java.util.Calendar.MILLISECOND;
    private java.util.Calendar c;   //日历类

    /**
     * 获取一个DateTime,此DateTime尚未初始化,表示的时间是1970-1-1 00:00:00.000
     * <p>要获取当前系统时间,请用DateTime.now();</p>
     */
    public DateTimeUtils() {
        c = Calendar.getInstance();
        c.clear();
    }

    /**
     * 设置时间 <p>可以传入一个时间对象，将会被转换为DateTime类型</p>
     *
     * @param date 时间对象
     */
    public DateTimeUtils(java.util.Date date) {
        c = Calendar.getInstance();
        c.setTime(date);
    }

    /**
     * 设置时间 <p>可以传入一个日历对象，将会被转换为DateTime类型</p>
     *
     * @param calendar 日历对象
     */
    public DateTimeUtils(java.util.Calendar calendar) {
        this.c = calendar;
    }

    /**
     * 获取当前系统时间
     *
     * @return DateTime 当前系统时间
     */
    public static DateTimeUtils now() {
        Calendar calendar = Calendar.getInstance();
        return new DateTimeUtils(calendar);
    }

    /**
     * 用毫秒来设置时间, 时间的基数是1970-1-1 00:00:00.000; <p>比如,new DateTime(1000)
     * 则表示1970-1-1 00:00:01.000;<br> 用负数表示基础时间以前的时间</p>
     *
     * @param milliseconds 毫秒
     */
    public DateTimeUtils(long milliseconds) {
        c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
    }

    /**
     * 转换为Date类型
     *
     * @return Date时间
     */
    public Date toDate() {
        return c.getTime();
    }

    /**
     * 转换成 日历对象
     *
     * @return Calendar对象
     */
    public java.util.Calendar toCalendar() {
        return c;
    }

    /**
     * 转换成java.sql.Date(yyyy-MM-dd)日期
     *
     * @return java.sql.Date日期
     */
    public java.sql.Date toSqlDate() {
        return new java.sql.Date(c.getTimeInMillis());
    }

    /**
     * 转换为java.sql.Time(hh:mm:ss)时间
     *
     * @return java.sql.Time时间
     */
    public java.sql.Time toSqlTime() {
        return new java.sql.Time(c.getTimeInMillis());
    }

    /**
     * 转换为java.sql.Timestamp(时间戳)
     *
     * @return java.sql.Timestamp时间戳
     */
    public java.sql.Timestamp toSqlTimestamp() {
        return new java.sql.Timestamp(c.getTimeInMillis());
    }

    /**
     * 解析时间 <p>根据DateTime中的DEFAULT_TIME_FORMAT_PATTERN规则转换为hh:mm:ss或hh:mm格式</p>
     *
     * @param time 字符串格式时间
     * @return DateTime 日期时间对象
     */
    public static Date parseTime(String time) throws java.text.ParseException {
        try {
            return DateTimeUtils.parseDateTime(time, DEFAULT_TIME_FORMAT_PATTERN);
        } catch (Exception e) {
            return DateTimeUtils.parseDateTime(time, DEFAULT_TIME_HHmm_FORMAT_PATTERN);
        }
    }

    /**
     * 解析日期 <p>根据DateTime中的DEFAULT_DATE_FORMAT_PATTERN规则转换为yyyy-MM-dd格式</p>
     *
     * @param date 字符串格式日期
     * @return DateTime 日期时间类
     */
    public static Date parseDate(String date) throws java.text.ParseException {
        return DateTimeUtils.parseDateTime(date, DEFAULT_DATE_FORMAT_PATTERN);
    }

    /**
     * 解析日期时间 <p>根据DateTime中的DEFAULT_DATE_TIME_FORMAT_PATTERN规则转换为yyyy-MM-dd
     * HH:mm:ss格式</p>
     *
     * @param datetime 字符串格式日期时间
     * @return DateTime 日期时间对象
     */
    public static Date parseDateTime(String datetime) throws java.text.ParseException {
        Date result = null;
        //尝试按yyyy-MM-dd HH:mm:ss分析
        try {
            result = DateTimeUtils.parseDateTime(datetime, DEFAULT_DATE_TIME_FORMAT_PATTERN);
        } catch (Exception e) {
            //解析错误
            result = null;
        }

        //尝试按yyyy-MM-dd HH:mm分析
        if (null == result) {
            try {
                result = DateTimeUtils.parseDateTime(datetime, DEFAULT_DATE_TIME_HHmm_FORMAT_PATTERN);
            } catch (Exception e) {
                //解析错误
                result = null;
            }
        }

        //尝试按yyyy-MM-dd HH分析
        if (null == result) {
            try {
                result = DateTimeUtils.parseDateTime(datetime, DEFAULT_DATE_TIME_HH_FORMAT_PATTERN);
            } catch (Exception e) {
                //解析错误
                result = null;
            }
        }

        //尝试按yyyy-MM-dd分析
        if (null == result) {
            try {
                result = DateTimeUtils.parseDate(datetime);
            } catch (Exception e) {
                //解析错误
                result = null;
            }
        }

        //尝试按时间分析
        if (null == result) {
            result = DateTimeUtils.parseTime(datetime);
        }
        return result;
    }

    /**
     * 用指定的pattern分析字符串 <p>pattern的用法参见java.text.SimpleDateFormat</p>
     *
     * @param datetime 字符串格式日期时间
     * @param pattern 日期解析规则
     * @return DateTime 日期时间对象
     * @see java.text.SimpleDateFormat
     */
    public static Date parseDateTime(String datetime, String pattern) throws java.text.ParseException {
        SimpleDateFormat fmt = (SimpleDateFormat) DateFormat.getDateInstance();
        fmt.applyPattern(pattern);
        return fmt.parse(datetime);
    }

    /**
     * 转换为 DEFAULT_DATE_FORMAT_PATTERN (yyyy-MM-dd) 格式字符串
     *
     * @return yyyy-MM-dd格式字符串
     */
    public String toDateString() {
        return toDateTimeString(DEFAULT_DATE_FORMAT_PATTERN);
    }

    /**
     * 转换为 DEFAULT_TIME_FORMAT_PATTERN (HH:mm:ss) 格式字符串
     *
     * @return HH:mm:ss 格式字符串
     */
    public String toTimeString() {
        return toDateTimeString(DEFAULT_TIME_FORMAT_PATTERN);
    }

    /**
     * 转换为 DEFAULT_DATE_TIME_FORMAT_PATTERN (yyyy-MM-dd HH:mm:ss) 格式字符串
     *
     * @return yyyy-MM-dd HH:mm:ss 格式字符串
     */
    public String toDateTimeString() {
        return toDateTimeString(DEFAULT_DATE_TIME_FORMAT_PATTERN);
    }

    /**
     * 使用日期转换pattern <p>pattern的用法参见java.text.SimpleDateFormat</p>
     *
     * @param pattern 日期解析规则
     * @return 按规则转换后的日期时间字符串
     */
    public String toDateTimeString(String pattern) {
        SimpleDateFormat fmt = (SimpleDateFormat) DateFormat.getDateInstance();
        fmt.applyPattern(pattern);
        return fmt.format(c.getTime());
    }

    /**
     * 获取DateTime所表示时间的某个度量的值
     *
     * @param field int 取值为:<br> DateTime.YEAR_FIELD -- 返回年份<br>
     * DateTime.MONTH_FIELD -- 返回月份,一月份返回1,二月份返回2,依次类推<br> DateTime.DAY_FIELD --
     * 返回当前的天(本月中的)<br> DateTime.HOUR_FIELD -- 返回小时数(本天中的),24小时制<br>
     * DateTime.MINUTE_FIELD -- 返回分钟数(本小时中的)<br> DateTime.SECOND_FIELD --
     * 返回秒数(本分钟中的)<br> DateTime.MILLISECOND_FIELD -- 返回毫秒数(本秒中的)
     * @return int field对应的值
     */
    public int get(int field) {
        //月份需要+1(月份从0开始)
        if (DateTimeUtils.MONTH_FIELD == field) {
            return c.get(field) + 1;
        } else {
            return c.get(field);
        }
    }

    /**
     * 返回自 1970-1-1 0:0:0 至此时间的毫秒数
     *
     * @return long 毫秒数
     */
    public long getTimeInMillis() {
        return c.getTimeInMillis();
    }

    /**
     * 设置field字段的值
     *
     * @param field int 取值为:<br> DateTime.YEAR_FIELD -- 年份<br>
     * DateTime.MONTH_FIELD -- 月份,一月份从1开始<br> DateTime.DAY_FIELD --
     * 当前的天(本月中的)<br> DateTime.HOUR_FIELD -- 小时数(本天中的),24小时制<br>
     * DateTime.MINUTE_FIELD -- 分钟数(本小时中的)<br> DateTime.SECOND_FIELD --
     * 秒数(本分钟中的)<br> DateTime.MILLISECOND_FIELD -- 毫秒数(本秒中的)
     * @param value
     */
    public void set(int field, int value) {
        //月份需要-1(月份从0开始)
        if (DateTimeUtils.MONTH_FIELD == field) {
            c.set(field, value - 1);
        } else {
            c.set(field, value);
        }
    }

    /**
     * 设置DateTime日期的年/月/日
     *
     * @param year 年
     * @param month 月
     * @param day 日
     */
    public void set(int year, int month, int day) {
        set(DateTimeUtils.YEAR_FIELD, year);
        set(DateTimeUtils.MONTH_FIELD, month);
        set(DateTimeUtils.DAY_FIELD, day);
    }

    /**
     * 设置DateTime日期的年/月/日/小时
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 小时
     */
    public void set(int year, int month, int day, int hour) {
        set(year, month, day);
        set(DateTimeUtils.HOUR_FIELD, hour);
    }

    /**
     * 设置DateTime日期的年/月/日/小时/分钟
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 小时
     * @param minute 分钟
     */
    public void set(int year, int month, int day, int hour, int minute) {
        set(year, month, day, hour);
        set(DateTimeUtils.MINUTE_FIELD, minute);
    }

    /**
     * 设置DateTime日期的年/月/日/小时/分钟/秒
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 小时
     * @param minute 分钟
     * @param second 秒
     */
    public void set(int year, int month, int day, int hour, int minute, int second) {
        set(year, month, day, hour, minute);
        set(SECOND_FIELD, second);
    }

    /**
     * 设置DateTime日期的年/月/日/小时/分钟/秒/毫秒
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 小时
     * @param minute 分钟
     * @param second 秒
     * @param milliSecond 毫秒
     */
    public void set(int year, int month, int day, int hour, int minute, int second, int milliSecond) {
        set(year, month, day, hour, minute, second);
        set(MILLISECOND_FIELD, milliSecond);
    }

    /**
     * 对field值进行相加 <p>add() 的功能非常强大，add 可以对 DateTime 的字段进行计算。<br>
     * 如果需要减去值，那么使用负数值就可以了，如 add(field, -value)。<br>
     * 或者调用DateTime.reduce(int,int)进行日期相减</p>
     *
     * @param field int 取值为:<br>   DateTime.YEAR_FIELD -- 年份<br>
     *   DateTime.MONTH_FIELD -- 月份,一月份从1开始<br>
     *   DateTime.DAY_FIELD -- 当前的天(本月中的)<br>
     *   DateTime.HOUR_FIELD -- 小时数(本天中的),24小时制<br>
     *   DateTime.MINUTE_FIELD -- 分钟数(本小时中的)<br>
     *   DateTime.SECOND_FIELD -- 秒数(本分钟中的)<br>
     *   DateTime.MILLISECOND_FIELD -- 毫秒数(本秒中的)
     * @param amount 数量(如果数量小于0则为相减)
     */
    public void add(int field, int amount) {
        c.add(field, amount);
    }

    /**
     * 对field值进行相减 <p>对add() 的功能进行封装，add 可以对 Calendar 的字段进行计算。<br>
     * 如果需要减去值，那么使用负数值就可以了，如 add(field, -value)。<br>
     * 详细用法参见Calendar.add(int,int)</p>
     *
     * @param field int 取值为:<br>   DateTime.YEAR_FIELD -- 年份<br>
     *   DateTime.MONTH_FIELD -- 月份,一月份从1开始<br>
     *   DateTime.DAY_FIELD -- 当前的天(本月中的)<br>
     *   DateTime.HOUR_FIELD -- 小时数(本天中的),24小时制<br>
     *   DateTime.MINUTE_FIELD -- 分钟数(本小时中的)<br>
     *   DateTime.SECOND_FIELD -- 秒数(本分钟中的)<br>
     *   DateTime.MILLISECOND_FIELD -- 毫秒数(本秒中的)
     * @param amount 数量(如果数量小于0则为相加)
     */
    public void reduce(int field, int amount) {
        c.add(field, -amount);
    }

    /**
     * 判断此 DateTime 表示的时间是否在指定 Object 表示的时间之后，返回判断结果。 <p>此方法等效于：compareTo(when)
     * > 0<br> 当且仅当 when 是一个 DateTime 实例时才返回 true。否则该方法返回 false。
     *
     * @param when 要比较的 Object
     * @return 如果此 DateTime 的时间在 when 表示的时间之后，则返回 true；否则返回 false。
     */
    public boolean after(Object when) {
        if (when instanceof Date) {
            return c.after((Date) when);
        }
        return c.after(when);
    }

    /**
     * 判断此 DateTime 表示的时间是否在指定 Object 表示的时间之前，返回判断结果。 <p>此方法等效于：compareTo(when)
     * < 0<br> 当且仅当 when 是一个 DateTime 实例时才返回 true。否则该方法返回 false。</p>
     *
     * @param when 要比较的 Object
     * @return 如果此 Calendar 的时间在 when 表示的时间之前，则返回 true；否则返回 false。
     */
    public boolean before(Object when) {
        if (when instanceof Date) {
            return c.before((Date) when);
        }
        return c.before(when);
    }

    /**
     * 创建并返回此对象的一个副本
     *
     * @return 日期时间对象
     */
    @Override
    public Object clone() {
        return new DateTimeUtils((Calendar) c.clone());
    }

    /**
     * 返回该此日历的哈希码
     *
     * @return 此对象的哈希码值。
     * @see Object
     */
    @Override
    public int hashCode() {
        return c.hashCode();
    }



//    /**
//     * 日期后移
//     *
//     * @param date     当前日期
//     * @param after    后移数量
//     * @param timeUnit 单位
//     * @return 前移后的日期
//     */
//    public static Date getAfterDate(Date date, final int after, final int timeUnit) {
//        DateTimeUtils dateTime = new DateTimeUtils(date);
//        Date result;
//        switch (timeUnit) {
//            case YEAR_UNIT:
//                result = dateTime.plusYears(after).toDate();
//                break;
//            case MONTH_UNIT:
//                result = dateTime.plusMonths(after).toDate();
//                break;
//            case DAY_UNIT:
//                result = dateTime.plusDays(after).toDate();
//                break;
//            case HOURE_UNIT:
//                result = dateTime.plusHours(after).toDate();
//                break;
//            case MINUTE_UNIT:
//                result = dateTime.plusMinutes(after).toDate();
//                break;
//            default:
//                result = date;
//        }
//        return result;
//    }

    /**
     * 得到今天的日期。只关心天，时分秒全部清零。
     *
     * @return
     */
    public static Date getToday() {
        return formatDate(new Date());
    }

    /**
     * 根据月份取得日历的第一个星期天
     *
     * @param month 当前月份字符
     * @return
     */
    public static Date getMonthFristWeekSunday(String month) {
        Calendar c = Calendar.getInstance();
        c.setTime(parse(month, "yyyy-MM"));
        c.set(Calendar.DATE, 1);
        int dayNum = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DATE, 1 - dayNum);
        return c.getTime();
    }

    /**
     * 取得这个月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);

        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 根据月份取得日历的最后一个星期六
     *
     * @param month 当前月份字符
     * @return
     */
    public static Date getMonthLastWeekSaturday(String month) {
        Calendar c = Calendar.getInstance();
        Date lastDay = getLastDayOfMonth(parse(month, "yyyy-MM"));
        c.setTime(lastDay);
        int dayNum = c.get(Calendar.DAY_OF_WEEK);
        if (dayNum != 7) {
            c.add(Calendar.DATE, 7 - dayNum);
        }
        return c.getTime();
    }

    /**
     * 获取今年的第一天
     *
     * @return
     */
    public static Date getCurrentYearFirst() {
        Calendar today = Calendar.getInstance();
        int yearPlus = Calendar.getInstance().get(Calendar.YEAR);
        today.set(yearPlus, 1, 1);
        return new Date(today.getTimeInMillis());
    }

    /**
     * 获取今年的最后一天
     *
     * @return
     */
    public static Date getCurrentYearLast() {
        Calendar today = Calendar.getInstance();
        int yearPlus = Calendar.getInstance().get(Calendar.YEAR);
        today.set(yearPlus, 12, 31);
        return new Date(today.getTimeInMillis());
    }

    /**
     * 比较指定毫秒数和当前系统毫秒数之前的间隔，返回间隔秒数
     *
     * @param startMilliSecond 指定的毫秒数
     * @return 返回两个毫秒数之间的间隔秒数
     */
    public static long compareToSecond(long startMilliSecond) {
        if (startMilliSecond > 0) {
            // 系统当前毫秒数
            long currentMilli = System.currentTimeMillis();
            long startSecond = startMilliSecond / 1000;
            long endSecond = currentMilli / 1000;

            return endSecond - startSecond;
        }

        return -1;
    }

    /**
     * 获取问候语
     * @return
     */
    @SuppressWarnings("deprecation")
	public static String getGreetings() {
        Date curTime = new Date();
        int hour = curTime.getHours();
        String greetings = "你好";
        if (hour >= 0 && hour < 6) {
            greetings = "凌晨好";
        }
        else if (hour >= 6 && hour < 8) {
            greetings = "早晨好";
        }
        else if (hour >= 8 && hour < 11) {
            greetings = "上午好";
        }
        else if (hour >= 11 && hour < 14) {
            greetings = "中午好";
        }
        else if (hour >= 14 && hour < 18) {
            greetings = "下午好";
        }
        else if (hour >= 18 && hour < 22) {
            greetings = "晚上好";
        }
        else if (hour >= 22 && hour < 24) {
            greetings = "午夜好";
        }
        return greetings;
    }

    /**
     * 将日期时间清零
     *
     * @param date 日期
     * @return
     */
    public static Date formatDate(Date date) {
        return parse(format(date), "yyyy-MM-dd");
    }

    /**
     * 对给定的日期以"yyyy-MM-dd"格式化
     *
     * @param date 日期
     * @return
     */
    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
    
    /**
     * 格式化时间
     * @param date
     * @return
     */
    public static String formatTime(Date date){
    	if(date == null){
    		return "";
    	}
    	DateFormat df = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT_PATTERN);
        return df.format(date);
    }

    /**
     * 格式化时间
     * @param date
     * @return
     */
    public static String formatTime(Date date,String format){
    	if(date == null){
    		return "";
    	}
    	DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    
    /**
     * 对给定的日期以"yyyy年MM月dd日 hh:mm"格式化
     *
     * @param date 日期
     * @return
     */
    public static String formatChinese(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return df.format(date);
    }

    /**
     * 对给定的日期以模式串pattern格式化
     *
     * @param date 日期
     * @param pattern 格式化模式
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 对给定的日期字符串以"yyyy-MM-dd HH:mm"格式解析
     *
     * @param dateString
     * @return
     */
    public static Date parse(String dateString) {
        return parse(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 对给定的日期字符串以pattern格式解析
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static Date parse(String dateString, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = df.parse(dateString);
        }
        catch (Throwable t) {
            date = null;
        }
        return date;
    }

    /**
     * 获取当前时间的UTC时间值
     * @return
     */
    public static long getTimeOfUTC(){
        Calendar calendar=Calendar.getInstance();
        //时区偏移量
        int zoneOffset=calendar.get(Calendar.ZONE_OFFSET);
        //夏令时差
        int dstOffset=calendar.get(Calendar.DST_OFFSET);
        //减去偏移量之和
        calendar.add(Calendar.MILLISECOND,-(zoneOffset+dstOffset));
        //获取UTC时间
        long utcTime=calendar.getTimeInMillis();
        return utcTime;
    }

}