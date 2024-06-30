package cn.edu.xcu.manager;

import cn.edu.xcu.manager.properties.MinioProperties;
import cn.edu.xcu.manager.properties.UseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 启动类
 * @author: Keith
 */


@SpringBootApplication
@ComponentScan(basePackages = {"cn.edu.xcu"}) // 扫描当前包及其子包中的内容
@EnableConfigurationProperties(value = {UseProperties.class, MinioProperties.class})
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class);
    }

}
