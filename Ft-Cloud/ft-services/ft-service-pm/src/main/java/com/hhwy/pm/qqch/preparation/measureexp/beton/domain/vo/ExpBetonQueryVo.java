package com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo;

import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:49
 * @remark qqch_exp_beton
 */
@Data
public class ExpBetonQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：配合比类型
     */
    private String mixRatioType;
    /**
     * 字段描述：配合比名称
     */
    private String mixRatioName;
}
