package com.hhwy.pm.word.export.domain.vo;

import lombok.Data;

@Data
public class BidWinHandoverFileVo {

    /**
     * 字段描述：移交资料名称
     */
    private String fileName;
    /**
     * 字段描述：是否移交
     */
    private String handover = "否";
    /**
     * 字段描述：备注
     */
    private String remark;
}
