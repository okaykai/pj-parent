package cn.edu.xcu.manager.service.hhb;

import cn.edu.xcu.yky.model.dto.product.ProductDto;
import cn.edu.xcu.yky.model.dto.product.SpuInfoDto;
import cn.edu.xcu.yky.model.entity.product.Product;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 商品规格服务层
 * @author: Keith
 */
public interface ProductService extends IService<Product> {
    IPage<Product> proList(Integer page, Integer limit, ProductDto productDto);

    void saveProductInfo(Product product);

    Product getProductById(Long id);

    void updateByProductId(Product product);

    void removeByProductId(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);

    void saveSpuInfo(SpuInfoDto spuInfo);

}
