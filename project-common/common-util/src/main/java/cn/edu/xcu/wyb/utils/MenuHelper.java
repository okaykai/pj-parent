package cn.edu.xcu.wyb.utils;

import cn.edu.xcu.yky.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 菜单工具类
 * @author: Keith
 */
public class MenuHelper {


    // 递归实现封装过程
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {

        // sysMenuList所有菜单集合
        // 创建一共List集合用于封装最终的数据
        List<SysMenu> trees = new ArrayList<>();

        // 遍历所有菜单
        for (SysMenu sysMenu : sysMenuList) {
            // 找到递归操作的入口
            // 条件： parent_id = 0
            if (sysMenu.getParentId().longValue() == 0) {
                // 根据第一层 ，找下一层数据，使用递归完成
                // 写方法实现找下一层的过程，方法里传两个参数，第一次参数是当前第一层菜单，第二个参数是所有菜单的集合。
                trees.add(findChildren(sysMenu, sysMenuList));
            }
        }
        return trees;
    }


    /**
     * @param sysMenu     当前第一层菜单
     * @param sysMenuList 所有菜单
     * @Description: 递归查找下一层数据
     * @Param
     * @Return cn.edu.xcu.project.model.entity.system.SysMenu
     * @Version v1.0
     */
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {

        // SysMenu 有属性 private List<SysMenu> children;封装下一层数据
        sysMenu.setChildren(new ArrayList<>());
        // sysMenu的id值和 sysMenuList中的ParentId相同
        for (SysMenu it : sysMenuList) {

            // 判断 id 和 parentId 是否相同
            if (sysMenu.getId().longValue() == it.getParentId().longValue()) {

                // it就是下一层数据，进行封装
                sysMenu.getChildren().add(findChildren(it, sysMenuList));
            }
        }

        return sysMenu;
    }
}
