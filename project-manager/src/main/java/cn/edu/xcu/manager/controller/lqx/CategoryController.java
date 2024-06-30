package cn.edu.xcu.manager.controller.lqx;

import cn.edu.xcu.manager.service.lqx.CategoryBrandService;
import cn.edu.xcu.manager.service.lqx.CategoryService;
import cn.edu.xcu.yky.model.dto.product.CategoryBrandDto;
import cn.edu.xcu.yky.model.entity.product.Brand;
import cn.edu.xcu.yky.model.entity.product.Category;
import cn.edu.xcu.yky.model.entity.product.CategoryBrand;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: 商品分类控制器
 * @author: Keith
 */

@Tag(name = "品牌分类")
@RestController
@RequestMapping("/admin/product/category-brand")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryBrandService categoryBrandService;

    @Operation(summary = "根据分类id查询对应品牌数据")
    @GetMapping("/find-brand-category/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId) {
        List<Brand> list = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "分类品牌条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findByCategoryPage(@PathVariable Integer page, @PathVariable Integer limit, CategoryBrandDto categoryBrandDto) {
        IPage<CategoryBrand> pageInfo = categoryBrandService.findByPage(page, limit, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌分类的添加")
    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌分类修改")
    @PutMapping("/update")
    public Result update(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌分类删除")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable("id") Long id) {
        categoryBrandService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "excel导入")
    @PostMapping("/import-data")
    public Result importData(MultipartFile file) {
        // 获取上传文件
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "Excel导出")
    @GetMapping("/export-data")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }


    // 分类列表 每次查询一程数据
    @Operation(summary = "分类列表")
    @GetMapping("/find-category/{id}")
    public Result findCategoryList(@PathVariable Long id) {
        List<Category> list = categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
