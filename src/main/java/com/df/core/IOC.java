package com.df.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MFine
 * @version 1.0
 * @date 2021/11/17 21:13
 **/
public class IOC {

    private static Map<String, Object> REQUEST_INSTANCE_MAP = new HashMap<>();
    private static Map<String, Method> REQUEST_METHOD_MAP = new HashMap<>();
    private static Map<String, Object> BEAN_MAP = new HashMap<>();

    public static Map<String, Object> getRequestInstanceMap() {
        return REQUEST_INSTANCE_MAP;
    }

    public static void setRequestInstanceMap(Map<String, Object> requestInstanceMap) {
        REQUEST_INSTANCE_MAP = requestInstanceMap;
    }

    public static Map<String, Method> getRequestMethodMap() {
        return REQUEST_METHOD_MAP;
    }

    public static void setRequestMethodMap(Map<String, Method> requestMethodMap) {
        REQUEST_METHOD_MAP = requestMethodMap;
    }

    public static Map<String, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static void setBeanMap(Map<String, Object> beanMap) {
        BEAN_MAP = beanMap;
    }
}
