package cn.edu.xcu.zyk.common.exception;

import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import lombok.Data;


/**
 * @description: 自定义异常处理类
 * @author: Keith
 */

@Data
public class CustomException extends RuntimeException {

    private Integer code;          // 错误状态码
    private String message;        // 错误消息

    private ResultCodeEnum resultCodeEnum;     // 封装错误状态码和错误消息

    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public CustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}