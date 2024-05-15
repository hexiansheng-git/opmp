package com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.impl;

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
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.statistics.util.TreeCountUtils;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.mapper.JdglWeekImagePlanMapper;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.IJdglWeekImagePlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service.IJdglWeekValuePlanService;
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
        /*计算作业产值 : ∑作业挂接的清单价*复核数量*/
        workValueCalc(jdglWeekImagePlanList);
        List<JdglWeekImagePlan> build = TreeUtil.build(jdglWeekImagePlanList, pid);
        return build;
    }

    //计算作业产值
    private void workValueCalc(List<JdglWeekImagePlan> jdglWeekImagePlanList) {
        // 获取图纸复核的清单
        List<XmslDrawReviewList> viewList = drawReviewListService.getFullEffectList();
        viewList.stream().filter(p -> StrUtil.isBlankIfStr(p.getWbsCode()))
                .forEach(p -> {
                    String[] split = p.getWbsCode().split("-");
                    p.setWbsCode(split[split.length-1]);
                });
        // 主合同清单
        List<XmslContractList> contractList = xmslContractListService.getValidMaxVersionContractInventoryList();
        Map<String, XmslContractList> contractMap = contractList.stream().collect(Collectors.toMap(XmslContractList::getCode, v -> v, (k1, k2) -> k1));
        for (JdglWeekImagePlan imagePlan : jdglWeekImagePlanList) {
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
                //计划完成产值 = 计划完成工程量/设计工程量*作业产值
                imagePlan.setPlanCompValue(imagePlan.getPlanCompQuantity().divide(imagePlan.getDesignQuantity(), 4, RoundingMode.HALF_UP).multiply(workValue));
            }
        }
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
        for (JdglWeekImagePlan jdglWeekImagePlan : jdglWeekImagePlanList) {
            jdglWeekImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglWeekImagePlan.setCreateTime(DateUtils.getNowDate());
        }
        TreeCountUtils<JdglWeekImagePlan> treeCountUtils = new TreeCountUtils<>();
        treeCountUtils.upCountValue(jdglWeekImagePlanList, "planCompValue");
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
            for (JdglWeekImagePlan jdglWeekImagePlan : jdglWeekImagePlanList) {
                jdglWeekImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglWeekImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            TreeCountUtils<JdglWeekImagePlan> treeCountUtils = new TreeCountUtils<>();
            treeCountUtils.upCountValue(jdglWeekImagePlanList, "planCompValue");
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
        // 获取图纸复核的清单，用于回填设计工程量
        List<XmslDrawReviewList> viewList = drawReviewListService.getFullEffectList();
        viewList.stream().filter(p -> StrUtil.isBlankIfStr(p.getWbsCode()))
                .forEach(p -> {
                    String[] split = p.getWbsCode().split("-");
                    p.setWbsCode(split[split.length-1]);
                });
        Map<String, BigDecimal> viewMap = viewList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getWbsCode()))
                .collect(Collectors.toMap(XmslDrawReviewList::getWbsCode, XmslDrawReviewList::getCheckNum));

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
            imagePlan.setDesignQuantity(viewMap.get(jdglMainPlanItem.getWbsCode()) == null ? BigDecimal.ZERO : viewMap.get(jdglMainPlanItem.getWbsCode()));
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
            /*计算作业产值 : ∑作业挂接的清单价*复核数量*/
            workValueCalc(returnList);
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
