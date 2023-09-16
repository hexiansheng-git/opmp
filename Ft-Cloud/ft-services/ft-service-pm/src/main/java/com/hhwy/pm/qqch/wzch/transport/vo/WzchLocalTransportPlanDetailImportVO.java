package com.hhwy.pm.qqch.wzch.transport.vo;

import com.alibaba.excel.annotation.ExcelProperty;


public class WzchLocalTransportPlanDetailImportVO {
    /** 注意事项 */
    @ExcelProperty(value = "注意事项")
    private String heedNote;

    /** 事项说明 */
    @ExcelProperty(value = "事项说明")
    private String description;

    /** 执行部门 */
    @ExcelProperty(value = "执行部门")
    private String executDept;


    @ExcelProperty(value = "备注")
    private String remark;

    public String getHeedNote() {
        return heedNote;
    }

    public void setHeedNote(String heedNote) {
        this.heedNote = heedNote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExecutDept() {
        return executDept;
    }

    public void setExecutDept(String executDept) {
        this.executDept = executDept;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
