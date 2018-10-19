package com.bill99.mam.platform.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
/**
 * http(JSON)日志脱敏
 * @author jerry.xu.coc
 *
 */
public class JsonDesensitiveUtil {

	private static final ImmutableList<String> SENSITIVE_KEYS = ImmutableList.of("bankAcctId"
			, "mobile","idCardNumber"
			,"email","legalIdCardNumber"
			,"legalMobile","idContent");

	public static String sensitiveFilter(String jsonString) {
		if (StringUtils.isBlank(jsonString)) return StringUtils.EMPTY;
		JSONObject jsonObject = JSON.parseObject(jsonString);
		return desensitize(jsonObject).toJSONString();
	}

	/**
	 * 递归对象
	 * @param jsonObject
	 * @return
	 */
	private static JSONObject desensitize(JSONObject jsonObject) {
		String json = null;
		if (jsonObject != null && CollectionUtils.isNotEmpty(jsonObject.keySet())) {
			for (String key : jsonObject.keySet()) {
				json = jsonObject.getString(key);
				if (isObject(json)) {
					jsonObject.put(key, desensitize(JSON.parseObject(json)));
				} else if (isArray(json)) {
					jsonObject.put(key, desensitize(JSON.parseArray(json)));
				} else {
					// 敏感属性脱敏
					if (isSensitiveKey(key)) {
						jsonObject.put(key, PrivacyUtil.encryptSensitiveInfo(json));
					}
				}
			}
		}
		return jsonObject;
	}

	/**
	 * 递归数组
	 * @param jsonArray
	 * @return
	 */
	private static JSONArray desensitize(JSONArray jsonArray) {
		String value = null;
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				value = jsonArray.getString(i);
				if (isArray(value)) {
					jsonArray.set(i, desensitize(jsonArray.getJSONArray(i)));
				} else if (isObject(value)) {
					jsonArray.set(i, desensitize(JSON.parseObject(value)));
				}
			}
		}
		
		return jsonArray;
	}
	/**
	 * 判断是否是对象
	 * @param str
	 * @return
	 */
	private static boolean isObject(String str) {
		try {
			JSON.parseObject(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断是否是数组
	 * @param str
	 * @return
	 */
	private static boolean isArray(String str) {
		try {
			JSON.parseArray(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 是否是敏感key
	 * @param key
	 * @return
	 */
	public static boolean isSensitiveKey(String key) {
		return StringUtils.isNotBlank(key) && SENSITIVE_KEYS.contains(key);
	}
}
