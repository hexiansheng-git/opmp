package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.vo.ActivityInfoVoBean;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.mapper.JdglMainPlanItemMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglData4P6Service;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark
 */
@Service
public class JdglMainPlanItemServiceImpl implements IJdglMainPlanItemService {

    @Autowired
    private JdglMainPlanItemMapper jdglMainPlanItemMapper;

    @Autowired
    private IJdglMainPlanService jdglMainPlanService;

    @Autowired
    private IJdglData4P6Service jdglData4P6Service;


    public JdglMainPlanItem getJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem) {
        return jdglMainPlanItemMapper.getJdglMainPlanItem(jdglMainPlanItem);
    }

    public List<JdglMainPlanItem> getJdglMainPlanItemList(JdglMainPlanItem jdglMainPlanItem) {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemMapper.getJdglMainPlanItemList(jdglMainPlanItem);
        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return jdglMainPlanItemList;
        }
//        List<JdglMainPlanItem> build = TreeUtil.build(jdglMainPlanItemList, jdglMainPlanItem.getPid());
        return jdglMainPlanItemList;
    }

    /**
     * 懒加载数据
     * @param jdglMainPlanItem
     * @return
     */
    public List<JdglMainPlanItem> getJdglMainPlanItemList4Lazy(JdglMainPlanItem jdglMainPlanItem) {
        List<JdglMainPlanItem> returnList = new ArrayList<>();
        if(jdglMainPlanItem == null) {
            return returnList;
        }
        Long mainPlanId = jdglMainPlanItem.getMainPlanId();
        if(mainPlanId == null) {
            JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlanNoItem();
            if(usingJdglMainPlan != null) {
                jdglMainPlanItem.setMainPlanId(usingJdglMainPlan.getId());
            }
        }

        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemMapper.getJdglMainPlanItemList(jdglMainPlanItem);

        if(StringUtils.isNotEmpty(jdglMainPlanItem.getItemCode()) || StringUtils.isNotEmpty(jdglMainPlanItem.getItemName())) {
            return jdglMainPlanItemList;
        }

        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return jdglMainPlanItemList;
        }

        if(jdglMainPlanItem.getPid() == null) {
            returnList = jdglMainPlanItemList.stream().filter(vo -> vo.getPid() == null).collect(Collectors.toList());
        } else {
            returnList = jdglMainPlanItemList;
        }

        if (!CollectionUtils.isEmpty(returnList)) {
            for (JdglMainPlanItem jdglMainPlanItem1 : returnList) {
                if("1".equals(jdglMainPlanItem1.getLeaf())) jdglMainPlanItem1.setHaveChildren(0);
                if(!"1".equals(jdglMainPlanItem1.getLeaf())) jdglMainPlanItem1.setHaveChildren(1);
            }
        }

//        List<JdglMainPlanItem> build = TreeUtil.build(jdglMainPlanItemList, jdglMainPlanItem.getPid());
        return returnList;
    }

    public List<JdglMainPlanItem> getJdglMainPlanItemListNoTree(JdglMainPlanItem jdglMainPlanItem) {
        Long mainPlanId = jdglMainPlanItem.getMainPlanId();
        String itemName = jdglMainPlanItem.getItemName();
        Date startDate = jdglMainPlanItem.getStartDate();
        jdglMainPlanItem = new JdglMainPlanItem();
        jdglMainPlanItem.setMainPlanId(mainPlanId);
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemMapper.getJdglMainPlanItemList(jdglMainPlanItem);
        if(!CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            List<JdglMainPlanItem> list4Query = new ArrayList<>(jdglMainPlanItemList);
            if(StringUtils.isNotEmpty(itemName)) {
                list4Query = list4Query.stream().filter(vo -> (StringUtils.isNotEmpty(vo.getItemName()) && vo.getItemName().contains(itemName))
                    || (StringUtils.isNotEmpty(vo.getItemCode()) && vo.getItemCode().contains(itemName))
                        || (StringUtils.isNotEmpty(vo.getExecuter()) && vo.getExecuter().contains(itemName))).collect(Collectors.toList());
            }
            if(startDate != null) {
                list4Query = list4Query.stream().filter(vo -> vo.getStartDate() != null && startDate.compareTo(vo.getStartDate()) == 0).collect(Collectors.toList());
            }
            if(CollectionUtils.isNotEmpty(list4Query)) {
                // 根据作业名称过滤数据
                List<JdglMainPlanItem> list4Filter = new ArrayList<>();
                if(CollectionUtils.isNotEmpty(list4Query)) {
                    for (JdglMainPlanItem item4ItemName : list4Query) {
                        String ancestors = item4ItemName.getAncestors();
                        // 获取过滤后的作业节点数据
                        List<JdglMainPlanItem> list4Item = jdglMainPlanItemList.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())
                                && vo.getAncestors().contains(ancestors)).collect(Collectors.toList());
                        if(CollectionUtils.isNotEmpty(list4Item)) {
                            for (JdglMainPlanItem item4item : list4Item) {
                                // 根据作业获取所有祖籍数据
                                List<JdglMainPlanItem> collect = jdglMainPlanItemList.stream().filter(vo -> item4item.getAncestors().contains(vo.getAncestors())).collect(Collectors.toList());
                                if(CollectionUtils.isNotEmpty(collect)) list4Filter.addAll(collect);
                            }
                        }
                    }
                }
                jdglMainPlanItemList = list4Filter.stream().distinct().collect(Collectors.toList());
            } else {
                return new ArrayList<>();
            }
            for (JdglMainPlanItem jdglMainPlanItem1 : jdglMainPlanItemList) {
                // 计划完成百分比 * 100
                jdglMainPlanItem1.setSchedulePercentComplete(jdglMainPlanItem1.getSchedulePercentComplete() == null ? BigDecimal.ZERO : jdglMainPlanItem1.getSchedulePercentComplete().multiply(new BigDecimal(100)));
                // 尚需工期 / 8
                if(jdglMainPlanItem1.getRemainingDuration() != null)
                    jdglMainPlanItem1.setRemainingDuration(new BigDecimal(jdglMainPlanItem1.getRemainingDuration()).divide(new BigDecimal(8), 0, BigDecimal.ROUND_UP).intValue());
                // 总浮时 / 8
                if(jdglMainPlanItem1.getTotalFloat() != null)
                    jdglMainPlanItem1.setTotalFloat(new BigDecimal(jdglMainPlanItem1.getTotalFloat()).divide(new BigDecimal(8), 0, BigDecimal.ROUND_UP).intValue());
                // 自由浮时 / 8
                if(jdglMainPlanItem1.getFreeFloat() != null)
                    jdglMainPlanItem1.setFreeFloat(new BigDecimal(jdglMainPlanItem1.getFreeFloat()).divide(new BigDecimal(8), 0, BigDecimal.ROUND_UP).intValue());
                // 是否关键线路转换 0：否，1：是
                if(jdglMainPlanItem1.getIsCritical() != null && JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem1.getItemType()))
                    jdglMainPlanItem1.setIsCritical("1".equals(jdglMainPlanItem1.getIsCritical()) ? "是" : "否");

                jdglMainPlanItem1.setPlannedDuration(StatisticsUtils.getDaysByRangeDate(jdglMainPlanItem1.getStartDate(), jdglMainPlanItem1.getFinishDate()));

                jdglMainPlanItem1.setText(jdglMainPlanItem1.getItemName());
                jdglMainPlanItem1.setParent(jdglMainPlanItem1.getPid());
                // 如果已经有实际开始时间，则取实际开始时间，否则取尚需最早开始;
                Date start_date = jdglMainPlanItem1.getActualStartDate() != null
                        ? jdglMainPlanItem1.getActualStartDate() : jdglMainPlanItem1.getRemainingEarlyStartDate();
                jdglMainPlanItem1.setStart_date(start_date);

                // 如果已经有实际完成时间，则取实际完成时间，否则取尚需最早完成;
                Date end_date = jdglMainPlanItem1.getActualFinishDate() != null
                        ? jdglMainPlanItem1.getActualFinishDate() : jdglMainPlanItem1.getRemainingEarlyFinishDate();

                // 计算总工期（天。尚需与实际综合计算）
                Integer plannedDuration = StatisticsUtils.getDaysByRangeDate(start_date, end_date);
                jdglMainPlanItem1.setDuration(new BigDecimal(plannedDuration));
//                jdglMainPlanItem1.setPlannedDuration(plannedDuration);

                jdglMainPlanItem1.setOpen(false);
//                jdglMainPlanItem1.setType("task");

                // 实际开始
                Date actualStartDate = jdglMainPlanItem1.getActualStartDate();
                // 实际结束（如没结束，则取当前时间）
                Date actualFinishDate = jdglMainPlanItem1.getActualFinishDate() != null
                        ? jdglMainPlanItem1.getActualFinishDate() : DateUtils.getNowDate();
                if(actualStartDate != null) {
                    // 计算进度
                    Integer daysByRangeDate = StatisticsUtils.getDaysByRangeDate(actualStartDate, actualFinishDate);
                    BigDecimal progress = new BigDecimal(plannedDuration == 0 ? 0 : (float)daysByRangeDate/(float)plannedDuration);
                    progress = progress.setScale(2, RoundingMode.HALF_UP);
                    jdglMainPlanItem1.setProgress(progress);
                } else {
                    jdglMainPlanItem1.setProgress(new BigDecimal(0));
                }

                // wbs层做子集块汇总拼接
                if("wbs".equals(jdglMainPlanItem1.getItemType())) {
                    jdglMainPlanItem1.setRender("split");
                    jdglMainPlanItem1.setType("wbs");
                }
                // 判断里程碑
                if(jdglMainPlanItem1.getTaskType() != null && jdglMainPlanItem1.getTaskType().contains("Milestone")) {
                    if("Finish Milestone".equals(jdglMainPlanItem1.getTaskType())) {
                        jdglMainPlanItem1.setStart_date(jdglMainPlanItem1.getFinishDate());
                    }
                    jdglMainPlanItem1.setType("milestone");
                    jdglMainPlanItem1.setRollup(true);
                }
            }
        }
        return jdglMainPlanItemList;
    }

    @Transactional
    public int insertJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem) {
        jdglMainPlanItem.setId(IdWorker.createId());
        jdglMainPlanItem.setCreateUser(SecurityUtils.getUserName());
        jdglMainPlanItem.setCreateTime(DateUtils.getNowDate());
        return jdglMainPlanItemMapper.insertJdglMainPlanItem(jdglMainPlanItem);
    }

    @Transactional
    public int insertJdglMainPlanItemList(List<JdglMainPlanItem> jdglMainPlanItemList) {
        for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItemList) {
//            jdglMainPlanItem.setId(IdWorker.createId());
//            jdglMainPlanItem.setCreateUser(SecurityUtils.getSysUser().getNickName());
            jdglMainPlanItem.setCreateTime(DateUtils.getNowDate());
//            jdglMainPlanItem.setUpdateUser(SecurityUtils.getSysUser().getNickName());
//            jdglMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanItemMapper.insertJdglMainPlanItemList(jdglMainPlanItemList);
    }

    @Transactional
    public int updateJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem) {
        jdglMainPlanItem.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        if(JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem.getItemType())) {
//            jdglData4P6Service.pushUserToP6(jdglMainPlanItem.getItemCode(), jdglMainPlanItem.getExecuter());
        }
        return jdglMainPlanItemMapper.updateJdglMainPlanItem(jdglMainPlanItem);
    }

    @Transactional
    public int updateJdglMainPlanItemList(List<JdglMainPlanItem> jdglMainPlanItemList) {
        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return 0;
        }
        List<JdglMainPlanItem> jdglMainPlanItems = TreeUtil.treeToListWithoutId(jdglMainPlanItemList);
        List<ActivityInfoVoBean> activityInfoVoBeanList = new ArrayList<>();
        for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItems) {
            jdglMainPlanItem.setIsCritical(JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem.getItemType()) && "是".equals(jdglMainPlanItem.getIsCritical()) ? "1" : "0");
            jdglMainPlanItem.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            jdglMainPlanItem.setUpdateTime(DateUtils.getNowDate());
            if(JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem.getItemType())) {
                ActivityInfoVoBean activityInfoVoBean = new ActivityInfoVoBean();
                activityInfoVoBean.setId(jdglMainPlanItem.getItemCode());
                activityInfoVoBean.setJobPerson(jdglMainPlanItem.getExecuter());
                activityInfoVoBeanList.add(activityInfoVoBean);
            }
        }
//        if(CollectionUtils.isNotEmpty(activityInfoVoBeanList)) {
//            jdglData4P6Service.pushUserToP6(activityInfoVoBeanList);
//        }
        return jdglMainPlanItemMapper.updateJdglMainPlanItemList(jdglMainPlanItems);
    }

    @Transactional
    public int deleteJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem) {
        jdglMainPlanItem.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanItemMapper.deleteJdglMainPlanItem(jdglMainPlanItem);
    }

    @Transactional
    public int deleteJdglMainPlanItemByPks(List<Long> jdglMainPlanItemPkList) {
        return jdglMainPlanItemMapper.deleteJdglMainPlanItemByPks(jdglMainPlanItemPkList);
    }

    @Override
    public List<JdglMainPlanItem> getUsingJdglMainPlanItemList(JdglMainPlanItem jdglMainPlanItem) {

        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlanNoItem();

        if(usingJdglMainPlan != null) {
            Long id = usingJdglMainPlan.getId();
            if(jdglMainPlanItem == null) jdglMainPlanItem = new JdglMainPlanItem();
            jdglMainPlanItem.setMainPlanId(id);
            return getJdglMainPlanItemList(jdglMainPlanItem);

        }

        return null;
    }

    @Override
    public List<JdglMainPlanItem> getUsingJdglMainPlanItemListByDate(Date date) {
        Long mainPlanId = 0l;
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlanNoItem();
        if(usingJdglMainPlan != null) {
            mainPlanId = usingJdglMainPlan.getId();
        }
        return jdglMainPlanItemMapper.getUsingJdglMainPlanItemListByDate(date, mainPlanId);
    }

    @Override
    public List<JdglMainPlanItem> getUsingJdglMainPlanItemListByDateRange(Date startDate, Date endDate) {
        Long mainPlanId = 0l;
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlanNoItem();
        if(usingJdglMainPlan != null) {
            mainPlanId = usingJdglMainPlan.getId();
        }
        return jdglMainPlanItemMapper.getUsingJdglMainPlanItemListByDateRange(startDate, endDate, mainPlanId);
    }

    public JdglMainPlanItem getMaxActualStartDate() {
        Long mainPlanId = 0l;
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlanNoItem();
        if(usingJdglMainPlan != null) {
            mainPlanId = usingJdglMainPlan.getId();
        }
        return jdglMainPlanItemMapper.getMaxActualStartDate(mainPlanId);
    }

    /**
     * 获取关键线路数据
     * @param jdglMainPlanItemParam
     * @return
     */
    @Override
    public List<JdglMainPlanItem> getKeyRoad(JdglMainPlanItem jdglMainPlanItemParam) {
        Long mainPlanId = jdglMainPlanItemParam.getMainPlanId();
        String itemName = jdglMainPlanItemParam.getItemName();
        Date startDate = jdglMainPlanItemParam.getStartDate();
        jdglMainPlanItemParam = new JdglMainPlanItem();
        jdglMainPlanItemParam.setMainPlanId(mainPlanId);
        List<JdglMainPlanItem> returnList = new ArrayList<>();
        List<JdglMainPlanItem> jdglMainPlanItemListNoTree = getJdglMainPlanItemListNoTree(jdglMainPlanItemParam);
        if(CollectionUtils.isEmpty(jdglMainPlanItemListNoTree)) {
            return returnList;
        }
        List<JdglMainPlanItem> collect = jdglMainPlanItemListNoTree.stream().filter(vo -> "是".equals(vo.getIsCritical())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect)) {
            return returnList;
        }
        returnList.addAll(collect);
        Set<Long> ids = new HashSet<>();
        for (JdglMainPlanItem jdglMainPlanItem : collect) {
            List<JdglMainPlanItem> collect1 = jdglMainPlanItemListNoTree.stream().filter(vo -> "wbs".equals(vo.getItemType()) && jdglMainPlanItem.getAncestors().contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect1)) {
                for (JdglMainPlanItem jdglMainPlanItem1 : collect1) {
                    ids.add(jdglMainPlanItem1.getId());
                }
            }
//            if(!CollectionUtils.isEmpty(collect1)) returnList.addAll(collect1);
        }
        if(!CollectionUtils.isEmpty(ids)) {
            for (Long id : ids) {
                JdglMainPlanItem jdglMainPlanItem = jdglMainPlanItemListNoTree.stream().filter(vo -> id.equals(vo.getId())).findFirst().orElse(null);
                if(jdglMainPlanItem != null) returnList.add(jdglMainPlanItem);
            }
        }
        if(CollectionUtils.isNotEmpty(returnList)) {
            List<JdglMainPlanItem> list4Query = new ArrayList<>(returnList);
            if(StringUtils.isNotEmpty(itemName)) {
                list4Query = list4Query.stream().filter(vo -> (StringUtils.isNotEmpty(vo.getItemName()) && vo.getItemName().contains(itemName))
                        || (StringUtils.isNotEmpty(vo.getItemCode()) && vo.getItemCode().contains(itemName))
                        || (StringUtils.isNotEmpty(vo.getExecuter()) && vo.getExecuter().contains(itemName))).collect(Collectors.toList());
            }

            if(startDate != null) {
                list4Query = list4Query.stream().filter(vo -> vo.getStartDate() != null && startDate.compareTo(vo.getStartDate()) == 0).collect(Collectors.toList());
            }
            // 根据作业名称过滤数据
            List<JdglMainPlanItem> list4Filter = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(list4Query)) {
                for (JdglMainPlanItem item4ItemName : list4Query) {
                    String ancestors = item4ItemName.getAncestors();
                    // 获取过滤后的作业节点数据
                    List<JdglMainPlanItem> list4Item = returnList.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())
                            && vo.getAncestors().contains(ancestors)).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(list4Item)) {
                        for (JdglMainPlanItem item4item : list4Item) {
                            // 根据作业获取所有祖籍数据
                            List<JdglMainPlanItem> collect1 = returnList.stream().filter(vo -> item4item.getAncestors().contains(vo.getAncestors())).collect(Collectors.toList());
                            if(CollectionUtils.isNotEmpty(collect1)) list4Filter.addAll(collect1);
                        }
                    }
                }
            } else {
                return new ArrayList<>();
            }
            returnList = list4Filter.stream().distinct().collect(Collectors.toList());

        }
//        List<JdglMainPlanItem> collect1 = returnList.stream().sorted(Comparator.comparing(JdglMainPlanItem::getWbsCode).thenComparing(JdglMainPlanItem::getLeaf).thenComparing(JdglMainPlanItem::getItemCode)).collect(Collectors.toList());
//        Stream<JdglMainPlanItem> sorted = returnList.stream().sorted(Comparator.comparing(JdglMainPlanItem::getSort));
//        Stream<JdglMainPlanItem> sorted1 = sorted.collect(Collectors.toList()).stream().sorted(Comparator.comparing(JdglMainPlanItem::getItemCode));
        List<JdglMainPlanItem> collect1  = returnList.stream().sorted(Comparator.comparing(JdglMainPlanItem::getSort, Comparator.nullsFirst(Integer::compareTo)).thenComparing(JdglMainPlanItem::getItemCode, Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
        return CollectionUtils.isEmpty(collect1) ? new ArrayList<>() : collect1;
    }

    @Override
    public JdglMainPlanItem getUsing4One(JdglMainPlanItem jdglMainPlanItemParam) {

        if(jdglMainPlanItemParam == null || StringUtils.isEmpty(jdglMainPlanItemParam.getWbsCode())) {
            throw new RuntimeException("参数异常!");
        }

        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlanNoItem();

        if(usingJdglMainPlan != null) {
            jdglMainPlanItemParam.setMainPlanId(usingJdglMainPlan.getId());
            jdglMainPlanItemParam.setItemCode(jdglMainPlanItemParam.getWbsCode());
            JdglMainPlanItem jdglMainPlanItem = getJdglMainPlanItem(jdglMainPlanItemParam);
            return jdglMainPlanItem;
        }

        return null;
    }

    /**
     * 获取项目开始与结束
     * @return
     */
    @Override
    public JdglMainPlanItem getProjStartAndFinish() {
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlanNoItem();
        if(usingJdglMainPlan == null) {
            return null;
        }
        return jdglMainPlanItemMapper.getProjStartAndFinish(usingJdglMainPlan.getId());

    }

    @Override
    public List<JdglMainPlanItem> getUsingKeyRoad() {
        List<JdglMainPlanItem> returnList = new ArrayList<>();
        List<JdglMainPlanItem> usingJdglMainPlanItemList = getUsingJdglMainPlanItemList(new JdglMainPlanItem());
        if (CollectionUtils.isEmpty(usingJdglMainPlanItemList)) return returnList;

        List<JdglMainPlanItem> collect = usingJdglMainPlanItemList.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())
                && "1".equals(vo.getIsCritical())).collect(Collectors.toList());
        returnList.addAll(collect);
        for (JdglMainPlanItem jdglMainPlanItem : collect) {
            List<JdglMainPlanItem> collect1 = usingJdglMainPlanItemList.stream().filter(vo -> jdglMainPlanItem.getAncestors().contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect1)) returnList.addAll(collect1);
        }
        returnList = returnList.stream().distinct().collect(Collectors.toList());
        return returnList;
    }

    @Override
    public List<JdglMainPlanItem> getUsingNoKeyRoad() {
        List<JdglMainPlanItem> returnList = new ArrayList<>();
        List<JdglMainPlanItem> usingJdglMainPlanItemList = getUsingJdglMainPlanItemList(new JdglMainPlanItem());
        if(CollectionUtils.isEmpty(usingJdglMainPlanItemList)) return returnList;
        List<JdglMainPlanItem> collect = usingJdglMainPlanItemList.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())
                && "0".equals(vo.getIsCritical())).collect(Collectors.toList());
        returnList.addAll(collect);
        for (JdglMainPlanItem jdglMainPlanItem : collect) {
            List<JdglMainPlanItem> collect1 = usingJdglMainPlanItemList.stream().filter(vo -> jdglMainPlanItem.getAncestors().contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect1)) returnList.addAll(collect1);
        }
        returnList = returnList.stream().distinct().collect(Collectors.toList());
        return returnList;
    }

    @Override
    public List<JdglMainPlanItem> getUsingJdglMainPlanItemByItemCodes(List<String> itemCodes) {

//        List<JdglMainPlanItem> returnList = new ArrayList<>();

        if(CollectionUtils.isEmpty(itemCodes)) {
            return null;
        }

        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlanNoItem();

        if(usingJdglMainPlan == null) {
            return null;
        }

        return jdglMainPlanItemMapper.getUsingJdglMainPlanItemByItemCodes(usingJdglMainPlan.getId(), itemCodes);

//        List<JdglMainPlanItem> usingJdglMainPlanItemList = getUsingJdglMainPlanItemList(new JdglMainPlanItem());
//
//        if(CollectionUtils.isEmpty(usingJdglMainPlanItemList)) {
//            return returnList;
//        }
//
//        for (String itemCode: itemCodes) {
//            JdglMainPlanItem jdglMainPlanItem = usingJdglMainPlanItemList.stream().filter(vo -> itemCode.equals(vo.getItemCode())).findFirst().orElse(null);
//            if(jdglMainPlanItem != null) returnList.add(jdglMainPlanItem);
//        }

//        return returnList;
    }

    @Override
    public List<JdglMainPlanItem> getJdglMainPlanItemByMainPlanId(Long mainPlanId) {
        if(mainPlanId == null) return null;
        JdglMainPlanItem jdglMainPlanItem = new JdglMainPlanItem();
        jdglMainPlanItem.setMainPlanId(mainPlanId);
        return getJdglMainPlanItemList(jdglMainPlanItem);
    }
}
