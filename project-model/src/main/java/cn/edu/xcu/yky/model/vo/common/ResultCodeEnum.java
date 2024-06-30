package cn.edu.xcu.yky.model.vo.common;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    LOGIN_ERROR(201, "用户名或者密码错误"),
    VALIDATE_CODE_ERROR(202, "验证码错误"),
    LOGIN_AUTH(208, "用户未登录"),
    USER_NAME_IS_EXISTS(209, "用户名已经存在"),
    SYSTEM_ERROR(9999, "您的网络有问题请稍后重试"),
    NODE_ERROR(217, "该节点下有子节点，不可以删除"),
    DATA_ERROR(204, "数据异常"),
    ACCOUNT_STOP(216, "账号已停用"),

    STOCK_LESS(219, "库存不足"),
    ROLE_EXIST(221, "角色已存在"),
    ROLE_ADD_ERROR(220, "角色添加失败！"),

    USER_EXIST(221, "用户已存在"),
    USER_ADD_ERROR(220, "用户添加失败！");


    private Integer code;      // 业务状态码
    private String message;    // 响应消息

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
