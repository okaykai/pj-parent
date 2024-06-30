package cn.edu.xcu.manager.controller.lqx;

import cn.edu.xcu.manager.service.lqx.FileUploadService;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件上传接口
 * @author: Keith
 */

@Tag(name = "文件管理")
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {


    @Autowired
    private FileUploadService fileUploadService;


    @Operation(summary = "文件上传")
    // <input type="file" name="file"/> 名称要保持一致 可以使用@RequestParam("file")  指定名称
    @PostMapping(value = "/file/upload")
    public Result fileUpload(MultipartFile file) {
        // 2.调用Service 方法，返回minio路径
        String url = fileUploadService.upload(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }


}
