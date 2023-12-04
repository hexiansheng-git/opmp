package com.hhwy.system.service;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.core.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRoleService {

    List<SysRole> list(SysRole role);

    List<SysRoleMenu> selectRoleMenuList(String tenantKey,Long roleId);
}
