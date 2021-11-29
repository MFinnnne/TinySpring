package com.df.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author MFine
 * @version 1.0
 * @date 2021/11/18 22:48
 **/
public class MatchAndPerform {

    static Object perform(String url) throws IllegalAccessException {
        final Object instance = IOC.getRequestInstanceMap().get(url);
        if (instance == null) {
            throw new IllegalAccessException("该url访问的资源不存在");
        }
        final Method method = IOC.getRequestMethodMap().get(url);
        if (method == null) {
            throw new IllegalAccessException("该url访问的资源不存在");
        }
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
