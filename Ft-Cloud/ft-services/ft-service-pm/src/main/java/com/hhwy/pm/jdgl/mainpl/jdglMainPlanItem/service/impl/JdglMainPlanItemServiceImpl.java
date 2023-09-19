package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.mapper.JdglMainPlanItemMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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


    public JdglMainPlanItem getJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem) {
        return jdglMainPlanItemMapper.getJdglMainPlanItem(jdglMainPlanItem);
    }

    public List<JdglMainPlanItem> getJdglMainPlanItemList(JdglMainPlanItem jdglMainPlanItem) {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemMapper.getJdglMainPlanItemList(jdglMainPlanItem);
        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return jdglMainPlanItemList;
        }
        List<JdglMainPlanItem> build = TreeUtil.build(jdglMainPlanItemList, jdglMainPlanItem.getPid());
        return build;
    }

    public List<JdglMainPlanItem> getJdglMainPlanItemListNoTree(JdglMainPlanItem jdglMainPlanItem) {
        return jdglMainPlanItemMapper.getJdglMainPlanItemList(jdglMainPlanItem);
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
            jdglMainPlanItem.setCreateUser(SecurityUtils.getSysUser().getNickName());
            jdglMainPlanItem.setCreateTime(DateUtils.getNowDate());
//            jdglMainPlanItem.setUpdateUser(SecurityUtils.getSysUser().getNickName());
//            jdglMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanItemMapper.insertJdglMainPlanItemList(jdglMainPlanItemList);
    }

    @Transactional
    public int updateJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem) {
        jdglMainPlanItem.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanItemMapper.updateJdglMainPlanItem(jdglMainPlanItem);
    }

    @Transactional
    public int updateJdglMainPlanItemList(List<JdglMainPlanItem> jdglMainPlanItemList) {
        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return 0;
        }
        List<JdglMainPlanItem> jdglMainPlanItems = TreeUtil.treeToListWithoutId(jdglMainPlanItemList);
        for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItems) {
            jdglMainPlanItem.setUpdateUser(SecurityUtils.getUserName());
            jdglMainPlanItem.setUpdateTime(DateUtils.getNowDate());
        }
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

        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlan();

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
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlan();
        if(usingJdglMainPlan != null) {
            mainPlanId = usingJdglMainPlan.getId();
        }
        return jdglMainPlanItemMapper.getUsingJdglMainPlanItemListByDate(date, mainPlanId);
    }

    @Override
    public List<JdglMainPlanItem> getUsingJdglMainPlanItemListByDateRange(Date startDate, Date endDate) {
        Long mainPlanId = 0l;
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlan();
        if(usingJdglMainPlan != null) {
            mainPlanId = usingJdglMainPlan.getId();
        }
        return jdglMainPlanItemMapper.getUsingJdglMainPlanItemListByDateRange(startDate, endDate, mainPlanId);
    }

    public JdglMainPlanItem getMaxActualStartDate() {
        Long mainPlanId = 0l;
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlan();
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
        List<JdglMainPlanItem> returnList = new ArrayList<>();
        List<JdglMainPlanItem> jdglMainPlanItemListNoTree = getJdglMainPlanItemListNoTree(jdglMainPlanItemParam);
        if(CollectionUtils.isEmpty(jdglMainPlanItemListNoTree)) {
            return returnList;
        }
        List<JdglMainPlanItem> collect = jdglMainPlanItemListNoTree.stream().filter(vo -> "1".equals(vo.getIsCritical())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(collect)) {
            return returnList;
        }
        returnList.addAll(collect);
        Set<Long> ids = new HashSet<>();
        for (JdglMainPlanItem jdglMainPlanItem : collect) {
            List<JdglMainPlanItem> collect1 = jdglMainPlanItemListNoTree.stream().filter(vo -> jdglMainPlanItem.getWbsCode().contains(vo.getItemCode())).collect(Collectors.toList());
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
        List<JdglMainPlanItem> collect1 = returnList.stream().sorted(Comparator.comparing(JdglMainPlanItem::getWbsCode).thenComparing(JdglMainPlanItem::getLeaf).thenComparing(JdglMainPlanItem::getItemCode)).collect(Collectors.toList());
        return collect1;
    }
}
