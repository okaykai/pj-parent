package cn.edu.xcu.manager.service.yky;

import cn.edu.xcu.yky.model.dto.system.AssginRoleDto;
import cn.edu.xcu.yky.model.entity.system.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 用户持久层
 * @author: Keith
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    void doAssign(AssginRoleDto assginRoleDto);

}
