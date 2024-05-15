package com.hhwy.sp.experiment.mixRatioManage.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 施工方案管理-施工方案评审-人员意见记录对象 sgjs_mix_ratio_manage_staff_record
 * 
 * @author wk
 * @date 2024-04-29
 */
@Data
public class SgjsMixRatioManageStaffRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 主表id */
    @FtExcel(name = "主表id")
    private Long mainId;

    /** 人员表id,sgjs_mix_ratio_manage_staff */
    @FtExcel(name = "人员表id,sgjs_mix_ratio_manage_staff")
    private Long staffId;

    /** 意见 */
    @FtExcel(name = "意见")
    private String reviewOpinion;

    /** 附件组id */
    @FtExcel(name = "附件组id")
    private String fileGroupId;

    /** 方案得分 */
    @FtExcel(name = "方案得分")
    private BigDecimal score;

    /** 提交时间 */
    @FtExcel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date submitTime;

    /** 修改结果 */
    @FtExcel(name = "修改结果")
    private String updateResult;

    /** 流程节点标识 */
    @FtExcel(name = "流程节点标识")
    private String flowNodeMark;

    /** 所属区域id */
    @FtExcel(name = "所属区域id")
    private Long regionId;

    /** 所属区域名称 */
    @FtExcel(name = "所属区域名称")
    private String regionName;

    /** 项目id */
    @FtExcel(name = "项目id")
    private Long projectId;

    /** 项目名称 */
    @FtExcel(name = "项目名称")
    private String projectName;

    /** 项目编码 */
    @FtExcel(name = "项目编码")
    private String projectCode;

    /** 部门id */
    @FtExcel(name = "部门id")
    private Long deptId;

    /** 数据创建者id */
    @FtExcel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @FtExcel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @FtExcel(name = "数据修改者id")
    private String updateUser;

    /** 数据删除者 */
    @FtExcel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @FtExcel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0未删除；1已删除 */
    private String delFlag;

    /** 预留字段1 */
    @FtExcel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @FtExcel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @FtExcel(name = "预留字段3")
    private String ptVar3;

    /** 预留字段4 */
    @FtExcel(name = "预留字段4")
    private String ptVar4;

    /** 预留字段5 */
    @FtExcel(name = "预留字段5")
    private String ptVar5;

    

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mainId", getMainId())
            .append("staffId", getStaffId())
            .append("reviewOpinion", getReviewOpinion())
            .append("fileGroupId", getFileGroupId())
            .append("score", getScore())
            .append("submitTime", getSubmitTime())
            .append("updateResult", getUpdateResult())
            .append("flowNodeMark", getFlowNodeMark())
            .append("remark", getRemark())
            .append("regionId", getRegionId())
            .append("regionName", getRegionName())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("projectCode", getProjectCode())
            .append("deptId", getDeptId())
            .append("createUser", getCreateUser())
            .append("createUserName", getCreateUserName())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("delUser", getDelUser())
            .append("delTime", getDelTime())
            .append("delFlag", getDelFlag())
            .append("ptVar1", getPtVar1())
            .append("ptVar2", getPtVar2())
            .append("ptVar3", getPtVar3())
            .append("ptVar4", getPtVar4())
            .append("ptVar5", getPtVar5())
            .toString();
    }
}
