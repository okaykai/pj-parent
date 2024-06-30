package cn.edu.xcu.manager.interceptor;

import cn.edu.xcu.manager.utils.JwtUtil;
import cn.edu.xcu.wyb.utils.AuthContextUtil;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.edu.xcu.zyk.common.constant.AppConstants;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 登录拦截器
 * @author: Keith
 */


@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private JwtUtil jwtUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        // 获取请求路径
        String uri = request.getRequestURI();
        // 如果请求路径是Swagger相关路径，则直接放行
        if (uri.startsWith("/swagger-resources") ||
                uri.startsWith("/swagger-ui.html") ||
                uri.startsWith("/v2/api-docs") ||
                uri.startsWith("/webjars") ||
                uri.startsWith("/doc.html") ||
                uri.startsWith("/v3/api-docs")) {
            return true;
        }

        // 1. 获取请求方式
        // 如果请求方式是options 遇见请求直接放行
        String method = request.getMethod();
        if (AppConstants.OPTIONS.equals(method)) {      // 如果是跨域预检请求，直接放行
            return true;
        }

        // 2从请求头获取token
        String token = request.getHeader(AppConstants.TOKEN);

        // 如果 Token为空，返回错误提示
        if (StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }

        SysUser sysUser = jwtUtil.parseToken(token);


        // 如果Token不为空，拿着token查询redis
//        String sysUserInfoJson = redisTemplate.opsForValue().get(AppConstants.USER_LOGIN + token);

        // 如果Redis查询不到数据，返回错误提示
//        if (StrUtil.isEmpty(sysUserInfoJson)) {
//            responseNoLoginInfo(response);
//            return false;
//        }

        // 6. 如果Redis查询到用户信息，把用户信息放到ThreadLocal中
//        SysUser sysUser = JSON.parseObject(sysUserInfoJson, SysUser.class);
        AuthContextUtil.set(sysUser);

        // 7.把Redis用户信息数据更新过期时间
//        redisTemplate.expire(AppConstants.USER_LOGIN + token, 7, TimeUnit.DAYS);

        // 8.放行
        return true;
    }


    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        // ThreadLocal 删除
        AuthContextUtil.remove(); // 移除threadLocal中的数据

    }


}
