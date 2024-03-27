package com.hhwy.system.service;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.api.domain.SysDept;

import java.util.List;

public interface IDeptService {

    List<SysDept> list(SysDept dept);
    
    //查询部门树形结构 update: zxb 2022-12-30
    List<SysTreeUtil> getDeptByTree();

    Object getRegionInfo();

    SysDept selectDeptIdByprojectId(String projectId);

    List<SysDept> selectAllDept(Long deptId, String ancestors);

    List<SysDept> selectPrjInfo(Long deptId, String ancestors);

    List<SysDept> getProjectOrgInfo();

    List<SysDept> selectDeptList(SysDept dept);

    List selectOneLevelDeptList(SysDept dept);

    List selectChildrenDeptList(SysDept dept);

    String getAllDepNames();
}
