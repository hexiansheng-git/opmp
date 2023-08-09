package com.hhwy.domain.base.system.equipment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 基础模块设备分类子表对象 t_equipment_info
 * 
 * @author lcf
 * @date 2023-03-03
 */
@Data
public class EquipmentInfo extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Update.class})
    private Long id;

    /** 设备分类表id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long equipId;

    /***父级id**/
    @NotNull(message = "父级id不能为空",groups = {ValidationGroups.Save.class})
    private Long parentId;

    /** 设备编码 */
    @Excel(name = "编码")
    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String equipCode;

    /** 设备名称 */
    @Excel(name = "名称")
    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String equipName;

    /** 规格型号 */
    @Excel(name = "规格")
    private String equipSpec;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String equipUnit;

    /** 设备分类 */
    @Excel(name = "设备分类")
    private String equipCalssify;

    //0否1是
    @Excel(name = "使用状态 0未使用1已使用")
    private String isUse;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 创建者 */
    //@Excel(name = "创建者")
    private String createUser;

    /** 更新者 */
    //@Excel(name = "更新者")
    private String updateUser;

    /** 租户标识 */
    //@Excel(name = "租户标识")
    private String tenantKey;

    /** 平台备用字段 */
    //@Excel(name = "平台备用字段")
    private String ptVar1;

    /** 平台备用字段 */
    //@Excel(name = "平台备用字段")
    private String ptVar2;

    /** 平台备用字段 */
    //@Excel(name = "平台备用字段")
    private String ptVar3;

    /** 平台备用字段 */
    //@Excel(name = "平台备用字段")
    private String ptVar4;

    /** 平台备用字段 */
    //@Excel(name = "平台备用字段")
    private String ptVar5;

    private List<String> ids;

    private String newEquipCode;

    /** 设备分类编码*/
    private String equipCalssifyCode;

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getEquipCalssifyCode() {
        return equipCalssifyCode;
    }

    public void setEquipCalssifyCode(String equipCalssifyCode) {
        this.equipCalssifyCode = equipCalssifyCode;
    }

    public String getNewEquipCode() {
        return newEquipCode;
    }

    public void setNewEquipCode(String newEquipCode) {
        this.newEquipCode = newEquipCode;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setEquipId(Long equipId){
        this.equipId = equipId;
    }

    public Long getEquipId(){
        return equipId;
    }
    public void setEquipCode(String equipCode){
        this.equipCode = equipCode;
    }

    public String getEquipCode(){
        return equipCode;
    }
    public void setEquipName(String equipName){
        this.equipName = equipName;
    }

    public String getEquipName(){
        return equipName;
    }
    public void setEquipSpec(String equipSpec){
        this.equipSpec = equipSpec;
    }

    public String getEquipSpec(){
        return equipSpec;
    }
    public void setEquipCalssify(String equipCalssify){
        this.equipCalssify = equipCalssify;
    }

    public String getEquipCalssify(){
        return equipCalssify;
    }
    public void setEquipUnit(String equipUnit){
        this.equipUnit = equipUnit;
    }

    public String getEquipUnit(){
        return equipUnit;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("equipId", getEquipId())
            .append("equipCode", getEquipCode())
            .append("equipName", getEquipName())
            .append("equipSpec", getEquipSpec())
            .append("equipCalssify", getEquipCalssify())
            .append("equipUnit", getEquipUnit())
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
