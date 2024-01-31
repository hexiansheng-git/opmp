package com.hhwy.sp.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;

import java.util.Date;

@Data
public class TechManageCommon extends CommonBaseEntity {
    /**
     * 字段描述：申报奖项
     */
    @JsonProperty
    private String applyAward;
    /**
     * 字段描述：奖项等级
     */
    @JsonProperty
    private String awardGrade;
    /**
     * 字段描述：奖项类别
     */
    @JsonProperty
    private String awardType;
    /**
     * 字段描述：授予单位
     */
    @JsonProperty
    private String grantUnit;
    /**
     * 字段描述：奖项时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date awardTime;

    @FtExcel(name = "成果奖项")
    private String allAward;
}
