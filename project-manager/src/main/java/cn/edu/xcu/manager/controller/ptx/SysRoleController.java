package cn.edu.xcu.manager.controller.ptx;

import cn.edu.xcu.manager.service.ptx.SysRoleService;
import cn.edu.xcu.manager.service.yky.SysUserRoleService;
import cn.edu.xcu.yky.model.dto.system.AssginRoleDto;
import cn.edu.xcu.yky.model.entity.system.SysRole;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description: 角色控制器
 * @author: Keith
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping(value = "/admin/acl/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * @param current 当前页
     * @param limit   每页显示条数
     * @Description: 角色方法列表
     * @Return cn.edu.xcu.project.model.vo.common.Result
     * @Version v1.0
     */
    @Operation(summary = "角色方法列表")
    @GetMapping("/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestParam(required = false) String roleName) {
        IPage<SysRole> pageInfo = sysRoleService.findByPage(roleName, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色修改")
    @PutMapping(value = "/update")
    public Result updateRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加角色")
    @PostMapping(value = "/save")
    public Result saveRole(@RequestBody SysRole sysRole) {
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping(value = "/remove/{roleId}")
    public Result deleteRole(@PathVariable("roleId") Long roleId) {
        sysRoleService.deleteRoleById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "查询所有角色")
    @GetMapping("/toAssign/{userId}")
    public Result findAllRoles(@PathVariable Long userId) {
        Map<String, Object> map = sysRoleService.findAllRole(userId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        sysUserRoleService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
