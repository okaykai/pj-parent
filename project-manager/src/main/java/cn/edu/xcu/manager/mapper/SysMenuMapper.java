package cn.edu.xcu.manager.mapper;

import cn.edu.xcu.yky.model.entity.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: Mapper接口
 * @author: Keith
 */

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenuByUserId(@Param("userId") Long userId);
}
