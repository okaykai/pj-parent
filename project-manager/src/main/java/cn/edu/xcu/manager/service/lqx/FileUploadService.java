package cn.edu.xcu.manager.service.lqx;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件上传表现层
 * @author: Keith
 */
public interface FileUploadService {
    String upload(MultipartFile file);
}
