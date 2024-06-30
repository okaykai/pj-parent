package cn.edu.xcu.manager.service.impl.lqx;

import cn.edu.xcu.manager.listener.ExcelListener;
import cn.edu.xcu.manager.mapper.CategoryMapper;
import cn.edu.xcu.manager.service.lqx.CategoryService;
import cn.edu.xcu.yky.model.entity.product.Category;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.edu.xcu.yky.model.vo.product.CategoryExcelVo;
import cn.edu.xcu.zyk.common.exception.CustomException;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 商品类别业务实现层
 * @author: Keith
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> findCategoryList(Long id) {


        // 根据ID条件查询进行查询
        List<Category> list = list(new LambdaQueryWrapper<Category>().eq(Category::getParentId, id).orderByDesc(Category::getId));
        // 便利返回list集合，判断每个分类是否有下一层，如果有就设置hanChildren = true
        if (!CollectionUtil.isEmpty(list)) {
            list.forEach(category -> {
                // 判断是否有下层分类
                long count = count(new LambdaQueryWrapper<Category>()
                        .eq(Category::getParentId, category.getId()));
                if (count > 0) {
                    // 下一层分类
                    category.setHasChildren(true);
                } else {
                    category.setHasChildren(false);
                }
            });
        }

        return list;
    }


    // excel导出
    @Override
    public void exportData(HttpServletResponse response) {

        try {
            // 1.设置响应头信息和其他信息 为下载做准备
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");

            // 设置头信息，Content-disposition 表示文件以下载方式打开
            response.setHeader("Content-disposition", fileName + ".xlsx");

            // 2. 调用Mapper方法查询所有分类
            List<Category> list = list();
            List<CategoryExcelVo> categoryExcelVos = new ArrayList<>();
            list.forEach(category -> {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                // 将值复制到VO中
                BeanUtils.copyProperties(category, categoryExcelVo);
                categoryExcelVos.add(categoryExcelVo);
            });


            // 3. EasyExcel的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据")
                    .doWrite(categoryExcelVos);


        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResultCodeEnum.DATA_ERROR);
        }

    }

    @Override
    public void importData(MultipartFile file) {


        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(this);

        // 将Excel中的数据加入到数据库中
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
