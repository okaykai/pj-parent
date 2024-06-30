package cn.edu.xcu.manager.mapper;

import cn.edu.xcu.yky.model.dto.product.ProductDto;
import cn.edu.xcu.yky.model.entity.product.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description: 商品管理Mapper
 * @author: Keith
 */


@Mapper
public interface ProductMapper extends BaseMapper<Product> {


    IPage<Product> findByPage(Page<Product> pageObj, @Param("productDto") ProductDto productDto);

    void save(Product product);

    Product findProductById(@Param("id") Long id);

    void updateByProductId(Product product);
}
