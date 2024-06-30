package cn.edu.xcu.manager.service.hhb;

import cn.edu.xcu.yky.model.entity.product.Brand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 品牌管理表现层
 * @author: Keith
 */
public interface BrandService extends IService<Brand> {

    IPage<Brand> findByPage(Integer page, Integer limit);
}
