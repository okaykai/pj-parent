package cn.edu.xcu.zyk.common.constant;

/**
 * @description: 自定义常量类
 * @author: Keith
 */
public class AppConstants {


    // Redis中存放的验证码路径
    public static final String VALIDATE = "user:login:validate:";

    public static final String USER_LOGIN = "user:login:";

    public static final String OPTIONS = "OPTIONS";

    public static final String TOKEN = "token";

    // 避免实例化
    private AppConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
