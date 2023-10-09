package com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.IJdglYearValuePlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.mapper.JdglYearImagePlanMapper;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.utils.idworker.IdWorker;

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


    public JdglYearImagePlan getJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        return jdglYearImagePlanMapper.getJdglYearImagePlan(jdglYearImagePlan);
    }

    public List<JdglYearImagePlan> getJdglYearImagePlanList(JdglYearImagePlan jdglYearImagePlan) {
        Long pid = jdglYearImagePlan.getPid();
        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearImagePlanMapper.getJdglYearImagePlanList(jdglYearImagePlan);
        if(CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            return jdglYearImagePlanList;
        }
        List<JdglYearImagePlan> build = TreeUtil.build(jdglYearImagePlanList, pid);
        return build;
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
        if(CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            return 0;
        }
        Long yearPlanId = jdglYearImagePlanList.get(0).getYearPlanId();
        for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
//            jdglYearImagePlan.setId(IdWorker.createId());
            jdglYearImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglYearImagePlan.setCreateTime(DateUtils.getNowDate());
        }
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
        if(!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
//            List<JdglYearImagePlan> jdglYearImagePlanList1 = TreeUtil.treeToList(jdglYearImagePlanList);
            Long yearPlanId = jdglYearImagePlanList.get(0).getYearPlanId();

            // 主合同清单
            List<XmslContractList> inventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();

            // 获取图纸复核的清单
            List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();

            for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
                jdglYearImagePlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
                String wbsCode = jdglYearImagePlan.getWbsCode();
                if(!CollectionUtils.isEmpty(list)) {
                    BigDecimal compValue = new BigDecimal(0);
                    BigDecimal designQuantity = jdglYearImagePlan.getDesignQuantity();
                    BigDecimal planCompQuantity = jdglYearImagePlan.getPlanCompQuantity();
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
                    jdglYearImagePlan.setPlanCompValue(compValue);
                }
            }
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
     * @param jdglYearPlanParam
     * @return
     */
    @Override
    public JdglYearPlan syncFromTotalPlan(JdglYearPlan jdglYearPlanParam) {

        // 年份
        String year = jdglYearPlanParam.getYear();

        if(StringUtils.isEmpty(year)) {
            throw new RuntimeException("传参异常!");
        }

        List<JdglYearImagePlan> returnList = new ArrayList<JdglYearImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）
        JdglMainPlan usingJdglMainPlan = iJdglMainPlanService.getUsingJdglMainPlan();
        Map<String, Date> dateRange4Year = StatisticsUtils.getDateRange4Year(year);
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(dateRange4Year.get("start"), dateRange4Year.get("end"));

        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return jdglYearPlanParam;
        }

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
            jdglYearImagePlan.setDesignQuantity(jdglMainPlanItem.getQuantity());
            jdglYearImagePlan.setTotalCompQuantity(null);
            jdglYearImagePlan.setRemainQuantity(null);
            jdglYearImagePlan.setPlanStartDate(jdglMainPlanItem.getStartDate());
            jdglYearImagePlan.setPlanEndDate(jdglMainPlanItem.getFinishDate());
            jdglYearImagePlan.setWbsCode(jdglMainPlanItem.getWbsCode());
            jdglYearImagePlan.setWbsName(jdglMainPlanItem.getWbsName());
    //                jdglYearImagePlan.setWbsId();
            jdglYearImagePlan.setResponsePerson(jdglMainPlanItem.getExecuter());
            jdglYearImagePlan.setResponsePersonId(jdglMainPlanItem.getExecuterId());
            returnList.add(jdglYearImagePlan);
        }

        if(!CollectionUtils.isEmpty(returnList)) {
            for (JdglYearImagePlan yearImagePlan : returnList) {
                JdglYearImagePlan jdglYearImagePlan = returnList.stream().filter(vo -> vo.getPtVar1().equals(yearImagePlan.getPtVar2())).findFirst().orElse(null);
                if(jdglYearImagePlan != null) yearImagePlan.setPid(jdglYearImagePlan.getId());
            }
        }

        // 维护returnList树结构
        List<JdglYearImagePlan> build = TreeUtil.build(returnList, null);

        jdglYearPlanParam.setJdglYearImagePlanList(build);

        // 修改年进度计划主表引用总体计划的版本号
        if(usingJdglMainPlan != null) {
            jdglYearPlanParam.setThisTotalVersion(usingJdglMainPlan.getVersion());
        }

        return jdglYearPlanParam;
    }

    @Override
    public List<JdglYearImagePlan> getWbsListByYear(String year) {
        return jdglYearImagePlanMapper.getWbsListByYear(year);
    }
}
