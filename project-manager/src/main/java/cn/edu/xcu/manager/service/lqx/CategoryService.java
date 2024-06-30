package cn.edu.xcu.manager.service.lqx;

import cn.edu.xcu.yky.model.entity.product.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: 商品类别服务层
 * @author: Keith
 */


public interface CategoryService extends IService<Category> {


    List<Category> findCategoryList(Long id);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
