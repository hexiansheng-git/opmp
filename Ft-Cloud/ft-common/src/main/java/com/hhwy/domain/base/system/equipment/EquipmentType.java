package com.hhwy.domain.base.system.equipment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;


/**
 * 基础模块---设备分类对象 t_equipment_type
 * 
 * @author lcf
 * @date 2023-03-03
 */
public class EquipmentType extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**父级id**/
    @NotNull(message = "父级id不能为空",groups = {ValidationGroups.Save.class})
    private Long parentId;

    /**  */
    @Excel(name = "")
    private String type;

    /** 分类名称 */
    @Excel(name = "分类名称")
    @NotNull(message = "分类名称不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String equipName;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 创建者 */
    @Excel(name = "创建者")
    private String createUser;

    /** 更新者 */
    @Excel(name = "更新者")
    private String updateUser;

    /** 租户标识 */
    @Excel(name = "租户标识")
    private String tenantKey;

    /** 平台备用字段 */
    @Excel(name = "平台备用字段")
    private String ptVar1;

    /** 平台备用字段 */
    @Excel(name = "平台备用字段")
    private String ptVar2;

    /** 平台备用字段 */
    @Excel(name = "平台备用字段")
    private String ptVar3;

    /** 平台备用字段 */
    @Excel(name = "平台备用字段")
    private String ptVar4;

    /** 平台备用字段 */
    @Excel(name = "平台备用字段")
    private String ptVar5;

    private String ids;

    private String searchName;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setEquipName(String equipName){
        this.equipName = equipName;
    }

    public String getEquipName(){
        return equipName;
    }
    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }
    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }

    public String getCreateUser(){
        return createUser;
    }
    public void setUpdateUser(String updateUser){
        this.updateUser = updateUser;
    }

    public String getUpdateUser(){
        return updateUser;
    }
    public void setTenantKey(String tenantKey){
        this.tenantKey = tenantKey;
    }

    public String getTenantKey(){
        return tenantKey;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("type", getType())
            .append("equipName", getEquipName())
            .append("delFlag", getDelFlag())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("tenantKey", getTenantKey())
            .append("ptVar1", getPtVar1())
            .append("ptVar2", getPtVar2())
            .append("ptVar3", getPtVar3())
            .append("ptVar4", getPtVar4())
            .append("ptVar5", getPtVar5())
            .toString();
    }
}
