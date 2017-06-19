package dvt.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;



/**
 * 通用辅助类
 * 注：该类调整到commons中，后续不予更新
 * @author Lij
 * @see 请使用com.coamctech.bxloan.commons.utils.CommonHelper予以替代
 */
public final class CommonHelper {
	private final static String EMPTY = "";
	public final static String DF_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public final static String DF_DATE_SHORT_TIME = "yyyy-MM-dd HH:mm";
	public final static String DF_DATE_SUB = "yyyyMMdd";
	public final static String DF_DATE = "yyyy-MM-dd";
	public final static String DF_TIME = "HH:mm:ss";
	private static Map<String, DateFormat> DF_MAP;
	static {
		DF_MAP = new HashMap<String, DateFormat>();
		DF_MAP.put(DF_DATE, new SimpleDateFormat(DF_DATE));
		DF_MAP.put(DF_DATE_SHORT_TIME, new SimpleDateFormat(DF_DATE_SHORT_TIME));
		DF_MAP.put(DF_DATE_TIME, new SimpleDateFormat(DF_DATE_TIME));
	}

	// TODO String
	/**
	 * 判断字符串不能是null、""、"null"(忽略大小写)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		return StringUtils.isNotBlank(str) && !"null".equalsIgnoreCase(str);
	}

	/**
	 * Object转String
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStr(Object obj) {
		if (obj == null) {
			return null;
		}
		return String.valueOf(obj);
	}

	// TODO Long
	/**
	 * Object转Long
	 * 
	 * @param obj
	 * @return
	 */
	public static Long toLong(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).longValue();
		}
		if (obj instanceof String) {
			return Long.parseLong(String.valueOf(obj));
		}
		return (Long) obj;
	}

	/**
	 * Object转Int
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer toInt(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).intValue();
		}
		if (obj instanceof String) {
			return Integer.parseInt(String.valueOf(obj));
		}
		return (Integer) obj;
	}

	/**
	 * Object转Int
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer toInt(Object obj, int defaultVal) {
		Integer val = toInt(obj);
		return val != null ? val : 0;
	}

	/**
	 * Object转BigDecimal
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			String str = toStr(obj);
			if (StringUtils.isBlank(str)) {
				return null;
			}
			return new BigDecimal(toStr(obj));
		}
		if (obj instanceof Integer) {
			return new BigDecimal(toStr(obj));
		}
		return (BigDecimal) obj;
	}

	/**
	 * String转BigDecimal
	 * 
	 * @param str
	 * @return
	 */
	public static BigDecimal str2BigDecimal(String str) {
		if (str == null) {
			return BigDecimal.ZERO;
		}
		if (str instanceof String) {
			return new BigDecimal(toStr(str));
		}
		return null;
	}

	/**
	 * Long转Str
	 * 
	 * @param str
	 *            如果为null
	 * @return 如果为null则返回""
	 */
	public static String long2StrIfEmpty(Long val) {
		if (val == null) {
			return EMPTY;
		}
		return String.valueOf(val);
	}

	// TODO Date
	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}
	
	public static String getNowStr(String format) {
		if(StringUtils.isBlank(format)){
			format = DF_DATE;
		}
		return CommonHelper.date2Str(new Date(), format);
	}
	
	/**
	 * 获取指定格式DateFormat
	 * 
	 * @param format
	 * @return
	 */
	public static DateFormat getDateFormat(String format) {
		if (StringUtils.isBlank(format)) {
			return null;
		}
		DateFormat df = DF_MAP.get(format);
		if (df == null) {
			df = new SimpleDateFormat(format);
		}
		return df;
	}

	/**
	 * 格式化日期为指定格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date formatDate(final Date date, String format) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isBlank(format)) {
			return date;
		}
		Date temp_date = null;
		try {
			DateFormat df = getDateFormat(format);
			temp_date = df.parse(df.format(date));
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		return temp_date;
	}

	/**
	 * 将日期转换为指定格式的字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date == null || StringUtils.isBlank(format)) {
			return null;
		}
		DateFormat df = getDateFormat(format);
		return df.format(date);
	}

	/**
	 * 将日期字符串按照指定格式转换为date
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date str2Date(String dateStr, String format) {
		if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)) {
			return null;
		}
		try {
			DateFormat df = getDateFormat(format);
			return df.parse(dateStr);
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * Object转Boolean
	 * 
	 * @param obj
	 * @return
	 */
	public static Boolean toBoolean(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		return null;
	}
	
	public static Date toDate(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Date) {
			return (Date) obj;
		}
		if (obj instanceof Timestamp) {
			return new Date(((Timestamp) obj).getTime());
		}
		return null;
	}

	
	public static String getDaySub(String beginDateStr, String endDateStr, String tjzq){
		 long time=0;
		 long nd = 1000 * 24 * 60 * 60;
		 long nw = 7000 * 24 * 60 * 60;
		 String formatStr = "yyyyMMdd";
		 if(beginDateStr.contains("-")){
			 formatStr = "yyyy-MM-dd";
		 }
		 SimpleDateFormat format = new SimpleDateFormat(formatStr);
		 Date beginDate;
		 Date endDate;
		 String formerDateStr="";
		 try {
			  Calendar cal = Calendar.getInstance();
			  beginDate = format.parse(beginDateStr);
	          endDate= format.parse(endDateStr);    
	          cal.setTime(beginDate);
	          if("1".equals(tjzq)){//日
	        	  time=(beginDate.getTime()-(endDate.getTime()-beginDate.getTime()+nd));    
	          }else if("2".equals(tjzq)){//周
	      		  cal.add(Calendar.WEEK_OF_MONTH, -1);
	      		  time = cal.getTime().getTime();
	          }else if("3".equals(tjzq)){//月
	      		  cal.add(Calendar.MONTH, -1);
	      		  time = cal.getTime().getTime();
	          }else if("4".equals(tjzq)){//半年
	      		  cal.add(Calendar.MONTH, -6);
	      		  time = cal.getTime().getTime();
	          }else{
	        	  cal.add(Calendar.YEAR, -1);
	      		  time = cal.getTime().getTime();
	          }
	          format = new SimpleDateFormat(CommonHelper.DF_DATE);
	          formerDateStr = format.format(time);
		 } catch (Exception e) {
			 e.printStackTrace();
		}
		 return formerDateStr;
	}
	

	public static boolean isEmpty(Object object) {
		if(object==null){
			return true;
		}else if("".equals(object.toString())){
			return true;
		}else if("null".equals(object.toString())){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isNumeric(String str){
			return str.matches("^[-+]?(([1-9]+)|([0-9]+)([.]([0-9]+)))$");
	}

	public static boolean isSfdm(String str) {
		String sfdm = "10000000,20000000,30000000,40000000,50000000,00000000,";
		for(int i=0;i<32;i++){
			String _str = "000000";
			if(i<10){
				_str += "0"+i;
			}else{
				_str += "" +i;
			}
			sfdm+=_str+",";
		}
		sfdm = sfdm.substring(0,sfdm.length()-1);
		return sfdm.contains(str);
	}
	/**
	 * 天数加减
	 * 
	 * @param amount
	 *            加减的天数 整数
	 * **/
	public static String calculateDateOnDays(String dateStr, int amount) {
		Date date = CommonHelper.str2Date(dateStr, CommonHelper.DF_DATE);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DAY_OF_YEAR, amount);
		return CommonHelper.date2Str(rightNow.getTime(), CommonHelper.DF_DATE);
	}
	public static Date calculateDateOnDays(Date date, int amount) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DAY_OF_YEAR, amount);
		return rightNow.getTime();
	}
	public static int StringHasStrNumber(String str,String str2){
		if(StringUtils.isNotBlank(str)){
			String[] _str =  str.split(str2);
			return _str.length-1;
		}else{
			return 0;
		}
	}
}
