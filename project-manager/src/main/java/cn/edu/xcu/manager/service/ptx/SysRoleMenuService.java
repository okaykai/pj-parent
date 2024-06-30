package cn.edu.xcu.manager.service.ptx;

import cn.edu.xcu.yky.model.dto.system.AssignMenuDto;
import cn.edu.xcu.yky.model.entity.system.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 角色持久层
 * @author: Keith
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    void doAssign(AssignMenuDto assignMenuDto);

}
