package cn.edu.xcu.manager.service.impl.hhb;

import cn.edu.xcu.manager.mapper.ProductSpecMapper;
import cn.edu.xcu.manager.service.hhb.ProductSpecService;
import cn.edu.xcu.yky.model.entity.product.ProductSpec;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 商品规格业务代码实现层
 * @author: Keith
 */

@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec> implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;


    @Override
    public IPage<ProductSpec> findByPage(Integer page, Integer limit) {

        Page<ProductSpec> pageObj = new Page<>(page, limit);
        pageObj.orders().add(OrderItem.desc("id"));

        Page<ProductSpec> pageInfo = page(pageObj);
        return pageInfo;
    }
}
