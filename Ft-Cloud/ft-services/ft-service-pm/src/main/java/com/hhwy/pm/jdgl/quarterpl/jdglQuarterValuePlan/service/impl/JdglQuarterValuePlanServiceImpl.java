package com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.IJdglQuarterPlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.mapper.JdglQuarterValuePlanMapper;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.IJdglQuarterValuePlanService;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
@Service
public class JdglQuarterValuePlanServiceImpl implements IJdglQuarterValuePlanService {

    @Autowired
    private JdglQuarterValuePlanMapper jdglQuarterValuePlanMapper;

    @Autowired
    private IJdglQuarterImagePlanService jdglQuarterImagePlanService;

    @Autowired
    private IJdglQuarterPlanService jdglQuarterPlanService;

    @Autowired
    private IXmslDrawReviewListService drawReviewListService;

    @Autowired
    private IXmslContractListService xmslContractListService;

    @Autowired
    private IJdglDayScheduleBillService jdglDayScheduleBillService;


    public JdglQuarterValuePlan getJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan) {
        return jdglQuarterValuePlanMapper.getJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    public List<JdglQuarterValuePlan> getJdglQuarterValuePlanList(JdglQuarterValuePlan jdglQuarterValuePlan) {
        Long pid = jdglQuarterValuePlan.getPid();
        List<JdglQuarterValuePlan> jdglQuarterValuePlanList = jdglQuarterValuePlanMapper.getJdglQuarterValuePlanList(jdglQuarterValuePlan);
        List<JdglQuarterValuePlan> build = TreeUtil.build(jdglQuarterValuePlanList, pid);
        return build;
    }

    /**
     * 查询产值进度&未完&
     * @param planId
     * @return
     */
    public List<JdglQuarterValuePlan> getJdglQuarterValuePlanListByPlanId(Long planId) {
        JdglQuarterValuePlan jdglQuarterValuePlan = new JdglQuarterValuePlan();
        jdglQuarterValuePlan.setPlanId(planId);

        // 获取清单及wbs关联数据

        // 获取形象进度、总形象进度及wbs关联数据

        // 根据上面2个结果集wbs关联进行清单设计量等计算

        // 获取进度填报中已填报的清单产值数据

        // 树形结构维护

        return getJdglQuarterValuePlanList(jdglQuarterValuePlan);
    }

    @Transactional
    public int insertJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan) {
//        jdglQuarterValuePlan.setId(IdWorker.createId());
        jdglQuarterValuePlan.setCreateUser(SecurityUtils.getUserName());
        jdglQuarterValuePlan.setCreateTime(DateUtils.getNowDate());
        return jdglQuarterValuePlanMapper.insertJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    @Transactional
    public int insertJdglQuarterValuePlanList(List<JdglQuarterValuePlan> jdglQuarterValuePlanList) {
        for (JdglQuarterValuePlan jdglQuarterValuePlan : jdglQuarterValuePlanList) {
//            jdglQuarterValuePlan.setId(IdWorker.createId());
            jdglQuarterValuePlan.setCreateUser(SecurityUtils.getUserName());
            jdglQuarterValuePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglQuarterValuePlanMapper.insertJdglQuarterValuePlanList(jdglQuarterValuePlanList);
    }

    @Transactional
    public int updateJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan) {
        jdglQuarterValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglQuarterValuePlanMapper.updateJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    @Transactional
    public int updateJdglQuarterValuePlanList(List<JdglQuarterValuePlan> jdglQuarterValuePlanList) {
        if(!CollectionUtils.isEmpty(jdglQuarterValuePlanList)) {
            for (JdglQuarterValuePlan jdglQuarterValuePlan : jdglQuarterValuePlanList) {
                jdglQuarterValuePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglQuarterValuePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglQuarterValuePlanMapper.updateJdglQuarterValuePlanList(jdglQuarterValuePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglQuarterValuePlan(JdglQuarterValuePlan jdglQuarterValuePlan) {
        jdglQuarterValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglQuarterValuePlanMapper.deleteJdglQuarterValuePlan(jdglQuarterValuePlan);
    }

    @Override
    public int deleteJdglQuarterValuePlanByPlanId(Long planId) {
        return jdglQuarterValuePlanMapper.deleteJdglQuarterValuePlanByPlanId(planId);
    }

    @Transactional
    public int deleteJdglQuarterValuePlanByPks(List<Long> jdglQuarterValuePlanPkList) {
        return jdglQuarterValuePlanMapper.deleteJdglQuarterValuePlanByPks(jdglQuarterValuePlanPkList);
    }

    @Override
    public List<JdglQuarterValuePlan> getBillListByYearAndQuarter(String year, String quarter) {
        return jdglQuarterValuePlanMapper.getBillListByYearAndQuarter(year, quarter);
    }

    @Override
    public List<JdglQuarterValuePlan> updateValuePlanData(Long planId, List<JdglQuarterImagePlan> imagePlans) {
        List<JdglQuarterValuePlan> returnList = new ArrayList<>();
        if(planId == null) {
            return returnList;
        }

        if(CollectionUtils.isEmpty(imagePlans)) {
            imagePlans = jdglQuarterImagePlanService.getJdglQuarterImagePlanListByPlanId(planId);
        }

        JdglQuarterPlan jdglQuarterPlan = jdglQuarterPlanService.getJdglQuarterPlanById(planId);

        if(jdglQuarterPlan == null) {
            return returnList;
        }

        String year = jdglQuarterPlan.getYear();
        String quarter = jdglQuarterPlan.getQuarter();

        Map<String, Date> dateRange = StatisticsUtils.getDateRange4Quarter(year, quarter);
        List<JdglDayScheduleBill> dayScheduleBillList = jdglDayScheduleBillService.getBillValueListByEndDate(dateRange.get("start"));

        if(!CollectionUtils.isEmpty(imagePlans)) {
            // 获取图纸复核的清单
            List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();
            list.stream().filter(p -> StrUtil.isNotBlank(p.getWbsCode()))
                    .forEach(p -> {
                        String[] split = p.getWbsCode().split("-");
                        p.setWbsCode(split[split.length - 1]);
                    });
            // 获取主合同清单
            List<XmslContractList> validMaxVersionContractInventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();
            if(CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(validMaxVersionContractInventoryList)) {
                return returnList;
            }
            Set<String> listCodes = new HashSet<>();
            for (JdglQuarterImagePlan jdglQuarterImagePlan : imagePlans) {
                for (XmslDrawReviewList xmslDrawReviewList: list) {
                    if(jdglQuarterImagePlan.getWorkCode() != null && jdglQuarterImagePlan.getWorkCode().equals(xmslDrawReviewList.getWbsCode())) {
                        listCodes.add(xmslDrawReviewList.getListCode());
                    }
                }
            }
            if(!CollectionUtils.isEmpty(listCodes)) {
                Set<String> allListCode = new HashSet<>();
                allListCode.addAll(listCodes);
                for (String listCode : listCodes) {
                    XmslContractList xmslContractList = validMaxVersionContractInventoryList.stream().filter(vo -> listCode.equals(vo.getCode())).findFirst().orElse(null);
                    if(xmslContractList != null) {
                        String ancestors = xmslContractList.getAncestors();
                        if(StringUtils.isNotEmpty(ancestors)) {
                            String[] split = ancestors.split(",");
                            for (String id : split) {
                                XmslContractList xmslContractList1 = validMaxVersionContractInventoryList.stream().filter(vo -> id.equals(vo.getId()+"")).findFirst().orElse(null);
                                if(xmslContractList1 != null) allListCode.add(xmslContractList1.getCode());
                            }
                        }
                    }
                }
                for (String listCode : allListCode) {
                    JdglQuarterValuePlan valuePlan = new JdglQuarterValuePlan();
                    XmslContractList xmslContractList = validMaxVersionContractInventoryList.stream().filter(vo -> listCode.equals(vo.getCode())).findFirst().orElse(null);
                    if(xmslContractList != null) {
                        valuePlan.setId(IdWorker.createId());
//                            jdglYearValuePlan.setPid();
                        valuePlan.setInventoryId(xmslContractList.getId());
                        valuePlan.setInventoryPid(xmslContractList.getPid());
                        valuePlan.setInventoryCode(xmslContractList.getCode());
                        valuePlan.setInventoryName(xmslContractList.getChineseName());
                        valuePlan.setPlanId(planId);
                        valuePlan.setUnit(xmslContractList.getUnit());
                        valuePlan.setDesignQuantity(xmslContractList.getChangeNum() == null ? xmslContractList.getWinNum() : xmslContractList.getChangeNum());
                        valuePlan.setPriceCu(xmslContractList.getChangeUnitPrice() == null ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice());
                        valuePlan.setTotalCompDesignQuantity(BigDecimal.ZERO);
                        if(!CollectionUtils.isEmpty(dayScheduleBillList)) {
                            JdglDayScheduleBill jdglDayScheduleBill = dayScheduleBillList.stream().filter(vo -> xmslContractList.getCode().equals(vo.getBillCode())).findFirst().orElse(null);
                            if(jdglDayScheduleBill != null) valuePlan.setTotalCompDesignQuantity(jdglDayScheduleBill.getThisQuantity());
                        }
                        if(valuePlan.getDesignQuantity() != null && valuePlan.getTotalCompDesignQuantity() != null) {
                            valuePlan.setRemainDesignQuantity(valuePlan.getDesignQuantity().subtract(valuePlan.getTotalCompDesignQuantity()));
                        }
                        if(xmslContractList.getCode() != null) {
                            BigDecimal quarterplanCompQuantity = new BigDecimal(0);
                            List<XmslDrawReviewList> collect = list.stream().filter(vo -> xmslContractList.getCode().equals(vo.getListCode())).collect(Collectors.toList());
                            if(!CollectionUtils.isEmpty(collect)) {
                                for (XmslDrawReviewList xmslDrawReviewList :  collect) {
                                    String wbsCode = xmslDrawReviewList.getWbsCode();
                                    // 根据wbs获取年形象计划对应wbs
                                    List<JdglQuarterImagePlan> collect1 = imagePlans.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).collect(Collectors.toList());
                                    if(!CollectionUtils.isEmpty(collect1)) {
                                        BigDecimal wbsDesignNum = new BigDecimal(0);
                                        BigDecimal wbsPlanNum = new BigDecimal(0);
                                        for (JdglQuarterImagePlan jdglQuarterImagePlan : collect1) {
                                            if(wbsCode.equals(jdglQuarterImagePlan.getWorkCode())) {
                                                if(jdglQuarterImagePlan.getDesignQuantity() != null) wbsDesignNum = jdglQuarterImagePlan.getDesignQuantity();
                                            } else {
                                                wbsPlanNum = wbsPlanNum.add(jdglQuarterImagePlan.getPlanCompQuantity() == null ? new BigDecimal(0) : jdglQuarterImagePlan.getPlanCompQuantity());
                                            }
                                        }
                                        if(wbsDesignNum.compareTo(new BigDecimal(0)) != 0) {
                                            quarterplanCompQuantity = quarterplanCompQuantity.add(xmslDrawReviewList.getCheckNum() == null
                                                    ? new BigDecimal(0) : xmslDrawReviewList.getCheckNum().multiply(wbsPlanNum.divide(wbsDesignNum, 4, BigDecimal.ROUND_HALF_UP)));
                                        }
                                    }
                                }
                            }
                            valuePlan.setQuarterPlanCompDesignQuantity(quarterplanCompQuantity);
                        }
                        if (valuePlan.getPriceCu() != null && valuePlan.getQuarterPlanCompDesignQuantity() != null) {
                            valuePlan.setQuarterPlanValueCu(valuePlan.getPriceCu().multiply(valuePlan.getQuarterPlanCompDesignQuantity()));
                        }
                        returnList.add(valuePlan);
                    }
                }
                if(!CollectionUtils.isEmpty(returnList)) {
                    for (JdglQuarterValuePlan valuePlan : returnList) {
                        JdglQuarterValuePlan valuePlan1 = returnList.stream().filter(vo -> vo.getInventoryId().equals(valuePlan.getInventoryPid())).findFirst().orElse(null);
                        if(valuePlan1 != null) valuePlan.setPid(valuePlan1.getId());
                    }
                    deleteJdglQuarterValuePlanByPlanId(planId);
                    TreeCountUtils<JdglQuarterValuePlan> treeCountUtils = new TreeCountUtils<>();
                    treeCountUtils.upCountValue(returnList, "quarterPlanValueCu");
                    insertJdglQuarterValuePlanList(returnList);
                }
            }
        }

        return returnList;
    }

    private List<JdglQuarterValuePlan> getBillListByNext(String year, String quarter) {
        return jdglQuarterValuePlanMapper.getBillListByNext(year, quarter);
    }
}
