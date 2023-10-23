package com.hhwy.system.market;

import com.fasterxml.jackson.annotation.JsonFormat;
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

/**
 * 市场调查对象 t_market_survey
 * 
 * @author lcf
 * @date 2022-11-30
 */
public class MarketSurvey extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Update.class})
    private Long id;

    /** 设备名称 **/
    @Excel(name = "设备名称")
    @NotBlank(message = "设备名称不能为空",groups = {ValidationGroups.Save.class})
    private String materialName;

    /** 调查地点 */
    @Excel(name = "调查地点")
    @NotBlank(message = "调查地点不能为空",groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    private String place;

    /** 调查日期 */
    @Excel(name = "调查日期", width = 30, dateFormat = "yyyy-MM-dd")
    @NotNull(message = "调查日期不能为空",groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date surveyDate;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    @NotBlank(message = "生产厂家不能为空",groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    private String factory;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String spec;

    /** 性能参数 */
    @Excel(name = "性能参数")
    private String parameter;

    /** 配置 */
    @Excel(name = "配置")
    private String configure;

    /** 市场报价 */
    @Excel(name = "市场报价")
    @NotNull(message = "市场报价不能为空",groups = {ValidationGroups.Save.class,ValidationGroups.Update.class})
    private BigDecimal marketPrice;

    /** 性能参数 */
    @Excel(name = "优劣势分析")
    private String analysis;

    /** 删除标识：0有效1无效 */
    private String delFlag;

    /** 附件 */
    private String fileGroupId;

    /** 设备编码 **/
    @Excel(name = "设备编码")
    @NotBlank(message = "设备编码不能为空",groups = {ValidationGroups.Save.class})
    private String materialCode;

    @Excel(name = "备注")
    private String  remark;
    @Excel(name = "创建人")
    private String ptVar1;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String deleteUser;

    private Date deleteTime;

    public String getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    /**分类编码**/
    private String categoryCode;

    /**分类编码名称**/
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setPlace(String place){
        this.place = place;
    }

    public String getPlace(){
        return place;
    }
    public void setSurveyDate(Date surveyDate){
        this.surveyDate = surveyDate;
    }

    public Date getSurveyDate(){
        return surveyDate;
    }
    public void setFactory(String factory){
        this.factory = factory;
    }

    public String getFactory(){
        return factory;
    }
    public void setSpec(String spec){
        this.spec = spec;
    }

    public String getSpec(){
        return spec;
    }
    public void setParameter(String parameter){
        this.parameter = parameter;
    }

    public String getParameter(){
        return parameter;
    }
    public void setConfigure(String configure){
        this.configure = configure;
    }

    public String getConfigure(){
        return configure;
    }
    public void setMarketPrice(BigDecimal marketPrice){
        this.marketPrice = marketPrice;
    }

    public BigDecimal getMarketPrice(){
        return marketPrice;
    }
    public void setAnalysis(String analysis){
        this.analysis = analysis;
    }

    public String getAnalysis(){
        return analysis;
    }

    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }

    public void setFileGroupId(String fileGroupId){
        this.fileGroupId = fileGroupId;
    }

    public String getFileGroupId(){
        return fileGroupId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("place", getPlace())
            .append("surveyDate", getSurveyDate())
            .append("factory", getFactory())
            .append("spec", getSpec())
            .append("parameter", getParameter())
            .append("configure", getConfigure())
            .append("marketPrice", getMarketPrice())
            .append("analysis", getAnalysis())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("remark", getRemark())
            .append("ptVar1", getPtVar1())
            .append("ptVar2", getPtVar2())
            .append("ptVar3", getPtVar3())
            .append("fileGroupId", getFileGroupId())
            .toString();
    }

    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
