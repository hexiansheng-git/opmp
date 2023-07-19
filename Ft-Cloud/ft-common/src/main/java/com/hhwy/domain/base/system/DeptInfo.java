package com.hhwy.domain.base.system;

import com.hhwy.utils.validation.ValidationGroups;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class DeptInfo {
    //部门类型
    @NotBlank(message = "部门类型不能为空",groups = {ValidationGroups.Select.class})
    private List<String> deptType;

    @NotBlank(message = "状态不能为空",groups = {ValidationGroups.Select.class})
    private String status;

    @NotBlank(message = "区域id",groups = {ValidationGroups.Other.class})
    private String regionId;
    @NotBlank(message = "区域id",groups = {ValidationGroups.Other.class})
    private String type;

    private List<Long> deptIdList;

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public List<String> getDeptType() {
        return deptType;
    }

    public void setDeptType(List<String> deptType) {
        this.deptType = deptType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
