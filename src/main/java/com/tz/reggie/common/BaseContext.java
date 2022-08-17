package com.tz.reggie.common;

/**
 * 基于ThreadLocal封装的工具类，用来保存和获取当前用户id
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     */
    public static void setThreadLocal(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getThreadLocal(){
        return threadLocal.get();
    }

}
