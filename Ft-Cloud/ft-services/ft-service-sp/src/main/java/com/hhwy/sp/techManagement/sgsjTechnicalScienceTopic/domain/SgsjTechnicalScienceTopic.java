package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sp.common.sgjsAuthenticateEvaluate.domain.SgjsAuthenticateEvaluate;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能描述: 科技管理 - 科研课题研发管理
 * @author fsd
 * @date 2024-01-29 14:11:17
 * @remark sgsj_technical_science_topic
 */
@Data
public class SgsjTechnicalScienceTopic extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    //鉴定或评价
    private List<SgjsAuthenticateEvaluate> evaluateList;

    //成果奖项
    private List<SgjsAchievementAward> awardList;

    //知识库课题申请
    private List<SgjsExpertLibrary> listApply;
    //知识库课题立项
    private List<SgjsExpertLibrary> listTopic;
    //知识库大纲审查
    private List<SgjsExpertLibrary> listOutline;
    //知识库课题验收
    private List<SgjsExpertLibrary> listAcceptance;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：申请状态
     * 字典: data_current_state
     * 1	未发起
     * 2	审批中
     * 3	申请通过
     * 4	申请不通过
     */
    @JsonProperty
    @Excel(name = "申请状态")
    private String applyState;
    /**
     * 字段描述：当前状态
     */
    @JsonProperty
    @Excel(name = "当前状态")
    private String taskStatus;
    /**
     * 字段描述：当前经办人
     */
    @JsonProperty
    @Excel(name = "当前经办人")
    private String handlePerson;
    /**
     * 字段描述：课题编号
     */
    @JsonProperty
    @Excel(name = "课题编号")
    private String topicCode;
    /**
     * 字段描述：课题名称
     */
    @JsonProperty
    @Excel(name = "课题名称")
    private String topicName;
    /**
     * 字段描述：课题进度
     * 字典：topic_curent_node
     * 1.课题立项
     * 2.大纲审查
     * 3.签订合同
     * 4.中期检查
     * 5.课题验收
     */
    @JsonProperty
    @Excel(name = "课题进度")
    private String topicCurentNode;
    /**
     * 字段描述：课题研发日期
     */
    @JsonProperty
    @Excel(name = "课题研发日期")
    private String startEndDate;
    /**
     * 字段描述：课题负责人id
     */
    @JsonProperty
    @Excel(name = "课题负责人id")
    private String dutyPerson;
    /**
     * 字段描述：课题负责人
     */
    @JsonProperty
    @Excel(name = "课题负责人")
    private String dutyPersonName;
    /**
     * 字段描述：协作单位
     */
    @JsonProperty
    @Excel(name = "协作单位")
    private String togetherUnit;
    /**
     * 字段描述：其他协作单位
     */
    @JsonProperty
    @Excel(name = "其他协作单位")
    private String togetherUnitOther;
    /**
     * 字段描述：研发预算（万元）
     */
    @JsonProperty
    @Excel(name = "研发预算（万元）")
    private String rdCost;
    /**
     * 字段描述：已拨付经费（万元）
     */
    @JsonProperty
    @Excel(name = "已拨付经费（万元）")
    private String alreadyPayCost;
    /**
     * 字段描述：剩余经费（万元）
     */
    @JsonProperty
    @Excel(name = "剩余经费（万元）")
    private String leftCost;
    /**
     * 字段描述：登记人id
     */
    @JsonProperty
    @Excel(name = "登记人id")
    private String writeInPerson;
    /**
     * 字段描述：登记人
     */
    @JsonProperty
    @Excel(name = "登记人")
    private String writeInPersonName;
    /**
     * 字段描述：登记人联系方式
     */
    @JsonProperty
    @Excel(name = "登记人联系方式")
    private String writeInPersonPhoneNum;
    /**
     * 字段描述：课题简介
     */
    @JsonProperty
    @Excel(name = "课题简介")
    private String topicSummary;
    /**
     * 字段描述：课题附件
     */
    @JsonProperty
    @Excel(name = "课题附件")
    private String topicFileGroupId;
    /**
     * 字段描述：大纲附件
     */
    @JsonProperty
    @Excel(name = "大纲附件")
    private String outlineFileGroupId;
    /**
     * 字段描述：合同附件
     */
    @JsonProperty
    @Excel(name = "合同附件")
    private String contractFileGroupId;
    /**
     * 字段描述：检查附件
     */
    @JsonProperty
    @Excel(name = "检查附件")
    private String inspectFileGroupId;
    /**
     * 字段描述：验收附件
     */
    @JsonProperty
    @Excel(name = "验收附件")
    private String acceptanceFileGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：申请的流程业务id
     *
     */
    @JsonProperty
    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：立项的流程业务id
     */
    @JsonProperty
    @Excel(name = "预留字段2")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     * 自关联：存放申请的id，查看详情用
     */
    @JsonProperty
    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     *
     * 存放申请时用到得附件名称
     */
    @JsonProperty
    @Excel(name = "预留字段5")
    private String ptVar5;

    private Long[] ids;

}
