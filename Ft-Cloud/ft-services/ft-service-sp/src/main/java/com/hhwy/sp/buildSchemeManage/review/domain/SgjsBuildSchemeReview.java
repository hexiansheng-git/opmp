package com.hhwy.sp.buildSchemeManage.review.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewOpinionVo;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeStaffOpinionVo;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark sgjs_build_scheme_review
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgjsBuildSchemeReview extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;


    /*流程标识   submit：发起   end：结束   no：不是流程   turnDown： 驳回，清数据*/
    private String processStatus;

    /*  add:新增     edit：编辑保存    dispose：处理保存*/
    private String saveType;
    /*流程节点标识*/
    private String flowNodeMark;
    /*用户名  测试用*/
    @JsonProperty
    private String userName;
    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：关联id （废弃）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long relevancyId;
    /**
     * 字段描述：方案编号
     */
    @JsonProperty
    @FtExcel(name = "方案编号")
    private String schemeNum;
    /**
     * 字段描述：方案名称
     */
    @JsonProperty
    @FtExcel(name = "方案名称")
    private String schemeName;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    private String relationWbsId;
    /**
     * 字段描述：关联WBS
     */
    @JsonProperty
    @FtExcel(name = "关联WBS")
    private String relationWbsName;
    /**
     * 字段描述：方案类型
     */
    @JsonProperty
    @FtExcel(name = "方案类型",dictType = "scheme_type_all")
    private String schemeType;
    
    /**
     * 字段描述：方案分级 1Ⅰ、2Ⅱ、3Ⅲ、4Ⅳ
     */
    @JsonProperty
    @FtExcel(name = "方案分级",dictType = "scheme_level")
    private String schemeLevel;
    /**
     * 字段描述：危大等级 1危大、2超危大、3一般
     */
    @JsonProperty
    @FtExcel(name = "危大等级",dictType = "danger_level")
    private String dangerLevel;
    /**
     * 字段描述：计划编制完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划编制完成时间", dateFormat = "yyyy年MM月dd日")
    private Date planCompletionTime;
    /**
     * 字段描述：计划实施时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "计划实施时间", dateFormat = "yyyy年MM月dd日")
    private Date planImplementTime;
    /**
     * 字段描述：本方案编制负责人
     */
    @JsonProperty
    @FtExcel(name = "本方案编制负责人")
    private String schemeCompilePrincipal;
    /**
     * 字段描述：本方案编制负责人联系方式
     */
    @JsonProperty
    @FtExcel(name = "本方案编制负责人联系方式")
    private String principalContactWay;
    /**
     * 字段描述：方案发起人
     */
    @JsonProperty
    @FtExcel(name = "方案发起人")
    private String schemeInitiatorName;
    /**
     * 字段描述：方案发起人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private String schemeInitiatorId;
    /**
     * 字段描述：提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "提交日期", dateFormat = "yyyy年MM月dd日")
    private Date submitDate;

    /**
     * 字段描述：方案附件组id
     */
    @JsonProperty
    private String schemeGroupId;
    /**
     * 字段描述：方案清单项目内部审核记录表附件组id
     */
    @JsonProperty
    private String auditRecordGroupId;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    private String remark;
    /**
     * 字段描述：流程状态
     */
    @JsonProperty
    @FtExcel(name = "流程状态",readConverterExp = "0=未发起,1=审批中,4=审批完成")
    private String taskStatus;

    @FtExcel(name = "当前处理人")
    private String processTaskMan;//当前处理人
    
    /**
     * 字段描述：方案总得分
     */
    @JsonProperty
    private Double score;
    /**
     * 字段描述：方案审核通过时间
     */
    @JsonProperty
    private Date approvalTime;
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
     * 字段描述：是否是被驳回的数据  是：“YES”  否：”NO“
     */
    @JsonProperty
    private String dismissed;
    /**
     * 字段描述：本方案编制负责人用户名
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

    /*针对1,2节点选择的审批人员数据*/
    private List<SgjsBuildSchemeReviewStaff> reviewStaffList;

    /*针对3,4,5,6节点的意见详情数据*/
    private BuildSchemeStaffOpinionVo staffOpinionVo;

    /*针对7,8节点的意见详情和表格数据*/
    private BuildSchemeReviewOpinionVo reviewOpinionVo;

    /*意见记录表数据*/
    private SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord;
}
