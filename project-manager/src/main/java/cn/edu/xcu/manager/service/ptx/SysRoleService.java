package cn.edu.xcu.manager.service.ptx;

import cn.edu.xcu.yky.model.entity.system.SysRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @description: 角色持久层
 * @author: Keith
 */
public interface SysRoleService extends IService<SysRole> {


    IPage<SysRole> findByPage(String roleName, Integer current, Integer limit);


    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteRoleById(Long roleId);

    Map<String, Object> findAllRole(Long userId);


}
