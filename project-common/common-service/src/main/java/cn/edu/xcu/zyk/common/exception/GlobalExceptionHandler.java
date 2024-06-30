package cn.edu.xcu.zyk.common.exception;

import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 统一异常处理类
 * @author: Keith
 */
@ControllerAdvice // 对Controller进行统一的异常操作
public class GlobalExceptionHandler {

    // 全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    // 自定义异常处理
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(CustomException e) {

        return Result.build(null, e.getResultCodeEnum());
    }
}
