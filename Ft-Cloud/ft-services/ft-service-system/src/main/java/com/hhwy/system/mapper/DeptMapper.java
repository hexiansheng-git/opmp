package com.hhwy.system.mapper;

import com.hhwy.domain.base.system.TreeUtil;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface DeptMapper {

    //查询部门树形结构 update: zxb 2022-12-30
    @Select({"<script>" +
            "select dept_id id, parent_id pId, dept_name label, order_num orderNum, dept_type deptType " +
            "from sys_dept where del_flag=0 " +
            "order by order_num" +
            "</script>"})
    List<TreeUtil> getDeptByTree();


}