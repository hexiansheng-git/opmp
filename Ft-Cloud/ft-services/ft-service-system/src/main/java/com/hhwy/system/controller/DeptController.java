package com.hhwy.system.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.service.IDeptService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * new dept
 *
 * @author  lcf
 * @date 20221124
 */
@RestController
@RequestMapping({"/newDept/info", "/selfSysDept"})
public class DeptController {
    @Autowired
    private IDeptService deptService;

    //查询部门树形结构 update: zxb 2022-12-30
    @PostMapping("/getDeptByTree")
    public AjaxResult getDeptByTree(@RequestBody(required = false) Map map){
        return AjaxResult.success("查询成功!", deptService.getDeptByTree());
    }
}
