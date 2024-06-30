package cn.edu.xcu.manager.mapper;

import cn.edu.xcu.yky.model.entity.system.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description: 角色菜单Mapper
 * @author: Keith
 */

@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {


    void physicalDeleteByRoleId(@Param("roleId") Long roleId);

}
