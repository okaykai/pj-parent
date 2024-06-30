package cn.edu.xcu.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: Minio配置文件读取类
 * @author: Keith
 */

@Data
@ConfigurationProperties(prefix = "project.minio")
public class MinioProperties {

    private String endpointUrl;
    private String accessKey;
    private String secreKey;
    private String bucketName;

}
