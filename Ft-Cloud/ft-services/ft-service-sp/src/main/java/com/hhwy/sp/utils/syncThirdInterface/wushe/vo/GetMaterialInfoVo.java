package com.hhwy.sp.utils.syncThirdInterface.wushe.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备信息vo
 *
 * @data 2023-12-15
 * @author lcf
 */
@Data
public class GetMaterialInfoVo {

    private String code;//materialCode
    private String bottomNo;//底盘系列号
    private String type;//0：自有  1协作单位   2租赁
    private String spec;//规格型号
    private String mainNo;//主机系列号
    private String weight;//重量
    private String originalValue;//原值
    private String Managementcode;//设备管理编号
    private String productPower;//生产能力
    private String name;//名称
    private String mainPower;//生产能力
    private String mainModel;//主机型号
    private String netValue;//净值














//    private Long id;
//    private String theL;//设备ABCD类
//    private String theC;//产地
//    private String manageCode;//设备管理编码
//    private String materialName;//设备名称
//    private String proPower;//生产能力
//    private String materialSpec;//规格型号
//    private String countryFactory;//国家厂家  厂商
//    private String source;//来源
//    private String categoryCode;
//    private String categoryName;//物资类型
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    private Date produceDate;//生产日期
//    private String mEngineBrand;
//    private String mEnginePower;//主机功率（KW）
//    private String mEngineNo;//主机系列号
//
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    private Date mProduceDate;
//
//    private String aEngineBrand;
//    private String aEngineSpec;
//    private String aEnginePower;
//    private String aChassisNo;
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    private Date aProduceDate;
//
//    private String chassisFactory;
//    private String chassisType;
//    private String chassisNo;
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    private Date chassisProduceDate;
//
//    private String sizeMsg;//外形尺寸
//    private BigDecimal theWeight;//重量（吨）
//    private BigDecimal originalValue;//原值（美元）
//    private BigDecimal importFobPrice;
//    private BigDecimal importSumPrice;
//    private BigDecimal importDiscountPrice;
//    private BigDecimal importResidualPrice;
//    private BigDecimal netValue;
//    private String qyValue;
//    private String btgzValue;
//    private String projectName;
//    private String regionName;
//    private Long regionId;
//
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    private Date checkDate;//验收日期
//
//    private String carNumber;
//    private String importProjectName;
//    private String importWay;
//    private String extractCode;
//    private String nominalPropertyUnit;
//    private String materialStatus;
//    private String status;//设备状态 0在用 1停用 2闲置
//
//    private String prjCode;
//
//    private String projectId;
//
//
//    /*租赁专用*/
//    private Long supplierId;
//    private String suplierName;
//    private String belongPlace;
//    private Long leaseContractId;
//    private String leaseContractNo;
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date exitDate;//退场时间
//    private String quarter;
//    private BigDecimal techTotalLoad;
//    private BigDecimal techTotalWork;
//    private BigDecimal backMilieage;
//    private BigDecimal backWorkHours;
//    private String teamUnitId;
//    private String teamUnitName;
//    private String remark;
//    private String isSingle;//是否单价合同 0是 1否"
//    private BigDecimal dollarLeaseUnitPrice;
//    private BigDecimal dollarLeaseTotalPrice;
//
//    /*协助单位专用*/
//    private String techMaeStatu;
//    private String useStatus;
//    private String subContractNo;
//    private String subContractName;
//    private String materialType;
//    private String newOrOldDegree;
}
