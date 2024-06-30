package cn.edu.xcu.manager.service.ptx;

import cn.edu.xcu.yky.model.entity.system.SysMenu;
import cn.edu.xcu.yky.model.vo.system.SysMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @description: 菜单持久层
 * @author: Keith
 */
public interface SysMenuService extends IService<SysMenu> {


    List<SysMenu> findNodes();

    void removeMenuById(Long menuId);

    Map<String, Object> findRoleMenuByRoleID(Long roleId);


    List<SysMenuVo> findMenuByUserId();

    void saveMenu(SysMenu sysMenu);
}
