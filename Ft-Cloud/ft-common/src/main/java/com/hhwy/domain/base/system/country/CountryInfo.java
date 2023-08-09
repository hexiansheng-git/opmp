package com.hhwy.domain.base.system.country;

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
 * 国别对象 t_country_info
 * 
 * @author lcf
 * @date 2022-11-01
 */
public class CountryInfo extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Update.class})
    private Long id;

    /** 国别中文名称 */
    @Excel(name = "国别中文名称")
    @NotBlank(message = "国别名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String countryName;

    /** 国别英文名称 */
    @Excel(name = "国别英文名称")
    @NotBlank(message = "国别英文名称不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String countryEnName;

    /** 国别编码 */
    @Excel(name = "国别编码")
    @NotBlank(message = "国别编码不能为空",groups = {ValidationGroups.Update.class, ValidationGroups.Save.class})
    private String countryCode;

    /** 区域(字典项) */
    @Excel(name = "区域(字典项)")
    private String areaType;

    /** 数据删除者 */
    @Excel(name = "数据删除者")
    private String delUser;

    /** 数据删除系统时间 */
    @Excel(name = "数据删除系统时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setCountryName(String countryName){
        this.countryName = countryName;
    }

    public String getCountryName(){
        return countryName;
    }
    public void setCountryEnName(String countryEnName){
        this.countryEnName = countryEnName;
    }

    public String getCountryEnName(){
        return countryEnName;
    }
    public void setCountryCode(String countryCode){
        this.countryCode = countryCode;
    }

    public String getCountryCode(){
        return countryCode;
    }
    public void setAreaType(String areaType){
        this.areaType = areaType;
    }

    public String getAreaType(){
        return areaType;
    }

    public void setdelUser(String delUser){
        this.delUser = delUser;
    }

    public String getdelUser(){
        return delUser;
    }
    public void setdelTime(Date delTime){
        this.delTime = delTime;
    }

    public Date getdelTime(){
        return delTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("countryName", getCountryName())
            .append("countryEnName", getCountryEnName())
            .append("countryCode", getCountryCode())
            .append("areaType", getAreaType())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("delUser", getdelUser())
            .append("delTime", getdelTime())
            .append("delFlag", getDelFlag())
            .append("remark", getRemark())
            .append("ptVar1", getPtVar1())
            .append("ptVar2", getPtVar2())
            .append("ptVar3", getPtVar3())
            .toString();
    }
}
