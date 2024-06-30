package cn.edu.xcu.manager.service.hhb;

import cn.edu.xcu.yky.model.entity.product.ProductSpec;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 商品规格服务层
 * @author: Keith
 */
public interface ProductSpecService extends IService<ProductSpec> {
    IPage<ProductSpec> findByPage(Integer page, Integer limit);
}
