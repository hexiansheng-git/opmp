package com.hhwy.domain.base.system.currency;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * info对象 t_currency_info
 * 
 * @author lcf
 * @date 2022-11-24
 */
public class CurrencyInfo extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Update.class})
    private Long id;

    /** 币种名称 */
    @Excel(name = "币种名称")
    @NotBlank(message = "币种名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String currencyName;

    /** 币种编码 */
    @Excel(name = "币种代码")
    @NotBlank(message = "币种编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String currencyCode;

    /** 排序 */
    @Excel(name = "排序")
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "排序不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private Long sort;

    /** 数据创建者 */
    @Excel(name = "数据创建者")
    private String createUser;

    /** 数据修改者 */
    @Excel(name = "数据修改者")
    private String updateUser;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String ptVar1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String ptVar3;

    @Excel(name = "备注")
    private String remark;

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @NotBlank(message = "ids不能为空",groups = {ValidationGroups.Delete.class})
    private String ids;

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
    public void setCurrencyName(String currencyName){
        this.currencyName = currencyName;
    }

    public String getCurrencyName(){
        return currencyName;
    }
    public void setCurrencyCode(String currencyCode){
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode(){
        return currencyCode;
    }
    public void setSort(Long sort){
        this.sort = sort;
    }

    public Long getSort(){
        return sort;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("currencyName", getCurrencyName())
            .append("currencyCode", getCurrencyCode())
            .append("sort", getSort())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("delUser", getDelUser())
            .append("delTime", getDelTime())
            .append("delFlag", getDelFlag())
            .append("remark", getRemark())
            .append("ptVar1", getPtVar1())
            .append("ptVar2", getPtVar2())
            .append("ptVar3", getPtVar3())
            .toString();
    }
}
