package cn.edu.xcu.manager.service.impl.ptx;

import cn.edu.xcu.manager.mapper.SysRoleMapper;
import cn.edu.xcu.manager.mapper.SysUserRoleMapper;
import cn.edu.xcu.manager.service.ptx.SysRoleService;
import cn.edu.xcu.yky.model.entity.system.SysRole;
import cn.edu.xcu.yky.model.entity.system.SysUserRole;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 角色业务代码表现层
 * @author: Keith
 */


@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public IPage<SysRole> findByPage(String roleName, Integer current, Integer limit) {

        Page<SysRole> page = new Page<>(current, limit);
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        // 根据 sysRoleDto 添加查询条件
        if (StringUtils.isNotBlank(roleName)) {
            wrapper.like(SysRole::getRoleName, roleName);
        }

        wrapper.orderByDesc(SysRole::getId);
        Page<SysRole> sysRolePage = sysRoleMapper.selectPage(page, wrapper);
        return sysRolePage;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {

        if (ObjectUtil.isEmpty(sysRole)) {
            throw new CustomException(ResultCodeEnum.ROLE_ADD_ERROR);
        }

        String roleName = sysRole.getRoleName();
        SysRole ifExist = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleName, roleName));

        if (ObjectUtil.isNotEmpty(ifExist)) {
            throw new CustomException(ResultCodeEnum.ROLE_EXIST);
        }
        sysRoleMapper.insert(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {

        sysRole.setUpdateTime(null);
        this.updateById(sysRole);
    }

    @Override
    public void deleteRoleById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public Map<String, Object> findAllRole(Long userId) {

        // 1.查询所有的角色
        List<SysRole> roleList = this.list();

        Map<String, Object> map = new HashMap<>();


        // 根据用户查询用户分配过的角色ID列表
//        sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));


        List<Long> roleIds = sysUserRoleMapper.selectObjs(new LambdaQueryWrapper<SysUserRole>()
                        .select(SysUserRole::getRoleId)
                        .eq(SysUserRole::getUserId, userId)
                ).stream()
                .map(o -> (Long) o)
                .collect(Collectors.toList());

        map.put("aliRolesList", roleList);
        map.put("sysUserRole", roleIds);

        return map;
    }


}
