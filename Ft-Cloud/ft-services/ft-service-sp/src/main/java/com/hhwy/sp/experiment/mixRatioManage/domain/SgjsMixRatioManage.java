package com.hhwy.sp.experiment.mixRatioManage.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:38:19
 * @remark sgjs_mix_ratio_manage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgjsMixRatioManage extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /*保存类型  add：新增  update：修改 */
    private String saveType;

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：配合比编号
     */
    @JsonProperty
    @FtExcel(name = "配合比编号")
    @NotBlank(message = "配合比编号不能为空",groups = {ValidationGroups.Save.class})
    private String mixRatioCode;
    /**
     * 字段描述：配合比类型
     */
    @JsonProperty
    @FtExcel(name = "配合比类型")
    @NotBlank(message = "配合比类型不能为空",groups = {ValidationGroups.Save.class})
    private String mixRatioType;
    
    //配合比等级
    private String mixLevel;
    //计划开始日期
    private Date planStartDate;
    //计划结束日期
    private Date planEndDate;
    //是否批复
    private Integer allowFlag;
    //是否审批通过
    private Integer approvalFlag;
    
    /**
     * 字段描述：前期策划配合比
     */
    @JsonProperty
    @FtExcel(name = "前期策划配合比")
    private String previousPlanMixRatio;
    /**
     * 字段描述：所属WBS编码
     */
    @JsonProperty
    private String relationWbsCode;
    /**
     * 字段描述：所属WBS名称
     */
    @JsonProperty
    @FtExcel(name = "所属WBS名称")
    private String relationWbsName;
    /**
     * 字段描述：配合比名称
     */
    @JsonProperty
    @FtExcel(name = "配合比名称")
    @NotBlank(message = "配合比名称不能为空",groups = {ValidationGroups.Save.class})
    private String mixRatioName;
    /**
     * 字段描述：规格
     */
    @JsonProperty
    @FtExcel(name = "规格")
    private String specification;
    /**
     * 字段描述：内部审核日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "内部审核日期", dateFormat = "yyyy年MM月dd日")
    private Date internalAuditDate;
    /**
     * 字段描述：外部批准日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "外部批准日期", dateFormat = "yyyy年MM月dd日")
    private Date externalApprovalDate;
    /**
     * 字段描述：是否批复
     */
    @JsonProperty
    @FtExcel(name = "是否批复",dictType = "common_yes")
    private String approveOrNot;
    /**
     * 字段描述：是否有效
     */
    @JsonProperty
    @FtExcel(name = "是否有效",dictType = "common_yes")
    private String validOrNot;
    /**
     * 字段描述：编制人用户名
     */
    @JsonProperty
    private String principalUserName;
    /**
     * 字段描述：编制人姓名
     */
    @JsonProperty
    @FtExcel(name = "编制人")
    private String principalName;
    /**
     * 字段描述：编制人联系方式
     */
    @JsonProperty
    @FtExcel(name = "编制人联系方式")
    private String principalContactWay;
    /**
     * 字段描述：配合比附件
     */
    @JsonProperty
    private String mixRatioGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：项目编码
     */
    @JsonProperty
    private String projectCode;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    private String delFlag;
    /**
     * 字段描述：源数据ID
     */
    @JsonProperty
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    private String ptVar5;

    private List<SgjsMixRatioManageMaterial> materialList;

    private List<SgjsMixRatioManageStaff> staffList;
}
