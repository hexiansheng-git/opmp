package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.lang.tree.Tree;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Add;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewWbsService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.mapper.JdglDayScheduleWbsMapper;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-08-24 14:05:56
 * @remark
 */
@Service
public class JdglDayScheduleWbsServiceImpl implements IJdglDayScheduleWbsService {

    @Autowired
    private JdglDayScheduleWbsMapper jdglDayScheduleWbsMapper;

    @Autowired
    private IJdglDayScheduleBillService iJdglDayScheduleBillService;

    @Autowired
    private IJdglDayScheduleService jdglDayScheduleService;

    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;

    @Autowired
    private IXmslWbsService xmslWbsService;

    @Autowired
    private IXmslDrawReviewListService drawReviewListService;

    public JdglDayScheduleWbs getJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        JdglDayScheduleWbs jdglDayScheduleWbs1 = jdglDayScheduleWbsMapper.getJdglDayScheduleWbs(jdglDayScheduleWbs);
        if(jdglDayScheduleWbs1 != null) {
            JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
            jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
            jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
            List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
            jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
        }
        return jdglDayScheduleWbs1;
    }

    public List<JdglDayScheduleWbs> getJdglDayScheduleWbsList(JdglDayScheduleWbs jdglDayScheduleWbs) {
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
//        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
//            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbsList) {
//                JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
//                jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
//                jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
//                List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
//                jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
//            }
//        }
        List<JdglDayScheduleWbs> build = TreeUtil.build(jdglDayScheduleWbsList, jdglDayScheduleWbs.getId());
        return build;
    }


    @Override
    public List<JdglDayScheduleWbs> getJdglDayScheduleWbsListByPerson(JdglDayScheduleWbs jdglDayScheduleWbs) {

        List<JdglDayScheduleWbs> returnList = new ArrayList<>();

        Long dayScheduleId = jdglDayScheduleWbs.getDayScheduleId();

        JdglDaySchedule jdglDaySchedule = null;

        // 所有数据
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);

        if(dayScheduleId != null) {
//            JdglDaySchedule jdglDaySchedule1 = new JdglDaySchedule();
//            jdglDaySchedule1.setId(dayScheduleId);
            jdglDaySchedule = jdglDayScheduleService.getJdglDayScheduleById(dayScheduleId);
            if(jdglDaySchedule != null && StringUtils.isNotEmpty(jdglDaySchedule.getTaskStatus()) && !"0".equals(jdglDaySchedule.getTaskStatus())) {
                return TreeUtil.build(jdglDayScheduleWbsList, jdglDayScheduleWbs.getId());
            }
        }

        if(CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            if(jdglDaySchedule != null) {
                Date date = jdglDaySchedule.getDate();
                return TreeUtil.build(getInitWbs(date), jdglDayScheduleWbs.getId());
            }
        }

        // 过滤出当前登录人过滤出的wbs叶子节点集合
        String userId = SecurityUtils.getUserId() + "";
        List<JdglDayScheduleWbs> collect = jdglDayScheduleWbsList.stream().filter(vo -> "1".equals(vo.getIsLeaf()) && userId.equals(vo.getEditerId())).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(collect)) {
            if(jdglDaySchedule != null) {
                Date date = jdglDaySchedule.getDate();
                return TreeUtil.build(getInitWbs(date), jdglDayScheduleWbs.getId());
            }
        }

        Set<String> set = new HashSet<>();

        for (JdglDayScheduleWbs jdglDayScheduleWbs1 : collect) {
            set.add(jdglDayScheduleWbs1.getPtVar3());
        }

        // 根据当前登录人过滤出来的wbs叶子节点的祖籍id获取所有相关wbs层级数据
        for (String ancestrals : set) {
            List<JdglDayScheduleWbs> collect1 = jdglDayScheduleWbsList.stream().filter(vo ->
                    ancestrals.contains(vo.getPtVar3() + "")).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect1))  returnList.addAll(collect1);
        }

        returnList = returnList.stream().distinct().collect(Collectors.toList());

//        if(!CollectionUtils.isEmpty(returnList)) {
//            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : returnList) {
//                JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
//                jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
//                jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
//                List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
//                jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
//            }
//        }
        List<JdglDayScheduleWbs> build = TreeUtil.build(returnList, jdglDayScheduleWbs.getId());
        return build;
    }


    /**
     * 获取wbs及图纸复核数据并过滤当前日报的wbs
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs> getLazyWbs4NoThis(JdglDayScheduleWbs jdglDayScheduleWbsParam) {

        Long pid = jdglDayScheduleWbsParam.getPid();
        Long dayScheduleId = jdglDayScheduleWbsParam.getDayScheduleId();
        String wbsName = jdglDayScheduleWbsParam.getWbsName();

        JdglMainPlanItem queryVO = new JdglMainPlanItem();
        queryVO.setPid(pid);
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getJdglMainPlanItemList4Lazy(queryVO);

        List<JdglDayScheduleWbs> returnList = new ArrayList<>();

        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return returnList;
        }

        // 获取当前日填报wbs数据
        JdglDayScheduleWbs jdglDayScheduleWbs = new JdglDayScheduleWbs();
        jdglDayScheduleWbs.setDayScheduleId(dayScheduleId);
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);

        //

        jdglMainPlanItemList.stream().forEach(vo -> {
            JdglDayScheduleWbs jdglDayScheduleWbs1 = new JdglDayScheduleWbs();
            jdglDayScheduleWbs1.setId(Long.valueOf(vo.getId()));
            jdglDayScheduleWbs1.setPid(vo.getPid() == null ? null : Long.valueOf(vo.getPid()));
            jdglDayScheduleWbs1.setIsLeaf(vo.getHaveChildren() == 0 ? "1" : "0");
            jdglDayScheduleWbs1.setHaveChildren(vo.getHaveChildren());
            jdglDayScheduleWbs1.setWbsCode(vo.getItemCode());
            jdglDayScheduleWbs1.setWbsName(vo.getItemName());
            jdglDayScheduleWbs1.setPtVar1(vo.getWbsObjectId());
            jdglDayScheduleWbs1.setPtVar2(vo.getWbsParentObjectId());
            jdglDayScheduleWbs1.setPtVar3(vo.getAncestors());
            jdglDayScheduleWbs1.setUnit(vo.getUnit());
            jdglDayScheduleWbs1.setUnicode(vo.getUnicode());
            jdglDayScheduleWbs1.setDesignQuantity(vo.getQuantity());
            jdglDayScheduleWbs1.setEditerId(vo.getExecuterId());
            jdglDayScheduleWbs1.setEditer(vo.getExecuter());
            jdglDayScheduleWbs1.setEditerDate(DateUtils.getNowDate());
            if (!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
                for (JdglDayScheduleWbs jdglDayScheduleWbs2 : jdglDayScheduleWbsList) {
                    if (StringUtils.isNotEmpty(vo.getItemCode()) && vo.getItemCode().equals(jdglDayScheduleWbs2.getWbsCode())) {
                        jdglDayScheduleWbs1.setIsExists("1");
                    }
                }
            }
            returnList.add(jdglDayScheduleWbs1);
        });


        return returnList;
    }

    /**
     * 获取末级节点wbs数据
     * @param jdglDayScheduleWbsParam
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs> getLeafWbsList(JdglDayScheduleWbs jdglDayScheduleWbsParam) {
        return jdglDayScheduleWbsMapper.getLeafWbsList(jdglDayScheduleWbsParam);
    }

    @Override
    public List<JdglDayScheduleWbs> getJdglDayScheduleWbsLazyList(JdglDayScheduleWbs jdglDayScheduleWbs) {
        Long id = jdglDayScheduleWbs.getId();
        jdglDayScheduleWbs.setId(null);
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
//        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
//            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbsList) {
//                JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
//                jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
//                jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
//                List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
//                jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
//            }
//        }
        List<JdglDayScheduleWbs> build = TreeUtil.build(jdglDayScheduleWbsList, id);
        if(!CollectionUtils.isEmpty(build)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs1: build) {
                List<JdglDayScheduleWbs> children = jdglDayScheduleWbs1.getChildren();
                if(!CollectionUtils.isEmpty(children)) {
                    jdglDayScheduleWbs1.setHaveChildren(children.size());
                    jdglDayScheduleWbs1.setChildren(null);
                }
            }
        }
        return build;
    }

    /**
     * 根据日期从总进度计划获取初始化wbs及作业数据
     * @param date
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs> getInitWbs(Date date) {

        if(date == null) {
            throw new RuntimeException("参数异常!");
        }

        // 根据日期和当前登录人获取初始化作业数据（从总体计划）
        List<JdglDayScheduleWbs> returnList = getUnitWbsListByDateAndUser(date, SecurityUtils.getUserName());

        // 维护树形结构
        List<JdglDayScheduleWbs> build = TreeUtil.build(returnList, null);

        return build;
    }

    /**
     * 根据日期和人员编码获取初始化作业数据（从总体计划）
     * @param date
     * @param userName
     * @return
     */
    public List<JdglDayScheduleWbs> getUnitWbsListByDateAndUser(Date date, String userName) {

        List<JdglDayScheduleWbs> returnList = new ArrayList<JdglDayScheduleWbs>();
        // 根据日期获取总进度计划中作业区间内的wbs数据
        List<JdglMainPlanItem> usingJdglMainPlanItemListByDate = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDate(date);

        if(CollectionUtils.isEmpty(usingJdglMainPlanItemListByDate)) {
            return returnList;
        }

        List<JdglMainPlanItem> allList4User = new ArrayList<>();

        // 根据人员编码过滤出该人员作业数据
        List<JdglMainPlanItem> work4User = usingJdglMainPlanItemListByDate.stream().filter(vo ->
                JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())
                        && userName.equals(vo.getExecuterId())).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(work4User)) {
            return returnList;
        }

        allList4User.addAll(work4User);

        for (JdglMainPlanItem jdglMainPlanItem : work4User) {
            List<JdglMainPlanItem> collect = usingJdglMainPlanItemListByDate.stream().filter(vo ->
                    JdglMainPlanItem.ITEMTYPE_WBS.equals(vo.getItemType())
                            && jdglMainPlanItem.getAncestors().contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect))  {
                for (JdglMainPlanItem jdglMainPlanItem1 : collect) {
                    JdglMainPlanItem jdglMainPlanItem2 = allList4User.stream().filter(vo -> jdglMainPlanItem1.getItemCode().equals(vo.getItemCode())).findFirst().orElse(null);
                    if(jdglMainPlanItem2 == null) allList4User.add(jdglMainPlanItem1);
                }
            }
        }

        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsByDate(date);

        // 获取图纸复核的清单
//        List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();

        List<JdglDayScheduleWbs4Value> totalWbsListByDateRange = getTotalWbsListByDateRange4OnlyWbs(StatisticsUtils.addDays(date, -1));

        for (JdglMainPlanItem jdglMainPlanItem : allList4User) {
            JdglDayScheduleWbs jdglDayScheduleWbs = new JdglDayScheduleWbs();

            JdglDayScheduleWbs jdglDayScheduleWbs1 = null;
            if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
                jdglDayScheduleWbs1 = jdglDayScheduleWbsList.stream().filter(vo -> jdglMainPlanItem.getItemCode().equals(vo.getWbsCode())).findFirst().orElse(null);
            }

            if(jdglDayScheduleWbs1 != null) {
                jdglDayScheduleWbs.setId(jdglDayScheduleWbs1.getId());
                jdglDayScheduleWbs.setDesignQuantity(jdglDayScheduleWbs1.getDesignQuantity());
                jdglDayScheduleWbs.setThisQuantity(jdglDayScheduleWbs1.getThisQuantity());
                jdglDayScheduleWbs.setRemainQuantity(jdglDayScheduleWbs1.getRemainQuantity());
            } else {
                jdglDayScheduleWbs.setId(IdWorker.createId());
                jdglDayScheduleWbs.setIsAdd("1");
                jdglDayScheduleWbs.setDesignQuantity(jdglMainPlanItem.getQuantity());
                if(!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
                    JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = totalWbsListByDateRange.stream().filter(vo ->
                            jdglMainPlanItem.getItemCode().equals(vo.getWbsCode())
                    ).findFirst().orElse(null);
                    BigDecimal designQuantity = jdglDayScheduleWbs.getDesignQuantity();
                    if(jdglDayScheduleWbs4Value == null) {
                        jdglDayScheduleWbs.setRemainQuantity(designQuantity);
                    } else {
                        if(designQuantity != null && jdglDayScheduleWbs4Value.getThisQuantity() != null) {
                            jdglDayScheduleWbs.setRemainQuantity(designQuantity.subtract(jdglDayScheduleWbs4Value.getThisQuantity()));
                        }
                    }
                }
                if(jdglDayScheduleWbs.getRemainQuantity() == null) jdglDayScheduleWbs.setRemainQuantity(jdglDayScheduleWbs.getDesignQuantity());
            }
            jdglDayScheduleWbs.setIsLeaf(jdglMainPlanItem.getLeaf());
            jdglDayScheduleWbs.setWbsCode(jdglMainPlanItem.getItemCode());
            jdglDayScheduleWbs.setWbsName(jdglMainPlanItem.getItemName());
            jdglDayScheduleWbs.setUnit(jdglMainPlanItem.getUnit());
            if(JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem.getItemType())) {
                jdglDayScheduleWbs.setEditerId(jdglMainPlanItem.getExecuterId());
                jdglDayScheduleWbs.setEditer(jdglMainPlanItem.getExecuter());
                jdglDayScheduleWbs.setEditerDate(DateUtils.getNowDate());
            }
            // 从redis中获取wbs数据
//            XmslWbs wbsByCode = WbsRedisUtils.getWbsByCode(jdglMainPlanItem.getWbsCode());
//            XmslWbs wbsByCode = xmslWbsService.getByCode(jdglMainPlanItem.getWbsCode());
//            if(wbsByCode != null) {
//                jdglDayScheduleWbs.setWbsId(JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem.getItemType()) ? IdWorker.createId() : Long.valueOf(wbsByCode.getId()));
//                jdglDayScheduleWbs.setWbsPid(JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem.getItemType()) ? Long.valueOf(wbsByCode.getId()) : Long.valueOf(wbsByCode.getParentId()));
//                jdglDayScheduleWbs.setAncestrals(wbsByCode.getAncestors());
//                if(JdglMainPlanItem.ITEMTYPE_WBS.equals(jdglMainPlanItem.getItemType())) {
//                    jdglDayScheduleWbs.setUnit(wbsByCode.getUnit());
//                    jdglDayScheduleWbs.setUnicode(wbsByCode.getUnicode());
//                    // 赋值wbs的设计数量
//                    if(!CollectionUtils.isEmpty(list)) {
//                        XmslDrawReviewList xmslDrawReviewList = list.stream().filter(vo ->
//                                "1".equals(vo.getImageProgress())
//                                        && jdglMainPlanItem.getItemCode().equals(vo.getWbsCode()))
//                                .findFirst().orElse(null);
//                        if(xmslDrawReviewList != null) jdglDayScheduleWbs.setDesignQuantity(xmslDrawReviewList.getCheckNum());
//                    }
                    // 赋值wbs的剩余数量
//                    if(!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
//                        JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = totalWbsListByDateRange.stream().filter(vo ->
//                                jdglMainPlanItem.getItemCode().equals(vo.getWbsCode())
//                        ).findFirst().orElse(null);
//                        BigDecimal designQuantity = jdglDayScheduleWbs.getDesignQuantity();
//                        if(jdglDayScheduleWbs4Value == null) {
//                            jdglDayScheduleWbs.setRemainQuantity(designQuantity);
//                        } else {
//                            if(designQuantity != null && jdglDayScheduleWbs4Value.getThisQuantity() != null) {
//                                jdglDayScheduleWbs.setRemainQuantity(designQuantity.subtract(jdglDayScheduleWbs4Value.getThisQuantity()));
//                            }
//                        }
//                    }
//                }
//            }
            // 源数据id
            jdglDayScheduleWbs.setPtVar1(jdglMainPlanItem.getWbsObjectId());
            // 源数据父id
            jdglDayScheduleWbs.setPtVar2(jdglMainPlanItem.getWbsParentObjectId());
            // 源数据层级码
            jdglDayScheduleWbs.setPtVar3(jdglMainPlanItem.getAncestors());
            returnList.add(jdglDayScheduleWbs);
        }

        if(!CollectionUtils.isEmpty(returnList)) {
            for(JdglDayScheduleWbs jdglDayScheduleWbs : returnList) {
                JdglDayScheduleWbs jdglDayScheduleWbs1 = returnList.stream().filter(vo -> jdglDayScheduleWbs.getPtVar2().equals(vo.getPtVar1())).findFirst().orElse(null);
                if(jdglDayScheduleWbs1 != null) jdglDayScheduleWbs.setPid(jdglDayScheduleWbs1.getId());
            }
        }

        return returnList;

    }

    /**
     * 根据日期区间查询wbs数据
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs4Value> getWbsListByDateRange(Date startDate, Date endDate) {
        return jdglDayScheduleWbsMapper.getWbsListByDateRange4Value(startDate, endDate);
    }

    /**
     * 根据日期获取开累信息
     * @param endDate
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs4Value> getTotalWbsListByDateRange(Date endDate) {
        return jdglDayScheduleWbsMapper.getTotalWbsListByDateRange4Value(endDate);
    }

    @Override
    public List<JdglDayScheduleWbs4Value> getWbsListByDateRange4OnlyWbs(Date startDate, Date endDate) {
        return jdglDayScheduleWbsMapper.getWbsListByDateRange4OnlyWbs(startDate, endDate);
    }

    @Override
    public List<JdglDayScheduleWbs4Value> getTotalWbsListByDateRange4OnlyWbs(Date endDate) {
        return jdglDayScheduleWbsMapper.getTotalWbsListByDateRange4OnlyWbs(endDate);
    }


    @Transactional
    public int insertJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        jdglDayScheduleWbs.setId(IdWorker.createId());
        jdglDayScheduleWbs.setCreateUser(SecurityUtils.getUserName());
        jdglDayScheduleWbs.setCreateTime(DateUtils.getNowDate());
        return jdglDayScheduleWbsMapper.insertJdglDayScheduleWbs(jdglDayScheduleWbs);
    }

    @Transactional
    public int insertJdglDayScheduleWbsList(List<JdglDayScheduleWbs> jdglDayScheduleWbsList) {
        if(CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            return 0;
        }

        for (JdglDayScheduleWbs jdglDayScheduleWbs : jdglDayScheduleWbsList) {
//            jdglDayScheduleWbs.setId(IdWorker.createId());
            jdglDayScheduleWbs.setCreateUser(SecurityUtils.getUserName());
            jdglDayScheduleWbs.setCreateTime(DateUtils.getNowDate());
            jdglDayScheduleWbs.setEditer(SecurityUtils.getSysUser().getNickName());
            jdglDayScheduleWbs.setEditerId(SecurityUtils.getUserId()+"");
            jdglDayScheduleWbs.setEditerDate(DateUtils.getNowDate());
        }

        return jdglDayScheduleWbsMapper.insertJdglDayScheduleWbsList(jdglDayScheduleWbsList);
    }

    @Override
    public List<JdglDayScheduleWbs> addWbsList(JdglDayScheduleWbs4Add jdglDayScheduleWbsListParam){

        List<JdglDayScheduleWbs> addWbsList = jdglDayScheduleWbsListParam.getAddWbsList();
        List<JdglDayScheduleWbs> wbsTreeList = jdglDayScheduleWbsListParam.getWbsTreeList() == null ? new ArrayList<>() : jdglDayScheduleWbsListParam.getWbsTreeList();
        Long dayScheduleId = jdglDayScheduleWbsListParam.getDayScheduleId();
        Date date = jdglDayScheduleWbsListParam.getDate();

        if(CollectionUtils.isEmpty(addWbsList)) {
            return null;
        }
//        if(date == null) {
//            throw new RuntimeException("参数异常!");
//        }

        Set<String> ancestorsSet = new HashSet<>();

        addWbsList.stream().forEach(vo -> {
            vo.setId(IdWorker.createId());
            vo.setPid(null);
            vo.setIsAdd("1");
            vo.setIsExists("1");
            vo.setIsLeaf("1");
            vo.setEditerDate(DateUtils.getNowDate());
            ancestorsSet.add(vo.getPtVar3());
        });

        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = TreeUtil.treeToListWithoutId(wbsTreeList);

        List<JdglMainPlanItem> usingJdglMainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemList(new JdglMainPlanItem());

        if(CollectionUtils.isEmpty(usingJdglMainPlanItemList)) {
            return null;
        }

        // 获取图纸复核的清单
        List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();

        List<JdglDayScheduleWbs4Value> totalWbsListByDateRange = getTotalWbsListByDateRange4OnlyWbs(StatisticsUtils.addDays(date, -1));

        for (String ancestors : ancestorsSet) {
            List<JdglMainPlanItem> JdglMainPlanItemAncestorsList = usingJdglMainPlanItemList.stream().filter(vo -> ancestors.contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(JdglMainPlanItemAncestorsList)) {
                for (JdglMainPlanItem jdglMainPlanItem : JdglMainPlanItemAncestorsList) {

                    if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
                        JdglDayScheduleWbs jdglDayScheduleWbs1 = jdglDayScheduleWbsList.stream().filter(vo -> vo.getWbsCode().equals(jdglMainPlanItem.getItemCode())).findFirst().orElse(null);
                        if(jdglDayScheduleWbs1 != null) {
                            jdglDayScheduleWbs1.setPtVar1(jdglMainPlanItem.getWbsObjectId());
                            jdglDayScheduleWbs1.setPtVar2(jdglMainPlanItem.getWbsParentObjectId());
                            jdglDayScheduleWbs1.setPtVar3(jdglMainPlanItem.getAncestors());
                            continue;
                        }
                    }

                    JdglDayScheduleWbs jdglDayScheduleWbs = new JdglDayScheduleWbs();
                    jdglDayScheduleWbs.setId(IdWorker.createId());
                    jdglDayScheduleWbs.setIsLeaf(jdglMainPlanItem.getLeaf());
                    jdglDayScheduleWbs.setWbsCode(jdglMainPlanItem.getItemCode());
                    jdglDayScheduleWbs.setWbsName(jdglMainPlanItem.getItemName());
                    jdglDayScheduleWbs.setUnit(jdglMainPlanItem.getUnit());
                    jdglDayScheduleWbs.setDesignQuantity(jdglMainPlanItem.getQuantity());

                    // 赋值wbs的剩余数量
                    if(!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
                        JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = totalWbsListByDateRange.stream().filter(vo ->
                                jdglMainPlanItem.getItemCode().equals(vo.getWbsCode())
                        ).findFirst().orElse(null);
                        BigDecimal designQuantity = jdglDayScheduleWbs.getDesignQuantity();
                        if(jdglDayScheduleWbs4Value == null) {
                            jdglDayScheduleWbs.setRemainQuantity(designQuantity);
                        } else {
                            if(designQuantity != null && jdglDayScheduleWbs4Value.getThisQuantity() != null) {
                                jdglDayScheduleWbs.setRemainQuantity(designQuantity.subtract(jdglDayScheduleWbs4Value.getThisQuantity()));
                            }
                        }
                    }
                    if(jdglDayScheduleWbs.getRemainQuantity() == null) jdglDayScheduleWbs.setRemainQuantity(jdglDayScheduleWbs.getDesignQuantity());

                    jdglDayScheduleWbs.setEditerId(jdglMainPlanItem.getExecuterId());
                    jdglDayScheduleWbs.setEditer(jdglMainPlanItem.getExecuter());
                    jdglDayScheduleWbs.setIsAdd("1");
                    // 从redis中获取wbs数据
//                    XmslWbs wbsByCode = WbsRedisUtils.getWbsByCode(jdglMainPlanItem.getWbsCode());
//                    XmslWbs wbsByCode = xmslWbsService.getByCode(jdglMainPlanItem.getWbsCode());
//                    if(wbsByCode != null) {
//                        jdglDayScheduleWbs.setWbsId(JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem.getItemType()) ? IdWorker.createId() : Long.valueOf(wbsByCode.getId()));
//                        jdglDayScheduleWbs.setWbsPid(JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem.getItemType()) ? Long.valueOf(wbsByCode.getId()) : Long.valueOf(wbsByCode.getParentId()));
//                        jdglDayScheduleWbs.setAncestrals(wbsByCode.getAncestors());
//                        if(JdglMainPlanItem.ITEMTYPE_WBS.equals(jdglMainPlanItem.getItemType())) {
//                            jdglDayScheduleWbs.setUnit(wbsByCode.getUnit());
//                            jdglDayScheduleWbs.setUnicode(wbsByCode.getUnicode());
//                            // 赋值wbs的设计数量
//                            if(!CollectionUtils.isEmpty(list)) {
//                                XmslDrawReviewList xmslDrawReviewList = list.stream().filter(vo ->
//                                        "1".equals(vo.getImageProgress())
//                                                && jdglMainPlanItem.getItemCode().equals(vo.getWbsCode()))
//                                        .findFirst().orElse(null);
//                                if(xmslDrawReviewList != null) jdglDayScheduleWbs.setDesignQuantity(xmslDrawReviewList.getCheckNum());
//                            }
//                            // 赋值wbs的剩余数量
//                            if(!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
//                                JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = totalWbsListByDateRange.stream().filter(vo ->
//                                        jdglMainPlanItem.getItemCode().equals(vo.getWbsCode())
//                                ).findFirst().orElse(null);
//                                BigDecimal designQuantity = jdglDayScheduleWbs.getDesignQuantity();
//                                if(jdglDayScheduleWbs4Value == null) {
//                                    jdglDayScheduleWbs.setRemainQuantity(designQuantity);
//                                } else {
//                                    if(designQuantity != null && jdglDayScheduleWbs4Value.getThisQuantity() != null) {
//                                        jdglDayScheduleWbs.setRemainQuantity(designQuantity.subtract(jdglDayScheduleWbs4Value.getThisQuantity()));
//                                    }
//                                }
//                            }
//                            if(jdglDayScheduleWbs.getRemainQuantity() == null) jdglDayScheduleWbs.setRemainQuantity(jdglDayScheduleWbs.getDesignQuantity());
//                        }
//                    }
                    // 源数据id
                    jdglDayScheduleWbs.setPtVar1(jdglMainPlanItem.getWbsObjectId());
                    // 源数据父id
                    jdglDayScheduleWbs.setPtVar2(jdglMainPlanItem.getWbsParentObjectId());
                    // 源数据层级码
                    jdglDayScheduleWbs.setPtVar3(jdglMainPlanItem.getAncestors());
                    jdglDayScheduleWbsList.add(jdglDayScheduleWbs);
                }
            }
        }

        jdglDayScheduleWbsList.stream().forEach(vo->{
            if(vo.getPid() == null) {
                JdglDayScheduleWbs jdglDayScheduleWbs1 = jdglDayScheduleWbsList.stream().filter(vo1 -> vo.getPtVar2().equals(vo1.getPtVar1())).findFirst().orElse(null);
                vo.setPid(jdglDayScheduleWbs1 == null ? null : jdglDayScheduleWbs1.getId());
            }
        });

        List<JdglDayScheduleWbs> build = TreeUtil.build(jdglDayScheduleWbsList, null);

        return build;

    }

    @Override
    public int deleteJdglDayScheduleWbsByOrLevel(List<JdglDayScheduleWbs> deleteWbsList, Long dayScheduleId) {
        if(CollectionUtils.isEmpty(deleteWbsList)) {
            return 0;
        }

        List<JdglDayScheduleWbs> listNeedDelete = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (JdglDayScheduleWbs dayScheduleWbs: deleteWbsList) {

            JdglDayScheduleWbs jdglDayScheduleWbs1 = new JdglDayScheduleWbs();
            jdglDayScheduleWbs1.setDayScheduleId(dayScheduleWbs.getDayScheduleId());
            jdglDayScheduleWbs1.setPid(dayScheduleWbs.getId());
            List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs1);
            if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
                if(CollectionUtils.isEmpty(dayScheduleWbs.getChildren())) {
                    listNeedDelete.add(dayScheduleWbs);
                    ids.add(dayScheduleWbs.getId());
                } else {
                    if(jdglDayScheduleWbsList.size() == dayScheduleWbs.getChildren().size()) {
                        listNeedDelete.add(dayScheduleWbs);
                        ids.add(dayScheduleWbs.getId());
                        List<JdglDayScheduleWbs> children = dayScheduleWbs.getChildren();
                        List<JdglDayScheduleWbs> jdglDayScheduleWbsList1 = TreeUtil.treeToListWithoutId(children);
                        for (JdglDayScheduleWbs jdglDayScheduleWbs11 : jdglDayScheduleWbsList1) {
                            listNeedDelete.add(jdglDayScheduleWbs11);
                            ids.add(jdglDayScheduleWbs11.getId());;
                        }
                    }
                }

            } else {
                listNeedDelete.add(dayScheduleWbs);
                ids.add(dayScheduleWbs.getId());
            }
        }

        return deleteJdglDayScheduleBillByDayWbsIds(ids, dayScheduleId);
    }

    @Transactional
    public int updateJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        jdglDayScheduleWbs.setUpdateUser(SecurityUtils.getUserName());
        jdglDayScheduleWbs.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleWbsMapper.updateJdglDayScheduleWbs(jdglDayScheduleWbs);
    }

    @Transactional
    public int updateJdglDayScheduleWbsList(List<JdglDayScheduleWbs> jdglDayScheduleWbsList, Long dayScheduleId) {

        if(CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            return 0;
        }

        List<JdglDayScheduleBill> jdglDayScheduleBillListAdd = new ArrayList<>();
        List<JdglDayScheduleBill> jdglDayScheduleBillListUpdate = new ArrayList<>();

        for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbsList ) {
            List<JdglDayScheduleBill> jdglDayScheduleBillList = jdglDayScheduleWbs1.getJdglDayScheduleBillList();
            if(CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
                continue;
            }
            for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
                BigDecimal thisQuantity = jdglDayScheduleBill.getThisQuantity();
                BigDecimal billPrice = jdglDayScheduleBill.getBillPrice();
                BigDecimal billValue = thisQuantity == null || billPrice == null ? new BigDecimal(0) : thisQuantity.multiply(billPrice);
                jdglDayScheduleBill.setBillValue(billValue);
                jdglDayScheduleBill.setDayScheduleId(dayScheduleId);
                if(jdglDayScheduleBill.getId() == null) {
                    jdglDayScheduleBillListAdd.add(jdglDayScheduleBill);
                } else {
                    jdglDayScheduleBillListUpdate.add(jdglDayScheduleBill);
                }
            }
        }

        iJdglDayScheduleBillService.insertJdglDayScheduleBillList(jdglDayScheduleBillListAdd);
        iJdglDayScheduleBillService.updateJdglDayScheduleBillList(jdglDayScheduleBillListUpdate);

        return jdglDayScheduleWbsMapper.updateJdglDayScheduleWbsList(jdglDayScheduleWbsList);
    }

    @Transactional
    public int deleteJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        jdglDayScheduleWbs.setUpdateUser(SecurityUtils.getUserName());
        jdglDayScheduleWbs.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbs(jdglDayScheduleWbs);
    }

    @Transactional
    public int deleteJdglDayScheduleWbsByPks(List<Long> jdglDayScheduleWbsPkList) {
        if(CollectionUtils.isEmpty(jdglDayScheduleWbsPkList)) {
            return 0;
        }
//        iJdglDayScheduleBillService.deleteJdglDayScheduleBillByDayWbsIds(jdglDayScheduleWbsPkList);

        return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbsByPks(jdglDayScheduleWbsPkList);
    }

    @Transactional
    public int deleteJdglDayScheduleBillByDayWbsIds(List<Long> jdglDayScheduleWbsPkList, Long dayScheduleId) {
        if(CollectionUtils.isEmpty(jdglDayScheduleWbsPkList)) {
            return 0;
        }
        iJdglDayScheduleBillService.deleteJdglDayScheduleBillByDayWbsIds(jdglDayScheduleWbsPkList, dayScheduleId);

        return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbsByPks(jdglDayScheduleWbsPkList);
    }


}
