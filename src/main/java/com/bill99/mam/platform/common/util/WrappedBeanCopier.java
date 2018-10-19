package com.bill99.mam.platform.common.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cglib.beans.BeanCopier;

/**
 * add by:jerry_xu@99bill.com
 */
public class WrappedBeanCopier {
	
    private static final ConcurrentMap<String, BeanCopier> beanCopierCache = new ConcurrentHashMap<>();
    @SuppressWarnings("rawtypes")
	private static final ConcurrentMap<String,Constructor> constructorAccessCache = new ConcurrentHashMap<>();

    private static void copyProperties(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    @SuppressWarnings("rawtypes")
	private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier = null;
        if (!beanCopierCache.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            beanCopierCache.putIfAbsent(beanKey, copier);
        } else {
            copier = beanCopierCache.get(beanKey);
        }
        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null || targetClass == null){
            return null;
        }
        T t = null;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
        }
        copyProperties(source, t);
        return t;
    }

    public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        Constructor<T> constructorAccess = getConstructorAccess(targetClass);
        List<T> resultList = new ArrayList<>(sourceList.size());
        for (Object o : sourceList) {
            T t = null;
            try {
                t = constructorAccess.newInstance();
                copyProperties(o, t);
                resultList.add(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
	private static <T> Constructor<T> getConstructorAccess(Class<T> targetClass) {
        Constructor<T> constructorAccess = constructorAccessCache.get(targetClass.toString());
        if(constructorAccess != null) {
            return constructorAccess;
        }
        try {
            constructorAccess = targetClass.getConstructor();
            constructorAccess.newInstance();
            constructorAccessCache.putIfAbsent(targetClass.toString(),constructorAccess);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
        }
        return constructorAccess;	
    }

}
