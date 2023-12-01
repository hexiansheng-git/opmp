package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能：作业信息
 * 作者: fushudong
 * 时间: 2023/09/11
 */
@Data
public class ActivityInfoVo {

    //项目代码
    private String projectId;

    private List<ActivityInfoVoBean> activityList;

}

