package cn.edu.xcu.manager.controller.yky;

import cn.edu.xcu.manager.service.yky.SysUserService;
import cn.edu.xcu.yky.model.dto.system.SysUserDto;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 用户控制层
 * @author: Keith
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping(value = "/admin/acl/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * @param current 当前页
     * @param limit   每页显示条数
     * @Description: 用户列表
     * @Return cn.edu.xcu.project.model.vo.common.Result
     * @Version v1.0
     */
    @Operation(summary = "用户列表方法")
    @GetMapping("/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             SysUserDto sysUserDto) {
        Console.log("请求进来没？", sysUserDto);
        IPage<SysUser> pageInfo = sysUserService.findByPage(sysUserDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "用户修改")
    @PutMapping(value = "/update")
    public Result updateUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加用户")
    @PostMapping(value = "/save")
    public Result saveUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping(value = "/remove/{userId}")
    public Result deleteRole(@PathVariable("userId") Long userId) {
        sysUserService.deleteUserById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
