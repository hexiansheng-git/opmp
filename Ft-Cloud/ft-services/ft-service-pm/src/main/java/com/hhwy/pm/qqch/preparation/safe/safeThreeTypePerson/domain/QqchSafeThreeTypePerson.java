package com.hhwy.pm.qqch.preparation.safe.safeThreeTypePerson.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zq
 * @date 2023-08-08 17:22:03
 * @remark qqch_safe_three_type_person
 */
@Data
public class QqchSafeThreeTypePerson  extends TreeNode<QqchSafeThreeTypePerson> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;

    private Long pid;
    /**
     * 字段描述：职务
     */
    @JsonProperty
    @Excel(name = "职务")
    private String duties;
    /**
     * 字段描述：人员姓名
     */
    @JsonProperty
    @Excel(name = "人员姓名")
    private String personName;

    private String personId;
    /**
     * 字段描述：证照名称
     */
    @JsonProperty
    @Excel(name = "证照名称")
    private String cardName;
    /**
     * 字段描述：证照号码
     */
    @JsonProperty
    @Excel(name = "证照号码")
    private String cardNumber;
    /**
     * 字段描述：签发日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @Excel(name = "签发日期", dateFormat = "yyyy-MM-dd")
    private Date signDate;
    /**
     * 字段描述：有效期限（年）
     */
    @JsonProperty
    @Excel(name = "有效期限（年）")
    private Integer validPeriod;
    /**
     * 字段描述：消息提醒
     */
    @JsonProperty
    @Excel(name = "消息提醒")
    private String noticeMsg;
    /**
     * 字段描述：证件地址
     */
    @JsonProperty
    @Excel(name = "证件地址")
    private String cardUrl;
    /**
     * 字段描述：管控措施
     */
    @JsonProperty
    @Excel(name = "管控措施")
    private String controMeasures;
    /**
     * 字段描述：注意事项
     */
    @JsonProperty
    @Excel(name = "注意事项")
    private String attenPoint;
    /**
     * 字段描述：主责部门
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "主责部门")
    private Long mainResDeptId;
    /**
     * 字段描述：主责部门名称
     */
    @JsonProperty
    @Excel(name = "主责部门名称")
    private String mainResDeptName;
    /**
     * 字段描述：协作部门
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "协作部门")
    private Long otherDeptId;
    /**
     * 字段描述：协作部门名称
     */
    @JsonProperty
    @Excel(name = "协作部门名称")
    private String otherDeptName;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @Excel(name = "版本")
    private BigDecimal version;
    /**
     * 字段描述：是否有效 1-有效 0-失效
     */
    @JsonProperty
    @Excel(name = "是否有效 1-有效 0-失效")
    private String valid;
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
     * 字段描述：预留字段1
     */
    @JsonProperty
    @Excel(name = "预留字段1")
    private String ptVar1;
    /**
     * 字段描述：预留字段2
     */
    @JsonProperty
    @Excel(name = "预留字段2")
    private String ptVar2;

    private List<QqchSafeThreeTypePerson> children;
}
