package com.hhwy.sd.designFileManage.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zmh
 * @date 2023-12-19 11:06:31
 * @remark kcsj_design_file_manage
 */
@Data
public class KcsjDesignFileManage extends TreeNode<KcsjDesignFileManage> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long id;
    /**
     * 字段描述：
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    private Long pid;

    private String serialNumber;
    /**
     * 字段描述：设计文件名称
     */
    @JsonProperty
    @FtExcel(name = "设计文件名称")
    private String designFileName;
    /**
     * 字段描述：版本
     */
    @JsonProperty
    @FtExcel(name = "版本")
    private String versions;
    /**
     * 字段描述：设计部位范围简述
     */
    @JsonProperty
    @FtExcel(name = "设计部位范围简述")
    private String designScopeSketch;
    /**
     * 字段描述：接收日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "接收日期", dateFormat = "yyyy年MM月dd日")
    private Date receptionDate;

    private String receptionDateStr;
    /**
     * 字段描述：提交单位
     */
    @JsonProperty
    @FtExcel(name = "提交单位")
    private String submitUnit;
    /**
     * 字段描述：接收人
     */
    @JsonProperty
    @FtExcel(name = "接收人")
    private String recipient;
    /**
     * 字段描述：报监理业主日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "报监理业主日期", dateFormat = "yyyy年MM月dd日")
    private Date reportDate;
    private String reportDateStr;
    /**
     * 字段描述：监理业主批复日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    @FtExcel(name = "监理业主批复日期", dateFormat = "yyyy年MM月dd日")
    private Date repleDate;
    private String repleDateStr;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @FtExcel(name = "备注")
    private String remark;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
//    @FtExcel(name = "数据来源 0新增1同步")
    private String dataSource;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
//    @FtExcel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
//    @FtExcel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @FtExcel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
//    @FtExcel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @FtExcel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
//    @FtExcel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @FtExcel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
//    @FtExcel(name = "删除标识：0未删除；1已删除")
    private String delFlag;

    private String isAdd;

    private List<String> ids;

    private String path;

    private List<String> paths;

    /**
     * 字段描述：同步id
     */
    @JsonProperty
//    @FtExcel(name = "附件id")
    private String fileGroupId;
    /**
     * 字段描述：预留字段2  leaf 是否是叶子节点 0否1是
     */
    @JsonProperty
//    @FtExcel(name = "预留字段2  leaf 是否是叶子节点 0否1是")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
//    @FtExcel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
//    @FtExcel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
//    @FtExcel(name = "预留字段5")
    private String ptVar5;
    //项目id
    private Long projectId;
    //项目名称
    private String projectName;
}
