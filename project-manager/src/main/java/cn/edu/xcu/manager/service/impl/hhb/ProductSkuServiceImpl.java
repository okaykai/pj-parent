package cn.edu.xcu.manager.service.impl.hhb;

import cn.edu.xcu.manager.mapper.ProductSkuMapper;
import cn.edu.xcu.manager.service.hhb.ProductSkuService;
import cn.edu.xcu.yky.model.entity.base.BaseEntity;
import cn.edu.xcu.yky.model.entity.product.ProductSku;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.edu.xcu.zyk.common.exception.CustomException;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 商品规格业务代码实现层
 * @author: Keith
 */

@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {


    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Override
    public void updateSkuSale(Long skuId) {
        ProductSku sku = getById(skuId);
        if (sku == null) {
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }

        LambdaUpdateWrapper<ProductSku> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(BaseEntity::getId, skuId)
                .eq(ProductSku::getIsDeleted, 0)
                .set(ProductSku::getStatus, 1);

        update(wrapper);
    }

    @Override
    public void updateOnSkuSkuSale(Long skuId) {
        LambdaUpdateWrapper<ProductSku> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(BaseEntity::getId, skuId)
                .eq(ProductSku::getIsDeleted, 0)
                .set(ProductSku::getStatus, -1);

        update(wrapper);
    }


}
