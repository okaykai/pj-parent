package cn.edu.xcu.yky.model.entity.system;

import cn.edu.xcu.yky.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @description: 角色菜单中间表
 * @author: Keith
 */

@Data
public class SysRoleMenu extends BaseEntity {

    private Long roleId;       // 角色id
    private Long menuId;       // 菜单id
    private Integer isHalf;    // 全开半开值
}
