package cn.edu.xcu.manager.service.lqx;

import cn.edu.xcu.yky.model.dto.product.CategoryBrandDto;
import cn.edu.xcu.yky.model.entity.product.Brand;
import cn.edu.xcu.yky.model.entity.product.CategoryBrand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 品牌分类服务层
 * @author: Keith
 */
public interface CategoryBrandService extends IService<CategoryBrand> {


    IPage<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
