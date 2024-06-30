package cn.edu.xcu.manager.controller.wcx;

import cn.edu.xcu.manager.service.ptx.SysMenuService;
import cn.edu.xcu.manager.service.ptx.ValidateCodeService;
import cn.edu.xcu.manager.service.wcx.IndexService;
import cn.edu.xcu.wyb.utils.AuthContextUtil;
import cn.edu.xcu.yky.model.dto.system.LoginDto;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import cn.edu.xcu.yky.model.vo.common.Result;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.edu.xcu.yky.model.vo.system.LoginVo;
import cn.edu.xcu.yky.model.vo.system.SysMenuVo;
import cn.edu.xcu.yky.model.vo.system.ValidateCodeVo;
import cn.hutool.core.lang.Console;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 主页用户登录
 * @author: Keith
 */

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/acl/index")
public class IndexController {


    @Autowired
    private IndexService indexService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private SysMenuService sysMenuService;


    @Operation(summary = "查询用户可以操作的菜单")
    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> list = sysMenuService.findMenuByUserId();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户退出")
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(name = "token") String token) {

        indexService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }


    @Operation(summary = "获取当前登录的用户信息")
    @GetMapping(value = "/info")
    public Result<SysUser> getUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Console.log("User is authenticated: {}", authentication.getName());
            // 返回用户信息
            return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);
        } else {
            Console.error("User is not authenticated");
            return Result.build("User not authenticated", ResultCodeEnum.SYSTEM_ERROR);
        }
    }

//    @Operation(summary = "获取当前登录的用户信息")
//    @GetMapping(value = "/info")
//    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
//        // 1.可以从请求头中取
////        String token = request.getHeader("token");
//
//        // 根据TOKEN查询Redis获取用户信息
//        SysUser sysUser = sysUserService.getUserInfo(token);
//        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
//    }


    @Operation(summary = "获取验证码")
    @GetMapping(value = "/captcha")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = indexService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

//
//    @Operation(summary = "获取登录用户信息")
//    @GetMapping(value = "info")
//    public Result<T> info() {
//
//    }
}
