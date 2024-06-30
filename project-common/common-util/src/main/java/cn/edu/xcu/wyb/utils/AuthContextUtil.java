package cn.edu.xcu.wyb.utils;

import cn.edu.xcu.yky.model.entity.system.SysUser;

/**
 * @description: 验证登录工具类 用于操作ThreadLocal
 * @author: Keith
 */
public class AuthContextUtil {

    // 创建ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();


    // 添加数据
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // 获取数据
    public static SysUser get() {
        return threadLocal.get();
    }

    // 删除数据
    public static void remove() {
        threadLocal.remove();
    }
}
