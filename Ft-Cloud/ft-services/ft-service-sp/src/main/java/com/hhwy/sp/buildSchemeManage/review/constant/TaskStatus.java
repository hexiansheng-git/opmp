package com.hhwy.sp.buildSchemeManage.review.constant;

import lombok.Data;

@Data
public class TaskStatus {
    public static final String NOT_INITIATED = "未发起";//未发起
    public static final String IN_PROGRESS = "审批中";//审批中
    public static final String COMPLETED = "已完成";//已完成
}
