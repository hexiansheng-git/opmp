package com.hhwy.pm.qqch.preparation.sbch.equAllot.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 功能：现场设备查询实体
 * 作者: fushudong
 * 时间: 2023/10/23
 */
@Data
public class ActiveEquVo {
    //页码
    private String pageNum;
    //每页条数
    private String pageSize;
    //管理编号
    private String manageCode;
    //设备名称
    private String materialName;
    //账状态 0账内 1账外 2待处理
    private String theType;
    //设备abcd类 下拉传大写 A，B,C D
    private String materialType;
}