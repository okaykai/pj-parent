package cn.edu.xcu.manager.service.impl.lqx;

import cn.edu.xcu.manager.mapper.CategoryBrandMapper;
import cn.edu.xcu.manager.service.lqx.CategoryBrandService;
import cn.edu.xcu.yky.model.dto.product.CategoryBrandDto;
import cn.edu.xcu.yky.model.entity.product.Brand;
import cn.edu.xcu.yky.model.entity.product.CategoryBrand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 分类品牌业务实现层
 * @author: Keith
 */

@Service
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public IPage<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        Page<CategoryBrand> pageObj = new Page<>(page, limit);
        return categoryBrandMapper.findByPage(pageObj, categoryBrandDto);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {

        List<Brand> list = categoryBrandMapper.findBrandByCategoryId(categoryId);


        return list;
    }
}
