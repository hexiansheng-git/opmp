package com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.mapper.JdglQuarterImagePlanMapper;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.IJdglQuarterValuePlanService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.statistics.util.TreeCountUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.image.PixelConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
@Service
public class JdglQuarterImagePlanServiceImpl implements IJdglQuarterImagePlanService {

    @Autowired
    private JdglQuarterImagePlanMapper jdglQuarterImagePlanMapper;

    @Autowired
    private IJdglQuarterValuePlanService jdglQuarterValuePlanService;

    @Autowired
    private IJdglMainPlanService iJdglMainPlanService;

    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;

    @Autowired
    private IXmslContractListService xmslContractListService;

    @Autowired
    private IXmslDrawReviewListService drawReviewListService;

    @Autowired
    private IJdglDayScheduleWbsService jdglDayScheduleWbsService;


    public JdglQuarterImagePlan getJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        return jdglQuarterImagePlanMapper.getJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    public List<JdglQuarterImagePlan> getJdglQuarterImagePlanList(JdglQuarterImagePlan jdglQuarterImagePlan) {
        Long pid = jdglQuarterImagePlan.getPid();
        List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterImagePlanMapper.getJdglQuarterImagePlanList(jdglQuarterImagePlan);
        if(CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
            return jdglQuarterImagePlanList;
        }
        /*计算作业产值 : ∑作业挂接的清单价*复核数量*/
        workValueCalc(jdglQuarterImagePlanList);
        List<JdglQuarterImagePlan> build = TreeUtil.build(jdglQuarterImagePlanList, pid);
        return build;
    }

    //计算作业产值
    private void workValueCalc(List<JdglQuarterImagePlan> jdglQuarterImagePlanList) {
        // 获取图纸复核的清单
        List<XmslDrawReviewList> viewList = drawReviewListService.getFullEffectList();
        // 主合同清单
        List<XmslContractList> contractList = xmslContractListService.getValidMaxVersionContractInventoryList();
        Map<String, XmslContractList> contractMap = contractList.stream().collect(Collectors.toMap(XmslContractList::getCode, v -> v, (k1, k2) -> k1));
        for (JdglQuarterImagePlan imagePlan : jdglQuarterImagePlanList) {
            //作业产值 初始
            BigDecimal workValue = new BigDecimal(0);
            String workCode = imagePlan.getWorkCode();
            List<XmslDrawReviewList> drawReviewList = viewList.stream()
                    .filter(p -> StrUtil.isNotBlank(p.getWbsCode()) && p.getWbsCode().equals(workCode))
                    .collect(Collectors.toList());
            if (CollUtil.isEmpty(drawReviewList)) continue;
            for (XmslDrawReviewList xmslDrawReviewList : drawReviewList) {
                //复核数量
                BigDecimal checkNum = xmslDrawReviewList.getCheckNum();
                //合同清单标号
                String listCode = xmslDrawReviewList.getListCode();
                XmslContractList xmslContractList = contractMap.get(listCode);
                if (null != xmslContractList) {
                    //得到清单单价，优先使用变更后的单价
                    BigDecimal price = xmslContractList.getChangeUnitPrice() == null
                            ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice();
                    if (checkNum != null && price != null){
                        workValue = workValue.add(checkNum.multiply(price));
                    }
                }
            }
            imagePlan.setWorkValue(workValue);
            if (!NumberUtil.equals(workValue, BigDecimal.ZERO)
                    && imagePlan.getPlanCompQuantity() != null
                    && imagePlan.getDesignQuantity() != null
                    && imagePlan.getPlanCompValue() == null) {
                imagePlan.setPlanCompValue(imagePlan.getPlanCompQuantity().divide(imagePlan.getDesignQuantity(), 4, RoundingMode.HALF_UP).multiply(workValue));
            }
        }
    }

    public List<JdglQuarterImagePlan> getJdglQuarterImagePlanListByPlanId(Long planId) {
        JdglQuarterImagePlan jdglQuarterImagePlan = new JdglQuarterImagePlan();
        jdglQuarterImagePlan.setPlanId(planId);
        return getJdglQuarterImagePlanList(jdglQuarterImagePlan);
    }

    @Transactional
    public int insertJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        jdglQuarterImagePlan.setId(IdWorker.createId());
        jdglQuarterImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglQuarterImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglQuarterImagePlanMapper.insertJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    @Transactional
    public int insertJdglQuarterImagePlanList(List<JdglQuarterImagePlan> jdglQuarterImagePlanList) {
        if(CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
            return 0;
        }
        Long planId = jdglQuarterImagePlanList.get(0).getPlanId();

        for (JdglQuarterImagePlan jdglQuarterImagePlan : jdglQuarterImagePlanList) {
            jdglQuarterImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglQuarterImagePlan.setCreateTime(DateUtils.getNowDate());
        }
        TreeCountUtils<JdglQuarterImagePlan> treeCountUtils = new TreeCountUtils<>();
        treeCountUtils.upCountValue(jdglQuarterImagePlanList, "planCompValue");
        jdglQuarterValuePlanService.updateValuePlanData(planId, jdglQuarterImagePlanList);
        return jdglQuarterImagePlanMapper.insertJdglQuarterImagePlanList(jdglQuarterImagePlanList);
    }

    @Transactional
    public int updateJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        jdglQuarterImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglQuarterImagePlanMapper.updateJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    @Transactional
    public int updateJdglQuarterImagePlanList(List<JdglQuarterImagePlan> jdglQuarterImagePlanList) {
        if(!CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
//            List<JdglQuarterImagePlan> jdglQuarterImagePlans = TreeUtil.treeToList(jdglQuarterImagePlanList);
            Long planId = jdglQuarterImagePlanList.get(0).getPlanId();

            for (JdglQuarterImagePlan jdglQuarterImagePlan : jdglQuarterImagePlanList) {
                jdglQuarterImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglQuarterImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            TreeCountUtils<JdglQuarterImagePlan> treeCountUtils = new TreeCountUtils<>();
            treeCountUtils.upCountValue(jdglQuarterImagePlanList, "planCompValue");
            deleteJdglQuarterImagePlanByPlanId(planId);
            jdglQuarterValuePlanService.updateValuePlanData(planId, jdglQuarterImagePlanList);
            return jdglQuarterImagePlanMapper.insertJdglQuarterImagePlanList(jdglQuarterImagePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        jdglQuarterImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglQuarterImagePlanMapper.deleteJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    @Transactional
    public int deleteJdglQuarterImagePlanByPks(List<Long> jdglQuarterImagePlanPkList) {
        return jdglQuarterImagePlanMapper.deleteJdglQuarterImagePlanByPks(jdglQuarterImagePlanPkList);
    }

    @Override
    public int deleteJdglQuarterImagePlanByPlanId(Long planId) {
        return jdglQuarterImagePlanMapper.deleteJdglQuarterImagePlanByPlanId(planId);
    }

    /**
     * 从总进度计划获取数据&未完&
     * @param jdglQuarterPlanParam
     * @return
     */
    @Override
    public JdglQuarterPlan syncFromTotalPlan(JdglQuarterPlan jdglQuarterPlanParam) {

        String year = jdglQuarterPlanParam.getYear();
        String quarter = jdglQuarterPlanParam.getQuarter();

        if(StringUtils.isEmpty(year)||StringUtils.isEmpty(quarter)) {
            throw new RuntimeException("传参异常!");
        }

        List<JdglQuarterImagePlan> returnList = new ArrayList<JdglQuarterImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）
        JdglMainPlan usingJdglMainPlan = iJdglMainPlanService.getUsingJdglMainPlan();
        Map<String, Date> dateRange = StatisticsUtils.getDateRange4Quarter(year, quarter);

        // 获取所有总进度计划数据
        List<JdglMainPlanItem> allMainPlanItem = jdglMainPlanItemService.getJdglMainPlanItemByMainPlanId(usingJdglMainPlan.getId());
        if(CollectionUtils.isEmpty(allMainPlanItem)) {
            return jdglQuarterPlanParam;
        }

        List<JdglMainPlanItem> jdglMainPlanItemList = new ArrayList<>();

        // 获取在日期区间内的总进度计划数据
        List<JdglMainPlanItem> listByDateRange = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(dateRange.get("start"), dateRange.get("end"));

        if(CollectionUtils.isEmpty(listByDateRange)) {
            return jdglQuarterPlanParam;
        }

        // 获取在日期区间内的总进度计划作业数据
        listByDateRange = listByDateRange.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(listByDateRange)) {
            return jdglQuarterPlanParam;
        }

        // 根据作业数据查找上级总体wbs数据
        for (JdglMainPlanItem jdglMainPlanItem : listByDateRange) {
            String ancestors = jdglMainPlanItem.getAncestors();
            List<JdglMainPlanItem> collect = allMainPlanItem.stream().filter(vo -> ancestors.contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)) jdglMainPlanItemList.addAll(collect);
        }

        if(CollectionUtils.isEmpty(jdglMainPlanItemList)){
            return jdglQuarterPlanParam;
        }

        // 数据去重
        jdglMainPlanItemList = jdglMainPlanItemList.stream().distinct().collect(Collectors.toList());

        List<JdglDayScheduleWbs4Value> dayScheduleWbs4ValueList = jdglDayScheduleWbsService.getTotalWbsListByDateRange(StatisticsUtils.addDays(dateRange.get("start"), -1));

        for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItemList) {
            JdglQuarterImagePlan jdglQuarterImagePlan = new JdglQuarterImagePlan();

            jdglQuarterImagePlan.setId(IdWorker.createId());
//            jdglYearImagePlan.setPid(jdglMainPlanItem.getPid());
            jdglQuarterImagePlan.setPtVar1(jdglMainPlanItem.getId() + "");
            jdglQuarterImagePlan.setPtVar2(jdglMainPlanItem.getPid() == null ? null : jdglMainPlanItem.getPid() + "");
            jdglQuarterImagePlan.setPlanId(jdglQuarterPlanParam.getId());
            jdglQuarterImagePlan.setWorkId(jdglMainPlanItem.getId());
            jdglQuarterImagePlan.setWorkCode(jdglMainPlanItem.getItemCode());
            jdglQuarterImagePlan.setWorkName(jdglMainPlanItem.getItemName());
            jdglQuarterImagePlan.setUnit(jdglMainPlanItem.getUnit());
            jdglQuarterImagePlan.setDesignQuantity(jdglMainPlanItem.getQuantity());
            jdglQuarterImagePlan.setSort(jdglMainPlanItem.getSort());
            if(!CollectionUtils.isEmpty(dayScheduleWbs4ValueList)) {
                JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = dayScheduleWbs4ValueList.stream().filter(vo -> jdglMainPlanItem.getItemCode().equals(vo.getWbsCode())).findFirst().orElse(null);
                if(jdglDayScheduleWbs4Value != null) {
                    BigDecimal thisQuantity = jdglDayScheduleWbs4Value.getThisQuantity();
                    jdglQuarterImagePlan.setTotalCompQuantity(thisQuantity);
                    if(thisQuantity != null && jdglMainPlanItem.getQuantity() != null) {
                        jdglQuarterImagePlan.setRemainQuantity(jdglMainPlanItem.getQuantity().subtract(thisQuantity));
                    }
                }
            }
            if(jdglQuarterImagePlan.getTotalCompQuantity() == null && jdglQuarterImagePlan.getDesignQuantity() != null) jdglQuarterImagePlan.setTotalCompQuantity(BigDecimal.ZERO);
            if(jdglQuarterImagePlan.getRemainQuantity() == null) jdglQuarterImagePlan.setRemainQuantity(jdglQuarterImagePlan.getDesignQuantity());
            jdglQuarterImagePlan.setPlanStartDate(jdglMainPlanItem.getStartDate());
            jdglQuarterImagePlan.setPlanEndDate(jdglMainPlanItem.getFinishDate());
            jdglQuarterImagePlan.setWbsCode(jdglMainPlanItem.getWbsCode());
            jdglQuarterImagePlan.setWbsName(jdglMainPlanItem.getWbsName());
            //                jdglYearImagePlan.setWbsId();
            jdglQuarterImagePlan.setResponsePerson(jdglMainPlanItem.getExecuter());
            jdglQuarterImagePlan.setResponsePersonId(jdglMainPlanItem.getExecuterId());
            returnList.add(jdglQuarterImagePlan);
        }

        if(!CollectionUtils.isEmpty(returnList)) {
            for (JdglQuarterImagePlan imagePlan : returnList) {
                JdglQuarterImagePlan imagePlan1 = returnList.stream().filter(vo -> vo.getPtVar1().equals(imagePlan.getPtVar2())).findFirst().orElse(null);
                if(imagePlan1 != null) imagePlan.setPid(imagePlan1.getId());
            }
        }

        // 维护returnList树结构
        List<JdglQuarterImagePlan> build = TreeUtil.build(returnList, null);
        jdglQuarterPlanParam.setJdglQuarterImagePlanList(build);


        // 修改年进度计划主表引用总体计划的版本号
        if(usingJdglMainPlan != null) {
            jdglQuarterPlanParam.setThisTotalVersion(usingJdglMainPlan.getVersion());
        }

        return jdglQuarterPlanParam;
    }

    @Override
    public List<JdglQuarterImagePlan> getWbsListByYearAndQuarter(String year, String quarter) {
        return jdglQuarterImagePlanMapper.getWbsListByYearAndQuarter(year, quarter);
    }

    @Override
    public BigDecimal getThisPlanAmt(Long planId) {
        return jdglQuarterImagePlanMapper.getThisPlanAmt(planId);
    }
}
