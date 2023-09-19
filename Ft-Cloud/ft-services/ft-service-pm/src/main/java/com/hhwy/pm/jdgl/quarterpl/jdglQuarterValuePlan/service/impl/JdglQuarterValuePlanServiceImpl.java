package com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.mapper.JdglQuarterValuePlanMapper;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.IJdglQuarterValuePlanService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private IXmslDrawReviewListService drawReviewListService;

    @Autowired
    private IXmslContractListService xmslContractListService;


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
            jdglQuarterValuePlan.setId(IdWorker.createId());
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
        JdglQuarterValuePlan jdglQuarterValuePlan = new JdglQuarterValuePlan();
        jdglQuarterValuePlan.setPlanId(planId);
        return deleteJdglQuarterValuePlan(jdglQuarterValuePlan);
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

        if(!CollectionUtils.isEmpty(imagePlans)) {
            // 获取图纸复核的清单
            List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();
            // 获取主合同清单
            List<XmslContractList> validMaxVersionContractInventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();
            if(CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(validMaxVersionContractInventoryList)) {
                return returnList;
            }
            Set<Long> listids = new HashSet<>();
            for (JdglQuarterImagePlan jdglQuarterImagePlan : imagePlans) {
                for (XmslDrawReviewList xmslDrawReviewList: list) {
                    if(jdglQuarterImagePlan.getWbsCode() != null && jdglQuarterImagePlan.getWbsCode().equals(xmslDrawReviewList.getWbsCode())) {
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
                    JdglQuarterValuePlan valuePlan = new JdglQuarterValuePlan();
                    XmslContractList xmslContractList = validMaxVersionContractInventoryList.stream().filter(vo -> listId.equals(vo.getId())).findFirst().orElse(null);
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
//                            valuePlan.setTotalCompDesignQuantity();
                        if(valuePlan.getDesignQuantity() != null && valuePlan.getTotalCompDesignQuantity() != null) {
                            valuePlan.setRemainDesignQuantity(valuePlan.getDesignQuantity().subtract(valuePlan.getTotalCompDesignQuantity()));
                        }
                        if(xmslContractList.getCode() != null) {
                            BigDecimal quarterplanCompQuantity = new BigDecimal(0);
                            List<XmslDrawReviewList> collect = list.stream().filter(vo -> xmslContractList.getId().equals(vo.getListId())).collect(Collectors.toList());
                            if(!CollectionUtils.isEmpty(collect)) {
                                for (XmslDrawReviewList xmslDrawReviewList :  collect) {
                                    List<JdglQuarterImagePlan> collect1 = imagePlans.stream().filter(vo -> xmslDrawReviewList.getWbsCode().equals(vo.getWbsCode())).collect(Collectors.toList());
                                    if(!CollectionUtils.isEmpty(collect1)) {
                                        for (JdglQuarterImagePlan jdglQuarterImagePlan : collect1) {
                                            if(jdglQuarterImagePlan.getPlanCompQuantity() != null) quarterplanCompQuantity = quarterplanCompQuantity.add(jdglQuarterImagePlan.getPlanCompQuantity());
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
                        JdglQuarterValuePlan valuePlan1 = returnList.stream().filter(vo -> valuePlan.getInventoryPid().equals(vo.getInventoryId())).findFirst().orElse(null);
                        if(valuePlan1 != null) valuePlan.setPid(valuePlan1.getId());
                    }
                    deleteJdglQuarterValuePlanByPlanId(planId);
                    insertJdglQuarterValuePlanList(returnList);
                }
            }
        }

        return returnList;
    }
}
