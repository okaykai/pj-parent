package cn.edu.xcu.manager.service.impl.hhb;

import cn.edu.xcu.manager.mapper.ProductDetailsMapper;
import cn.edu.xcu.manager.mapper.ProductMapper;
import cn.edu.xcu.manager.mapper.ProductSkuMapper;
import cn.edu.xcu.manager.service.hhb.ProductAttrService;
import cn.edu.xcu.manager.service.hhb.ProductService;
import cn.edu.xcu.yky.model.dto.product.ProductDto;
import cn.edu.xcu.yky.model.dto.product.SpuInfoDto;
import cn.edu.xcu.yky.model.entity.product.Product;
import cn.edu.xcu.yky.model.entity.product.ProductAttr;
import cn.edu.xcu.yky.model.entity.product.ProductDetails;
import cn.edu.xcu.yky.model.entity.product.ProductSku;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 商品规格业务代码实现层
 * @author: Keith
 */

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductAttrService productAttrService;


    @Override
    public IPage<Product> proList(Integer page, Integer limit, ProductDto productDto) {
        return productMapper.findByPage(new Page<>(page, limit), productDto);
    }


    @Override
    public void saveProductInfo(Product product) {
        // 保存商品基本信息，Product表
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);

        // 2.获取商品SKU列表集合，保存SKU信息，product_sku表
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size(); i++) {
            ProductSku productSku = productSkuList.get(i);

            // 商品编号
            productSku.setSkuCode(product.getId() + "_" + i);
            // 商品ID
            productSku.setProductId(product.getId());
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0); // 设置销量为0
            productSku.setStatus(0);
            // 获取商品SKU列表集合，保存SKU信息，product_sku表
            productSkuMapper.save(productSku);

        }
        // 保存商品详情数据，product_details表
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }

    @Override
    public Product getProductById(Long id) {

        // 根据id查询商品基本信息 product
        Product product = productMapper.findProductById(id);

        // 2根据 id查询，商品sku信息 列表，product_sku
        List<ProductSku> productSku = productSkuMapper.findProductSkuByProductId(id);
        product.setProductSkuList(productSku);

        // 3.根据id删除商品详情数据 product_details
        ProductDetails productDetails = productDetailsMapper.findProductDetailsByProductId(id);
        product.setDetailsImageUrls(productDetails.getImageUrls());

        return product;
    }

    @Override
    public void updateByProductId(Product product) {
        // 修改product
        productMapper.updateByProductId(product);


        // 修改product_sku
        List<ProductSku> productSkuList = product.getProductSkuList();
        productSkuList.forEach(productSku -> {
            productSkuMapper.updateByProductId(productSku);
        });

        // 修改product_details
        ProductDetails productDetails = productDetailsMapper.findProductDetailsByProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.updateByProductId(productDetails);
    }

    @Override
    public void removeByProductId(Long id) {
        productMapper.deleteById(id);                   // 根据id删除商品基本数据
        productSkuMapper.deleteById(id);         // 根据商品id删除商品的sku数据
        productDetailsMapper.deleteById(id);     // 根据商品的id删除商品的详情数据
    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if (auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if (status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }

    @Override
    public void saveSpuInfo(SpuInfoDto spuInfo) {
        // 使用 record 来创建不可变的 Product 对象
        record ProductData(int status, int auditStatus, String name, Long brandId, String proDesc,
                           Long category3Id, Long category2Id, Long category1Id) {
        }

        var productData = new ProductData(0, 0, spuInfo.getName(), spuInfo.getBrandId(),
                spuInfo.getProDesc(), spuInfo.getCategory3Id(), spuInfo.getCategory2Id(), spuInfo.getCategory1Id());

        // 使用 var 关键字简化变量声明
        var product = new Product();
        BeanUtils.copyProperties(productData, product);
        productMapper.save(product);

        // 使用 Stream API 和方法引用简化处理
        spuInfo.getSpuSaleAttrList().forEach(spuSaleAttr -> {
            saveProductAttr(product.getId(), spuSaleAttr.getSaleAttrName(), spuSaleAttr.getSaleAttrValue());

            spuSaleAttr.getSpuSaleAttrValueList().forEach(spuSaleAttrValue ->
                    saveProductAttr(product.getId(), spuSaleAttr.getSaleAttrName(), spuSaleAttrValue.getSaleAttrValueName())
            );
        });

        // 使用 Text Blocks 简化 SQL 查询
        var imageUrls = spuInfo.getSpuImageList().stream()
                .map(SpuInfoDto.SpuImage::getImgUrl)
                .collect(Collectors.joining(","));

        var productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(imageUrls);
        productDetailsMapper.save(productDetails);
    }

    private void saveProductAttr(Long productId, String attrKey, String attrValue) {
        var existingAttr = productAttrService.getOne(new LambdaQueryWrapper<ProductAttr>()
                .eq(ProductAttr::getProductId, productId)
                .eq(ProductAttr::getAttrKey, attrKey)
                .eq(ProductAttr::getAttrValue, attrValue));

        if (existingAttr == null) {
            var productAttr = new ProductAttr();
            productAttr.setProductId(productId);
            productAttr.setAttrKey(attrKey);
            productAttr.setAttrValue(attrValue);
            productAttrService.save(productAttr);
        }

    }


}