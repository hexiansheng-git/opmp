package com.hhwy.domain.base.system.periodCurrency;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 期次汇率对象 t_period_currency
 * 
 * @author lcf
 * @date 2022-11-24
 */
public class PeriodCurrency extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 期次编码id */
    @Excel(name = "期次编码")
    @NotBlank(message = "期次编码id不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String periodId;

    /** 期次编码 */
    @Excel(name = "期次编码")
    private String periodCode;

    /** 币种编码 */
    @Excel(name = "币种编码id")
    @NotBlank(message = "币种编码id不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String currencyId;

    /** 当前币种对美元的汇率 */
    @Excel(name = "当前币种对美元的汇率")
    @NotNull(message = "汇率不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private BigDecimal rate;

    /** 数据修改者 */
    @Excel(name = "数据修改者")
    private String updateUser;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;


    @NotNull(message = "ids不能为空",groups = {ValidationGroups.Delete.class})
    private String ids;

    private String  currencyCode;

    //币种id list
    private List<Long> currencyIdList;

    public List<Long> getCurrencyIdList() {
        return currencyIdList;
    }

    public void setCurrencyIdList(List<Long> currencyIdList) {
        this.currencyIdList = currencyIdList;
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
    public void setPeriodId(String periodId){
        this.periodId = periodId;
    }

    public String getPeriodId(){
        return periodId;
    }
    public void setPeriodCode(String periodCode){
        this.periodCode = periodCode;
    }

    public String getPeriodCode(){
        return periodCode;
    }
    public void setCurrencyId(String currencyId){
        this.currencyId = currencyId;
    }

    public String getCurrencyId(){
        return currencyId;
    }
    public void setRate(BigDecimal rate){
        this.rate = rate;
    }

    public BigDecimal getRate(){
        return rate;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("periodId", getPeriodId())
            .append("periodCode", getPeriodCode())
            .append("currencyId", getCurrencyId())
            .append("rate", getRate())
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
