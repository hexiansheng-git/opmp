package com.hhwy.pm.qqch.preparation.sbch.equAllot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能：现场设备查询实体
 * 作者: fushudong
 * 时间: 2023/10/23
 */
@Data
public class ActiveEquVo extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String theL;

    private String theC;

    /** 管理编号 */
    private String manageCode;

    /** 材料编码 */
    private String materialCode;

    /** 材料名称 */
    private String materialName;

    /** 规格型号 */
    private String materialSpec;

    /** 单位 */
    private String materialUnit;

    /** 生产能力 */
    private String proPower;

    /** 国家厂家 */
    private String countryFactory;

    /** 来源 */
    private String source;

    /** 出厂日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date produceDate;

    /** 主机厂牌 */
    private String mEngineBrand;

    /** 主机型号 */
    private String mEngineSpec;

    /** 主机功率（KW） */
    private String mEnginePower;

    /** 主机系列号 */
    private String mEngineNo;

    /** 主机出厂日期 */
    @Excel(name = "主机出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date mProduceDate;

    /** 副机厂牌 */
    @Excel(name = "副机厂牌")
    private String aEngineBrand;

    /** 副机型号 */
    @Excel(name = "副机型号")
    private String aEngineSpec;

    /** 副机功率（KW） */
    @Excel(name = "副机功率", readConverterExp = "K=W")
    private String aEnginePower;

    /** 出厂系列号 */
    @Excel(name = "出厂系列号", readConverterExp = "K=W")
    private String aChassisNo;

    /** 副机出厂日期 */
    @Excel(name = "副机出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date aProduceDate;

    /** 底盘厂家 */
    @Excel(name = "底盘厂家")
    private String chassisFactory;

    /** 底盘型式 */
    @Excel(name = "底盘型式")
    private String chassisType;

    /** 底盘系列号 */
    @Excel(name = "底盘系列号")
    private String chassisNo;

    /** 底盘出厂日期 */
    @Excel(name = "底盘出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date chassisProduceDate;

    /** 外形尺寸（mm） */
    @Excel(name = "外形尺寸", readConverterExp = "m=m")
    private String sizeMsg;

    /** 自重 */
    @Excel(name = "自重")
    private BigDecimal theWeight;

    /** 原值（美元） */
    @Excel(name = "原值", readConverterExp = "美=元")
    private BigDecimal originalValue;

    /** 净值（美元） */
    @Excel(name = "净值", readConverterExp = "美=元")
    private BigDecimal netValue;

    /** null */
    @Excel(name = "引进FOB价")
    private BigDecimal importFobPrice;

    /** null */
    @Excel(name = "引进总价")
    private BigDecimal importSumPrice;

    /** null */
    @Excel(name = "引进总折价")
    private BigDecimal importDiscountPrice;

    /** 引进余值 */
    @Excel(name = "引进余值")
    private BigDecimal importResidualPrice;

    /** QY */
    @Excel(name = "QY")
    private String qyValue;

    /** BTGZ */
    @Excel(name = "BTGZ")
    private String btgzValue;

    /** 数据创建者id */
    @Excel(name = "数据创建者id")
    private String createUser;

    /** 数据创建者名称 */
    @Excel(name = "数据创建者名称")
    private String createUserName;

    /** 数据修改者id */
    @Excel(name = "数据修改者id")
    private String updateUser;

    /** 数据修改者名称 */
    @Excel(name = "数据修改者名称")
    private String updateUserName;

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

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private String ptVar4;

    /** 部门id */
    @Excel(name = "部门id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /** 项目id */
    @Excel(name = "项目id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    //项目编码
    private String prjCode;
    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 所属区域id */
    @Excel(name = "所属区域id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String regionName;

    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    /** 车牌照号码 */
    @Excel(name = "车牌照号码")
    private String carNumber;

    /** 办理进口项目名称 */
    @Excel(name = "办理进口项目名称")
    private String importProjectName;

    /** 进口方式 */
    @Excel(name = "进口方式")
    private String importWay;

    /** 提单号 */
    @Excel(name = "提单号")
    private String extractCode;

    /** 名义产权单位 */
    @Excel(name = "名义产权单位")
    private String nominalPropertyUnit;

    /** 整机综合状态 */
    @Excel(name = "整机综合状态")
    private String materialStatus;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    //账状态   0账内  1账外  2待处理
    private String theType;

    private List<String> types;

    private String query;//设备管理编号或者设备名称

    private String type;//设备类型（0自有设备、1租赁设备、2特种设备、3协作单位设备）

    private Long useUnitId;

    //使用单位名称
    private String useUnitName;

    //所在地点
    private String thePlace;

    //计划批文号
    private String planCheckNo;

    /** null */

    private Long id;

    private Integer pageNum;
    private Integer pageSize;
    /** 规格型号 */
    @Excel(name = "设备ABCD类")
    private String materialType;

    @JsonFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date createTime;
    private String isSpecial;//是否特种设备 1：是 0/空：不是
    private String isFilterPro;//1：不过滤 0/空：过滤
}