package cn.edu.xcu.manager.config;


import cn.edu.xcu.manager.interceptor.LoginAuthInterceptor;
import cn.edu.xcu.manager.properties.UseProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 跨域异常处理配置类
 * @author: Keith
 */

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;

    @Resource
    private UseProperties useProperties;

    // 拦截器注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns(useProperties.getNoAuthUrls())
                .addPathPatterns("/**");
    }


    // 处理跨域异常
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")      // 添加路径规则
//                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
//                .allowedOriginPatterns("*")           // 允许请求来源的域规则
//                .allowedMethods("*")
//                .allowedHeaders("*");                // 允许所有的请求头
//    }
}
