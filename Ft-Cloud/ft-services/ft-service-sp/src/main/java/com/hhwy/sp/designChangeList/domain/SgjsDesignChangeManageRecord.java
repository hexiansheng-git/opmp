package com.hhwy.sp.designChangeList.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.utils.excel.FtExcel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 施工技术管理-设计变更管理-过程记录对象 sgjs_design_change_manage_record
 * 
 * @author wk
 * @date 2024-04-16
 */
public class SgjsDesignChangeManageRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 主表id */
    @FtExcel(name = "主表id")
    private Long mainId;

    /** 类型（1：事项记录，2：业主/监理沟通记录/纪要） */
    @FtExcel(name = "类型", readConverterExp = "1=：事项记录，2：业主/监理沟通记录/纪要")
    private String type;

    /** 登记日期/沟通日期 */
    @FtExcel(name = "登记日期/沟通日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

    /** 事项记录 */
    @FtExcel(name = "事项记录")
    private String recordContent;

    /** 附件 */
    @FtExcel(name = "附件")
    private String fileGroupId;

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

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setMainId(Long mainId){
        this.mainId = mainId;
    }

    public Long getMainId(){
        return mainId;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setRecordDate(Date recordDate){
        this.recordDate = recordDate;
    }

    public Date getRecordDate(){
        return recordDate;
    }
    public void setRecordContent(String recordContent){
        this.recordContent = recordContent;
    }

    public String getRecordContent(){
        return recordContent;
    }
    public void setFileGroupId(String fileGroupId){
        this.fileGroupId = fileGroupId;
    }

    public String getFileGroupId(){
        return fileGroupId;
    }
    public void setRegionId(Long regionId){
        this.regionId = regionId;
    }

    public Long getRegionId(){
        return regionId;
    }
    public void setRegionName(String regionName){
        this.regionName = regionName;
    }

    public String getRegionName(){
        return regionName;
    }
    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }

    public Long getProjectId(){
        return projectId;
    }
    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public String getProjectName(){
        return projectName;
    }
    public void setProjectCode(String projectCode){
        this.projectCode = projectCode;
    }

    public String getProjectCode(){
        return projectCode;
    }
    public void setDeptId(Long deptId){
        this.deptId = deptId;
    }

    public Long getDeptId(){
        return deptId;
    }
    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }

    public String getCreateUser(){
        return createUser;
    }
    public void setCreateUserName(String createUserName){
        this.createUserName = createUserName;
    }

    public String getCreateUserName(){
        return createUserName;
    }
    public void setUpdateUser(String updateUser){
        this.updateUser = updateUser;
    }

    public String getUpdateUser(){
        return updateUser;
    }
    public void setDelUser(String delUser){
        this.delUser = delUser;
    }

    public String getDelUser(){
        return delUser;
    }
    public void setDelTime(Date delTime){
        this.delTime = delTime;
    }

    public Date getDelTime(){
        return delTime;
    }
    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }
    public void setPtVar1(String ptVar1){
        this.ptVar1 = ptVar1;
    }

    public String getPtVar1(){
        return ptVar1;
    }
    public void setPtVar2(String ptVar2){
        this.ptVar2 = ptVar2;
    }

    public String getPtVar2(){
        return ptVar2;
    }
    public void setPtVar3(String ptVar3){
        this.ptVar3 = ptVar3;
    }

    public String getPtVar3(){
        return ptVar3;
    }
    public void setPtVar4(String ptVar4){
        this.ptVar4 = ptVar4;
    }

    public String getPtVar4(){
        return ptVar4;
    }
    public void setPtVar5(String ptVar5){
        this.ptVar5 = ptVar5;
    }

    public String getPtVar5(){
        return ptVar5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mainId", getMainId())
            .append("type", getType())
            .append("recordDate", getRecordDate())
            .append("recordContent", getRecordContent())
            .append("fileGroupId", getFileGroupId())
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
