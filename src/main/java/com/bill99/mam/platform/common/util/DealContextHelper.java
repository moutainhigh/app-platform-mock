package com.bill99.mam.platform.common.util;
public class DealContextHelper {
	private static ThreadLocal<DealContext>  dealContextThreadLocal  = new ThreadLocal<DealContext>();
	/**
	 * 保存上下文对象
	 */
	public void addDealContext(DealContext dealContext) {
		dealContextThreadLocal.set(dealContext);
	}
	/**
	 * 获取上下文对象
	 */
	public static DealContext getDealContext() {
		DealContext txnContext = dealContextThreadLocal.get();
		if (txnContext == null) {
			txnContext = new DealContext();
			dealContextThreadLocal.set(txnContext);
		}
		return txnContext;
	}
	/**
	  * @Description:删除变量
	 *  在有线程复用的情况下，如果没有清理可能会拿到脏数据
	 */
	public static void removeThreadLoacal() {
		dealContextThreadLocal.remove();
	}

}
