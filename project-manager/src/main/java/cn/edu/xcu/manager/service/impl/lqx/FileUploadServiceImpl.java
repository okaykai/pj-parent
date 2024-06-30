package cn.edu.xcu.manager.service.impl.lqx;

import cn.edu.xcu.manager.properties.MinioProperties;
import cn.edu.xcu.manager.service.lqx.FileUploadService;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.edu.xcu.zyk.common.exception.CustomException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @description: 文件上传实现层
 * @author: Keith
 */


@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) {

        try {
            // 创建一个Minio的客户端对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                    .build();

            // 判断桶是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {       // 如果不存在，那么此时就创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {  // 如果存在打印信息
                System.out.println("Bucket 'project-bucket' already exists.");
            }

            // 获取上传的文件名称
            // 根据当前日期对上传文件进行分组 20240910
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            // 每个上传文件的名称是唯一的 uuid生成 01.jpg
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = dateDir + "/" + uuid + file.getOriginalFilename();

            // 以流的形式做文件上传操作
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build());

            // 获取文件在Minio路径
            // http://127.0.0.1:9000/project-buket/01.jpg
            String url = minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }

    }
}
