package com.hhwy.pm.qqch.wzch.specialproject.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专项物资策划物资详情对象 wzch_special_project_detail
 *
 * @author mls
 * @date 2022-12-11
 */

@Data
@ToString
public class WzchSpecialProjectDetail extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 优先进场id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long specialProjectId;

    /**
     * 物资编码
     */
    private String materialCode;

    @Excel(name = "系统/成套设备名称")
    private String materialName;

    @Excel(name = "单位")
    private String unit;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal num;

    /**
     * 货源地
     */
    @Excel(name = "货源地")
    private String sourceSupply;

    /**
     * 品牌
     */
    @Excel(name = "品牌")
    private String brand;

    /**
     * 供应商
     */
    @Excel(name = "供应商")
    private String supplier;

    /**
     * 执行标准
     */
    @Excel(name = "执行标准")
    private String materialStandard;

    /**
     * 技术参数
     */
    @Excel(name = "技术参数")
    private String materialTechParam;

    /**
     * 检验标准(1-样品检测、2-厂检、3-第三方监测)
     */
    @Excel(name = "检验标准")
    private String checkStandard;

    /**
     * 产品认证(0-未认证,1-已认证)
     */
    @Excel(name = "产品认证")
    private String prodCert;
    private Boolean bprodCert;

    /**
     * 发运方式(1-海运、2-空运、3-携带)
     */
    @Excel(name = "发运方式")
    private String despatchType;

    /**
     * 设计图纸 提供状态(1-提供、0-未提供)
     */
    @Excel(name = "设计图纸 提供状态")
    private String designProvide;

    /**
     * 设计图纸 是否经过监理（业主）审批 (0-否,1-是)
     */
    @Excel(name = "设计图纸 是否经过监理")
    private String designApprove;
    private Boolean bdesignApprove;

    /**
     * 设计图纸 现场进度计划使用时间
     */
    @Excel(name = "设计图纸 现场进度计划使用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date designSitePlanTime;

    /**
     * 要求(1-需供货方优化、0-不需供货方优化)
     */
    @Excel(name = "要求")
    private String demand;

    /**
     * 后期图纸 是否经过监理（业主）审批 (0-否,1-是)
     */
    @Excel(name = "后期图纸 是否经过监理")
    private String laterApprove;
    private Boolean blaterApprove;

    /**
     * 后期图纸 提供状态(1-提供、0-未提供)
     */
    @Excel(name = "后期图纸 提供状态")
    private String laterProvide;

    /**
     * 施工图纸 是否经过监理（业主）审批(0-否,1-是)
     */
    @Excel(name = "施工图纸 是否经过监理")
    private String constructApprove;
    private Boolean bconstructApprove;

    /**
     * 施工图纸 现场进度计划使用时间
     */
    @Excel(name = "施工图纸 现场进度计划使用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date constructSitePlanTime;

    /**
     * 系统集成专业物资采购细化清单
     */
    @Excel(name = "系统集成专业物资采购细化清单")
    private String bom;

    /**
     * 招标情况
     */
    @Excel(name = "招标情况")
    private String biddingSituation;

    /**
     * 厂家报样品 提供状态(1-提供、0-未提供)
     */
    @Excel(name = "厂家报样品 提供状态")
    private String factoryProvide;

    /**
     * 厂家报样品 是否经过监理（业主）审批 (0-否,1-是)
     */
    @Excel(name = "厂家报样品 是否经过监理")
    private String factoryApprove;
    private Boolean bfactoryApprove;

    /**
     * 采购流程(1-开启、2-暂定)
     */
    @Excel(name = "采购流程")
    private String purchaseProcess;

    /**
     * 文件id
     */
    private String fileGroupId;

    /**
     * 项目id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 部门id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     * 数据创建者id
     */
    private String createUser;

    /**
     * 数据创建者名称
     */
    private String createUserName;

    /**
     * 数据修改者id
     */
    private String updateUser;

    /**
     * 数据修改者名称
     */
    private String updateUserName;

    /**
     * 数据删除者
     */
    private String delUser;

    /**
     * 数据删除系统时间
     */
    private Date delTime;

    /**
     * 删除标记: 0-未删除 1-已删除
     */
    private String delFlag;

    /**
     * 是否有效 1-有效 0-失效
     */
    private String valid;

    /**
     * 预留字段1
     */
    private String ptVar1;

    /**
     * 预留字段2
     */
    private String ptVar2;

    /**
     * 预留字段3
     */
    private String ptVar3;


    public Boolean getBprodCert() {

        return "1".equals(prodCert);
    }

    public void setBprodCert(Boolean bprodCert) {
        this.prodCert = bprodCert ? "1" : "0";
        this.bprodCert = bprodCert;
    }

    public Boolean getBdesignApprove() {
        return "1".equals(designApprove);
    }

    public void setBdesignApprove(Boolean bdesignApprove) {
        this.designApprove = bdesignApprove ? "1" : "0";
        this.bdesignApprove = bdesignApprove;
    }

    public Boolean getBlaterApprove() {
        return "1".equals(laterApprove);
    }

    public void setBlaterApprove(Boolean blaterApprove) {
        this.laterApprove = blaterApprove ? "1" : "0";
        this.blaterApprove = blaterApprove;
    }

    public Boolean getBconstructApprove() {
        return "1".equals(constructApprove);
    }

    public void setBconstructApprove(Boolean bconstructApprove) {
        this.constructApprove = bconstructApprove ? "1" : "0";
        this.bconstructApprove = bconstructApprove;
    }

    public Boolean getBfactoryApprove() {
        return "1".equals(factoryApprove);
    }

    public void setBfactoryApprove(Boolean bactoryApprove) {
        this.factoryApprove = bactoryApprove ? "1" : "0";
        this.bfactoryApprove = bactoryApprove;
    }
}
