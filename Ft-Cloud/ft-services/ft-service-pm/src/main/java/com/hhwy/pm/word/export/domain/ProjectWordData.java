package com.hhwy.pm.word.export.domain;

import com.deepoove.poi.data.PictureRenderData;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo.KeyInventoryContentItemClassify;
import com.hhwy.pm.qqch.preparation.costControl.operateTarget.domain.QqchProjectOperationObjective;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcImplementPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrange;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.domain.QqchTaxGoal;
import com.hhwy.pm.word.export.domain.vo.BidWinHandoverFileVo;
import com.hhwy.pm.word.export.domain.vo.ConditionVo;
import com.hhwy.pm.xmsl.implement.domain.*;
import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProjectWordData {
    /**
     * 字段描述：项目名称（中文）
     */
    @JsonProperty
    private String projectName;
    /**
     * 字段描述：里程
     */
    @JsonProperty
    private String mileage;
    /**
     * 字段描述：合同价格
     */
    @JsonProperty
    private String contractPrice;
    /**
     * 字段描述：合同汇率
     */
    @JsonProperty
    private String contractExchangeRate;
    /**
     * 字段描述：业主单位
     */
    @JsonProperty
    private String proprietorUnit;
    /**
     * 字段描述：监理单位
     */
    @JsonProperty
    private String supervisorUnit;
    /**
     * 字段描述：管理公司
     */
    @JsonProperty
    private String managementCompany;
    /**
     * 字段描述：资金来源
     */
    @JsonProperty
    private String capitalSource;
    /**
     * 字段描述：合同类型
     */
    @JsonProperty
    private String contractType;
    /**
     * 字段描述：合同工期
     */
    @JsonProperty
    private String contractSchedule;
    /**
     * 字段描述：项目开竣工日期
     */
    private String startCompletionDate;
    /**
     * 字段描述：缺陷责任期
     */
    @JsonProperty
    private String defectsLiabilityPeriod;
    /**
     * 字段描述：执行规范标准
     */
    @JsonProperty
    private String executiveSpecificationStandard;
    /**
     * 字段描述：合同语言
     */
    @JsonProperty
    private String contractLang;
    /**
     * 字段描述：支付货币
     */
    @JsonProperty
    private String paymentCurrency;
    /**
     * 字段描述：调价方式
     */
    @JsonProperty
    private String priceAdjustmentMode;
    /**
     * 字段描述：预付款比例
     */
    @JsonProperty
    private BigDecimal prepaymentRatio;
    /**
     * 字段描述：履约保函比例
     */
    @JsonProperty
    private BigDecimal performanceSecurityRatio;
    /**
     * 字段描述：保留金比例
     */
    @JsonProperty
    private BigDecimal retentionMoneyRatio;
    /**
     * 字段描述：质量保证金比例
     */
    @JsonProperty
    private BigDecimal qualityGuaranteeDepositRatio;
    /**
     * 字段描述：主要材料
     */
    private List<XmslProjectMaterialsAmount> materialsAmountList;
    /**
     * 字段描述：主要工程
     */
    private List<XmslProjectEngineeringAmount> engineeringAmountList;
    /**
     * 字段描述：工程地理位置附件集合
     */
    private List<Map<String,PictureRenderData>> locationPictureList;
    /**
     * 字段描述：工程结构形式附件集合
     */
    private List<Map<String,PictureRenderData>> structurePictureList;

    /*---------------------------------------实施条件----------------------------------------*/
    /**
     * 地形地貌
     */
    private List<XmslTerrainLandforms> terrainLandformsList;
    /**
     * 地形地貌附件
     */
    private List<Map<String,PictureRenderData>> terrainLandformsList4Picture;
    /**
     * 主线典型地质勘察表
     */
    private List<XmslMainTypicalGeologySurvey> mainTypicalGeologySurveyList;
    /**
     * 主线典型地质勘察表附件
     */
    private List<Map<String,PictureRenderData>> mainTypicalGeologySurveyList4Picture;
    /**
     * 不良地质调查表
     */
    private List<XmslBadGeologySurvey> badGeologySurveyList;
    /**
     * 不良地质调查表附件
     */
    private List<Map<String,PictureRenderData>> badGeologySurveyList4Picture;
    /**
     *主要构造物水文条件
     */
    private List<XmslMainStructureHydrology> mainStructureHydrologyList;
    /**
     * 主要构造物水文条件附件
     */
    private List<Map<String,PictureRenderData>> mainStructureHydrologyList4Picture;
    /**
     *气候条件
     */
    private List<XmslClimateCondition> climateConditionList;

    /**
     * 气候条件附件
     */
    private List<Map<String,PictureRenderData>> climateConditionList4Picture;
    /**
     *水、电、交通、通讯条件
     */
    private List<XmslBasicFacilitiesConditions> basicFacilitiesConditionsList;
    /**
     *施工干扰
     */
    private List<XmslConstructionInterference> constructionInterferenceList;
    /**
     * 施工干扰附件
     */
    private List<Map<String,PictureRenderData>> constructionInterferenceList4Picture;
    /**
     * 属地工人供应情况
     */
    private List<XmslLocalWorkerSupply> localWorkerSupplyList;
    /**
     * 属地物资供应情况
     */
    private List<XmslLocalMaterialsSupply> localMaterialsSupplyList;
    /**
     * 属地设备供应情况
     */
    private List<XmslLocalEquipmentSupply> localEquipmentSupplyList;
    /**
     * 当地政策要点说明
     */
    private String policyKeyPointsExplanation;
    /**
     * 社会和人文条件说明
     */
    private String socialHumanisticExplanation;
    /**
     * 重要干系人识别及沟通
     */
    private List<XmslKeyPersonCommunication> keyPersonCommunicationList;

    /*---------------------------------------合同条件----------------------------------------*/
    /**
     * 中标资料移交
     */
    private List<BidWinHandoverFileVo> handoverFileVoList;
    /**
     * 量差较大清单
     */
    private List<KeyInventoryContentItemClassify> largeQuantityDifferenceInventoryList;
    /*量差较大清单的合计*/
    private BigDecimal largeQuantityDifferenceInventoryTotal;
    /**
     * 价差较大清单
     */
    private List<KeyInventoryContentItemClassify> wideSpreadInventoryList;
    /*价差较大清单的合计*/
    private BigDecimal wideSpreadInventoryTotal;
    /**
     * 主要漏项清单
     */
    private List<KeyInventoryContentItemClassify> ulcerativeCervicalScrofulaInventoryList;
    /*主要漏项清单的合计*/
    private BigDecimal ulcerativeCervicalScrofulaInventoryTotal;

    /**
     * 特殊条款
     */
    private List<ConditionVo> exceptionConditionList;
    /**
     * 经营有利条款
     */
    private List<ConditionVo> advantage1ConditionList;
    /**
     * 经营不利条款
     */
    private List<ConditionVo> advantage2ConditionList;
    /**
     * 技术有利条款
     */
    private List<ConditionVo> advantage3ConditionList;
    /**
     * 技术不利条款
     */
    private List<ConditionVo> advantage4ConditionList;

    /*---------------------------------------项目目标----------------------------------------*/
    /**
     * 经营目标
     */
    private List<QqchProjectOperationObjective> projectOperationObjectiveList;
    /**
     * 质量目标
     */
    private List<QqchQcImplementPlan> qcImplementPlanList;
    /**
     * 财务目标
     */
    private List<QqchTaxGoal> taxGoalList;


    /*---------------------------------------项目组织及施工部署----------------------------------------*/
    /**
     * 大临设施-图片
     */
    private PictureRenderData workPlanPrjImg;
    /**
     * 大临设施-大临设施一览表
     */
    private List<QqchWorkPlanningBuildPlan> workPlanBuildPlanList;
    /**
     * 大临设施-施工便道跨越障碍物措施
     */
    private List<QqchWorkPlaningArrange> workPlanArrangeList;
}
