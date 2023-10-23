package com.hhwy.pm.qqch.preparation.sbch.equAllot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 月度报--自有设备台账对象 xcsb_month_self_equ_info  这个表每个设备的有效数据只会有一个 月度报表的功能表叫xcsb_equ_info_zy_comfirm_records
 * 
 * @author zq
 * @date 2023-02-28
 */
@Data
public class XcsbMonthSelfEquInfo extends CommonBaseEntity {
//    private static final long serialVersionUID = 1L;

    /** null */
    private Integer noNum;
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {ValidationGroups.Update.class})
    private Long id;

    //设备类型 0机械设备   1仪器仪表
    private String maeType;

    //验收表id xcsb_check_equ_info
    private Long checkId;

    //新旧程度
    private String newOrOldDegree;

    //设备ABCD类
    @Excel(name = "L")
    @NotBlank(message = "L不能为空",groups = {ValidationGroups.Type2.class})
    private String theL;

    private List<String> thelList;

    //产地
    @Excel(name = "C")
    @NotBlank(message = "C不能为空",groups = {ValidationGroups.Type2.class})
    private String theC;

    /** 管理编号 */
    @Excel(name = "设备管理编号")
    @NotBlank(message = "机械管理编号不能为空",groups = {ValidationGroups.Type2.class})
    @NotBlank(message = "机械管理编号不能为空",groups = {ValidationGroups.Type3.class})
    @NotBlank(message = "设备管理编号不能为空",groups = {ValidationGroups.Other.class})
    private String manageCode;

    /** 材料编码 */
    @Excel(name = "设备编号")
    private String materialCode;

    /** 材料名称 */
    @Excel(name = "机械名称")
    @NotBlank(message = "机械名称不能为空",groups = {ValidationGroups.Type2.class})
    private String materialName;

    /** 规格型号 */
    @Excel(name = "型号")
    @NotBlank(message = "型号不能为空",groups = {ValidationGroups.Type2.class})
    private String materialSpec;

    /** 规格型号 */
    @Excel(name = "设备ABCD类")
    private String materialType;

    /** 单位 */
    @Excel(name = "单位")
    private String materialUnit;

    private String categoryCode;

    private String categoryName;
    /** 生产能力 */
    @Excel(name = "规格")
    @NotBlank(message = "规格不能为空",groups = {ValidationGroups.Type2.class})
    private String proPower;

    /** 国家厂家 */
    @Excel(name = "国家厂家")
    @NotBlank(message = "国家厂家不能为空",groups = {ValidationGroups.Type2.class})
    private String countryFactory;

    /** 来源 */
    @Excel(name = "来源")
    @NotBlank(message = "来源不能为空",groups = {ValidationGroups.Type2.class})
    private String source;

    @Excel(name = "来源项目id（调出项目）")
    private Long sourceProjectId;

    @Excel(name = "来源项目名称")
    private String sourceProjectName;

    @Excel(name = "调拨单编号")
    private String allotFormNo;

    /** 出厂日期 */
    @Excel(name = "出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "出厂日期不能为空",groups = {ValidationGroups.Type2.class})
    private Date produceDate;

    /** 主机厂牌 */
    @Excel(name = "主机厂牌")
    @NotBlank(message = "主机厂牌不能为空",groups = {ValidationGroups.Type2.class})
    private String mEngineBrand;

    /** 主机型号 */
    @Excel(name = "主机型号")
    @NotBlank(message = "主机型号不能为空",groups = {ValidationGroups.Type2.class})
    private String mEngineSpec;

    /** 主机功率（KW） */
    @Excel(name = "主机功率", readConverterExp = "K=W")
    @NotBlank(message = "主机功率不能为空",groups = {ValidationGroups.Type2.class})
    private String mEnginePower;

    /** 主机系列号 就是发动机号 */
    @Excel(name = "主机系列号")
    @NotBlank(message = "主机系列号不能为空",groups = {ValidationGroups.Type2.class})
    private String mEngineNo;

    /** 主机出厂日期 */
    @Excel(name = "主机出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date mProduceDate;

    /** 副机厂牌 */
    @Excel(name = "付机厂牌")
    private String aEngineBrand;

    /** 副机型号 */
    @Excel(name = "付机型号")
    private String aEngineSpec;

    @Excel(name = "付机系列号")
    private String aEngineNo;

    /** 副机功率（KW） */
    @Excel(name = "付机功率", readConverterExp = "K=W")
    private String aEnginePower;

    /** 出厂系列号 */
    @Excel(name = "出厂系列号", readConverterExp = "K=W")
    private String aChassisNo;

    /** 副机出厂日期 */
    @Excel(name = "付机出厂期", width = 30, dateFormat = "yyyy-MM-dd")
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
    @NotBlank(message = "底盘系列号不能为空",groups = {ValidationGroups.Type2.class})
    private String chassisNo;

    /** 底盘出厂日期 */
    @Excel(name = "底盘出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date chassisProduceDate;

    /** 外形尺寸（mm） */
    @Excel(name = "外形尺寸(mm)", readConverterExp = "m=m")
    @NotBlank(message = "外形尺寸(mm)不能为空",groups = {ValidationGroups.Type2.class})
    private String sizeMsg;

    /** 自重 */
    @Excel(name = "自重(t)")
    @NotNull(message = "自重(t)不能为空",groups = {ValidationGroups.Type2.class})
    private BigDecimal theWeight;

    /** 原值（美元） */
    @Excel(name = "原值(美元)", readConverterExp = "美=元")
    @NotNull(message = "原值(美元)不能为空",groups = {ValidationGroups.Type2.class})
    private BigDecimal originalValue;

    /** 原值（人民币） */
    @Excel(name = "原值(人民币)", readConverterExp = "人民币")
    private BigDecimal originalValueRmb;
    /** 净值（美元） */
    @Excel(name = "净值", readConverterExp = "美=元")
    @NotNull(message = "净值(美元)不能为空",groups = {ValidationGroups.Type2.class})
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

    /** 财务管理编号 */
    @Excel(name = "财务管理编号")
    private String ptVar1;

    /** 0:自有  1：集团公司内部单位 */
    @Excel(name = "分类")
    //0:自有  1：集团公司内部单位
    private String ptVar2;

    /** 预留字段3 */
    @Excel(name = "计划退场日期")
    private String ptVar3;

    /** 国家名称 根据projectId */
    @Excel(name = "国家名称")
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
    @NotBlank(message = "项目编码不能为空",groups = {ValidationGroups.Select.class})
    private String prjCode;
    /** 项目名称 */
    @Excel(name = "项目名称")
    @NotBlank(message = "项目名称不能为空",groups = {ValidationGroups.Type2.class})
    private String projectName;

    /** 所属区域id */
    @Excel(name = "所属区域id")

    @JsonSerialize(using= ToStringSerializer.class)
    private Long regionId;

    /** 区域名称 */
    @Excel(name = "区域名称")
    @NotBlank(message = "单位名称不能为空",groups = {ValidationGroups.Type2.class})
    private String regionName;

    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "验收时间不能为空",groups = {ValidationGroups.Type2.class})
    private Date checkDate;

    /** 车牌照号码 */
    @Excel(name = "车辆牌照号")
    @NotBlank(message = "车辆牌照号不能为空",groups = {ValidationGroups.Type2.class})
    private String carNumber;

    /** 办理进口项目名称 */
    @Excel(name = "办理进口项目名称")
    @NotBlank(message = "办理进口项目名称不能为空",groups = {ValidationGroups.Type2.class})
    private String importProjectName;

    /** 进口方式 */
    @Excel(name = "进口方式")
    @NotBlank(message = "进口方式不能为空",groups = {ValidationGroups.Type2.class})
    private String importWay;

    /** 提单号 */
    @Excel(name = "提单号")
    @NotBlank(message = "提单号不能为空",groups = {ValidationGroups.Type2.class})
    private String extractCode;

    /** 名义产权单位 */
    @Excel(name = "名义产权单位")
    @NotBlank(message = "名义产权单位不能为空",groups = {ValidationGroups.Type2.class})
    private String nominalPropertyUnit;

    /** 整机综合状态 */
    @Excel(name = "整机综合状态")
    private String materialStatus;

    /** 状态 0在用  1停用  2闲置  3待修 */
    @Excel(name = "状态")
    private String status;

    //账状态   0账内  1账外  2待处理
    private String theType;

    private List<String> types;//账状态

    private String query;//设备管理编号或者设备名称

    @NotBlank(message = "设备类型不能为空",groups = {ValidationGroups.Select.class})
    private String type;//设备类型（0自有设备、1租赁设备、2特种设备、3协作单位设备）

    @JsonSerialize(using= ToStringSerializer.class)
    private Long useUnitId;

    //使用单位名称
    @Excel(name = "单位名称")
    private String useUnitName;

    //所在地点
    private String thePlace;

    //计划批文号
    private String planCheckNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    @NotBlank(message = "是否特种设备不能为空",groups = {ValidationGroups.Type2.class})
    @NotBlank(message = "是否特种设备不能为空",groups = {ValidationGroups.Type3.class})
    private String isSpecial;

    private BigDecimal contarctPrice;

    @Excel(name = "对外出售转让进展")
    private String transToOutProcess;

    @Excel(name = "是否完稅")
    private String isTax;

    @Excel(name = "是否抵押保函")
    private String isMortgage;

    //类别大类编码
    private String theBLevelCode;
    //类别大类名称
    @Excel(name = "类别大类")
    private String theBLevelName;
    //类别小类编码
    private String theSLevelCode;
    //类别小类名称
    @Excel(name = "类别小类")
    private String theSLevelName;

    @Excel(name = "采购合同编号")
    private String contractNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date allotDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transDate;

    private String remark;

    @Excel(name = "出厂序列号")
    private String outNo;

    private String operatorId;//作业人员id（后期会与人资打通--备用字段）
    private String operatorName;//作业人员名称
    private String sex;//性别
    private String operationItems;//准操作项目
    private String classificationCode;//分类代码
    private String ssueUnit;//发证单位
    private String identificationNumber;//证件号码
    private String certificateValidity;//证件有效期
    private String personSsueDate;//发证日期
    private String personReviewDate;//复审日期
    private String certFileGroupId;//证件附件
    private String personFileGroupId;//人员简历
    private String operatorRemark;//操作手信息备注

    //保险到期日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insureEndDate;
    //环保标牌号
    private String environTagNo;
    //能源类型
    private String powerType;

    private String confirmMonth;

    private String certificateNumber;//校监证书号
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sbSsueDate;//设备发证日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sbReviewDate;//设备复审日期

    private String ptVar5;
    private String ptVar6;
    private String ptVar7;
    private String ptVar8;
    private String ptVar9;
    private String ptVar10;
    private String ptVar11;
    private String ptVar12;
    private String ptVar13;
    private String ptVar14;
    private String ptVar15;
    private Date ptVar16;
    private Date ptVar17;
    private Date ptVar18;
    private Date ptVar19;
    private Date ptVar20;
    private String techEngineStatu;
    private String techGearboxStatu;
    private String techTransmitStatu;
    private String techWorlConfigStatu;
    private String techChassisStatu;
    private String techPanelStatu;
    private String techLookLike;

    private String workPlace;//施工地点
    private BigDecimal hydraulicOil;//液压油(吨)
    private BigDecimal engineOil;//机油(吨)
    private BigDecimal fuelOil;//燃油(吨)
    private BigDecimal useEleNum;//用电量(千瓦/时)
    private Double workDayNum;//施工天数
    private Double unusedDayNum;//闲置天数
    private Double transDayNum;//调遣天数
    private Double fixDayNum;//修理天数
    private BigDecimal overOfMonth;//月完成工程量
    private BigDecimal sumOfYear;//月完成工程量
    private BigDecimal sumOfTotal;//开工累计工程量
    private BigDecimal totalEnergy;//总能耗
    private String workUnit;//工程量单位
    private BigDecimal proOfMonth;//月完成产值
    private Long sourcePrjId;//调拨来源项目id
    private String sourcePrjName;//调拨来源项目名称
    private String fileGroupId;
    //realTime标识查询的是xcsb_month_self_equ_info
    //record标识查询的是xcsb_equ_info_zy_record表
    @NotBlank(message = "selectFlag不能为空",groups = {ValidationGroups.Update.class})
    private String selectFlag;//查询标识 realTime和record
    private List<Long> ids;
    private List<XcsbMonthSelfEquInsure> insureList;//保险信息
    private BigDecimal sumOfDiscount;//已提折旧
    private BigDecimal scrapNetValue;//报废净值
    private Integer infoDiscountYear;///折旧年限
    private BigDecimal netResidualValue;//设备残值
    /** 设备照片-前部照片 */
    private String picFrontGroupId;
    /** 设备照片-侧面照片 */
    private String picSideGroupId;
    /** 设备照片-尾部照片 */
    private String picTailGroupId;
    /** 设备照片-铭牌照片 */
    private String picNameGroupId;
    /** 设备照片-其他关键照片 */
    private String picOtherGroupId;
    /** 基本信息-车辆注册名称 */
    private String carRegisterName;
    /** 基本信息-折旧年限 */
    private Integer discountYear;
    /**附加费证号*/
    private String otherFeeCode;
    private String isTrans;//转让情况
}
