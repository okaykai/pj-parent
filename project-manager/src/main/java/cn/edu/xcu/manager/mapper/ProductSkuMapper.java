package cn.edu.xcu.manager.mapper;

import cn.edu.xcu.yky.model.entity.product.ProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 商品单位Mapper
 * @author: Keith
 */

@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    void save(ProductSku productSku);

    List<ProductSku> findProductSkuByProductId(Long id);

    void updateByProductId(ProductSku productSku);
}
