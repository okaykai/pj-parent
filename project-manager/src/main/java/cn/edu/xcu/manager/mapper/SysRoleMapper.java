package cn.edu.xcu.manager.mapper;

import cn.edu.xcu.yky.model.dto.system.SysRoleDto;
import cn.edu.xcu.yky.model.entity.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 角色Mapper
 * @author: Keith
 */

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void deleteById(Long roleId);

}
