package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItemPre;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemPreService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.mapper.JdglMainPlanMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:20
 * @remark
 */
@Service
public class JdglMainPlanServiceImpl implements IJdglMainPlanService {

    @Autowired
    private JdglMainPlanMapper jdglMainPlanMapper;

    @Autowired
    private IJdglMainPlanItemService iJdglMainPlanItemService;

    @Autowired
    private IJdglMainPlanItemPreService jdglMainPlanItemPreService;

    @Autowired
    private ISysSyncInfoService sysSyncInfoService;


    public JdglMainPlan getJdglMainPlan(JdglMainPlan jdglMainPlan) {
        JdglMainPlan jdglMainPlan1 = jdglMainPlanMapper.getJdglMainPlan(jdglMainPlan);
        if(jdglMainPlan1 == null) {
            return new JdglMainPlan();
        }
        JdglMainPlanItem jdglMainPlanItem = new JdglMainPlanItem();
        jdglMainPlanItem.setMainPlanId(jdglMainPlan1.getId());
        List<JdglMainPlanItem> jdglMainPlanItemList = iJdglMainPlanItemService.getJdglMainPlanItemListNoTree(jdglMainPlanItem);
        jdglMainPlan1.setJdglMainPlanItemList(jdglMainPlanItemList == null ? new ArrayList<>() : jdglMainPlanItemList);
        JdglMainPlanItemPre jdglMainPlanItemPre = new JdglMainPlanItemPre();
        jdglMainPlanItemPre.setMainPlanId(jdglMainPlan1.getId());
        List<JdglMainPlanItemPre> jdglMainPlanItemPreList = jdglMainPlanItemPreService.getJdglMainPlanItemPreList(jdglMainPlanItemPre);
        jdglMainPlan1.setJdglMainPlanItemPreList(jdglMainPlanItemPreList == null ? new ArrayList<>() : jdglMainPlanItemPreList);
        List<JdglMainPlanItem> keyRoad = iJdglMainPlanItemService.getKeyRoad(jdglMainPlanItem);
        jdglMainPlan1.setKeyLoadList(keyRoad == null ? new ArrayList<>() : keyRoad);

        return jdglMainPlan1;
    }

    @Override
    public JdglMainPlan getUsingJdglMainPlan() {
        JdglMainPlan jdglMainPlan = new JdglMainPlan();
        jdglMainPlan.setIsUse("1");
        return getJdglMainPlan(jdglMainPlan);
    }

    @Override
    public JdglMainPlan getUsingJdglMainPlanNoItem() {
        JdglMainPlan jdglMainPlan = new JdglMainPlan();
        jdglMainPlan.setIsUse("1");
        return jdglMainPlanMapper.getJdglMainPlan(jdglMainPlan);
    }

    public List<JdglMainPlan> getJdglMainPlanList(JdglMainPlan jdglMainPlan) {
        List<JdglMainPlan> jdglMainPlanList = jdglMainPlanMapper.getJdglMainPlanList(jdglMainPlan);

        if(CollectionUtils.isEmpty(jdglMainPlanList)) return jdglMainPlanList;

//        for (JdglMainPlan jdglMainPlan1: jdglMainPlanList) {
//            List<JdglMainPlanItem> jdglMainPlanItemList = iJdglMainPlanItemService.getJdglMainPlanItemList(new JdglMainPlanItem());
//            jdglMainPlan1.setJdglMainPlanItemList(jdglMainPlanItemList);
//        }

        return jdglMainPlanList;
    }

    @Transactional
    public int insertJdglMainPlan(JdglMainPlan jdglMainPlan) {
//        jdglMainPlan.setId(IdWorker.createId());
        jdglMainPlan.setCreateUser(SecurityUtils.getUserName());
        jdglMainPlan.setCreateTime(DateUtils.getNowDate());
        int i = jdglMainPlanMapper.insertJdglMainPlan(jdglMainPlan);
        if(i > 0) {
            sysSyncInfoService.pushJdglMainPlan(jdglMainPlan);
        }
        return i;
    }

    @Transactional
    public int insertJdglMainPlanList(List<JdglMainPlan> jdglMainPlanList) {
        for (JdglMainPlan jdglMainPlan : jdglMainPlanList) {
            jdglMainPlan.setId(IdWorker.createId());
            jdglMainPlan.setCreateUser(SecurityUtils.getUserName());
            jdglMainPlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanMapper.insertJdglMainPlanList(jdglMainPlanList);
    }

    @Transactional
    public int updateJdglMainPlan(JdglMainPlan jdglMainPlan) {
        jdglMainPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlan.setUpdateTime(DateUtils.getNowDate());
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlan.getJdglMainPlanItemList();
        iJdglMainPlanItemService.updateJdglMainPlanItemList(jdglMainPlanItemList);
        int i = jdglMainPlanMapper.updateJdglMainPlan(jdglMainPlan);
        if(i > 0) {
            sysSyncInfoService.pushJdglMainPlan(jdglMainPlan);
        }
        return i;
    }

    @Transactional
    public int updateJdglMainPlanList(List<JdglMainPlan> jdglMainPlanList) {
        for (JdglMainPlan jdglMainPlan : jdglMainPlanList) {
            jdglMainPlan.setUpdateUser(SecurityUtils.getUserName());
            jdglMainPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglMainPlanMapper.updateJdglMainPlanList(jdglMainPlanList);
    }

    @Transactional
    public int deleteJdglMainPlan(JdglMainPlan jdglMainPlan) {
        jdglMainPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanMapper.deleteJdglMainPlan(jdglMainPlan);
    }

    @Transactional
    public int deleteJdglMainPlanByPks(List<Long> jdglMainPlanPkList) {
        return jdglMainPlanMapper.deleteJdglMainPlanByPks(jdglMainPlanPkList);
    }

    @Override
    public JdglMainPlan getBaseMainPlan() {
        JdglMainPlan jdglMainPlan = jdglMainPlanMapper.getMinVersionMainPlan();
        if(jdglMainPlan == null) {
            return new JdglMainPlan();
        }
        JdglMainPlan vo = new JdglMainPlan();
        Long id = jdglMainPlan.getId();
        vo.setId(id);
        return getJdglMainPlan(vo);
    }
}
