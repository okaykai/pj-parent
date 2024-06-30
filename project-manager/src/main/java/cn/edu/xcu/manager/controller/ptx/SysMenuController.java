package cn.edu.xcu.manager.controller.ptx;

import cn.edu.xcu.manager.service.ptx.SysMenuService;
import cn.edu.xcu.manager.service.ptx.SysRoleMenuService;
import cn.edu.xcu.yky.model.dto.system.AssignMenuDto;
import cn.edu.xcu.yky.model.entity.system.SysMenu;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 菜单控制层
 * @author: Keith
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping(value = "/admin/acl/permission")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    @Operation(summary = "菜单列表方法")
    @GetMapping()
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "菜单更新")
    @PutMapping("/update")
    public Result updateMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "菜单删除")
    @DeleteMapping("/remove/{menuId}")
    public Result removeMenu(@PathVariable("menuId") Long menuId) {
        sysMenuService.removeMenuById(menuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "菜单保存")
    @PostMapping("/save")
    public Result saveMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    // 查询所有菜单 和查询角色分配过的菜单ID
    @Operation(summary = "查询所有菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result findAllRoles(@PathVariable("roleId") Long roleId) {
        Map<String, Object> map = sysMenuService.findRoleMenuByRoleID(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "角色分配菜单")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignMenuDto assignMenuDto) {
        sysRoleMenuService.doAssign(assignMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
