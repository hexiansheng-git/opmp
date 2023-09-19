package com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.mapper.JdglMonthValuePlanMapper;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
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
public class JdglMonthValuePlanServiceImpl implements IJdglMonthValuePlanService {

    @Autowired
    private JdglMonthValuePlanMapper jdglMonthValuePlanMapper;

    @Autowired
    private IJdglMonthImagePlanService jdglMonthImagePlanService;

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
        JdglMonthValuePlan jdglMonthValuePlan = new JdglMonthValuePlan();
        jdglMonthValuePlan.setPlanId(planId);
        return deleteJdglMonthValuePlan(jdglMonthValuePlan);
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

        if(!CollectionUtils.isEmpty(imagePlans)) {
            // 获取图纸复核的清单
            List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();
            // 获取主合同清单
            List<XmslContractList> validMaxVersionContractInventoryList = xmslContractListService.getValidMaxVersionContractInventoryList();
            if(CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(validMaxVersionContractInventoryList)) {
                return returnList;
            }
            Set<Long> listids = new HashSet<>();
            for (JdglMonthImagePlan jdglMonthImagePlan : imagePlans) {
                for (XmslDrawReviewList xmslDrawReviewList: list) {
                    if(jdglMonthImagePlan.getWbsCode() != null && jdglMonthImagePlan.getWbsCode().equals(xmslDrawReviewList.getWbsCode())) {
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
                    JdglMonthValuePlan valuePlan = new JdglMonthValuePlan();
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
                            BigDecimal monthplanCompQuantity = new BigDecimal(0);
                            List<XmslDrawReviewList> collect = list.stream().filter(vo -> xmslContractList.getId().equals(vo.getListId())).collect(Collectors.toList());
                            if(!CollectionUtils.isEmpty(collect)) {
                                for (XmslDrawReviewList xmslDrawReviewList :  collect) {
                                    List<JdglMonthImagePlan> collect1 = imagePlans.stream().filter(vo -> xmslDrawReviewList.getWbsCode().equals(vo.getWbsCode())).collect(Collectors.toList());
                                    if(!CollectionUtils.isEmpty(collect1)) {
                                        for (JdglMonthImagePlan jdglMonthImagePlan : collect1) {
                                            if(jdglMonthImagePlan.getPlanCompQuantity() != null) monthplanCompQuantity = monthplanCompQuantity.add(jdglMonthImagePlan.getPlanCompQuantity());
                                        }
                                    }
                                }
                            }
                            valuePlan.setMonthPlanCompDesignQuantity(monthplanCompQuantity);
                        }
                        if (valuePlan.getPriceCu() != null && valuePlan.getMonthPlanCompDesignQuantity() != null) {
                            valuePlan.setMonthPlanValueCu(valuePlan.getPriceCu().multiply(valuePlan.getMonthPlanCompDesignQuantity()));
                        }
                        returnList.add(valuePlan);
                    }
                }
                if(!CollectionUtils.isEmpty(returnList)) {
                    for (JdglMonthValuePlan valuePlan : returnList) {
                        JdglMonthValuePlan valuePlan1 = returnList.stream().filter(vo -> valuePlan.getInventoryPid().equals(vo.getInventoryId())).findFirst().orElse(null);
                        if(valuePlan1 != null) valuePlan.setPid(valuePlan1.getId());
                    }
                    deleteJdglMonthValuePlanByPlanId(planId);
                    insertJdglMonthValuePlanList(returnList);
                }
            }
        }

        return returnList;
    }
}
