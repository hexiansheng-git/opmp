package com.hhwy.sp.experiment.sgjsExperProgressManage.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark sgjs_exper_progress_manage
 */
//CommonCompileEntity<SgjsExperProgressManage>
@Data
public class SgjsExperProgressManage extends TreeNode<SgjsExperProgressManage> {
    private static final long serialVersionUID = 1L;
    /**
     * 字段描述： 序号
     */
    @JsonProperty
    @Excel(name = "序号"    )
    private String serialNumber;
    //节点查询
    private String[] paths;

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    private List<String> pathList;

    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    /**
     * 字段描述：主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "主键id")
    private Long id;
    /**
     * 字段描述：父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "父级id")
    private Long pid;
    /**
     * 字段描述：试验工作项
     */
    @JsonProperty
    @Excel(name = "试验工作项")
    private String experimentalWorkItems;
    /**
     * 字段描述：计量单位
     */
    @JsonProperty
    @Excel(name = "计量单位")
    private String measureUnit;
    /**
     * 字段描述：工作量
     */
    @JsonProperty
    @Excel(name = "工作量")
    private String workload;
    /**
     * 字段描述：计划开始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "计划开始日期", dateFormat = "yyyy年MM月dd日")
    private Date planStartDate;
    /**
     * 字段描述：计划结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "计划结束日期", dateFormat = "yyyy年MM月dd日")
    private Date planEndDate;
    /**
     * 字段描述：实际开始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "实际开始日期", dateFormat = "yyyy年MM月dd日")
    private Date realStartDate;
    /**
     * 字段描述：实际结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonProperty
    @Excel(name = "实际结束日期", dateFormat = "yyyy年MM月dd日")
    private Date realEndDate;
    /**
     * 字段描述：备注
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
    /**
     * 字段描述：数据来源 0新增1同步
     */
    @JsonProperty
//    @Excel(name = "数据来源 0新增1同步")
    private String dataSource;
    /**
     * 字段描述：所属区域id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "所属区域id")
    private Long regionId;
    /**
     * 字段描述：所属区域名称
     */
    @JsonProperty
//    @Excel(name = "所属区域名称")
    private String regionName;
    /**
     * 字段描述：项目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "项目id")
    private Long projectId;
    /**
     * 字段描述：项目名称
     */
    @JsonProperty
//    @Excel(name = "项目名称")
    private String projectName;
    /**
     * 字段描述：部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
//    @Excel(name = "部门id")
    private Long deptId;
    /**
     * 字段描述：数据创建者id
     */
    @JsonProperty
//    @Excel(name = "数据创建者id")
    private String createUser;
    /**
     * 字段描述：数据创建者名称
     */
    @JsonProperty
//    @Excel(name = "数据创建者名称")
    private String createUserName;
    /**
     * 字段描述：数据创建系统时间
     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据创建系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 字段描述：数据修改者id
     */
    @JsonProperty
//    @Excel(name = "数据修改者id")
    private String updateUser;
    /**
     * 字段描述：数据修改系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据修改系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 字段描述：数据删除者
     */
    @JsonProperty
//    @Excel(name = "数据删除者")
    private String delUser;
    /**
     * 字段描述：数据删除系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty
//    @Excel(name = "数据删除系统时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date delTime;
    /**
     * 字段描述：删除标识：0未删除；1已删除
     */
    @JsonProperty
//    @Excel(name = "删除标识：0未删除；1已删除")
    private String delFlag;
    /**
     * 字段描述：预留字段1
     */
    @JsonProperty
//    @Excel(name = "预留字段1")
    private Long syncId;
    /**
     * 字段描述：预留字段2  leaf是否是叶子节点 0否1是
     */
    @JsonProperty
//    @Excel(name = "预留字段2  leaf是否是叶子节点 0否1是")
    private String ptVar2;
    /**
     * 字段描述：预留字段3
     */
    @JsonProperty
//    @Excel(name = "预留字段3")
    private String ptVar3;
    /**
     * 字段描述：预留字段4
     */
    @JsonProperty
//    @Excel(name = "预留字段4")
    private String ptVar4;
    /**
     * 字段描述：预留字段5
     */
    @JsonProperty
//    @Excel(name = "预留字段5")
    private String ptVar5;





    /**
     * 字段描述：计划开始时间搜索1
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date planStartDate1;


    /**
     * 字段描述：计划开始时间搜索2
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date planStartDate2;


    /**
     * 字段描述：实际开始时间搜索1
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date realStartDate1;


    /**
     * 字段描述：实际开始时间搜索2
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty
    private Date realStartDate2;


    //导入查询
    private List<Long> ids;


    //标识新增才是修改  0新增；1修改
    private String type;

    //当前节点所在层级目录
    @JsonProperty
//    @Excel(name = "当前节点路径"    )
    private String path;


    //计划开始日期范围字符串
    private String planStartDateStr;


    //实际开始日期范围字符串
    private String realStartDateStr;




}
