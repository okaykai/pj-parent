package cn.edu.xcu.manager.service.impl.yky;

import cn.edu.xcu.manager.mapper.SysUserMapper;
import cn.edu.xcu.manager.service.yky.SysUserService;
import cn.edu.xcu.yky.model.dto.system.SysUserDto;
import cn.edu.xcu.yky.model.entity.system.SysUser;
import cn.edu.xcu.yky.model.vo.common.ResultCodeEnum;
import cn.edu.xcu.zyk.common.exception.CustomException;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @description: 用户业务代码表现层
 * @author: Keith
 */


@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public IPage<SysUser> findByPage(SysUserDto sysUserDto, Integer current, Integer limit) {

        Page<SysUser> page = new Page<>(current, limit);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        // 根据 sysUserDto 添加查询条件
        if (StringUtils.isNotBlank(sysUserDto.getKeyword())) {
            wrapper.like(SysUser::getUserName, sysUserDto.getKeyword())
                    .or().like(SysUser::getName, sysUserDto.getKeyword())
                    .or().like(SysUser::getPhone, sysUserDto.getKeyword());
        }

        if (StringUtils.isNotBlank(sysUserDto.getCreateTimeBegin())) {
            wrapper.ge(SysUser::getCreateTime, sysUserDto.getCreateTimeBegin());
        }
        if (StringUtils.isNotBlank(sysUserDto.getCreateTimeEnd())) {
            wrapper.le(SysUser::getCreateTime, sysUserDto.getCreateTimeEnd());
        }

        wrapper.orderByDesc(SysUser::getId);
        return sysUserMapper.selectPage(page, wrapper);
    }


    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUser.setUpdateTime(null);
        this.updateById(sysUser);
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new CustomException(ResultCodeEnum.USER_ADD_ERROR);
        }

        String userName = sysUser.getUserName();
        SysUser ifExist = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, userName));

        if (ObjectUtil.isNotEmpty(ifExist)) {
            throw new CustomException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        // 对用户的密码进行加密
        String md5Password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5Password);

        sysUserMapper.insert(sysUser);
    }

    @Override
    public void deleteUserById(Long userId) {
        sysUserMapper.deleteById(userId);
    }
}
