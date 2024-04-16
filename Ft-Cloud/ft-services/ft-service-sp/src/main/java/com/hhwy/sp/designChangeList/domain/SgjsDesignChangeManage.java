package com.hhwy.sp.designChangeList.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 施工技术管理-设计变更管理对象 sgjs_design_change_manage
 * 
 * @author wk
 * @date 2024-04-16
 */
@Data
public class SgjsDesignChangeManage extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @NotNull(message = "ID不能为空",groups = {ValidationGroups.Get.class})
    private Long id;

    /** 变更编号 */
    @FtExcel(name = "变更编号")
    private String changeCode;

    /** 主合同编号 */
    @FtExcel(name = "主合同编号")
    private String masterContractCode;

    /** 责任部门id */
    @FtExcel(name = "责任部门id")
    private String responsibleDeptId;

    /** 责任部门名称 */
    @FtExcel(name = "责任部门名称")
    @NotBlank(message = "责任部门不能为空",groups = {ValidationGroups.Other.class})
    private String responsibleDeptName;

    /** 变更来源 */
    @FtExcel(name = "变更来源")
    private String changeSource;

    /** 指令下达日期 */
    @FtExcel(name = "指令下达日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    /** 变更申请日期 */
    @FtExcel(name = "变更申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date changeApplyDate;

    /** 是否调整清单 */
    @FtExcel(name = "是否调整清单")
    private String adjustOrNot;

    /** 变更金额 */
    @FtExcel(name = "变更金额")
    private BigDecimal changeAmount;

    /** 变更目的 */
    @FtExcel(name = "变更目的")
    private String changePurpose;

    /** 设计变更简述 */
    @FtExcel(name = "设计变更简述")
    @NotBlank(message = "设计变更简述不能为空",groups = {ValidationGroups.Other.class})
    private String designChangeBrief;

    /** 优化方向 */
    @FtExcel(name = "优化方向")
    private String optimizedDirection;

    /** 二次经营要点 */
    @FtExcel(name = "二次经营要点")
    private String secondManageKeyPoint;

    /** 变更附件 */
    @FtExcel(name = "变更附件")
    private String changeGroupId;

    /** 报告提交日期 */
    @FtExcel(name = "报告提交日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reportSubmitDate;

    /** 报告说明 */
    @FtExcel(name = "报告说明")
    private String reportExplain;

    /** 报告附件 */
    @FtExcel(name = "报告附件")
    private String changeReportGroupId;

    /** 是否已变更，0:否/1:是 */
    @FtExcel(name = "是否已变更，0:否/1:是")
    private String alreadyChangeOrNot;

    /** 处理情况说明 */
    @FtExcel(name = "处理情况说明")
    private String handleExplain;

    /** 变更结果附件 */
    @FtExcel(name = "变更结果附件")
    private String changeResultGroupId;

    /** 流程状态 */
    @FtExcel(name = "流程状态")
    private String taskStatus;

    /** 当前节点 */
    @FtExcel(name = "当前节点")
    private String currentNode;

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

    private String currency;

    @NotBlank(message = "WBS不能为空",groups = {ValidationGroups.Other.class})
    private List<SgjsDesignChangeWbs> wbsList;
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("changeCode", getChangeCode())
            .append("masterContractCode", getMasterContractCode())
            .append("responsibleDeptId", getResponsibleDeptId())
            .append("responsibleDeptName", getResponsibleDeptName())
            .append("changeSource", getChangeSource())
            .append("orderDate", getOrderDate())
            .append("changeApplyDate", getChangeApplyDate())
            .append("adjustOrNot", getAdjustOrNot())
            .append("changeAmount", getChangeAmount())
            .append("changePurpose", getChangePurpose())
            .append("designChangeBrief", getDesignChangeBrief())
            .append("optimizedDirection", getOptimizedDirection())
            .append("secondManageKeyPoint", getSecondManageKeyPoint())
            .append("changeGroupId", getChangeGroupId())
            .append("reportSubmitDate", getReportSubmitDate())
            .append("reportExplain", getReportExplain())
            .append("changeReportGroupId", getChangeReportGroupId())
            .append("alreadyChangeOrNot", getAlreadyChangeOrNot())
            .append("handleExplain", getHandleExplain())
            .append("changeResultGroupId", getChangeResultGroupId())
            .append("taskStatus", getTaskStatus())
            .append("currentNode", getCurrentNode())
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
