package com.hhwy.system.service;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysRole;

import java.util.List;

public interface IRoleService {

    List<SysRole> list(SysRole role);
}
