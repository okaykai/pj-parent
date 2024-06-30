package cn.edu.xcu.manager.service.impl.ptx;

import cn.edu.xcu.manager.mapper.SysRoleMenuMapper;
import cn.edu.xcu.manager.service.ptx.SysRoleMenuService;
import cn.edu.xcu.yky.model.dto.system.AssignMenuDto;
import cn.edu.xcu.yky.model.entity.system.SysRoleMenu;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @description: 角色业务代码表现层
 * @author: Keith
 */


@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {


    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public void doAssign(AssignMenuDto assignMenuDto) {

        // 删除角色之前分配过的菜单数据
        // 执行逻辑删除
//        SqlHelper.retBool(sysRoleMenuMapper.delete(
//                new LambdaQueryWrapper<SysRoleMenu>()
//                        .eq(SysRoleMenu::getRoleId, assignMenuDto.getRoleId())));

        // 执行物理删除
        sysRoleMenuMapper.physicalDeleteByRoleId(assignMenuDto.getRoleId());

        // 保存分配数据
        List<Map<String, Number>> menuIdInfo = assignMenuDto.getMenuIdList();
        if (ObjectUtil.isNotEmpty(menuIdInfo) && menuIdInfo.size() > 0) { // 角色分配了菜单
            List<SysRoleMenu> roleMenus = menuIdInfo.stream()
                    .map(info -> {
                        SysRoleMenu roleMenu = new SysRoleMenu();
                        roleMenu.setRoleId(assignMenuDto.getRoleId());
                        roleMenu.setMenuId(info.get("id").longValue());
                        roleMenu.setIsHalf(info.get("isHalf").intValue());
                        return roleMenu;
                    })
                    .collect(Collectors.toList());

            saveBatch(roleMenus); // 批量插入
        }

    }

    private <T> T executeSqlWithoutLogicDelete(SqlWithResult<SysRoleMenuMapper, T> sqlWithResult) {
        try (SqlSession sqlSession = this.sqlSessionBatch()) {
            SysRoleMenuMapper mapper = sqlSession.getMapper(SysRoleMenuMapper.class);
            return sqlWithResult.execute(mapper);
        }
    }

    @FunctionalInterface
    private interface SqlWithResult<M, T> {
        T execute(M mapper);
    }

}
