package com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
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


    public JdglQuarterImagePlan getJdglQuarterImagePlan(JdglQuarterImagePlan jdglQuarterImagePlan) {
        return jdglQuarterImagePlanMapper.getJdglQuarterImagePlan(jdglQuarterImagePlan);
    }

    public List<JdglQuarterImagePlan> getJdglQuarterImagePlanList(JdglQuarterImagePlan jdglQuarterImagePlan) {
        Long pid = jdglQuarterImagePlan.getPid();
        List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterImagePlanMapper.getJdglQuarterImagePlanList(jdglQuarterImagePlan);
        if(CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
            return jdglQuarterImagePlanList;
        }
        List<JdglQuarterImagePlan> build = TreeUtil.build(jdglQuarterImagePlanList, pid);
        return build;
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
//            jdglQuarterImagePlan.setId(IdWorker.createId());
            jdglQuarterImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglQuarterImagePlan.setCreateTime(DateUtils.getNowDate());
        }
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

            // 主合同清单
            List<XmslContractList> inventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();

            // 获取图纸复核的清单
            List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();
            for (JdglQuarterImagePlan jdglQuarterImagePlan : jdglQuarterImagePlanList) {
                jdglQuarterImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglQuarterImagePlan.setUpdateTime(DateUtils.getNowDate());
                String wbsCode = jdglQuarterImagePlan.getWbsCode();
                if(!CollectionUtils.isEmpty(list)) {
                    BigDecimal compValue = new BigDecimal(0);
                    BigDecimal designQuantity = jdglQuarterImagePlan.getDesignQuantity();
                    BigDecimal planCompQuantity = jdglQuarterImagePlan.getPlanCompQuantity();
                    BigDecimal rate = new BigDecimal(0);
                    if(planCompQuantity != null && designQuantity != null && rate.compareTo(designQuantity) != 0) {
                        rate = planCompQuantity.divide(designQuantity);
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
                    jdglQuarterImagePlan.setPlanCompValue(compValue);
                }
            }
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
        Map<String, Date> dateRange4Quarter = StatisticsUtils.getDateRange4Quarter(year, quarter);
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(dateRange4Quarter.get("start"), dateRange4Quarter.get("end"));

        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return jdglQuarterPlanParam;
        }

        for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItemList) {
            JdglQuarterImagePlan jdglQuarterImagePlan = new JdglQuarterImagePlan();

            jdglQuarterImagePlan.setId(IdWorker.createId());
//            jdglYearImagePlan.setPid(jdglMainPlanItem.getPid());
            jdglQuarterImagePlan.setPtVar1(jdglMainPlanItem.getId() + "");
            jdglQuarterImagePlan.setPtVar2(jdglMainPlanItem.getPid() + "");
            jdglQuarterImagePlan.setPlanId(jdglQuarterPlanParam.getId());
            jdglQuarterImagePlan.setWorkId(jdglMainPlanItem.getId());
            jdglQuarterImagePlan.setWorkCode(jdglMainPlanItem.getItemCode());
            jdglQuarterImagePlan.setWorkName(jdglMainPlanItem.getItemName());
            jdglQuarterImagePlan.setUnit(jdglMainPlanItem.getUnit());
            jdglQuarterImagePlan.setDesignQuantity(jdglMainPlanItem.getQuantity());
            jdglQuarterImagePlan.setTotalCompQuantity(null);
            jdglQuarterImagePlan.setRemainQuantity(null);
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
                JdglQuarterImagePlan imagePlan1 = returnList.stream().filter(vo -> imagePlan.getPtVar2().equals(vo.getPtVar1())).findFirst().orElse(null);
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
}
