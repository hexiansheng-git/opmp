package com.hhwy.system.mapper;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    List<SysRole> selectRoleList(@Param("role") SysRole sysRole);
}