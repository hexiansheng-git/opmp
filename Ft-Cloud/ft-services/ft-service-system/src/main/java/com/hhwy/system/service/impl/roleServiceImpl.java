package com.hhwy.system.service.impl;

import com.hhwy.common.security.service.TokenService;
import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.mapper.DeptMapper;
import com.hhwy.system.mapper.RoleMapper;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.service.IRoleService;
import com.hhwy.system.utils.TreeObject;
import com.hhwy.system.utils.redis.SysRedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class roleServiceImpl implements IRoleService {

    private Logger logger= LoggerFactory.getLogger(roleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;
    public static final String DATA_SCOPE_ALL = "1";//全部数据权限

    @Override
    public List<SysRole> list(SysRole role) {
        return roleMapper.selectRoleList(role);
    }


}
