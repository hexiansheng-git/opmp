package com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.mapper.JdglWeekImagePlanMapper;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.IJdglWeekImagePlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service.IJdglWeekValuePlanService;
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

import java.math.BigDecimal;
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
public class JdglWeekImagePlanServiceImpl implements IJdglWeekImagePlanService {

    @Autowired
    private JdglWeekImagePlanMapper jdglWeekImagePlanMapper;

    @Autowired
    private IJdglWeekValuePlanService jdglWeekValuePlanService;

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


    public JdglWeekImagePlan getJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan) {
        return jdglWeekImagePlanMapper.getJdglWeekImagePlan(jdglWeekImagePlan);
    }

    public List<JdglWeekImagePlan> getJdglWeekImagePlanList(JdglWeekImagePlan jdglWeekImagePlan) {
        Long pid = jdglWeekImagePlan.getPid();
        List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekImagePlanMapper.getJdglWeekImagePlanList(jdglWeekImagePlan);
        if(CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
            return jdglWeekImagePlanList;
        }
        List<JdglWeekImagePlan> build = TreeUtil.build(jdglWeekImagePlanList, pid);
        return build;
    }

    public List<JdglWeekImagePlan> getJdglWeekImagePlanListByPlanId(Long planId) {
        JdglWeekImagePlan jdglWeekImagePlan = new JdglWeekImagePlan();
        jdglWeekImagePlan.setPlanId(planId);
        return getJdglWeekImagePlanList(jdglWeekImagePlan);
    }

    @Transactional
    public int insertJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan) {
        jdglWeekImagePlan.setId(IdWorker.createId());
        jdglWeekImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglWeekImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglWeekImagePlanMapper.insertJdglWeekImagePlan(jdglWeekImagePlan);
    }

    @Transactional
    public int insertJdglWeekImagePlanList(List<JdglWeekImagePlan> jdglWeekImagePlanList) {
        if(CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
            return 0;
        }
        Long planId = jdglWeekImagePlanList.get(0).getPlanId();

        // 主合同清单
        List<XmslContractList> inventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();

        // 获取图纸复核的清单
        List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();

        for (JdglWeekImagePlan jdglWeekImagePlan : jdglWeekImagePlanList) {
            jdglWeekImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglWeekImagePlan.setCreateTime(DateUtils.getNowDate());
            String wbsCode = jdglWeekImagePlan.getWbsCode();
            Long pid = jdglWeekImagePlan.getPid();
            if(!CollectionUtils.isEmpty(list)) {
                JdglWeekImagePlan imagePlan = jdglWeekImagePlanList.stream().filter(vo -> vo.getId().equals(pid)).findFirst().orElse(null);
                BigDecimal compValue = new BigDecimal(0);
                BigDecimal designQuantity = jdglWeekImagePlan.getDesignQuantity();
                BigDecimal planCompQuantity = jdglWeekImagePlan.getPlanCompQuantity();
                if(imagePlan != null) designQuantity = imagePlan.getDesignQuantity();
                BigDecimal rate = new BigDecimal(0);
                if(planCompQuantity != null && designQuantity != null && rate.compareTo(designQuantity) != 0) {
                    rate = planCompQuantity.divide(designQuantity, 4, BigDecimal.ROUND_HALF_UP);
                }
                List<XmslDrawReviewList> collect = list.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(collect)) {
                    for (XmslDrawReviewList xmslDrawReviewList : collect) {
                        String listCode = xmslDrawReviewList.getListCode();
                        BigDecimal checkNum = xmslDrawReviewList.getCheckNum();
                        if(!CollectionUtils.isEmpty(inventoryList)) {
                            XmslContractList xmslContractList = inventoryList.stream().filter(vo -> listCode.equals(vo.getCode())).findFirst().orElse(null);
                            if(xmslContractList != null) {
                                BigDecimal price = xmslContractList.getChangeUnitPrice() == null
                                        ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice();
                                BigDecimal quantity = checkNum == null
                                        ? new BigDecimal(0) : checkNum.multiply(rate);
                                if (quantity != null && price != null) {
                                    compValue = compValue.add(quantity.multiply(price));
                                }
                            }
                        }
                    }
                }
                jdglWeekImagePlan.setPlanCompValue(compValue);
            }
        }

        jdglWeekValuePlanService.updateValuePlanData(planId, jdglWeekImagePlanList);
        return jdglWeekImagePlanMapper.insertJdglWeekImagePlanList(jdglWeekImagePlanList);
    }

    @Transactional
    public int updateJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan) {
        jdglWeekImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglWeekImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglWeekImagePlanMapper.updateJdglWeekImagePlan(jdglWeekImagePlan);
    }

    @Transactional
    public int updateJdglWeekImagePlanList(List<JdglWeekImagePlan> jdglWeekImagePlanList) {
        if(!CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
            Long planId = jdglWeekImagePlanList.get(0).getPlanId();
//            List<JdglWeekImagePlan> jdglWeekImagePlans = TreeUtil.treeToList(jdglWeekImagePlanList);

            // 主合同清单
            List<XmslContractList> inventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();

            // 获取图纸复核的清单
            List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();

            for (JdglWeekImagePlan jdglWeekImagePlan : jdglWeekImagePlanList) {
                jdglWeekImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglWeekImagePlan.setUpdateTime(DateUtils.getNowDate());
                String wbsCode = jdglWeekImagePlan.getWbsCode();
                Long pid = jdglWeekImagePlan.getPid();
                if(!CollectionUtils.isEmpty(list)) {
                    JdglWeekImagePlan imagePlan = jdglWeekImagePlanList.stream().filter(vo -> vo.getId().equals(pid)).findFirst().orElse(null);
                    BigDecimal compValue = new BigDecimal(0);
                    BigDecimal designQuantity = jdglWeekImagePlan.getDesignQuantity();
                    BigDecimal planCompQuantity = jdglWeekImagePlan.getPlanCompQuantity();
                    if(imagePlan != null) designQuantity = imagePlan.getDesignQuantity();
                    BigDecimal rate = new BigDecimal(0);
                    if(planCompQuantity != null && designQuantity != null && rate.compareTo(designQuantity) != 0) {
                        rate = planCompQuantity.divide(designQuantity, 4, BigDecimal.ROUND_HALF_UP);
                    }
                    List<XmslDrawReviewList> collect = list.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(collect)) {
                        for (XmslDrawReviewList xmslDrawReviewList : collect) {
                            String listCode = xmslDrawReviewList.getListCode();
                            BigDecimal checkNum = xmslDrawReviewList.getCheckNum();
                            if(!CollectionUtils.isEmpty(inventoryList)) {
                                XmslContractList xmslContractList = inventoryList.stream().filter(vo -> listCode.equals(vo.getCode())).findFirst().orElse(null);
                                if(xmslContractList != null) {
                                    BigDecimal price = xmslContractList.getChangeUnitPrice() == null
                                            ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice();
                                    BigDecimal quantity = checkNum == null
                                            ? new BigDecimal(0) : checkNum.multiply(rate);
                                    if (quantity != null && price != null) {
                                        compValue = compValue.add(quantity.multiply(price));
                                    }
                                }
                            }
                        }
                    }
                    jdglWeekImagePlan.setPlanCompValue(compValue);
                }
            }
            deleteJdglWeekImagePlanByPlanId(planId);
            jdglWeekValuePlanService.updateValuePlanData(planId, jdglWeekImagePlanList);
            return jdglWeekImagePlanMapper.insertJdglWeekImagePlanList(jdglWeekImagePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglWeekImagePlan(JdglWeekImagePlan jdglWeekImagePlan) {
        jdglWeekImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglWeekImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglWeekImagePlanMapper.deleteJdglWeekImagePlan(jdglWeekImagePlan);
    }

    @Transactional
    public int deleteJdglWeekImagePlanByPks(List<Long> jdglWeekImagePlanPkList) {
        return jdglWeekImagePlanMapper.deleteJdglWeekImagePlanByPks(jdglWeekImagePlanPkList);
    }

    @Override
    public int deleteJdglWeekImagePlanByPlanId(Long planId) {
        return jdglWeekImagePlanMapper.deleteJdglWeekImagePlanByPlanId(planId);
    }

    /**
     * 从总进度计划获取数据&未完&
     * @param jdglWeekPlanParam
     * @return
     */
    @Override
    public JdglWeekPlan syncFromTotalPlan(JdglWeekPlan jdglWeekPlanParam) {

        String year = jdglWeekPlanParam.getYear();
        String week = jdglWeekPlanParam.getWeek();

        if(StringUtils.isEmpty(year)||StringUtils.isEmpty(week)) {
            throw new RuntimeException("传参异常!");
        }

        List<JdglWeekImagePlan> returnList = new ArrayList<JdglWeekImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）
        JdglMainPlan usingJdglMainPlan = iJdglMainPlanService.getUsingJdglMainPlan();
        Map<String, Date> dateRange = StatisticsUtils.getDateRange4Week(year, week);

        // 获取所有总进度计划数据
        List<JdglMainPlanItem> allMainPlanItem = jdglMainPlanItemService.getJdglMainPlanItemByMainPlanId(usingJdglMainPlan.getId());
        if(CollectionUtils.isEmpty(allMainPlanItem)) {
            return jdglWeekPlanParam;
        }

        List<JdglMainPlanItem> jdglMainPlanItemList = new ArrayList<>();

        // 获取在日期区间内的总进度计划数据
        List<JdglMainPlanItem> listByDateRange = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(dateRange.get("start"), dateRange.get("end"));

        if(CollectionUtils.isEmpty(listByDateRange)) {
            return jdglWeekPlanParam;
        }

        // 获取在日期区间内的总进度计划作业数据
        listByDateRange = listByDateRange.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(listByDateRange)) {
            return jdglWeekPlanParam;
        }

        // 根据作业数据查找上级总体wbs数据
        for (JdglMainPlanItem jdglMainPlanItem : listByDateRange) {
            String ancestors = jdglMainPlanItem.getAncestors();
            List<JdglMainPlanItem> collect = allMainPlanItem.stream().filter(vo -> ancestors.contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)) jdglMainPlanItemList.addAll(collect);
        }

        if(CollectionUtils.isEmpty(jdglMainPlanItemList)){
            return jdglWeekPlanParam;
        }

        // 数据去重
        jdglMainPlanItemList = jdglMainPlanItemList.stream().distinct().collect(Collectors.toList());

        List<JdglDayScheduleWbs4Value> dayScheduleWbs4ValueList = jdglDayScheduleWbsService.getTotalWbsListByDateRange(StatisticsUtils.addDays(dateRange.get("start"), -1));


        for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItemList) {
            JdglWeekImagePlan imagePlan = new JdglWeekImagePlan();

            imagePlan.setId(IdWorker.createId());
//            jdglYearImagePlan.setPid(jdglMainPlanItem.getPid());
            imagePlan.setPtVar1(jdglMainPlanItem.getId() + "");
            imagePlan.setPtVar2(jdglMainPlanItem.getPid() == null ? null : jdglMainPlanItem.getPid() + "");
            imagePlan.setPlanId(jdglWeekPlanParam.getId());
            imagePlan.setWorkId(jdglMainPlanItem.getId());
            imagePlan.setWorkCode(jdglMainPlanItem.getItemCode());
            imagePlan.setWorkName(jdglMainPlanItem.getItemName());
            imagePlan.setUnit(jdglMainPlanItem.getUnit());
            imagePlan.setDesignQuantity(jdglMainPlanItem.getQuantity());
            imagePlan.setSort(jdglMainPlanItem.getSort());
            if(!CollectionUtils.isEmpty(dayScheduleWbs4ValueList)) {
                JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = dayScheduleWbs4ValueList.stream().filter(vo -> jdglMainPlanItem.getItemCode().equals(vo.getWbsCode())).findFirst().orElse(null);
                if(jdglDayScheduleWbs4Value != null) {
                    BigDecimal thisQuantity = jdglDayScheduleWbs4Value.getThisQuantity();
                    imagePlan.setTotalCompQuantity(thisQuantity);
                    if(thisQuantity != null && jdglMainPlanItem.getQuantity() != null) {
                        imagePlan.setRemainQuantity(jdglMainPlanItem.getQuantity().subtract(thisQuantity));
                    }
                }
            }
            if(imagePlan.getTotalCompQuantity() == null && imagePlan.getDesignQuantity() != null) imagePlan.setTotalCompQuantity(BigDecimal.ZERO);
            if(imagePlan.getRemainQuantity() == null) imagePlan.setRemainQuantity(imagePlan.getDesignQuantity());
            imagePlan.setPlanStartDate(jdglMainPlanItem.getStartDate());
            imagePlan.setPlanEndDate(jdglMainPlanItem.getFinishDate());
            imagePlan.setWbsCode(jdglMainPlanItem.getWbsCode());
            imagePlan.setWbsName(jdglMainPlanItem.getWbsName());
            //                jdglYearImagePlan.setWbsId();
            imagePlan.setResponsePerson(jdglMainPlanItem.getExecuter());
            imagePlan.setResponsePersonId(jdglMainPlanItem.getExecuterId());
            returnList.add(imagePlan);
        }

        if(!CollectionUtils.isEmpty(returnList)) {
            for (JdglWeekImagePlan imagePlan : returnList) {
                JdglWeekImagePlan imagePlan1 = returnList.stream().filter(vo -> vo.getPtVar1().equals(imagePlan.getPtVar2())).findFirst().orElse(null);
                if(imagePlan1 != null) imagePlan.setPid(imagePlan1.getId());
            }
        }

        // 维护returnList树结构
        List<JdglWeekImagePlan> build = TreeUtil.build(returnList, null);
        jdglWeekPlanParam.setJdglWeekImagePlanList(build);


        // 修改年进度计划主表引用总体计划的版本号
        if(usingJdglMainPlan != null) {
            jdglWeekPlanParam.setThisTotalVersion(usingJdglMainPlan.getVersion());
        }

        return jdglWeekPlanParam;
    }

    @Override
    public List<JdglWeekImagePlan> getWbsListByYearAndWeek(String year, String week) {
        return jdglWeekImagePlanMapper.getWbsListByYearAndWeek(year, week);
    }

    @Override
    public BigDecimal getThisPlanAmt(Long id) {
        return jdglWeekImagePlanMapper.getThisPlanAmt(id);
    }
}
