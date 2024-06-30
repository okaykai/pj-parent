package cn.edu.xcu.manager.service.impl.hhb;

import cn.edu.xcu.manager.mapper.BrandMapper;
import cn.edu.xcu.manager.service.hhb.BrandService;
import cn.edu.xcu.yky.model.entity.product.Brand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 品牌管理业务实现层
 * @author: Keith
 */

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public IPage<Brand> findByPage(Integer page, Integer limit) {
        Page<Brand> pageObj = new Page<>(page, limit);
        pageObj.orders().add(OrderItem.desc("id"));
        Page<Brand> pageInfo = page(pageObj);
        return pageInfo;
    }
}
