package com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.impl;

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
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.mapper.JdglYearImagePlanMapper;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.IJdglYearValuePlanService;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
@Service
public class JdglYearImagePlanServiceImpl implements IJdglYearImagePlanService {

    @Autowired
    private JdglYearImagePlanMapper jdglYearImagePlanMapper;

    @Autowired
    private IJdglYearValuePlanService jdglYearValuePlanService;

    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;

    @Autowired
    private IJdglMainPlanService iJdglMainPlanService;

    @Autowired
    private IXmslContractListService xmslContractListService;

    @Autowired
    private IXmslDrawReviewListService drawReviewListService;

    @Autowired
    private IJdglDayScheduleWbsService jdglDayScheduleWbsService;


    public JdglYearImagePlan getJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        return jdglYearImagePlanMapper.getJdglYearImagePlan(jdglYearImagePlan);
    }

    public List<JdglYearImagePlan> getJdglYearImagePlanList(JdglYearImagePlan jdglYearImagePlan) {
        Long pid = jdglYearImagePlan.getPid();
        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearImagePlanMapper.getJdglYearImagePlanList(jdglYearImagePlan);
        if (CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            return jdglYearImagePlanList;
        }
        /*计算作业产值 : ∑作业挂接的清单价*复核数量*/
        workValueCalc(jdglYearImagePlanList);
        List<JdglYearImagePlan> build = TreeUtil.build(jdglYearImagePlanList, pid);
        return build;
    }

    //计算作业产值
    private void workValueCalc(List<JdglYearImagePlan> jdglYearImagePlanList) {
        // 获取图纸复核的清单
        List<XmslDrawReviewList> viewList = drawReviewListService.getFullEffectList();
        viewList.stream().filter(p -> StrUtil.isNotBlank(p.getWbsCode()))
                .forEach(p -> {
                    String[] split = p.getWbsCode().split("-");
                    p.setWbsCode(split[split.length - 1]);
                });
        // 主合同清单
        List<XmslContractList> contractList = xmslContractListService.getValidMaxVersionContractInventoryList();
        Map<String, XmslContractList> contractMap = contractList.stream().collect(Collectors.toMap(XmslContractList::getCode, v -> v, (k1, k2) -> k1));
        for (JdglYearImagePlan imagePlan : jdglYearImagePlanList) {
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
                    if (checkNum != null && price != null) {
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

    @Override
    public List<JdglYearImagePlan> getJdglYearImagePlanList4Lazy(JdglYearImagePlan jdglYearImagePlanParam) {

        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearImagePlanMapper.getJdglYearImagePlanList(jdglYearImagePlanParam);

        if (jdglYearImagePlanParam.getPid() == null) {
            jdglYearImagePlanList = jdglYearImagePlanList.stream().filter(vo -> vo.getPid() == null).collect(Collectors.toList());
        }


        if (!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
                if (jdglYearImagePlan.getWorkCode().equals(jdglYearImagePlan.getWbsCode())) {
                    jdglYearImagePlan.setHaveChildren(1);
                } else {
                    jdglYearImagePlan.setHaveChildren(0);
                }
            }
        }

        return jdglYearImagePlanList;
    }

    public List<JdglYearImagePlan> getJdglYearImagePlanListByYearPlanId(Long yearPlanId) {
        JdglYearImagePlan jdglYearImagePlan = new JdglYearImagePlan();
        jdglYearImagePlan.setYearPlanId(yearPlanId);
        return getJdglYearImagePlanList(jdglYearImagePlan);
    }

    @Transactional
    public int insertJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        jdglYearImagePlan.setId(IdWorker.createId());
        jdglYearImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglYearImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.insertJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int insertJdglYearImagePlanList(List<JdglYearImagePlan> jdglYearImagePlanList) {
        if (CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            return 0;
        }
        Long yearPlanId = jdglYearImagePlanList.get(0).getYearPlanId();
        for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
            jdglYearImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglYearImagePlan.setCreateTime(DateUtils.getNowDate());
        }
        TreeCountUtils<JdglYearImagePlan> treeCountUtils = new TreeCountUtils<>();
        treeCountUtils.upCountValue(jdglYearImagePlanList, "planCompValue");
        int i = jdglYearImagePlanMapper.insertJdglYearImagePlanList(jdglYearImagePlanList);
        jdglYearValuePlanService.updateValuePlanData(yearPlanId, jdglYearImagePlanList);
        return i;
    }

    @Transactional
    public int updateJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        jdglYearImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.updateJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int updateJdglYearImagePlanList(List<JdglYearImagePlan> jdglYearImagePlanList) {
        if (!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
//            List<JdglYearImagePlan> jdglYearImagePlanList1 = TreeUtil.treeToList(jdglYearImagePlanList);
            Long yearPlanId = jdglYearImagePlanList.get(0).getYearPlanId();
            for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
                jdglYearImagePlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            TreeCountUtils<JdglYearImagePlan> treeCountUtils = new TreeCountUtils<>();
            treeCountUtils.upCountValue(jdglYearImagePlanList, "planCompValue");
            deleteJdglYearImagePlanByYearPlanId(yearPlanId);
            int i = jdglYearImagePlanMapper.insertJdglYearImagePlanList(jdglYearImagePlanList);
            jdglYearValuePlanService.updateValuePlanData(yearPlanId, jdglYearImagePlanList);
            return i;
        }
        return 0;
    }

    @Transactional
    public int deleteJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
//        jdglYearImagePlan.setUpdateUser(SecurityUtils.getUserName());
//        jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.deleteJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int deleteJdglYearImagePlanByPks(List<Long> jdglYearImagePlanPkList) {
        return jdglYearImagePlanMapper.deleteJdglYearImagePlanByPks(jdglYearImagePlanPkList);
    }

    @Override
    public int deleteJdglYearImagePlanByYearPlanId(Long yearPlanId) {
        return jdglYearImagePlanMapper.deleteJdglYearImagePlanByYearPlanId(yearPlanId);
    }

    /**
     * 从总进度计划获取数据&未完&
     *
     * @param jdglYearPlanParam
     * @return
     */
    @Override
    public JdglYearPlan syncFromTotalPlan(JdglYearPlan jdglYearPlanParam) {

        // 年份
        String year = jdglYearPlanParam.getYear();

        if (StringUtils.isEmpty(year)) {
            throw new RuntimeException("传参异常!");
        }

        List<JdglYearImagePlan> returnList = new ArrayList<JdglYearImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）
        JdglMainPlan usingJdglMainPlan = iJdglMainPlanService.getUsingJdglMainPlan();
        Map<String, Date> dateRange = StatisticsUtils.getDateRange4Year(year);

        // 获取所有总进度计划数据
        List<JdglMainPlanItem> allMainPlanItem = jdglMainPlanItemService.getJdglMainPlanItemByMainPlanId(usingJdglMainPlan.getId());
        if (CollectionUtils.isEmpty(allMainPlanItem)) {
            return jdglYearPlanParam;
        }

        // 获取图纸复核的清单，用于回填设计工程量
        List<XmslDrawReviewList> viewListAll = drawReviewListService.getFullEffectList();
        List<XmslDrawReviewList> viewList = viewListAll.stream().filter(p -> StrUtil.isNotBlank(p.getWbsCode()) && p.getImageProgress().equals("1")).collect(Collectors.toList());
        viewList.forEach(p -> {
            String[] split = p.getWbsCode().split("-");
            p.setWbsCode(split[split.length - 1]);
        });
        Map<String, BigDecimal> viewMap = viewList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getWbsCode()))
                .collect(Collectors.groupingBy(XmslDrawReviewList::getWbsCode, Collectors.reducing(BigDecimal.ZERO, XmslDrawReviewList::getCheckNum, BigDecimal::add)));


        List<JdglMainPlanItem> jdglMainPlanItemList = new ArrayList<>();

        // 获取在日期区间内的总进度计划数据
        List<JdglMainPlanItem> listByDateRange = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(dateRange.get("start"), dateRange.get("end"));

        if (CollectionUtils.isEmpty(listByDateRange)) {
            return jdglYearPlanParam;
        }

        // 获取在日期区间内的总进度计划作业数据
        listByDateRange = listByDateRange.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(listByDateRange)) {
            return jdglYearPlanParam;
        }

        // 根据作业数据查找上级总体wbs数据
        for (JdglMainPlanItem jdglMainPlanItem : listByDateRange) {
            String ancestors = jdglMainPlanItem.getAncestors();
            List<JdglMainPlanItem> collect = allMainPlanItem.stream().filter(vo -> ancestors.contains(vo.getAncestors())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) jdglMainPlanItemList.addAll(collect);
        }

        if (CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return jdglYearPlanParam;
        }

        // 数据去重
        jdglMainPlanItemList = jdglMainPlanItemList.stream().distinct().collect(Collectors.toList());

        List<JdglDayScheduleWbs4Value> dayScheduleWbs4ValueList = jdglDayScheduleWbsService.getTotalWbsListByDateRange(StatisticsUtils.addDays(dateRange.get("start"), -1));

        for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItemList) {
            JdglYearImagePlan jdglYearImagePlan = new JdglYearImagePlan();

            jdglYearImagePlan.setId(IdWorker.createId());
//            jdglYearImagePlan.setPid(jdglMainPlanItem.getPid());
            jdglYearImagePlan.setPtVar1(jdglMainPlanItem.getId() + "");
            jdglYearImagePlan.setPtVar2(jdglMainPlanItem.getPid() == null ? null : jdglMainPlanItem.getPid() + "");
            jdglYearImagePlan.setYearPlanId(jdglYearPlanParam.getId());
            jdglYearImagePlan.setWorkId(jdglMainPlanItem.getId());
            jdglYearImagePlan.setWorkCode(jdglMainPlanItem.getItemCode());
            jdglYearImagePlan.setWorkName(jdglMainPlanItem.getItemName());
            jdglYearImagePlan.setUnit(jdglMainPlanItem.getUnit());
            jdglYearImagePlan.setDesignQuantity(viewMap.get(jdglMainPlanItem.getItemCode()) == null ? BigDecimal.ZERO : viewMap.get(jdglMainPlanItem.getItemCode()));
            jdglYearImagePlan.setSort(jdglMainPlanItem.getSort());
            if (!CollectionUtils.isEmpty(dayScheduleWbs4ValueList)) {
                JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = dayScheduleWbs4ValueList.stream().filter(vo -> jdglMainPlanItem.getItemCode().equals(vo.getWbsCode())).findFirst().orElse(null);
                if (jdglDayScheduleWbs4Value != null) {
                    BigDecimal thisQuantity = jdglDayScheduleWbs4Value.getThisQuantity();
                    jdglYearImagePlan.setTotalCompQuantity(thisQuantity);
                    if (thisQuantity != null && jdglMainPlanItem.getQuantity() != null) {
                        jdglYearImagePlan.setRemainQuantity(jdglMainPlanItem.getQuantity().subtract(thisQuantity));
                    }
                }
            }
            if (jdglYearImagePlan.getTotalCompQuantity() == null && jdglYearImagePlan.getDesignQuantity() != null)
                jdglYearImagePlan.setTotalCompQuantity(BigDecimal.ZERO);
            if (jdglYearImagePlan.getRemainQuantity() == null)
                jdglYearImagePlan.setRemainQuantity(jdglYearImagePlan.getDesignQuantity());
            jdglYearImagePlan.setPlanStartDate(jdglMainPlanItem.getStartDate());
            jdglYearImagePlan.setPlanEndDate(jdglMainPlanItem.getFinishDate());
            jdglYearImagePlan.setWbsCode(jdglMainPlanItem.getWbsCode());
            jdglYearImagePlan.setWbsName(jdglMainPlanItem.getWbsName());
            //                jdglYearImagePlan.setWbsId();
            jdglYearImagePlan.setResponsePerson(jdglMainPlanItem.getExecuter());
            jdglYearImagePlan.setResponsePersonId(jdglMainPlanItem.getExecuterId());
            returnList.add(jdglYearImagePlan);
        }

        if (!CollectionUtils.isEmpty(returnList)) {
            for (JdglYearImagePlan yearImagePlan : returnList) {
                JdglYearImagePlan jdglYearImagePlan = returnList.stream().filter(vo -> vo.getPtVar1().equals(yearImagePlan.getPtVar2())).findFirst().orElse(null);
                if (jdglYearImagePlan != null) yearImagePlan.setPid(jdglYearImagePlan.getId());
            }

            /*计算作业产值 : ∑作业挂接的清单价*复核数量*/
            workValueCalc(returnList);
        }

        // 维护returnList树结构
        List<JdglYearImagePlan> build = TreeUtil.build(returnList, null);

        jdglYearPlanParam.setJdglYearImagePlanList(build);

        // 修改年进度计划主表引用总体计划的版本号
        if (usingJdglMainPlan != null) {
            jdglYearPlanParam.setThisTotalVersion(usingJdglMainPlan.getVersion());
        }

        return jdglYearPlanParam;
    }

    @Override
    public List<JdglYearImagePlan> getWbsListByYear(String year) {
        return jdglYearImagePlanMapper.getWbsListByYear(year);
    }

    @Override
    public BigDecimal getThisPlanAmt(Long yearPlanId) {
        return jdglYearImagePlanMapper.getThisPlanAmt(yearPlanId);
    }
}
