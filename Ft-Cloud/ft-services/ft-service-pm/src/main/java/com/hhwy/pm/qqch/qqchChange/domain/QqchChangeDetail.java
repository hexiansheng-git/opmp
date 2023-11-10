package com.hhwy.pm.qqch.qqchChange.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wk
 * @date 2023-11-06 17:41:50
 * @remark qqch_change_detail
 */
@Data
public class QqchChangeDetail extends TreeNode<QqchChangeDetail> {
    private static final long serialVersionUID = 1L;

    public QqchChangeDetail() {
    }

    public QqchChangeDetail(Long mainId) {
        this.mainId = mainId;
    }

    public QqchChangeDetail(String isFirst, Long editorFirst, String editorFirstName, Date finishTimeFirst) {
        this.isFirst = Integer.parseInt(isFirst);
        this.editorFirst = editorFirst;
        this.editorFirstName = editorFirstName;
        this.finishTimeFirst = finishTimeFirst;
    }

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：父类id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "父类id")
    private Long pid;
    /**
     * 字段描述：主数据id  （qqch_change）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主数据id  （qqch_change）")
    private Long mainId;
    /**
     * 字段描述：策划项id 菜单地址
     */
    @JsonProperty
    @Excel(name = "策划项id 菜单地址")
    private String itemId;
    /**
     * 字段描述：策划项名称
     */
    @JsonProperty
    @Excel(name = "策划项名称")
    private String itemName;
    /**
     * 字段描述：显示顺序
     */
    @JsonProperty
    @Excel(name = "显示顺序")
    private Integer sort;
    /**
     * 字段描述：工作说明
     */
    @JsonProperty
    @Excel(name = "工作说明")
    private String workExplain;
    /**
     * 字段描述：是否编制 0否 1是
     */
    @JsonProperty
    @Excel(name = "是否编制 0否 1是")
    private Integer isFirst;
    /**
     * 字段描述：编制人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "编制人")
    private Long editorFirst;
    /**
     * 字段描述：编制人姓名
     */
    @JsonProperty
    @Excel(name = "编制人姓名")
    private String editorFirstName;
    /**
     * 字段描述：计划完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "计划完成日期", dateFormat = "yyyy-MM-dd")
    private Date finishTimeFirst;
    /**
     * 字段描述：实际完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "实际完成日期", dateFormat = "yyyy-MM-dd")
    private Date actFinishTimeFirst;
    /**
     * 字段描述：评审人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "评审人ID")
    private Long reviewerId;
    /**
     * 字段描述：评审人名称
     */
    @JsonProperty
    @Excel(name = "评审人名称")
    private String reviewerName;
    /**
     * 字段描述：评审完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "评审完成日期", dateFormat = "yyyy-MM-dd")
    private Date reviewFinishTime;
    /**
     * 字段描述：附件组id
     */
    @JsonProperty
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注/描述")
    private String remark;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
    @Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：是否是叶子节点 1是
     */
    @JsonProperty
    @Excel(name = "是否是叶子节点 1是")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "编制人user_name")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
    @Excel(name = "预留字段5")
    private String ptVar5;
    /**
     * 字段描述：流程状态
     */
    @JsonProperty
    @Excel(name = "流程状态")
    private String taskStatus;
    /**
     * 字段描述：是否有效0:否,1:是
     */
    @JsonProperty
    @Excel(name = "是否有效0:否,1:是")
    private Integer valid;


    private List<QqchChangeDetail> children;
}
