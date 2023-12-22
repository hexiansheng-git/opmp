package com.hhwy.system.controller;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.core.utils.DeptTreeUtils;
import com.hhwy.system.service.IDeptService;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
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
    //选择人员组件懒加载全部部门信息
    @GetMapping("/lazyList")
    public AjaxResult lazyList(SysDept dept, boolean showNextLevel){
        List depts;
        if ((StringUtils.isNotEmpty(dept.getStatus()) || StringUtils.isNotEmpty(dept.getDeptName())) && dept.getDeptId() == null) {
            List<SysDept> sysDeptList = this.deptService.selectDeptList(dept);
            depts = (new DeptTreeUtils()).deptList(sysDeptList);
            if (CollectionUtils.isNotEmpty(depts)) {
                Iterator var5 = depts.iterator();

                while(var5.hasNext()) {
                    SysDept sysDept = (SysDept)var5.next();
                    sysDept.setChildren((List)null);
                }
            }
        } else {
            if (dept.getDeptId() == null) {
                depts = this.deptService.selectOneLevelDeptList(dept);
            } else {
                depts = this.deptService.selectChildrenDeptList(dept);
            }

            if (showNextLevel && CollectionUtils.isNotEmpty(depts)) {
                this.setChildrenInfo(depts);
            }
        }

        return AjaxResult.success(depts);
    }

    private void setChildrenInfo(List<SysDept> deptList) {
        if (CollectionUtils.isNotEmpty(deptList)) {
            Iterator var2 = deptList.iterator();

            while(var2.hasNext()) {
                SysDept dept = (SysDept)var2.next();
                SysDept param = new SysDept();
                param.setDeptId(dept.getDeptId());
                List<SysDept> children = this.deptService.selectChildrenDeptList(param);
                dept.setChildren(children);
            }
        }

    }

    /**
     * 查询区域信息接口
     * @param map
     * @return
     */
    @PostMapping("/getRegionInfo")
    public AjaxResult getRegionInfo(@RequestBody(required = false) Map map){
        return AjaxResult.success("查询成功!", deptService.getRegionInfo());
    }
}
