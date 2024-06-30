package cn.edu.xcu.manager.mapper;

import cn.edu.xcu.yky.model.dto.product.CategoryBrandDto;
import cn.edu.xcu.yky.model.entity.product.Brand;
import cn.edu.xcu.yky.model.entity.product.CategoryBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 品牌分类Mapper
 * @author: Keith
 */

@Mapper
public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {


    IPage<CategoryBrand> findByPage(Page<CategoryBrand> pageObj, @Param("categoryBrandDto") CategoryBrandDto categoryBrandDto);

    List<Brand> findBrandByCategoryId(@Param("categoryId") Long categoryId);
}
