package com.hhwy.sp.buildSchemeManage.review.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:59
 * @remark 区域总工/海外事业部总工节点  或仅查看时的  人员-意见  表格数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildSchemeStaffOpinionGatherVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：评审人员
     */
    @JsonProperty
    @Excel(name = "评审人员")
    private String reviewStaffName;
    /**
     * 字段描述：评审人员id
     */
    @JsonProperty
    @Excel(name = "评审人员id")
    private String reviewStaffId;
    /**
     * 字段描述：角色  - 评审人员类型  （1：专家  2：部门）
     */
    @JsonProperty
    @Excel(name = "角色  - 评审人员类型  （1：专家  2：部门）")
    private String staffType;
    /**
     * 字段描述：意见
     */
    @JsonProperty
    @Excel(name = "意见")
    private List<String> reviewOpinionList;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private List<String> fileGroupIdList;
    /**
     * 字段描述：方案得分
     */
    @JsonProperty
    @Excel(name = "方案得分")
    private Double score;
    /**
     * 字段描述：提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "提交时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    /**
     * 字段描述：修改结果
     */
    @JsonProperty
    @Excel(name = "修改结果")
    private String updateResult;
    /**
     * 字段描述：流程节点标识
     */
    @JsonProperty
    @Excel(name = "流程节点标识")
    private String flowNodeMark;
}
