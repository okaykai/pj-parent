package cn.edu.xcu.manager.service.wcx;

import cn.edu.xcu.yky.model.dto.system.LoginDto;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import cn.edu.xcu.yky.model.vo.system.LoginVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 业务层代码
 * @author: Keith
 */

public interface IndexService extends IService<SysUser> {

    /**
     * 根据用户名查询用户数据
     *
     * @return
     */
    LoginVo login(LoginDto loginDto);


    SysUser getUserInfo(String token);

    void logout(String token);
}
