package cn.edu.xcu.manager.service.impl.yky;

import cn.edu.xcu.manager.mapper.SysUserRoleMapper;
import cn.edu.xcu.manager.service.yky.SysUserRoleService;
import cn.edu.xcu.yky.model.dto.system.AssginRoleDto;
import cn.edu.xcu.yky.model.entity.system.SysUserRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用户业务代码表现层
 * @author: Keith
 */


@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {


    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {

        // 1 根据用户ID删除用户之前分配角色数据 (逻辑删除)
//        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, assginRoleDto.getUserId()));

        sysUserRoleMapper.physicalDeleteByUserId(assginRoleDto.getUserId());

        // 2 重新分配数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();

        // 遍历得到每个角色ID
        List<SysUserRole> userRoles = new ArrayList<>(roleIdList.size());
        roleIdList.forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assginRoleDto.getUserId());
            sysUserRole.setRoleId(roleId);
            userRoles.add(sysUserRole);
        });
        saveBatch(userRoles);
    }

}
