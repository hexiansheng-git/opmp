package com.hhwy.sp.buildSchemeManage.review.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum TaskStatus {
    NOT_INITIATED("0","未发起"),
    IN_PROGRESS("1","审批中"),//审批中
    COMPLETED("4","审批完成");//审批完成

    private String code;
    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
