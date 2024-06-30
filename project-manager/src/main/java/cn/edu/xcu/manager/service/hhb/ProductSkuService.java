package cn.edu.xcu.manager.service.hhb;

import cn.edu.xcu.yky.model.entity.product.ProductSku;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 商品规格服务层
 * @author: Keith
 */
public interface ProductSkuService extends IService<ProductSku> {

    void updateSkuSale(Long skuId);

    void updateOnSkuSkuSale(Long skuId);
}
