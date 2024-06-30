package cn.edu.xcu.manager.controller.hhb;

import cn.edu.xcu.manager.service.hhb.*;
import cn.edu.xcu.yky.model.dto.product.ProductDto;
import cn.edu.xcu.yky.model.dto.product.SpuInfoDto;
import cn.edu.xcu.yky.model.entity.base.ProductUnit;
import cn.edu.xcu.yky.model.entity.product.Product;
import cn.edu.xcu.yky.model.entity.product.ProductAttr;
import cn.edu.xcu.yky.model.entity.product.ProductSku;
import cn.edu.xcu.yky.model.entity.product.ProductSpec;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 商品信息控制器
 * @author: Keith
 */
@Tag(name = "商品管理")
@RestController
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductSpecService productSpecService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductUnitService productUnitService;

    @Autowired
    private ProductDetailsService productDetailsService;

    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private ProductAttrService productAttrService;


    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody String spuJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SpuInfoDto spuInfo = objectMapper.readValue(spuJson, SpuInfoDto.class);
        productService.saveSpuInfo(spuInfo);
        // 处理spuInfo对象
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation
    @GetMapping("/baseSaleAttrList")
    public Result getBaseSaleAttrList() {
        List<ProductAttr> list = productAttrService.list();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取SKU信息")
    @GetMapping("/getSkuInfo/{skuId}")
    public Result getSkuInfo(@PathVariable Long skuId) {
        ProductSku byId = productSkuService.getById(skuId);
        return Result.build(byId, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "上下架SKU")
    @GetMapping("/onSale/{skuId}")
    public Result onSkuSale(@PathVariable Long skuId) {
        productSkuService.updateSkuSale(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "上下架SKU")
    @GetMapping("/cancelSale/{skuId}")
    public Result cancelSkuSale(@PathVariable Long skuId) {
        productSkuService.updateOnSkuSkuSale(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据skuId删除SKU")
    @DeleteMapping("/deleteSku/{skuId}")
    public Result removeSkuById(@PathVariable Long skuId) {
        productSkuService.removeById(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品SKU列表")
    @GetMapping("/list/{page}/{limit}")
    public Result getSKUList(@PathVariable Integer page, @PathVariable Integer limit) {
        return Result.build(productSkuService.page(new Page<>(page, limit)), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品审核")
    @GetMapping("/update-audit-status/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id, @PathVariable Integer auditStatus) {
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品上下架")
    @GetMapping("/update-status/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        productService.updateStatus(id, status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "删除商品")
    @DeleteMapping("/remove/{id}")
    public Result removeByProductId(@PathVariable Long id) {
        productService.removeByProductId(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "保存修改的数据")
    @PutMapping("/update")
    public Result update(@RequestBody Product product) {
        productService.updateByProductId(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据商品Id查询商品信息")
    @GetMapping("/get-product/{id}")
    public Result getProductId(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加商品信息")
    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        productService.saveProductInfo(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品信息列表")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit,
                       @RequestParam(required = false) Long category3Id,
                       @RequestParam(required = false) Long brandId,
                       ProductDto productDto) {
        if (category3Id != null) {
            productDto.setCategory3Id(category3Id);
        }
        // 如果 brandId 不为 null，则设置到 productDto
        if (brandId != null) {
            productDto.setBrandId(brandId);
        }

        // 调用服务层方法获取分页数据
        IPage<Product> pageInfo = productService.proList(page, limit, productDto);

        // 返回结果
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询商品单位信息")
    @GetMapping("/unit/find-all")
    public Result findAllUnit() {
        return Result.build(productUnitService.list(new LambdaQueryWrapper<ProductUnit>()
                        .orderByDesc(ProductUnit::getId)),
                ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取全部商品规格信息")
    @GetMapping("/product-spec/find-all")
    public Result findAllSpec() {
        return Result.build(productSpecService.list(new LambdaQueryWrapper<ProductSpec>()
                        .orderByDesc(ProductSpec::getId)),
                ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "获取商品规格列表")
    @GetMapping("/product-spec/{page}/{limit}")
    public Result specList(@PathVariable Integer page,
                           @PathVariable Integer limit) {
        IPage<ProductSpec> pageInfo = productSpecService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品规格列表保存")
    @PostMapping("/product-spec/save")
    public Result save(@RequestBody ProductSpec productSpec) {
        Console.log("发来的数据是：", productSpec);
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品规格列表修改")
    @PutMapping("/product-spec/update")
    public Result update(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "商品规格列表删除")
    @DeleteMapping("/product-spec/remove/{id}")
    public Result remove(@PathVariable Long id) {
        productSpecService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
