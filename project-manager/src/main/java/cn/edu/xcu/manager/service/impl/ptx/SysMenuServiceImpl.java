package cn.edu.xcu.manager.service.impl.ptx;

import cn.edu.xcu.manager.mapper.SysMenuMapper;
import cn.edu.xcu.manager.mapper.SysRoleMenuMapper;
import cn.edu.xcu.manager.service.ptx.SysMenuService;
import cn.edu.xcu.wyb.utils.AuthContextUtil;
import cn.edu.xcu.wyb.utils.MenuHelper;
import cn.edu.xcu.yky.model.entity.system.SysMenu;
import cn.edu.xcu.yky.model.entity.system.SysRoleMenu;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.edu.xcu.yky.model.vo.system.SysMenuVo;
import cn.edu.xcu.zyk.common.exception.CustomException;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 菜单业务代码表现层
 * @author: Keith
 */


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * @Description: 菜单列表
     * @Return java.util.List<cn.edu.xcu.project.model.entity.system.SysMenu>
     * @Version v1.0
     */
    @Override
    public List<SysMenu> findNodes() {

//        方法一:
//        // 1.先查询所有菜单，根据 sort_value 排序
//        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByAsc(SysMenu::getSortValue);
//        List<SysMenu> sysMenusList = sysMenuMapper.selectList(queryWrapper);
//
//
//        if (CollectionUtil.isEmpty(sysMenusList)) {
//            return null;
//        }
//
//        // 2.调用工具类中的方法，把 List 集合返回封装要求的数据格式
//        // 假设封装方法为 wrapMenuList(list)
//        List<SysMenu> treeMenus = MenuHelper.buildTree(sysMenusList);
//
//        return treeMenus;
        // 1. 查询所有菜单，根据 sort_value 排序
        List<SysMenu> sysMenusList = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSortValue)
        );

        // 2. 如果为空，返回 null
        if (sysMenusList.isEmpty()) {
            return null;
        }

        // 3. 构建菜单树 使用 Collectors.groupingBy 按 parentId 分组，构建 menuMap。
        Map<Long, List<SysMenu>> menuMap = sysMenusList.stream()
                .collect(Collectors.groupingBy(SysMenu::getParentId));


        return sysMenusList.stream()
                .filter(menu -> menu.getParentId() == 0) // 过滤出顶级菜单（parent_id 为 0）。
                .peek(menu -> setChildren(menu, menuMap)) // 过滤过程中设置每个菜单的子菜单。
                .collect(Collectors.toList()); // 将结果收集为 List。

    }

    @Override
    public void removeMenuById(Long menuId) {

        // 查询是否有子菜单
        Long count = sysMenuMapper.selectCount(
                new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, menuId));

        // 如果有子菜单不允许删除
        if (count > 0) {
            throw new CustomException(ResultCodeEnum.NODE_ERROR);
        }

        // count 等于0 直接删除
        sysMenuMapper.deleteById(menuId);
    }


    @Override
    public Map<String, Object> findRoleMenuByRoleID(Long roleId) {

        // 查询所有菜单
        List<SysMenu> sysMenuList = findNodes();

        Map<String, Object> map = new HashMap<>();

        // 查询角色分配过菜单ID列表
//        sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));

        List<Long> menuIds = sysRoleMenuMapper.selectObjs(new LambdaQueryWrapper<SysRoleMenu>()
                        .select(SysRoleMenu::getMenuId)
                        .eq(SysRoleMenu::getRoleId, roleId)
                        .eq(SysRoleMenu::getIsHalf, 0)
                ).stream()
                .map(o -> (Long) o)
                .collect(Collectors.toList());

        map.put("sysMenuList", sysMenuList);
        map.put("roleMenuIds", menuIds);

        return map;

    }

    @Override
    public List<SysMenuVo> findMenuByUserId() {

        // 获取当前登录的用户id
        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();

        // 根据userId查询可以操作的菜单
        List<SysMenu> sysMenuList = sysMenuMapper.selectMenuByUserId(userId);

        // 封装要求数据格式返回
        List<SysMenu> menuList = MenuHelper.buildTree(sysMenuList);

        List<SysMenuVo> sysMenuVos = this.buildMenus(menuList);

        return sysMenuVos;
    }

    @Override
    public void saveMenu(SysMenu sysMenu) {

        save(sysMenu);

        // 新添加菜单，将父菜单的IsHalf改为半开的状态 1
        updateSysRoleMenuIsHalf(sysMenu);

    }

    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {

        // 查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectOne(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getId, sysMenu.getParentId()));

        if (ObjectUtil.isNotEmpty(parentMenu)) {

            // 将该id的菜单设置为半开
            sysRoleMenuMapper.update(null, new LambdaUpdateWrapper<SysRoleMenu>()
                    .eq(SysRoleMenu::getMenuId, parentMenu.getId())
                    .set(SysRoleMenu::getIsHalf, 1));

            // 递归调用
            updateSysRoleMenuIsHalf(parentMenu);
        }

    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }

    private void setChildren(SysMenu menu, Map<Long, List<SysMenu>> menuMap) {
        List<SysMenu> children = menuMap.getOrDefault(menu.getId(), Collections.emptyList());
        children.forEach(child -> setChildren(child, menuMap));
        menu.setChildren(children);
    }

}
