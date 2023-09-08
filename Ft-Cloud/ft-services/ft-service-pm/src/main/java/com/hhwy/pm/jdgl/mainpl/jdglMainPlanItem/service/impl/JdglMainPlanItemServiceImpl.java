package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.impl;

import java.util.Date;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.mapper.JdglMainPlanItemMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.utils.idworker.IdWorker;

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
            jdglMainPlanItem.setId(IdWorker.createId());
            jdglMainPlanItem.setCreateUser(SecurityUtils.getUserName());
            jdglMainPlanItem.setCreateTime(DateUtils.getNowDate());
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
        List<JdglMainPlanItem> jdglMainPlanItems = TreeUtil.treeToList(jdglMainPlanItemList);
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
}
