package com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.mapper.JdglMonthValuePlanMapper;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.statistics.util.TreeCountUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2023-08-21 15:48:18
 * @remark
 */
@Service
@Slf4j
public class JdglMonthValuePlanServiceImpl implements IJdglMonthValuePlanService {

    @Autowired
    private JdglMonthValuePlanMapper jdglMonthValuePlanMapper;

    @Autowired
    private IJdglMonthImagePlanService jdglMonthImagePlanService;

    @Autowired
    private IJdglMonthPlanService jdglMonthPlanService;

    @Autowired
    private IJdglDayScheduleBillService jdglDayScheduleBillService;

    @Autowired
    private IXmslDrawReviewListService drawReviewListService;

    @Autowired
    private IXmslContractListService xmslContractListService;


    public JdglMonthValuePlan getJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan) {
        return jdglMonthValuePlanMapper.getJdglMonthValuePlan(jdglMonthValuePlan);
    }

    public List<JdglMonthValuePlan> getJdglMonthValuePlanList(JdglMonthValuePlan jdglMonthValuePlan) {
        Long pid = jdglMonthValuePlan.getPid();
        List<JdglMonthValuePlan> jdglMonthValuePlanList = jdglMonthValuePlanMapper.getJdglMonthValuePlanList(jdglMonthValuePlan);
        List<JdglMonthValuePlan> build = TreeUtil.build(jdglMonthValuePlanList, pid);
        return build;
    }

    /**
     * 查询产值进度&未完&
     * @param planId
     * @return
     */
    public List<JdglMonthValuePlan> getJdglMonthValuePlanListByPlanId(Long planId) {
        JdglMonthValuePlan jdglMonthValuePlan = new JdglMonthValuePlan();
        jdglMonthValuePlan.setPlanId(planId);

        // 获取清单及wbs关联数据

        // 获取形象进度、总形象进度及wbs关联数据

        // 根据上面2个结果集wbs关联进行清单设计量等计算

        // 获取进度填报中已填报的清单产值数据

        // 树形结构维护

        return getJdglMonthValuePlanList(jdglMonthValuePlan);
    }

    @Transactional
    public int insertJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan) {
        jdglMonthValuePlan.setId(IdWorker.createId());
        jdglMonthValuePlan.setCreateUser(SecurityUtils.getUserName());
        jdglMonthValuePlan.setCreateTime(DateUtils.getNowDate());
        return jdglMonthValuePlanMapper.insertJdglMonthValuePlan(jdglMonthValuePlan);
    }

    @Transactional
    public int insertJdglMonthValuePlanList(List<JdglMonthValuePlan> jdglMonthValuePlanList) {
        for (JdglMonthValuePlan jdglMonthValuePlan : jdglMonthValuePlanList) {
//            jdglMonthValuePlan.setId(IdWorker.createId());
            jdglMonthValuePlan.setCreateUser(SecurityUtils.getUserName());
            jdglMonthValuePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglMonthValuePlanMapper.insertJdglMonthValuePlanList(jdglMonthValuePlanList);
    }

    @Transactional
    public int updateJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan) {
        jdglMonthValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMonthValuePlanMapper.updateJdglMonthValuePlan(jdglMonthValuePlan);
    }

    @Transactional
    public int updateJdglMonthValuePlanList(List<JdglMonthValuePlan> jdglMonthValuePlanList) {
        if (!CollectionUtils.isEmpty(jdglMonthValuePlanList)) {
            for (JdglMonthValuePlan jdglMonthValuePlan : jdglMonthValuePlanList) {
                jdglMonthValuePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglMonthValuePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglMonthValuePlanMapper.updateJdglMonthValuePlanList(jdglMonthValuePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglMonthValuePlan(JdglMonthValuePlan jdglMonthValuePlan) {
        jdglMonthValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMonthValuePlanMapper.deleteJdglMonthValuePlan(jdglMonthValuePlan);
    }

    @Override
    public int deleteJdglMonthValuePlanByPlanId(Long planId) {
        return jdglMonthValuePlanMapper.deleteJdglMonthValuePlanByPlanId(planId);
    }

    @Transactional
    public int deleteJdglMonthValuePlanByPks(List<Long> jdglMonthValuePlanPkList) {
        return jdglMonthValuePlanMapper.deleteJdglMonthValuePlanByPks(jdglMonthValuePlanPkList);
    }

    @Override
    public List<JdglMonthValuePlan> getBillListByYearAndMonth(String year, String month) {
        return jdglMonthValuePlanMapper.getBillListByYearAndMonth(year, month);
    }

    @Override
    public List<JdglMonthValuePlan> updateValuePlanData(Long planId, List<JdglMonthImagePlan> imagePlans) {
        List<JdglMonthValuePlan> returnList = new ArrayList<>();
        if(planId == null) {
            return returnList;
        }

        if(CollectionUtils.isEmpty(imagePlans)) {
            imagePlans = jdglMonthImagePlanService.getJdglMonthImagePlanListByPlanId(planId);
        }

        JdglMonthPlan jdglMonthPlan = jdglMonthPlanService.getJdglMonthPlanListById(planId);

        if(jdglMonthPlan == null) {
            return returnList;
        }

        String year = jdglMonthPlan.getYear();
        String month = jdglMonthPlan.getMonth();
        Map<String, Date> dateRange = StatisticsUtils.getDateRange4YearMonth(year, month);
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
            for (JdglMonthImagePlan jdglMonthImagePlan : imagePlans) {
                for (XmslDrawReviewList xmslDrawReviewList: list) {
                    if(jdglMonthImagePlan.getWorkCode() != null && jdglMonthImagePlan.getWorkCode().equals(xmslDrawReviewList.getWbsCode())) {
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
                    JdglMonthValuePlan jdglYearValuePlan = new JdglMonthValuePlan();
                    XmslContractList xmslContractList = validMaxVersionContractInventoryList.stream().filter(vo -> listCode.equals(vo.getCode())).findFirst().orElse(null);
                    if(xmslContractList != null) {
                        jdglYearValuePlan.setId(IdWorker.createId());
//                            jdglYearValuePlan.setPid();
                        jdglYearValuePlan.setInventoryId(xmslContractList.getId());
                        jdglYearValuePlan.setInventoryPid(xmslContractList.getPid());
                        jdglYearValuePlan.setInventoryCode(xmslContractList.getCode());
                        jdglYearValuePlan.setInventoryName(xmslContractList.getChineseName());
                        jdglYearValuePlan.setPlanId(planId);
                        jdglYearValuePlan.setUnit(xmslContractList.getUnit());
                        jdglYearValuePlan.setDesignQuantity(xmslContractList.getChangeNum() == null ? xmslContractList.getWinNum() : xmslContractList.getChangeNum());
                        jdglYearValuePlan.setPriceCu(xmslContractList.getChangeUnitPrice() == null ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice());
                        if(!CollectionUtils.isEmpty(dayScheduleBillList)) {
                            JdglDayScheduleBill jdglDayScheduleBill = dayScheduleBillList.stream().filter(vo -> xmslContractList.getCode().equals(vo.getBillCode())).findFirst().orElse(null);
                            if(jdglDayScheduleBill != null) jdglYearValuePlan.setTotalCompDesignQuantity(jdglDayScheduleBill.getThisQuantity());
                        }
                        if(jdglYearValuePlan.getDesignQuantity() != null && jdglYearValuePlan.getTotalCompDesignQuantity() != null) {
                            jdglYearValuePlan.setRemainDesignQuantity(jdglYearValuePlan.getDesignQuantity().subtract(jdglYearValuePlan.getTotalCompDesignQuantity()));
                        }
                        if(jdglYearValuePlan.getTotalCompDesignQuantity() == null) {
                            jdglYearValuePlan.setTotalCompDesignQuantity(BigDecimal.ZERO);
                            jdglYearValuePlan.setRemainDesignQuantity(jdglYearValuePlan.getDesignQuantity());
                        }
                        if(xmslContractList.getCode() == null) {
                            log.info("清单编号为空");
                            continue;
                        }
                        BigDecimal yearplanCompQuantity = new BigDecimal(0);
                        // 根据清单获取图纸复核wbs清单数据
                        List<XmslDrawReviewList> collect = list.stream().filter(vo -> xmslContractList.getCode().equals(vo.getListCode())).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(collect)) {
                            for (XmslDrawReviewList xmslDrawReviewList :  collect) {
                                String workCode = xmslDrawReviewList.getWbsCode();
                                String imageProgress = xmslDrawReviewList.getImageProgress();
                                // 根据作业编号获取年形象计划对应的作业
                                List<JdglMonthImagePlan> yearImagePlan = imagePlans.stream().filter(vo -> workCode.equals(vo.getWorkCode())).collect(Collectors.toList());
                                if(CollectionUtils.isEmpty(yearImagePlan)) {
                                    log.info("根据作业编号获取年形象计划对应的作业 未找到");
                                    continue;
                                }
                                BigDecimal workDesignNum = new BigDecimal(0);
                                BigDecimal workPlanComplNum = new BigDecimal(0);
                                JdglMonthImagePlan jdglYearImagePlan = yearImagePlan.get(0);
                                BigDecimal designQuantity = jdglYearImagePlan.getDesignQuantity();
                                workDesignNum = designQuantity == null ? BigDecimal.ZERO : jdglYearImagePlan.getDesignQuantity();
                                BigDecimal planCompQuantity = jdglYearImagePlan.getPlanCompQuantity();
                                workPlanComplNum = planCompQuantity == null ? new BigDecimal(0) : planCompQuantity;
                                if (StrUtil.isNotBlank(imageProgress) && imageProgress.equals("1")) {
                                    yearplanCompQuantity = yearplanCompQuantity.add(workPlanComplNum);
                                }else {
                                    if(workPlanComplNum.compareTo(new BigDecimal(0)) != 0) {
                                        BigDecimal divide = workPlanComplNum.divide(workDesignNum, 4, RoundingMode.HALF_UP);
                                        BigDecimal checkNum = xmslDrawReviewList.getCheckNum();
                                        BigDecimal multiply = checkNum.multiply(divide);
                                        yearplanCompQuantity = yearplanCompQuantity.add(multiply);
                                    }
                                }
                            }
                        }
                        jdglYearValuePlan.setMonthPlanCompDesignQuantity(yearplanCompQuantity);
                        if (jdglYearValuePlan.getPriceCu() != null && jdglYearValuePlan.getMonthPlanCompDesignQuantity() != null) {
                            jdglYearValuePlan.setMonthPlanValueCu(jdglYearValuePlan.getPriceCu().multiply(jdglYearValuePlan.getMonthPlanCompDesignQuantity()));
                        }
                        returnList.add(jdglYearValuePlan);
                    }
                }
                if(!CollectionUtils.isEmpty(returnList)) {
                    for (JdglMonthValuePlan valuePlan : returnList) {
                        JdglMonthValuePlan valuePlan1 = returnList.stream().filter(vo -> vo.getInventoryId().equals(valuePlan.getInventoryPid())).findFirst().orElse(null);
                        if(valuePlan1 != null) valuePlan.setPid(valuePlan1.getId());
                    }
                    TreeCountUtils<JdglMonthValuePlan> treeCountUtils = new TreeCountUtils<>();
                    treeCountUtils.upCountValue(returnList, "monthPlanValueCu");
                    deleteJdglMonthValuePlanByPlanId(planId);
                    insertJdglMonthValuePlanList(returnList);
                }
            }
        }

        return returnList;
    }

    private List<JdglMonthValuePlan> getBillListByNext(String year, String month) {
        return jdglMonthValuePlanMapper.getBillListByNext(year, month);
    }
}
