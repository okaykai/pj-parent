package cn.edu.xcu.manager.mapper;


import cn.edu.xcu.yky.model.entity.system.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description: 用户角色关系Mapper
 * @author: Keith
 */

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    void physicalDeleteByUserId(@Param("userId") Long userId);
}
