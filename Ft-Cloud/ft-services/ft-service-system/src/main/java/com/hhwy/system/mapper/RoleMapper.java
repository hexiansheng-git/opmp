package com.hhwy.system.mapper;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.core.domain.SysRoleMenu;
import com.hhwy.system.core.domain.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    List<SysRole> selectRoleList(@Param("role") SysRole sysRole);

    List<SysRoleMenu> selectRoleMenuList(@Param("tenantKey") String tenantKey, @Param("roleId")Long roleId);

    List<SysUserRole> selectRoleUserExit(@Param("userId") Long userId, @Param("roleId") long l, @Param("tenantKey") String tenantKey);
}