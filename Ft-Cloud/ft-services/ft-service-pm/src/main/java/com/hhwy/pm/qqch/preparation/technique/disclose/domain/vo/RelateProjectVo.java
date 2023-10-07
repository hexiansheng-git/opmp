package com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zhenglili
 * @date 2023-07-27 15:07:49
 * @remark 根据关联wbs查询项目
 */
@Data
public class RelateProjectVo {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：危大工程编码
     */
    private String dangerProjectCode;
    /**
     * 字段描述：危大工程
     */
    private String dangerProject;
    /**
     * 字段描述：重难点工程编码
     */
    private String keyDifficultProjectCode;
    /**
     * 字段描述：重难点工程
     */
    private String keyDifficultProject;
    /**
     * 字段描述：施工方案查询
     */
    private String schemeQuery;

    //计划实施日期
    private Date planImplementTime;
}
