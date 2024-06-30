package cn.edu.xcu.manager.service.yky;

import cn.edu.xcu.yky.model.dto.system.SysUserDto;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 用户持久层
 * @author: Keith
 */
public interface SysUserService extends IService<SysUser> {


    IPage<SysUser> findByPage(SysUserDto sysUserDto, Integer current, Integer limit);

    void updateSysUser(SysUser sysUser);

    void saveSysUser(SysUser sysUser);

    void deleteUserById(Long userId);
}
