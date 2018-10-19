package com.bill99.mam.platform.common.util;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class DealContext implements Serializable {
	private static final long serialVersionUID = -302317798995403394L;
	/**
	 * 
	 */
//	private static String BALANCE_VERSION;

	/**
	 * 必须使用ConcurrentHashMap，因为在多线程的情况下，非线性安全的HashMap存入cache之后的反序列化会报错
	 */
	private Map<String, Object> variables = new ConcurrentHashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public <T> T getVarValue(String key, Class<T> clazz) {
		Object obj = variables.get(key);
		if (obj != null && clazz.isInstance(obj)) {
			return (T) obj;
		}
		return null;
	}

	/**
	  * @Description:设置上下文变量
	  * @param:
	 */
	public void setVariables(String key, Object object) {
		if (key != null && object != null) {
			getVariables().put(key, object);
		}
	}

	/**
	  * @Description:删除上下文中的变量
	  * @param:
	 */
	public void delVariables(String key) {
		getVariables().remove(key);
	}

	public Map<String, Object> getVariables() {
		return variables;
	}
	
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}
