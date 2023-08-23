package com.hhwy.system.service;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.api.domain.SysDept;

import java.util.List;
import java.util.Map;

public interface IDeptService {

    //查询部门树形结构 update: zxb 2022-12-30
    List<SysTreeUtil> getDeptByTree();

    Object getRegionInfo();

    SysDept selectDeptIdByprojectId(Long projectId);

    List<SysDept> selectAllDept(Long deptId);
}
