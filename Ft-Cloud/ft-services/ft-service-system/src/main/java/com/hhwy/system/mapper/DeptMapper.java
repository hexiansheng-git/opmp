package com.hhwy.system.mapper;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.system.api.domain.SysDept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DeptMapper {

    //查询部门树形结构 update: zxb 2022-12-30
    @Select({"<script>" +
            "select dept_id id, parent_id pId, dept_name label, order_num orderNum, dept_type deptType " +
            "from sys_dept where del_flag=0 " +
            "order by order_num" +
            "</script>"})
    List<SysTreeUtil> getDeptByTree();
    List<SysTreeUtil> selectRegionInfo();


    SysDept selectDeptIdByProjectId(@Param("projectId") String projectId);

    List<SysDept> selectAllDept(@Param("deptId") Long deptId, @Param("ancestors") String ancestors);

    List<SysDept> selectPrjInfo(@Param("deptId") Long deptId, @Param("ancestors") String ancestors);

    List<SysDept> getProjectOrgInfo();
}