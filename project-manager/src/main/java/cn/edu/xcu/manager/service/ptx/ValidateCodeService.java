package cn.edu.xcu.manager.service.ptx;

import cn.edu.xcu.yky.model.vo.system.ValidateCodeVo;

/**
 * @description: 处理登录验证码逻辑
 * @author: Keith
 */
public interface ValidateCodeService {
    ValidateCodeVo generateValidateCode();

}
