package com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.lang.hash.Hash;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.mapper.JdglYearValuePlanMapper;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.IJdglYearValuePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:18
 * @remark
 */
@Service
public class JdglYearValuePlanServiceImpl implements IJdglYearValuePlanService {

    @Autowired
    private JdglYearValuePlanMapper jdglYearValuePlanMapper;

    @Autowired
    private IJdglYearImagePlanService jdglYearImagePlanService;

    @Autowired
    private IXmslDrawReviewListService drawReviewListService;
    
    @Autowired
    private IXmslContractListService xmslContractListService;

    public JdglYearValuePlan getJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan) {
        return jdglYearValuePlanMapper.getJdglYearValuePlan(jdglYearValuePlan);
    }

    public List<JdglYearValuePlan> getJdglYearValuePlanList(JdglYearValuePlan jdglYearValuePlan) {
        Long pid = jdglYearValuePlan.getPid();
        List<JdglYearValuePlan> jdglYearValuePlanList = jdglYearValuePlanMapper.getJdglYearValuePlanList(jdglYearValuePlan);
        List<JdglYearValuePlan> build = TreeUtil.build(jdglYearValuePlanList, pid);
        return build;
    }

    /**
     * 查询产值进度&未完&
     * @param yearPlanId
     * @return
     */
    public List<JdglYearValuePlan> getJdglYearValuePlanListByYearPlanId(Long yearPlanId) {
        JdglYearValuePlan jdglYearValuePlan = new JdglYearValuePlan();
        jdglYearValuePlan.setYearPlanId(yearPlanId);

        // 获取清单及wbs关联数据

        // 获取形象进度、总形象进度及wbs关联数据

        // 根据上面2个结果集wbs关联进行清单设计量等计算

        // 获取进度填报中已填报的清单产值数据

        // 树形结构维护

        return getJdglYearValuePlanList(jdglYearValuePlan);
    }

    @Transactional
    public int insertJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan) {
        jdglYearValuePlan.setId(IdWorker.createId());
        jdglYearValuePlan.setCreateUser(SecurityUtils.getUserName());
        jdglYearValuePlan.setCreateTime(DateUtils.getNowDate());
        return jdglYearValuePlanMapper.insertJdglYearValuePlan(jdglYearValuePlan);
    }

    @Transactional
    public int insertJdglYearValuePlanList(List<JdglYearValuePlan> jdglYearValuePlanList) {
        for (JdglYearValuePlan jdglYearValuePlan : jdglYearValuePlanList) {
//            jdglYearValuePlan.setId(IdWorker.createId());
            jdglYearValuePlan.setCreateUser(SecurityUtils.getUserName());
            jdglYearValuePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglYearValuePlanMapper.insertJdglYearValuePlanList(jdglYearValuePlanList);
    }

    @Transactional
    public int updateJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan) {
        jdglYearValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearValuePlanMapper.updateJdglYearValuePlan(jdglYearValuePlan);
    }

    @Transactional
    public int updateJdglYearValuePlanList(List<JdglYearValuePlan> jdglYearValuePlanList) {
        if (!CollectionUtils.isEmpty(jdglYearValuePlanList)) {
            for (JdglYearValuePlan jdglYearValuePlan : jdglYearValuePlanList) {
                jdglYearValuePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglYearValuePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglYearValuePlanMapper.updateJdglYearValuePlanList(jdglYearValuePlanList);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglYearValuePlan(JdglYearValuePlan jdglYearValuePlan) {
        jdglYearValuePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearValuePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearValuePlanMapper.deleteJdglYearValuePlan(jdglYearValuePlan);
    }

    @Override
    public int deleteJdglYearValuePlanByYearPlanId(Long yearPlanId) {
        return jdglYearValuePlanMapper.deleteJdglYearValuePlanByYearPlanId(yearPlanId);
    }

    @Transactional
    public int deleteJdglYearValuePlanByPks(List<Long> jdglYearValuePlanPkList) {
        return jdglYearValuePlanMapper.deleteJdglYearValuePlanByPks(jdglYearValuePlanPkList);
    }

    @Override
    public List<JdglYearValuePlan> getBillListByYear(String year) {
        return jdglYearValuePlanMapper.getBillListByYear(year);
    }


    @Override
    public List<JdglYearValuePlan> updateValuePlanData(Long yearplanId, List<JdglYearImagePlan> imagePlans) {
        List<JdglYearValuePlan> returnList = new ArrayList<>();
        if(yearplanId == null) {
            return returnList;
        }

        if(CollectionUtils.isEmpty(imagePlans)) {
            imagePlans = jdglYearImagePlanService.getJdglYearImagePlanListByYearPlanId(yearplanId);
        }

        if(!CollectionUtils.isEmpty(imagePlans)) {
            // 获取图纸复核的清单
            List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();
            // 获取主合同清单
            List<XmslContractList> validMaxVersionContractInventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();
            if(CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(validMaxVersionContractInventoryList)) {
                return returnList;
            }
            Set<Long> listids = new HashSet<>();
            for (JdglYearImagePlan jdglYearImagePlan : imagePlans) {
                for (XmslDrawReviewList xmslDrawReviewList: list) {
                    if(jdglYearImagePlan.getWbsCode() != null && jdglYearImagePlan.getWbsCode().equals(xmslDrawReviewList.getWbsCode())) {
                        listids.add(xmslDrawReviewList.getListId());
                    }
                }
            }
            if(!CollectionUtils.isEmpty(listids)) {
                Set<Long> allListId = new HashSet<>();
                allListId.addAll(listids);
                for (Long listid : listids) {
                    XmslContractList xmslContractList = validMaxVersionContractInventoryList.stream().filter(vo -> listid.equals(vo.getId())).findFirst().orElse(null);
                    if(xmslContractList != null) {
                        String ancestors = xmslContractList.getAncestors();
                        if(StringUtils.isNotEmpty(ancestors)) {
                            String[] split = ancestors.split(",");
                            for (String id : split) {
                                allListId.add(Long.valueOf(id));
                            }
                        }
                    }
                }
                for (Long listId : allListId) {
                    JdglYearValuePlan jdglYearValuePlan = new JdglYearValuePlan();
                    XmslContractList xmslContractList = validMaxVersionContractInventoryList.stream().filter(vo -> listId.equals(vo.getId())).findFirst().orElse(null);
                    if(xmslContractList != null) {
                        jdglYearValuePlan.setId(IdWorker.createId());
//                            jdglYearValuePlan.setPid();
                        jdglYearValuePlan.setInventoryId(xmslContractList.getId());
                        jdglYearValuePlan.setInventoryPid(xmslContractList.getPid());
                        jdglYearValuePlan.setInventoryCode(xmslContractList.getCode());
                        jdglYearValuePlan.setInventoryName(xmslContractList.getChineseName());
                        jdglYearValuePlan.setYearPlanId(yearplanId);
                        jdglYearValuePlan.setUnit(xmslContractList.getUnit());
                        jdglYearValuePlan.setDesignQuantity(xmslContractList.getChangeNum() == null ? xmslContractList.getWinNum() : xmslContractList.getChangeNum());
                        jdglYearValuePlan.setPriceCu(xmslContractList.getChangeUnitPrice() == null ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice());
//                            jdglYearValuePlan.setTotalCompDesignQuantity();
                        if(jdglYearValuePlan.getDesignQuantity() != null && jdglYearValuePlan.getTotalCompDesignQuantity() != null) {
                            jdglYearValuePlan.setRemainDesignQuantity(jdglYearValuePlan.getDesignQuantity().subtract(jdglYearValuePlan.getTotalCompDesignQuantity()));
                        }
                        if(xmslContractList.getCode() != null) {
                            BigDecimal yearplanCompQuantity = new BigDecimal(0);

                            // 根据清单获取图纸复核wbs清单数据
                            List<XmslDrawReviewList> collect = list.stream().filter(vo -> xmslContractList.getId().equals(vo.getListId())).collect(Collectors.toList());
                            if(!CollectionUtils.isEmpty(collect)) {
                                for (XmslDrawReviewList xmslDrawReviewList :  collect) {
                                    String wbsCode = xmslDrawReviewList.getWbsCode();
                                    // 根据wbs获取年形象计划对应wbs
                                    List<JdglYearImagePlan> collect1 = imagePlans.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).collect(Collectors.toList());
                                    if(!CollectionUtils.isEmpty(collect1)) {
                                        BigDecimal wbsDesignNum = new BigDecimal(0);
                                        BigDecimal wbsPlanNum = new BigDecimal(0);
                                        for (JdglYearImagePlan jdglYearImagePlan : collect1) {
                                            if(wbsCode.equals(jdglYearImagePlan.getWorkCode())) {
                                                if(jdglYearImagePlan.getDesignQuantity() != null) wbsDesignNum = jdglYearImagePlan.getDesignQuantity();
                                            } else {
                                                wbsPlanNum = wbsPlanNum.add(jdglYearImagePlan.getPlanCompQuantity() == null ? new BigDecimal(0) : jdglYearImagePlan.getPlanCompQuantity());
                                            }
//                                            if(jdglYearImagePlan.getPlanCompQuantity() != null) yearplanCompQuantity = yearplanCompQuantity.add(jdglYearImagePlan.getPlanCompQuantity());
                                        }
                                        if(wbsDesignNum.compareTo(new BigDecimal(0)) != 0) {
                                            yearplanCompQuantity = yearplanCompQuantity.add(xmslDrawReviewList.getCheckNum() == null
                                                    ? new BigDecimal(0) : xmslDrawReviewList.getCheckNum().multiply(wbsPlanNum.divide(wbsDesignNum, 4, BigDecimal.ROUND_HALF_UP)));
                                        }
                                    }
                                }
                            }
                            jdglYearValuePlan.setYearPlanCompDesignQuantity(yearplanCompQuantity);
                        }
                        if (jdglYearValuePlan.getPriceCu() != null && jdglYearValuePlan.getYearPlanCompDesignQuantity() != null) {
                            jdglYearValuePlan.setYearPlanValueCu(jdglYearValuePlan.getPriceCu().multiply(jdglYearValuePlan.getYearPlanCompDesignQuantity()));
                        }
                        returnList.add(jdglYearValuePlan);
                    }
                }
                if(!CollectionUtils.isEmpty(returnList)) {
                    for (JdglYearValuePlan jdglYearValuePlan : returnList) {
                        JdglYearValuePlan jdglYearValuePlan1 = returnList.stream().filter(vo -> vo.getInventoryId().equals(jdglYearValuePlan.getInventoryPid())).findFirst().orElse(null);
                        if(jdglYearValuePlan1 != null) jdglYearValuePlan.setPid(jdglYearValuePlan1.getId());
                    }
                    deleteJdglYearValuePlanByYearPlanId(yearplanId);
                    insertJdglYearValuePlanList(returnList);
                }
            }
        }

        return returnList;
    }
}
