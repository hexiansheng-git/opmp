package com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.mapper.JdglMonthImagePlanMapper;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.statistics.util.TreeCountUtils;
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
public class JdglMonthImagePlanServiceImpl implements IJdglMonthImagePlanService {

    @Autowired
    private JdglMonthImagePlanMapper jdglMonthImagePlanMapper;

    @Autowired
    private IJdglMonthValuePlanService jdglMonthValuePlanService;

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


    public JdglMonthImagePlan getJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan) {
        return jdglMonthImagePlanMapper.getJdglMonthImagePlan(jdglMonthImagePlan);
    }

    public List<JdglMonthImagePlan> getJdglMonthImagePlanList(JdglMonthImagePlan jdglMonthImagePlan) {
        Long pid = jdglMonthImagePlan.getPid();
        List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthImagePlanMapper.getJdglMonthImagePlanList(jdglMonthImagePlan);
        if(CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
            return jdglMonthImagePlanList;
        }
        List<JdglMonthImagePlan> build = TreeUtil.build(jdglMonthImagePlanList, pid);
        return build;
    }

    public List<JdglMonthImagePlan> getJdglMonthImagePlanListByPlanId(Long planId) {
        JdglMonthImagePlan jdglMonthImagePlan = new JdglMonthImagePlan();
        jdglMonthImagePlan.setPlanId(planId);
        return getJdglMonthImagePlanList(jdglMonthImagePlan);
    }

    @Override
    public List<JdglMonthImagePlan> getJdglMonthImagePlanListByEndDate(Date endDate) {
        List<JdglMonthImagePlan> jdglMonthImagePlanListByEndDate = jdglMonthImagePlanMapper.getJdglMonthImagePlanListByEndDate(endDate);
        List<String> itemCodes = new ArrayList<>();
        jdglMonthImagePlanListByEndDate.stream().forEach(vo -> {
            itemCodes.add(vo.getWorkCode());
        });

        List<JdglMainPlanItem> mainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemByItemCodes(itemCodes);
        if(mainPlanItemList != null) {
            for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlanListByEndDate) {
                String wbsCode = jdglMonthImagePlan.getWorkCode();
                JdglMainPlanItem jdglMainPlanItem = mainPlanItemList.stream().filter(vo -> wbsCode != null && wbsCode.equals(vo.getItemCode())).findFirst().orElse(null);
                if(jdglMainPlanItem != null) {
                    jdglMonthImagePlan.setId(jdglMainPlanItem.getId());
                    jdglMonthImagePlan.setPid(jdglMainPlanItem.getPid());
                } else {
                    jdglMonthImagePlan.setPid(-1L);
                }
            }
        }
        return jdglMonthImagePlanListByEndDate;
    }

    @Transactional
    public int insertJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan) {
        jdglMonthImagePlan.setId(IdWorker.createId());
        jdglMonthImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglMonthImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglMonthImagePlanMapper.insertJdglMonthImagePlan(jdglMonthImagePlan);
    }

    @Transactional
    public int insertJdglMonthImagePlanList(List<JdglMonthImagePlan> jdglMonthImagePlanList) {
        if(CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
            return 0;
        }
        Long planId = jdglMonthImagePlanList.get(0).getPlanId();

        // 主合同清单
        List<XmslContractList> inventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();

        // 获取图纸复核的清单
        List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();

        //遍历所有的形象计划
        for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlanList) {
            jdglMonthImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglMonthImagePlan.setCreateTime(DateUtils.getNowDate());
            String wbsCode = jdglMonthImagePlan.getWbsCode();
            Long pid = jdglMonthImagePlan.getPid();
            if(CollectionUtils.isEmpty(list)) {
               continue;
            }
            //找到当前计划的父级
            JdglMonthImagePlan jdglMonthImagePlan1 = jdglMonthImagePlanList.stream().filter(vo -> vo.getId().equals(pid)).findFirst().orElse(null);
            BigDecimal compValue = new BigDecimal(0);
            //设计工程量
            BigDecimal designQuantity = jdglMonthImagePlan.getDesignQuantity();
            //计划完成工程量
            BigDecimal planCompQuantity = jdglMonthImagePlan.getPlanCompQuantity();
            //如果父级工程量不等于空，优先使用父级工程量
            if(jdglMonthImagePlan1 != null) designQuantity = jdglMonthImagePlan1.getDesignQuantity();
            BigDecimal rate = new BigDecimal(0);
            //计划完成工程量 除 设计工程量 等于 计划完成比例
            if(planCompQuantity != null && designQuantity != null && rate.compareTo(designQuantity) != 0) {
                rate = planCompQuantity.divide(designQuantity, 4, BigDecimal.ROUND_HALF_UP);
            }
            //找到当前wbs挂接的清单
            List<XmslDrawReviewList> collect = list.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(collect)) {
                continue;
            }
            //遍历所有清单
            for (XmslDrawReviewList xmslDrawReviewList : collect) {
                //清单编号
                String listCode = xmslDrawReviewList.getListCode();
                //复核数量
                BigDecimal checkNum = xmslDrawReviewList.getCheckNum();
                if(CollectionUtils.isEmpty(inventoryList)) {
                    continue;
                }
                //找到对应的合同清单
                XmslContractList xmslContractList = inventoryList.stream().filter(vo -> listCode.equals(vo.getCode())).findFirst().orElse(null);
                if(xmslContractList != null) {
                    //得到清单单价，优先使用变更后的单价
                    BigDecimal price = xmslContractList.getChangeUnitPrice() == null
                            ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice();
                    //清单复核数量 乘 计划完成比例 等于 计划完成数量
                    BigDecimal quantity = checkNum == null
                            ? new BigDecimal(0) : checkNum.multiply(rate);
                    // 计划完成数量 乘 单价 等于 计划完成产值
                    if (quantity != null && price != null) {
                        compValue = compValue.add(quantity.multiply(price));
                    }
                }
                jdglMonthImagePlan.setPlanCompValue(compValue);
            }
        }
        TreeCountUtils<JdglMonthImagePlan> treeCountUtils = new TreeCountUtils<>();
        treeCountUtils.upCountValue(jdglMonthImagePlanList, "planCompValue");
        jdglMonthValuePlanService.updateValuePlanData(planId, jdglMonthImagePlanList);
        return jdglMonthImagePlanMapper.insertJdglMonthImagePlanList(jdglMonthImagePlanList);
    }

    @Transactional
    public int updateJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan) {
        jdglMonthImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMonthImagePlanMapper.updateJdglMonthImagePlan(jdglMonthImagePlan);
    }

    @Transactional
    public int updateJdglMonthImagePlanList(List<JdglMonthImagePlan> jdglMonthImagePlanList) {
        if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
//            List<JdglMonthImagePlan> jdglMonthImagePlans = TreeUtil.treeToList(jdglMonthImagePlanList);
            Long planId = jdglMonthImagePlanList.get(0).getPlanId();

            // 主合同清单
            List<XmslContractList> inventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();

            // 获取图纸复核的清单
            List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();

            for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlanList) {
                jdglMonthImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglMonthImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            TreeCountUtils<JdglMonthImagePlan> treeCountUtils = new TreeCountUtils<>();
            treeCountUtils.upCountValue(jdglMonthImagePlanList, "planCompValue");
            deleteJdglMonthImagePlanByPlanId(planId);
            jdglMonthValuePlanService.updateValuePlanData(planId, jdglMonthImagePlanList);
            return jdglMonthImagePlanMapper.insertJdglMonthImagePlanList(jdglMonthImagePlanList);
        }

        return 0;
    }

    @Transactional
    public int deleteJdglMonthImagePlan(JdglMonthImagePlan jdglMonthImagePlan) {
        jdglMonthImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMonthImagePlanMapper.deleteJdglMonthImagePlan(jdglMonthImagePlan);
    }

    @Transactional
    public int deleteJdglMonthImagePlanByPks(List<Long> jdglMonthImagePlanPkList) {
        return jdglMonthImagePlanMapper.deleteJdglMonthImagePlanByPks(jdglMonthImagePlanPkList);
    }

    @Override
    public int deleteJdglMonthImagePlanByPlanId(Long planId) {
        return jdglMonthImagePlanMapper.deleteJdglMonthImagePlanByPlanId(planId);
    }

    /**
     * 从总进度计划获取数据&未完&
     * @param jdglMonthPlanParam
     * @return
     */
    @Override
    public JdglMonthPlan syncFromTotalPlan(JdglMonthPlan jdglMonthPlanParam) {

        String year = jdglMonthPlanParam.getYear();
        String month = jdglMonthPlanParam.getMonth();

        if(StringUtils.isEmpty(year)||StringUtils.isEmpty(month)) {
            throw new RuntimeException("传参异常!");
        }

        List<JdglMonthImagePlan> returnList = new ArrayList<JdglMonthImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）
        JdglMainPlan usingJdglMainPlan = iJdglMainPlanService.getUsingJdglMainPlan();
        Map<String, Date> dateRange = StatisticsUtils.getDateRange4YearMonth(year, month);

        // 获取所有总进度计划数据
        List<JdglMainPlanItem> allMainPlanItem = jdglMainPlanItemService.getJdglMainPlanItemByMainPlanId(usingJdglMainPlan.getId());
        if(CollectionUtils.isEmpty(allMainPlanItem)) {
            return jdglMonthPlanParam;
        }

        List<JdglMainPlanItem> jdglMainPlanItemList = new ArrayList<>();

        // 获取在日期区间内的总进度计划数据
        List<JdglMainPlanItem> listByDateRange = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(dateRange.get("start"), dateRange.get("end"));

        if(CollectionUtils.isEmpty(listByDateRange)) {
            return jdglMonthPlanParam;
        }

        // 获取在日期区间内的总进度计划作业数据
        listByDateRange = listByDateRange.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(listByDateRange)) {
            return jdglMonthPlanParam;
        }

        // 根据作业数据查找上级总体wbs数据
        for (JdglMainPlanItem jdglMainPlanItem : listByDateRange) {
            String ancestors = jdglMainPlanItem.getAncestors();
            List<JdglMainPlanItem> collect = allMainPlanItem.stream().filter(vo -> ancestors.contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)) jdglMainPlanItemList.addAll(collect);
        }

        if(CollectionUtils.isEmpty(jdglMainPlanItemList)){
            return jdglMonthPlanParam;
        }

        // 数据去重
        jdglMainPlanItemList = jdglMainPlanItemList.stream().distinct().collect(Collectors.toList());

        List<JdglDayScheduleWbs4Value> dayScheduleWbs4ValueList = jdglDayScheduleWbsService.getTotalWbsListByDateRange(StatisticsUtils.addDays(dateRange.get("start"), -1));


        for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItemList) {
            JdglMonthImagePlan imagePlan = new JdglMonthImagePlan();

            imagePlan.setId(IdWorker.createId());
//            jdglYearImagePlan.setPid(jdglMainPlanItem.getPid());
            imagePlan.setPtVar1(jdglMainPlanItem.getId() + "");
            imagePlan.setPtVar2(jdglMainPlanItem.getPid() == null ? null : jdglMainPlanItem.getPid() + "");
            imagePlan.setPlanId(jdglMonthPlanParam.getId());
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
            imagePlan.setIsCriticalPath(jdglMainPlanItem.getIsCritical());
            //                jdglYearImagePlan.setWbsId();
            imagePlan.setResponsePerson(jdglMainPlanItem.getExecuter());
            imagePlan.setResponsePersonId(jdglMainPlanItem.getExecuterId());
            returnList.add(imagePlan);
        }

        if(!CollectionUtils.isEmpty(returnList)) {
            for (JdglMonthImagePlan imagePlan : returnList) {
                JdglMonthImagePlan imagePlan1 = returnList.stream().filter(vo -> vo.getPtVar1().equals(imagePlan.getPtVar2())).findFirst().orElse(null);
                if(imagePlan1 != null) imagePlan.setPid(imagePlan1.getId());
            }
        }

        // 维护returnList树结构
        List<JdglMonthImagePlan> build = TreeUtil.build(returnList, null);
        jdglMonthPlanParam.setJdglMonthImagePlanList(build);


        // 修改年进度计划主表引用总体计划的版本号
        if(usingJdglMainPlan != null) {
            jdglMonthPlanParam.setThisTotalVersion(usingJdglMainPlan.getVersion());
        }

        return jdglMonthPlanParam;
    }

    @Override
    public List<JdglMonthImagePlan> getWbsListByYearAndMonth(String year, String month) {
        return jdglMonthImagePlanMapper.getWbsListByYearAndMonth(year, month);
    }

    @Override
    public BigDecimal getThisPlanAmt(Long planId) {
        return jdglMonthImagePlanMapper.getThisPlanAmt(planId);
    }
}
