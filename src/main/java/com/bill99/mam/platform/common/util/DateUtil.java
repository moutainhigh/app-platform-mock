package com.bill99.mam.platform.common.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final String FORMAT_PATTEN_HAT = "yyyyMMddHHmmss";
	public static final String FORMAT_PATTEN_YMD = "yyyy-MM-dd";
//	public static final String FORMAT_PATTEN_YMDHDS = "yyyy-MM-dd HH:mm:ss";
//	public static final String DATE_REGULAR_YMDHDS = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})$";
//	
//	public static Date parse(String dateStr){
//		Assert.isTrue(isLegalFormat(dateStr), "illegal patten dateStr=" + dateStr);
//		return parse(dateStr, DATE_REGULAR_YMDHDS);
//	}
	public static Date parseHat(String dateStr){
		if (StringUtils.isBlank(dateStr)) return null;
		return parse(dateStr, FORMAT_PATTEN_HAT);
	}
	
	private static Date parse(String dateStr, String pattern){
//		Assert.isTrue(isLegalFormat(dateStr), "illegal patten dateStr=" + dateStr);
		SimpleDateFormat df = new SimpleDateFormat(pattern);//设置日期格式
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			//TODO::
		}
		return null;
	}
//	public static String format(Date date){
//		return format(date,FORMAT_PATTEN_YMDHDS);
//	}
    public static String formatHat(Date date){
    	if (date == null) return null;
        return format(date,FORMAT_PATTEN_HAT);
    }
    
	private static String format(Date date,String patten){
		Assert.hasText(patten, "illegal patten dateStr="+patten);
		Assert.isTrue(date != null, "date is null");
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(patten);
	} 
	
	/**
	 * 是否yyyy-MM-dd HH:mm:ss格式
	 * @param srcDate
	 * @return
	 */
//	public static boolean isLegalFormat(String srcDate) {
//		return StringUtils.isNotBlank(srcDate) && srcDate.matches(DATE_REGULAR_YMDHDS);
//	}
}