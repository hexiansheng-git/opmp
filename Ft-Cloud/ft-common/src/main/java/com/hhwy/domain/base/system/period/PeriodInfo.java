package com.hhwy.domain.base.system.period;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * info对象 t_period_info
 * 
 * @author lcf
 * @date 2022-11-24
 */
public class PeriodInfo extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */

    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Update.class})
    private Long id;

    /** 开始时间 */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "开始时间不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /** 结束时间 */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "结束时间不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 编码 */
    @Excel(name = "期号")
    @NotNull(message = "期号不能为空",groups = {ValidationGroups.Save.class, ValidationGroups.Update.class})
    private String periodCode;

    /** 数据创建者 */
    //@Excel(name = "数据创建者")
    private String createUser;

    /** 数据修改者 */
    //@Excel(name = "数据修改者")
    private String updateUser;

    /** 数据删除者 */
    //@Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    //@Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;


    /**日期**/
    @NotBlank(message = "periodDate不能为空",groups = {ValidationGroups.Other.class})
    private String periodDate;

    @NotNull(message = "ids不能为空",groups = {ValidationGroups.Delete.class})
    private List<String> ids;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "创建人")
    private String ptVar1;

    private String lastMonth;
    //日期
    private String queryDate;
    //币种代码
    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
    }

    @Override
    public String getPtVar1() {
        return ptVar1;
    }

    @Override
    public void setPtVar1(String ptVar1) {
        this.ptVar1 = ptVar1;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    private List<PeriodCurrency> list;

    public List<PeriodCurrency> getList() {
        return list;
    }

    public void setList(List<PeriodCurrency> list) {
        this.list = list;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setBeginDate(Date beginDate){
        this.beginDate = beginDate;
    }

    public Date getBeginDate(){
        return beginDate;
    }
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public Date getEndDate(){
        return endDate;
    }
    public void setPeriodCode(String periodCode){
        this.periodCode = periodCode;
    }

    public String getPeriodCode(){
        return periodCode;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("beginDate", getBeginDate())
            .append("endDate", getEndDate())
            .append("periodCode", getPeriodCode())
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
