package com.bill99.mam.platform.common.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;

import retrofit2.Call;
import retrofit2.Response;

import com.bill99.mam.platform.common.constant.PlatformConst;
import com.bill99.mam.platform.common.enumeration.message.RemoteMsgEnum;
import com.bill99.mam.platform.common.exception.ExceptionFactory;

public class CommonUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T getUnique(Collection<T> list){
		
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		
		Iterator<T> iterator = list.iterator(); 
		while(iterator.hasNext()){
			Object obj = iterator.next();
			if(obj != null){
				return (T)obj;
			}
		}
		return null;
	}

	public static <T> T getResponseBody(Call<T> call){

		if(call != null){
			try {
				Response<T> response = call.execute();
				return response.body();
			} catch (IOException e) {
				ExceptionFactory.throwException(RemoteMsgEnum.CALL_RESPONSE_ERROR);
			}
		}
		return null;
	}
	
	public static Long toLong(String src){
		return NumberUtils.createLong(src);
	}
	
	public static Integer toInteger(String src){
		return NumberUtils.createInteger(src);
	}
	
	public static Integer toInteger(Short src){
		if(src == null) return null;
		return Integer.valueOf(src);
	}
	
	public static Short toShort(String src){
		if(StringUtils.isBlank(src) || 
				!StringUtils.isNumeric(src)){
			return null;
		}
		return Short.valueOf(src);
	}
	
//	public static Date toDate(String srcDate){
//		if(StringUtils.isBlank(srcDate) || 
//				!DateUtil.isLegalFormat(srcDate)){
//			return null;
//		}
//		return DateUtil.parse(srcDate);
//	}
	
//	public static String toSting(Date srcDate){
//		if(srcDate == null){
//			return null;
//		}
//		return DateUtil.format(srcDate);
//	}
	
	public static String toString(Object src){
		if(src == null){
			return null;
		}
		return src.toString();
	}
	
	public static Boolean toBoolean(String src){
		return BooleanUtils.toBooleanObject(src);
	}

	public static long random(long origNumber){
		StringBuilder currentTime = new StringBuilder(DateTime.now().toString("yyyyMMddHH"));
		long origRandom = ThreadLocalRandom.current().nextLong(9999)+origNumber;
		currentTime.append(origRandom);
		String random = StringUtils.rightPad(currentTime.toString(),18,"0");
		return  Long.valueOf(random);
	}

	public static String getLocalIp(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}
	
	/**
     * 平台是否主收款方 0 否 ,1 是
     * @param payeeUId
     * @return
     */
    public static int isPlatformPayFee(Long payeeUId){
        return BooleanUtils.toInteger(isPlatform(payeeUId));
    }
    
    public static boolean isPlatform(Long payeeUId){
        if (payeeUId != null &&
                PlatformConst.PLATFORM_MERCHENT_CODE.equalsIgnoreCase(payeeUId.toString())){
            return true;
        }
        return false;
    }

}
