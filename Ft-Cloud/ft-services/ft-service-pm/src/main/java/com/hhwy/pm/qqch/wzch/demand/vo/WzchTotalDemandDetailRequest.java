package com.hhwy.pm.qqch.wzch.demand.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 物资总需请求对象-领导视角
 * @author HCT
 */
@Data
@NoArgsConstructor
public class WzchTotalDemandDetailRequest implements Serializable {

    /**
     * 总需ID
     */
    private Long totalDemandId;
    /** 物资名称 */
    private String materialName;
    /** 规格型号 */
    private String materialSpec;
    /** 执行标准列表 */
    private List<String> materialStandardList;
    @NotNull(message = "页码不能为空",groups = {ValidationGroups.Select.class})
    private Integer pageNum;
    @NotNull(message = "页数不能为空",groups = {ValidationGroups.Select.class})
    private Integer pageSize;
    /** 项目ID */
    private Long projectId;
    private List<Long> projectIdList;
    /**
     * 年度视角-Y 月读视角-M 季度视角-Q
     */
    private String viewType;

    @Excel(name = "计划开始时间", width = 30, dateFormat = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planStartTime;

    /** 计划结束时间 */
    @Excel(name = "计划结束时间", width = 30, dateFormat = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planEndTime;

    private String valid;

    private Long deptId;

    private Long regionId;

    public WzchTotalDemandDetailRequest(Long totalDemandId) {
        this.totalDemandId = totalDemandId;
    }
}
