package cn.edu.xcu.manager.mapper;

import cn.edu.xcu.yky.model.entity.product.ProductDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description: 商品详情Mapper
 * @author: Keith
 */


@Mapper
public interface ProductDetailsMapper extends BaseMapper<ProductDetails> {

    ProductDetails findProductDetailsByProductId(@Param("id") Long id);


    void save(ProductDetails productDetails);

    void updateByProductId(ProductDetails productDetails);
}
