package com.hhwy.pm.qqch.sgch.mainpl.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.domain.vo.QqchMainPlanItemVo;
import com.hhwy.pm.qqch.sgch.mainpl.mapper.QqchMainPlanItemMapper;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.utils.VersionUtil;
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
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark
 */
@Service
public class QqchMainPlanItemServiceImpl implements IQqchMainPlanItemService {

    @Autowired
    private QqchMainPlanItemMapper qqchMainPlanItemMapper;

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchMainPlanItem getQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem) {
        return qqchMainPlanItemMapper.getQqchMainPlanItem(qqchMainPlanItem);
    }

    public List<QqchMainPlanItem> getQqchMainPlanItemList(QqchMainPlanItem qqchMainPlanItem) {
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemMapper.getQqchMainPlanItemList(qqchMainPlanItem);
        if(CollectionUtils.isEmpty(qqchMainPlanItemList)) {
            return qqchMainPlanItemList;
        }
//        List<QqchMainPlanItem> build = TreeUtil.build(qqchMainPlanItemList, qqchMainPlanItem.getPid());
        return qqchMainPlanItemList;
    }

    @Override
    public List<QqchMainPlanItem> getListByItemCodes(String itemCodes) {
        BigDecimal version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, null);
        return qqchMainPlanItemMapper.getListByItemCodes(itemCodes,version);
    }

    /**
     * 懒加载数据
     * @param qqchMainPlanItem
     * @return
     */
    public List<QqchMainPlanItem> getQqchMainPlanItemList4Lazy(QqchMainPlanItem qqchMainPlanItem) {

        BigDecimal version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, qqchMainPlanItem.getVersion());
        qqchMainPlanItem.setVersion(version);

        List<QqchMainPlanItem> returnList = new ArrayList<>();
        if(qqchMainPlanItem == null) {
            return returnList;
        }
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemMapper.getQqchMainPlanItemList(qqchMainPlanItem);
        if(CollectionUtils.isEmpty(qqchMainPlanItemList)) {
            return qqchMainPlanItemList;
        }

        if(qqchMainPlanItem.getPid() == null) {
            returnList = qqchMainPlanItemList.stream().filter(vo -> vo.getPid() == null).collect(Collectors.toList());
        } else {
            returnList = qqchMainPlanItemList;
        }

        if (!CollectionUtils.isEmpty(returnList)) {
            for (QqchMainPlanItem qqchMainPlanItem1 : returnList) {
                if("1".equals(qqchMainPlanItem1.getLeaf())) qqchMainPlanItem1.setHaveChildren(0);
                if(!"1".equals(qqchMainPlanItem1.getLeaf())) qqchMainPlanItem1.setHaveChildren(1);
            }
        }

        return returnList;
    }

    public List<QqchMainPlanItem> getQqchMainPlanItemListNoTree(QqchMainPlanItem qqchMainPlanItem) {
        BigDecimal version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, qqchMainPlanItem.getVersion());
        qqchMainPlanItem.setVersion(version);
        List<QqchMainPlanItem> qqchMainPlanItemList = qqchMainPlanItemMapper.getQqchMainPlanItemList(qqchMainPlanItem);
        if(!CollectionUtils.isEmpty(qqchMainPlanItemList)) {
            for (QqchMainPlanItem qqchMainPlanItem1 : qqchMainPlanItemList) {
                qqchMainPlanItem1.setText(qqchMainPlanItem1.getItemName());
                qqchMainPlanItem1.setParent(qqchMainPlanItem1.getPid());

                // 如果已经有实际开始时间，则取实际开始时间，否则取尚需最早开始;
                Date start_date = qqchMainPlanItem1.getActualStartDate() != null
                        ? qqchMainPlanItem1.getActualStartDate() : qqchMainPlanItem1.getRemainingEarlyStartDate();
                qqchMainPlanItem1.setStart_date(start_date);

                // 如果已经有实际完成时间，则取实际完成时间，否则取尚需最早完成;
                Date end_date = qqchMainPlanItem1.getActualFinishDate() != null
                        ? qqchMainPlanItem1.getActualFinishDate() : qqchMainPlanItem1.getRemainingEarlyFinishDate();

                // 计算总工期（天。尚需与实际综合计算）
                Integer plannedDuration = StatisticsUtils.getDaysByRangeDate(start_date, end_date);
                qqchMainPlanItem1.setDuration(new BigDecimal(plannedDuration));

                qqchMainPlanItem1.setOpen(true);
//                qqchMainPlanItem1.setType("task");

                // 实际开始
                Date actualStartDate = qqchMainPlanItem1.getActualStartDate();
                // 实际结束（如没结束，则取当前时间）
                Date actualFinishDate = qqchMainPlanItem1.getActualFinishDate() != null
                        ? qqchMainPlanItem1.getActualFinishDate() : DateUtils.getNowDate();
                if(actualStartDate != null) {
                    // 计算进度
                    Integer daysByRangeDate = StatisticsUtils.getDaysByRangeDate(actualStartDate, actualFinishDate);
                    BigDecimal progress = new BigDecimal(plannedDuration == 0 ? 0 : (float)daysByRangeDate/(float)plannedDuration);
                    progress = progress.setScale(2, RoundingMode.HALF_UP);
                    qqchMainPlanItem1.setProgress(progress);
                } else {
                    qqchMainPlanItem1.setProgress(new BigDecimal(0));
                }

                // wbs层做子集块汇总拼接
                if("wbs".equals(qqchMainPlanItem1.getItemType())) {
                    qqchMainPlanItem1.setRender("split");
                }
                // 判断里程碑
                if(qqchMainPlanItem1.getTaskType() != null && qqchMainPlanItem1.getTaskType().contains("Milestone")) {
                    if(QqchMainPlanItem.FINISH_MILESTONE.equals(qqchMainPlanItem1.getTaskType())) {
                        qqchMainPlanItem1.setStart_date(qqchMainPlanItem1.getFinishDate());
                    }
                    qqchMainPlanItem1.setType(QqchMainPlanItem.TYPE_MILESTONE);
                    qqchMainPlanItem1.setRollup(true);
                }
            }
        }
        return qqchMainPlanItemList;
    }

    @Transactional
    public int insertQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem) {
        qqchMainPlanItem.setId(IdWorker.createId());
        qqchMainPlanItem.setCreateUser(SecurityUtils.getUserName());
        qqchMainPlanItem.setCreateTime(DateUtils.getNowDate());
        return qqchMainPlanItemMapper.insertQqchMainPlanItem(qqchMainPlanItem);
    }

    @Transactional
    public int insertQqchMainPlanItemList(List<QqchMainPlanItem> qqchMainPlanItemList) {
        for (QqchMainPlanItem qqchMainPlanItem : qqchMainPlanItemList) {
//            qqchMainPlanItem.setId(IdWorker.createId());
            qqchMainPlanItem.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchMainPlanItem.setCreateTime(DateUtils.getNowDate());
//            qqchMainPlanItem.setUpdateUser(SecurityUtils.getSysUser().getNickName());
//            qqchMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMainPlanItemMapper.insertQqchMainPlanItemList(qqchMainPlanItemList);
    }

    @Transactional
    public int updateQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem) {
        qqchMainPlanItem.setUpdateUser(SecurityUtils.getUserName());
        qqchMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        return qqchMainPlanItemMapper.updateQqchMainPlanItem(qqchMainPlanItem);
    }

    @Transactional
    public int updateQqchMainPlanItemList(List<QqchMainPlanItem> qqchMainPlanItemList) {
        if(CollectionUtils.isEmpty(qqchMainPlanItemList)) {
            return 0;
        }
        List<QqchMainPlanItem> qqchMainPlanItems = TreeUtil.treeToListWithoutId(qqchMainPlanItemList);
        for (QqchMainPlanItem qqchMainPlanItem : qqchMainPlanItems) {
            qqchMainPlanItem.setUpdateUser(SecurityUtils.getUserName());
            qqchMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMainPlanItemMapper.updateQqchMainPlanItemList(qqchMainPlanItems);
    }

    @Transactional
    public int deleteQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem) {
        qqchMainPlanItem.setUpdateUser(SecurityUtils.getUserName());
        qqchMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        return qqchMainPlanItemMapper.deleteQqchMainPlanItem(qqchMainPlanItem);
    }

    @Transactional
    public int deleteQqchMainPlanItemByPks(List<Long> qqchMainPlanItemPkList) {
        return qqchMainPlanItemMapper.deleteQqchMainPlanItemByPks(qqchMainPlanItemPkList);
    }

    /**
     * 获取关键线路数据
     * @param qqchMainPlanItemParam
     * @return
     */
    @Override
    public List<QqchMainPlanItem> getKeyRoad(QqchMainPlanItem qqchMainPlanItemParam) {

        List<QqchMainPlanItem> returnList = new ArrayList<>();
        List<QqchMainPlanItem> qqchMainPlanItemListNoTree = getQqchMainPlanItemListNoTree(qqchMainPlanItemParam);
        if(CollectionUtils.isEmpty(qqchMainPlanItemListNoTree)) {
            return returnList;
        }
        List<QqchMainPlanItem> collect = qqchMainPlanItemListNoTree.stream().filter(vo -> "1".equals(vo.getIsCritical())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect)) {
            return returnList;
        }
        returnList.addAll(collect);
        Set<Long> ids = new HashSet<>();
        for (QqchMainPlanItem qqchMainPlanItem : collect) {
            List<QqchMainPlanItem> collect1 = qqchMainPlanItemListNoTree.stream().filter(vo -> "wbs".equals(vo.getItemType()) && qqchMainPlanItem.getAncestors().contains(vo.getAncestors())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect1)) {
                for (QqchMainPlanItem qqchMainPlanItem1 : collect1) {
                    ids.add(qqchMainPlanItem1.getId());
                }
            }
//            if(!CollectionUtils.isEmpty(collect1)) returnList.addAll(collect1);
        }
        if(!CollectionUtils.isEmpty(ids)) {
            for (Long id : ids) {
                QqchMainPlanItem qqchMainPlanItem = qqchMainPlanItemListNoTree.stream().filter(vo -> id.equals(vo.getId())).findFirst().orElse(null);
                if(qqchMainPlanItem != null) returnList.add(qqchMainPlanItem);
            }
        }
//        List<QqchMainPlanItem> collect1 = returnList.stream().sorted(Comparator.comparing(QqchMainPlanItem::getWbsCode).thenComparing(QqchMainPlanItem::getLeaf).thenComparing(QqchMainPlanItem::getItemCode)).collect(Collectors.toList());
//        Stream<QqchMainPlanItem> sorted = returnList.stream().sorted(Comparator.comparing(QqchMainPlanItem::getSort));
//        Stream<QqchMainPlanItem> sorted1 = sorted.collect(Collectors.toList()).stream().sorted(Comparator.comparing(QqchMainPlanItem::getItemCode));
        return returnList.stream().sorted(Comparator.comparing(QqchMainPlanItem::getSort, Comparator.nullsFirst(Integer::compareTo)).thenComparing(QqchMainPlanItem::getItemCode, Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
    }


    /**
     * 获取项目开始与结束
     * @return
     */
    @Override
    public QqchMainPlanItem getProjStartAndFinish(BigDecimal version) {
        version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, version);
        return qqchMainPlanItemMapper.getProjStartAndFinish(version);

    }

    /**
     * 获取里程碑数据
     * @param version
     * @return
     */
    @Override
    public List<QqchMainPlanItem> getMilestoneList(BigDecimal version) {
        version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, version);
        QqchMainPlanItem qqchMainPlanItem = new QqchMainPlanItem();
        qqchMainPlanItem.setVersion(version);
        List<QqchMainPlanItem> qqchMainPlanItemList = getQqchMainPlanItemList(qqchMainPlanItem);
        return qqchMainPlanItemList.stream().filter(vo -> vo.getTaskType() != null && vo.getTaskType().contains("Milestone")).collect(Collectors.toList());
    }

    @Override
    public int deleteQqchMainPlanByVersion(BigDecimal version) {
        return qqchMainPlanItemMapper.deleteQqchMainPlanByVersion(version);
    }

    @Override
    public void confirm(QqchMainPlanItemVo qqchMainPlanItemVoParam) {
        BigDecimal version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, qqchMainPlanItemVoParam.getVersion());
        qqchMainPlanItemMapper.updateValid(null, "0");
        qqchMainPlanItemMapper.updateValid(version, "1");
        String buttonMark = qqchMainPlanItemVoParam.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            String menuId = qqchMainPlanItemVoParam.getMenuId();
            String stageIdentity = qqchMainPlanItemVoParam.getStageIdentity();
            if(stageIdentity == null) stageIdentity = qqchReviewService.getStage();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 获取所有有关系的的父级子集数据
     * @param ids
     * @return
     */
    @Override
    public List<QqchMainPlanItem> getAllLinkList(List<Long> ids) {

        List<QqchMainPlanItem> returnList = new ArrayList<>();

        if (CollectionUtils.isEmpty(ids)) {
            return returnList;
        }

        // 获取总体计划生效数据
        List<QqchMainPlanItem> qqchMainPlanItemListNoTree = getQqchMainPlanItemListNoTree(new QqchMainPlanItem());
        if(CollectionUtils.isEmpty(qqchMainPlanItemListNoTree)) return returnList;

        for (Long id : ids) {
            // 查询传入id对应的数据
            QqchMainPlanItem qqchMainPlanItem = qqchMainPlanItemListNoTree.stream().filter(vo -> id.compareTo(vo.getId()) == 0).findFirst().orElse(null);
            if(qqchMainPlanItem == null) return returnList;

            String ancestors = qqchMainPlanItem.getAncestors();
            if(StringUtils.isEmpty(ancestors)) return returnList;

            // 查询传入id对应的数据的子集作业节点
            List<QqchMainPlanItem> collect = qqchMainPlanItemListNoTree.stream().filter(vo ->
                    StringUtils.isNotEmpty(vo.getAncestors()) && vo.getAncestors().contains(ancestors)
                            && QqchMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());

            if(CollectionUtils.isEmpty(collect)) return returnList;

            // 根据子集作业节点获取相关所有父级数据
            for (QqchMainPlanItem qqchMainPlanItem1 : collect) {
                String ancestors1 = qqchMainPlanItem1.getAncestors();

                if(StringUtils.isEmpty(ancestors1)) return returnList;

                List<QqchMainPlanItem> collect1 = qqchMainPlanItemListNoTree.stream().filter(vo ->
                        StringUtils.isNotEmpty(vo.getAncestors()) && ancestors.contains(vo.getAncestors())).collect(Collectors.toList());

                if(!CollectionUtils.isEmpty(collect1)) returnList.addAll(collect1);
            }
        }

        // 去重后返回
        return returnList.stream().distinct().collect(Collectors.toList());
    }
}
