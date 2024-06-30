package cn.edu.xcu.manager.listener;

import cn.edu.xcu.manager.service.impl.lqx.CategoryServiceImpl;
import cn.edu.xcu.yky.model.entity.product.Category;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: EasyExcel监听器 : 该监听器不能交给Spring管理
 * @author: Keith
 */
public class ExcelListener<T> implements ReadListener<T> {


    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 所以不能注入怎么办呢，可以通过构造传递Mapper 为了能操作数据库
    private CategoryServiceImpl categoryServiceImpl;


    public ExcelListener(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    // 从第二行开始读取，把每行读取的内容放到T中
    @Override
    public void invoke(T data, AnalysisContext context) {

        // 把每行数据对象t放到cacheDataList集合中

        cachedDataList.add(data);

        // 达到批处理数量，存储一次数据库，防止数据几万条数据堆在内存中，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            // 调用方法添加到数据库
            saveData();
            // 清理list集合
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 保存数据
        saveData();

    }

    // 保存方法
    private void saveData() {

        List<Category> categoryList = new ArrayList<>();
        cachedDataList.forEach(c -> {
            Category category = new Category();

            BeanUtils.copyProperties(c, category);
            categoryList.add(category);
        });

        categoryServiceImpl.saveBatch(categoryList);
    }
}
