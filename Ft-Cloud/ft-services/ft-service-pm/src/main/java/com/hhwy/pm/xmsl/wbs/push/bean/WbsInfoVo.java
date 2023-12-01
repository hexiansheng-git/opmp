package com.hhwy.pm.xmsl.wbs.push.bean;

import lombok.Data;

import java.util.List;

/**
 * 功能：wbs信息
 * 作者: fushudong
 * 时间: 2023/09/11
 */
@Data
public class WbsInfoVo {

    //项目代码
    private String projectId;

    List<WbsInfoVoBean> wbsList;

}