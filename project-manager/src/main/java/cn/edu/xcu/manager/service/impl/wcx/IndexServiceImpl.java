package cn.edu.xcu.manager.service.impl.wcx;

import cn.edu.xcu.manager.mapper.SysUserMapper;
import cn.edu.xcu.manager.service.wcx.IndexService;
import cn.edu.xcu.manager.utils.JwtUtil;
import cn.edu.xcu.yky.model.dto.system.LoginDto;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.edu.xcu.yky.model.vo.system.LoginVo;
import cn.edu.xcu.zyk.common.constant.AppConstants;
import cn.edu.xcu.zyk.common.exception.CustomException;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @description: 业务层实现类
 * @author: Keith
 */

@Service
public class IndexServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements IndexService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private JwtUtil jwtUtil;


    @Override
    public LoginVo login(LoginDto loginDto) {


        // 校验验证码是否正确
        String captcha = loginDto.getCaptcha();     // 用户输入的验证码
        String codeKey = loginDto.getCodeKey();     // redis中验证码的数据key

        // 从Redis中获取验证码
        String redisCode = redisTemplate.opsForValue().get(AppConstants.VALIDATE + codeKey);
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode, captcha)) {
            throw new CustomException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }

        // 验证通过删除redis中的验证码
        redisTemplate.delete(AppConstants.VALIDATE + codeKey);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword())
            );
            SysUser userInfo = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, loginDto.getUserName()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(userInfo);

            LoginVo loginVo = new LoginVo();
            loginVo.setToken(token);
            loginVo.setRefresh_token("");
            return loginVo;
        } catch (BadCredentialsException e) {
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
        }


//        // 根据用户名查询用户
//        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, loginDto.getUserName()));
//
//
////        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
//        if (sysUser == null) {
//            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
////            throw new RuntimeException("用户名或者密码错误");
//        }
//
//        // 验证密码是否正确
//        String inputPassword = loginDto.getPassword();
//        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
//        if (!md5InputPassword.equals(sysUser.getPassword())) {
////            throw new RuntimeException("用户名或者密码错误");
//            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
//        }
//
//        // 生成令牌，保存数据到Redis中
//        String token = UUID.randomUUID().toString().replace("-", "");
//        redisTemplate.opsForValue().set(AppConstants.USER_LOGIN + token, JSON.toJSONString(sysUser), 30, TimeUnit.MINUTES);
//
//        // 构建响应结果对象
//        LoginVo loginVo = new LoginVo();
//        loginVo.setToken(token);
//        loginVo.setRefresh_token("");
//
//        // 返回
//        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {

        String userJson = redisTemplate.opsForValue().get(AppConstants.USER_LOGIN + token);

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);


        return sysUser;
    }


    @Override
    public void logout(String token) {
        redisTemplate.delete(AppConstants.USER_LOGIN + token);
    }
}
