package com.hhwy.pm.qqch.wzch.demand.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 来源策划物资总需对象
 * @author HCT
 */
@Data
public class WzchSourceTotalDemandVO implements Serializable {


    private Long newId;

    private Long backId;

    /**
     *  版本
     */
    private String versionCode;
    /**
     * 生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validDate;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目ID
     */
    private String projectId;

    private List<WzchSourceTotalDemandDetailVO> wzchSourceTotalDemandDetailVOList;


}
