package com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlanQueryVO;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.mapper.JdglMainPlanMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItemPre;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.mapper.JdglMainPlanItemMapper;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemPreService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.el.lang.ELArithmetic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private JdglMainPlanItemMapper jdglMainPlanItemMapper;


    public JdglMainPlan getJdglMainPlan(JdglMainPlan jdglMainPlan) {
        JdglMainPlan jdglMainPlan1 = jdglMainPlanMapper.getJdglMainPlan(jdglMainPlan);
        if(jdglMainPlan1 == null) {
            jdglMainPlan1= new JdglMainPlan();
            jdglMainPlan1.setJdglMainPlanItemList(new ArrayList<>());
            jdglMainPlan1.setJdglMainPlanItemPreList(new ArrayList<>());
            jdglMainPlan1.setKeyLoadList(new ArrayList<>());
            return jdglMainPlan1;
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


    public JdglMainPlan getJdglMainPlan(JdglMainPlan jdglMainPlan, JdglMainPlanQueryVO queryVO) {
        JdglMainPlan jdglMainPlan1 = jdglMainPlanMapper.getJdglMainPlan(jdglMainPlan);
        if (jdglMainPlan1 == null) {
            jdglMainPlan1 = new JdglMainPlan();
            jdglMainPlan1.setJdglMainPlanItemList(new ArrayList<>());
            jdglMainPlan1.setJdglMainPlanItemPreList(new ArrayList<>());
            jdglMainPlan1.setKeyLoadList(new ArrayList<>());
            return jdglMainPlan1;
        }
        String tabNo = queryVO.getTabNo();
        String itemName = queryVO.getItemName();
        Date startDate = queryVO.getStartDate();
        Date endDate = queryVO.getEndDate();

        JdglMainPlanItem jdglMainPlanItem = new JdglMainPlanItem();
        jdglMainPlanItem.setMainPlanId(jdglMainPlan1.getId());
        if ("1".equals(tabNo)) {
            jdglMainPlanItem.setItemName(itemName);
            jdglMainPlanItem.setStartDate(startDate);
        }
        List<JdglMainPlanItem> jdglMainPlanItemList = iJdglMainPlanItemService.getJdglMainPlanItemListNoTree(jdglMainPlanItem);
        jdglMainPlan1.setJdglMainPlanItemList(jdglMainPlanItemList == null ? new ArrayList<>() : jdglMainPlanItemList);
        JdglMainPlanItemPre jdglMainPlanItemPre = new JdglMainPlanItemPre();
        jdglMainPlanItemPre.setMainPlanId(jdglMainPlan1.getId());
        List<JdglMainPlanItemPre> jdglMainPlanItemPreList = jdglMainPlanItemPreService.getJdglMainPlanItemPreList(jdglMainPlanItemPre);
        jdglMainPlan1.setJdglMainPlanItemPreList(jdglMainPlanItemPreList == null ? new ArrayList<>() : jdglMainPlanItemPreList);
        if ("2".equals(tabNo)) {
            jdglMainPlanItem.setItemName(itemName);
            jdglMainPlanItem.setStartDate(startDate);
        }
        List<JdglMainPlanItem> keyRoad = iJdglMainPlanItemService.getKeyRoad(jdglMainPlanItem);
        jdglMainPlan1.setKeyLoadList(keyRoad == null ? new ArrayList<>() : keyRoad);

        return jdglMainPlan1;
    }

    /**
     * 根据wbs编码获取对应的进度管理数据
     * @return
     */
    public List<JdglMainPlanItem> getJdglMainPlanByWBS(JdglMainPlanItem jdglMainPlanItem) {
        JdglMainPlan jdglMainPlan = new JdglMainPlan();
        jdglMainPlan.setIsUse("1");
        JdglMainPlan jdglMainPlan1 = jdglMainPlanMapper.getJdglMainPlan(jdglMainPlan);
        if (jdglMainPlan1 == null) {
            return new ArrayList<JdglMainPlanItem>();
        }
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemMapper.getJdglMainPlanItemList(jdglMainPlanItem);
        if (!CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            for (JdglMainPlanItem jdglMainPlanItem1 : jdglMainPlanItemList) {
                // 计划完成百分比 * 100
                jdglMainPlanItem1.setSchedulePercentComplete(jdglMainPlanItem1.getSchedulePercentComplete() == null ? BigDecimal.ZERO : jdglMainPlanItem1.getSchedulePercentComplete().multiply(new BigDecimal(100)));
                // 尚需工期 / 8
                if (jdglMainPlanItem1.getRemainingDuration() != null)
                    jdglMainPlanItem1.setRemainingDuration(new BigDecimal(jdglMainPlanItem1.getRemainingDuration()).divide(new BigDecimal(8), 0, BigDecimal.ROUND_UP).intValue());
                // 总浮时 / 8
                if (jdglMainPlanItem1.getTotalFloat() != null)
                    jdglMainPlanItem1.setTotalFloat(new BigDecimal(jdglMainPlanItem1.getTotalFloat()).divide(new BigDecimal(8), 0, BigDecimal.ROUND_UP).intValue());
                // 自由浮时 / 8
                if (jdglMainPlanItem1.getFreeFloat() != null)
                    jdglMainPlanItem1.setFreeFloat(new BigDecimal(jdglMainPlanItem1.getFreeFloat()).divide(new BigDecimal(8), 0, BigDecimal.ROUND_UP).intValue());
                // 是否关键线路转换 0：否，1：是
                if (JdglMainPlanItem.ITEMTYPE_ITEM.equals(jdglMainPlanItem1.getItemType()))
                    jdglMainPlanItem1.setIsCritical("1".equals(jdglMainPlanItem1.getIsCritical()) || "1".equals(jdglMainPlanItem1.getIsLongestPath()) ? "是" : "否");

                jdglMainPlanItem1.setPlannedDuration(StatisticsUtils.getDaysByRangeDate(jdglMainPlanItem1.getStartDate(), jdglMainPlanItem1.getFinishDate()));

                jdglMainPlanItem1.setText(jdglMainPlanItem1.getItemName());
                jdglMainPlanItem1.setParent(jdglMainPlanItem1.getPid());
                // 如果已经有实际开始时间，则取实际开始时间，否则取尚需最早开始;
                Date start_date = jdglMainPlanItem1.getActualStartDate() != null
                        ? jdglMainPlanItem1.getActualStartDate() : jdglMainPlanItem1.getRemainingEarlyStartDate();
                jdglMainPlanItem1.setStart_date(null == start_date ?
                        DateUtils.parseDate("0000-00-00") : start_date);

                // 如果已经有实际完成时间，则取实际完成时间，否则取尚需最早完成;
                Date end_date = jdglMainPlanItem1.getActualFinishDate() != null
                        ? jdglMainPlanItem1.getActualFinishDate() : jdglMainPlanItem1.getRemainingEarlyFinishDate();
                jdglMainPlanItem1.setFinishDate(null == end_date ?
                        DateUtils.parseDate("0000-00-00") : end_date);

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
                if (actualStartDate != null) {
                    // 计算进度
                    Integer daysByRangeDate = StatisticsUtils.getDaysByRangeDate(actualStartDate, actualFinishDate);
                    BigDecimal progress = new BigDecimal(plannedDuration == 0 ? 0 : (float) daysByRangeDate / (float) plannedDuration);
                    progress = progress.setScale(2, RoundingMode.HALF_UP);
                    jdglMainPlanItem1.setProgress(progress);
                } else {
                    jdglMainPlanItem1.setProgress(new BigDecimal(0));
                }
            }
        }
        return jdglMainPlanItemList;
    }

    @Override
    public JdglMainPlan getUsingJdglMainPlan() {
        JdglMainPlan jdglMainPlan = new JdglMainPlan();
        jdglMainPlan.setIsUse("1");
        return getJdglMainPlan(jdglMainPlan);
    }

    @Override
    public JdglMainPlan getUsingJdglMainPlan(JdglMainPlanQueryVO queryVO) {
        JdglMainPlan jdglMainPlan = new JdglMainPlan();
        jdglMainPlan.setIsUse("1");
        return getJdglMainPlan(jdglMainPlan, queryVO);
    }

    //查询已生效的总体计划
    @Override
    public JdglMainPlan getUsingJdglMainPlanNoItem() {
        JdglMainPlan jdglMainPlan = new JdglMainPlan();
        jdglMainPlan.setIsUse("1");
        return jdglMainPlanMapper.getJdglMainPlan(jdglMainPlan);
    }

    public List<JdglMainPlan> getJdglMainPlanList(JdglMainPlan jdglMainPlan) {
        List<JdglMainPlan> jdglMainPlanList = jdglMainPlanMapper.getJdglMainPlanList(jdglMainPlan);

        if (CollectionUtils.isEmpty(jdglMainPlanList)) return jdglMainPlanList;

//        for (JdglMainPlan jdglMainPlan1: jdglMainPlanList) {
//            List<JdglMainPlanItem> jdglMainPlanItemList = iJdglMainPlanItemService.getJdglMainPlanItemList(new JdglMainPlanItem());
//            jdglMainPlan1.setJdglMainPlanItemList(jdglMainPlanItemList);
//        }

        return jdglMainPlanList;
    }

    @Transactional
    public int insertJdglMainPlan(JdglMainPlan jdglMainPlan) {
//        jdglMainPlan.setId(IdWorker.createId());
//        jdglMainPlan.setCreateUser(SecurityUtils.getUserName());
        jdglMainPlan.setCreateTime(DateUtils.getNowDate());
        int i = jdglMainPlanMapper.insertJdglMainPlan(jdglMainPlan);
        if (i > 0) {
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
//        jdglMainPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMainPlan.setUpdateTime(DateUtils.getNowDate());
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlan.getJdglMainPlanItemList();
        iJdglMainPlanItemService.updateJdglMainPlanItemList(jdglMainPlanItemList);
        int i = jdglMainPlanMapper.updateJdglMainPlan(jdglMainPlan);
        if (i > 0) {
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
//        jdglMainPlan.setUpdateUser(SecurityUtils.getUserName());
//        jdglMainPlan.setUpdateTime(DateUtils.getNowDate());
        return jdglMainPlanMapper.deleteJdglMainPlan(jdglMainPlan);
    }

    @Transactional
    public int deleteJdglMainPlanByPks(List<Long> jdglMainPlanPkList) {
        return jdglMainPlanMapper.deleteJdglMainPlanByPks(jdglMainPlanPkList);
    }

    @Override
    public JdglMainPlan getBaseMainPlan() {
        JdglMainPlan jdglMainPlan = jdglMainPlanMapper.getMinVersionMainPlan();
        if (jdglMainPlan == null) {
            return new JdglMainPlan();
        }
        JdglMainPlan vo = new JdglMainPlan();
        Long id = jdglMainPlan.getId();
        vo.setId(id);
        return getJdglMainPlan(vo);
    }

    @Override
    public void test(Long id) {
        JdglMainPlan query = new JdglMainPlan();
        query.setId(id);
        JdglMainPlan jdglMainPlan = jdglMainPlanMapper.getJdglMainPlan(query);
        sysSyncInfoService.pushJdglMainPlan(jdglMainPlan);
    }

    //设置基线计划版本信息
    @Override
    public void updateJdglBaseMainPlan() {
        /*总体计划表中维护了两个版本,1.每次拉取P6数据的版本 2.另一个基线的版本(pt_var2)*/
        //查询总体计划中最新版本
        JdglMainPlan maxVersionMainPlan = jdglMainPlanMapper.getMaxVersionMainPlan();
        if (null == maxVersionMainPlan) return;
        //查询已存在的基线最高版本
        JdglMainPlan jdglMainPlan = jdglMainPlanMapper.getBaseMainPlanMaxVersion();
        JdglMainPlan param = new JdglMainPlan();
        if (null == jdglMainPlan) {
            param.setPtVar1("1");
        } else {
            String ptVar2 = jdglMainPlan.getPtVar2();
            param.setPtVar2(Integer.valueOf(ptVar2) + 1 + "");
        }
        param.setId(maxVersionMainPlan.getId());
        param.setPtVar3(DateUtil.format(new Date(), "yyyy年MM月dd日 HH") + ":00");
        jdglMainPlanMapper.updateJdglMainPlan(param);
    }

    //基线计划详情查询
    @Override
    public JdglMainPlan getBaseMainPlanDetail(String tenantKey, Long id) {
        JdglMainPlan query = new JdglMainPlan();
        query.setId(id);
        if (StrUtil.isBlank(tenantKey)) {
            return getJdglMainPlan(query);
        } else {
            String peek = DynamicDataSourceContextHolder.peek();
            try {
                DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
                return getJdglMainPlan(query);
            } finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(peek);
            }
        }
    }

    //基线计划列表查询
    @Override
    public List<JdglMainPlan> getBaseMainPlanList(String tenantKey) {
        List<JdglMainPlan> resuleList = null;
        if (StrUtil.isBlank(tenantKey)) {
            resuleList = jdglMainPlanMapper.getBaseMainPlanList();
        } else {
            String peek = DynamicDataSourceContextHolder.peek();
            try {
                DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
                resuleList = jdglMainPlanMapper.getBaseMainPlanList();
            } finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(peek);
            }
        }
        resuleList.forEach(p -> {
            int i = Integer.parseInt(p.getPtVar2());
            if (i < 10) {
                p.setPtVar2("JX00" + p.getPtVar2());
            } else if (i < 100) {
                p.setPtVar2("JX0" + p.getPtVar2());
            } else {
                p.setPtVar2("JX" + p.getPtVar2());
            }
        });
        return resuleList;
    }
}
