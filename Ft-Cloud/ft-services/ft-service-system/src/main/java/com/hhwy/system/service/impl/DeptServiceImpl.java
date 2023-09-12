package com.hhwy.system.service.impl;

import com.hhwy.common.security.service.TokenService;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.utils.TreeObject;
import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.mapper.DeptMapper;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.utils.redis.SysRedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeptServiceImpl implements IDeptService {

    private Logger logger= LoggerFactory.getLogger(DeptServiceImpl.class);

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysRedisUtils redisUtils;
    public static final String DATA_SCOPE_ALL = "1";//全部数据权限

    @Override
    public List<SysDept> list(SysDept dept) {
        
        return null;
    }

    @Override
    public List<SysTreeUtil> getDeptByTree() {
        List<SysTreeUtil> list = deptMapper.getDeptByTree();
        List<SysTreeUtil> deptTree = TreeObject.getDeptTree(list);
        return deptTree;
    }

    @Override
    public List<SysTreeUtil> getRegionInfo() {
        List<SysTreeUtil> regionList = deptMapper.selectRegionInfo();
        List<SysTreeUtil> deptTree = TreeObject.getDeptTree(regionList);
        return deptTree;
    }

    @Override
    public SysDept selectDeptIdByprojectId(String projectId) {
        return deptMapper.selectDeptIdByProjectId(projectId);
    }

    @Override
    public List<SysDept> selectAllDept(Long deptId, String ancestors) {
        return deptMapper.selectAllDept(deptId,ancestors);
    }

    @Override
    public List<SysDept> selectPrjInfo(Long deptId, String ancestors) {
        return deptMapper.selectPrjInfo(deptId,ancestors);
    }

    @Override
    public List<SysDept> getProjectOrgInfo() {
        return deptMapper.getProjectOrgInfo();
    }


}
