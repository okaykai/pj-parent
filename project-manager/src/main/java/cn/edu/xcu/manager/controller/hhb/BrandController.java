package cn.edu.xcu.manager.controller.hhb;

import cn.edu.xcu.manager.service.hhb.BrandService;
import cn.edu.xcu.yky.model.entity.product.Brand;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 品牌管理控制器
 * @author: Keith
 */
@Tag(name = "品牌管理")
@RestController
@RequestMapping("/admin/baseTrademark")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "查询所有品牌")
    @GetMapping("/find-all")
    public Result findAll() {
        List<Brand> list = brandService.list(new LambdaQueryWrapper<Brand>().orderByDesc(Brand::getId));
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "品牌列表")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page,
                       @PathVariable("limit") Integer limit) {
        IPage<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌保存")
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌删除")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        brandService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌修改")
    @PutMapping("/update")
    public Result update(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
