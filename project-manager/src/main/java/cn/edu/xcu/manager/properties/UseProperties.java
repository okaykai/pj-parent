package cn.edu.xcu.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @description: 配置文件的读取类
 * @author: Keith
 */


@Data
@ConfigurationProperties(prefix = "project.auth")
public class UseProperties {

    private List<String> noAuthUrls;
}
