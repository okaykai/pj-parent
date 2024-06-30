package cn.edu.xcu.manager.service.impl.wcx;

import cn.edu.xcu.manager.mapper.SysUserMapper;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用于从数据库加载用户
 * @author: Keith
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在: " + username);
        }

        if (sysUser.getStatus() == 0) {
            throw new DisabledException("用户账号已被禁用");
        }


        // 这里可以添加获取用户权限的逻辑
        List<GrantedAuthority> authorities = new ArrayList<>();

        return User.builder()
                .username(sysUser.getUserName())
                .password("{MD5}" + sysUser.getPassword()) // 添加 {MD5} 前缀
                .authorities(authorities)
                .build();
    }
}
