package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
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
        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbsList) {
                JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
                jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
                jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
                List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
                jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
            }
        }
        List<JdglDayScheduleWbs> build = TreeUtil.build(jdglDayScheduleWbsList, jdglDayScheduleWbs.getId());
        return build;
    }

    @Override
    public List<JdglDayScheduleWbs> getJdglDayScheduleWbsLazyList(JdglDayScheduleWbs jdglDayScheduleWbs) {
        Long id = jdglDayScheduleWbs.getId();
        jdglDayScheduleWbs.setId(null);
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsMapper.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbsList) {
                JdglDayScheduleBill jdglDayScheduleBill = new JdglDayScheduleBill();
                jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs1.getDayScheduleId());
                jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs1.getWbsCode());
                List<JdglDayScheduleBill> jdglDayScheduleBillList = iJdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBill);
                jdglDayScheduleWbs1.setJdglDayScheduleBillList(jdglDayScheduleBillList);
            }
        }
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
     * 根据日期从总进度计划获取形象清单关联的wbs及清单数据&未完&
     * @param date
     * @return
     */
    @Override
    public List<JdglDayScheduleWbs> getInitWbs(Date date) {

        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = new ArrayList<JdglDayScheduleWbs>();
        // 根据日期获取总进度计划中作业区间内的wbs数据

        // 根据wbs获取图纸复核中清单数据以及从合同工程量清单中获取清单单价
        iJdglDayScheduleBillService.getInitBill(jdglDayScheduleWbsList, date);

        return null;
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

            List<JdglDayScheduleBill> jdglDayScheduleBillList = jdglDayScheduleWbs.getJdglDayScheduleBillList();
            if(!CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
                for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
                    jdglDayScheduleBill.setDayScheduleId(jdglDayScheduleWbs.getDayScheduleId());
                    jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs.getWbsCode());
                }
                iJdglDayScheduleBillService.insertJdglDayScheduleBillList(jdglDayScheduleBillList);
            }
        }
        return jdglDayScheduleWbsMapper.insertJdglDayScheduleWbsList(jdglDayScheduleWbsList);
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
            iJdglDayScheduleBillService.deleteJdglDayScheduleBillByDayScheduleId(dayScheduleId);
            return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbsByDayScheduleId(dayScheduleId);
        }
        jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbsByDayScheduleId(dayScheduleId);
        List<JdglDayScheduleWbs> jdglDayScheduleWbs1 = TreeUtil.treeToList(jdglDayScheduleWbsList);
        for (JdglDayScheduleWbs jdglDayScheduleWbs : jdglDayScheduleWbs1) {
            List<JdglDayScheduleBill> jdglDayScheduleBillList = jdglDayScheduleWbs.getJdglDayScheduleBillList();
            if(!CollectionUtils.isEmpty(jdglDayScheduleBillList)) {
                for (JdglDayScheduleBill jdglDayScheduleBill : jdglDayScheduleBillList) {
                    jdglDayScheduleBill.setWbsCode(jdglDayScheduleWbs.getWbsCode());
                    jdglDayScheduleBill.setDayScheduleId(dayScheduleId);
                }

            }
            iJdglDayScheduleBillService.updateJdglDayScheduleBillList(jdglDayScheduleBillList, dayScheduleId, jdglDayScheduleWbs.getWbsCode());
            // 更新完清单更新主表产值数据;
        }

        return jdglDayScheduleWbsMapper.insertJdglDayScheduleWbsList(jdglDayScheduleWbs1);
    }

    @Transactional
    public int deleteJdglDayScheduleWbs(JdglDayScheduleWbs jdglDayScheduleWbs) {
        jdglDayScheduleWbs.setUpdateUser(SecurityUtils.getUserName());
        jdglDayScheduleWbs.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbs(jdglDayScheduleWbs);
    }

    @Transactional
    public int deleteJdglDayScheduleWbsByPks(List<Long> jdglDayScheduleWbsPkList) {
        return jdglDayScheduleWbsMapper.deleteJdglDayScheduleWbsByPks(jdglDayScheduleWbsPkList);
    }


}
