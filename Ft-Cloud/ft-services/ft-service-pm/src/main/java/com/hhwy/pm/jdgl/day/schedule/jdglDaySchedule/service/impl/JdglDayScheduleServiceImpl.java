package com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.IJdglQuarterPlanService;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.mapper.JdglDayScheduleMapper;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-08-24 14:05:37
 * @remark
 */
@Service
public class JdglDayScheduleServiceImpl implements IJdglDayScheduleService {

    @Autowired
    private JdglDayScheduleMapper jdglDayScheduleMapper;

    @Autowired
    private IJdglDayScheduleWbsService iJdglDayScheduleWbsService;

    @Autowired
    private IJdglMonthPlanService jdglMonthPlanService;

    public JdglDaySchedule getJdglDaySchedule(JdglDaySchedule jdglDaySchedule) {
        JdglDaySchedule jdglDaySchedule1 = jdglDayScheduleMapper.getJdglDaySchedule(jdglDaySchedule);
        if(jdglDaySchedule1 != null) {
            JdglDayScheduleWbs jdglDayScheduleWbs = new JdglDayScheduleWbs();
            jdglDayScheduleWbs.setDayScheduleId(jdglDaySchedule1.getId());
//            jdglDayScheduleWbs.setEditer(userName);
//             懒加载
//            List<JdglDayScheduleWbs> jdglDayScheduleWbsList = iJdglDayScheduleWbsService.getJdglDayScheduleWbsLazyList(jdglDayScheduleWbs);
            // 获取wbs列表中的wbs数据
            List<JdglDayScheduleWbs> jdglDayScheduleWbsList = iJdglDayScheduleWbsService.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
            jdglDaySchedule1.setJdglDayScheduleWbsList(jdglDayScheduleWbsList);
        }
        return jdglDaySchedule1;
    }

    @Override
    public JdglDaySchedule getJdglDayScheduleByPerson(JdglDaySchedule jdglDayScheduleParam) {
        JdglDaySchedule jdglDaySchedule1 = jdglDayScheduleMapper.getJdglDaySchedule(jdglDayScheduleParam);
        if(jdglDaySchedule1 != null) {
            JdglDayScheduleWbs jdglDayScheduleWbs = new JdglDayScheduleWbs();
            jdglDayScheduleWbs.setDayScheduleId(jdglDaySchedule1.getId());
//            jdglDayScheduleWbs.setEditer(userName);
//             懒加载
//            List<JdglDayScheduleWbs> jdglDayScheduleWbsList = iJdglDayScheduleWbsService.getJdglDayScheduleWbsLazyList(jdglDayScheduleWbs);
            // 获取wbs列表中的wbs数据
            List<JdglDayScheduleWbs> jdglDayScheduleWbsList = iJdglDayScheduleWbsService.getJdglDayScheduleWbsListByPerson(jdglDayScheduleWbs);
            jdglDaySchedule1.setJdglDayScheduleWbsList(jdglDayScheduleWbsList);
        }
        return jdglDaySchedule1;
    }

    public List<JdglDaySchedule> getJdglDayScheduleList(JdglDaySchedule jdglDaySchedule) {
        List<JdglDaySchedule> jdglDayScheduleList = jdglDayScheduleMapper.getJdglDayScheduleList(jdglDaySchedule);
        if(!CollectionUtils.isEmpty(jdglDayScheduleList)) {
            for (JdglDaySchedule jdglDaySchedule1 : jdglDayScheduleList) {
                JdglDayScheduleWbs jdglDayScheduleWbs = new JdglDayScheduleWbs();
                jdglDayScheduleWbs.setDayScheduleId(jdglDaySchedule1.getId());
                // 懒加载
                List<JdglDayScheduleWbs> jdglDayScheduleWbsList = iJdglDayScheduleWbsService.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
                jdglDaySchedule1.setJdglDayScheduleWbsList(jdglDayScheduleWbsList);
            }
        }
        return jdglDayScheduleList;
    }

    @Override
    public JdglDaySchedule getInit(JdglDaySchedule jdglDayScheduleParam) {
        if(jdglDayScheduleParam == null) {
            return null;
        }
        Date date = jdglDayScheduleParam.getDate();
        if(date == null){
            return null;
        }
        // 获取初始wbs数据
        List<JdglDayScheduleWbs> initWbs = iJdglDayScheduleWbsService.getInitWbs(date);
        jdglDayScheduleParam.setJdglDayScheduleWbsList(initWbs);

        // 获取开累产值
        JdglDaySchedule jdglDaySchedule = jdglDayScheduleMapper.getHistoryValue(date);

        JdglMonthPlan jdglMonthPlan = new JdglMonthPlan();
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        jdglMonthPlan.setMonth((cl.get(Calendar.MONTH)+1) +"");
        jdglMonthPlan.setYear(cl.get(Calendar.YEAR) +"");
        jdglMonthPlan.setTaskStatus("5");
        JdglMonthPlan jdglMonthPlan1 = jdglMonthPlanService.getJdglMonthPlan(jdglMonthPlan);

        if(jdglDaySchedule != null) {
            jdglDayScheduleParam.setTotalValueCu(jdglDaySchedule.getTotalValueCu());
            jdglDayScheduleParam.setTotalValueDl(jdglDaySchedule.getTotalValueDl());
        }

        return jdglDayScheduleParam;
    }

    /**
     * 获取日期区间内的每日产值合计，如果不传值，则获取所有。
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public BigDecimal getCountValue(Date startDate, Date endDate) {
        return jdglDayScheduleMapper.getCountValue(startDate, endDate);
    }

    /**
     * 获取日期区间内的日填报数据，如不传值，则获取所有。
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<JdglDaySchedule> getListByDateRange(Date startDate, Date endDate) {
        return jdglDayScheduleMapper.getListByDateRange(startDate, endDate);
    }

    @Transactional
    public int insertJdglDaySchedule(JdglDaySchedule jdglDaySchedule) {
        Long id = IdWorker.createId();
        if(jdglDaySchedule != null) {
            jdglDaySchedule.setId(id);
            jdglDaySchedule.setCreateUser(SecurityUtils.getUserName());
            jdglDaySchedule.setCreateTime(DateUtils.getNowDate());

            JdglDaySchedule query = new JdglDaySchedule();
            Date date = jdglDaySchedule.getDate();
            query.setDate(date);
            JdglDaySchedule jdglDaySchedule1 = getJdglDaySchedule(query);
            if(jdglDaySchedule1 != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                throw new RuntimeException("已存在" + sdf.format(date) +"日期数据!");
            }

            int i = jdglDayScheduleMapper.insertJdglDaySchedule(jdglDaySchedule);

            if(i > 0) {
                List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDaySchedule.getJdglDayScheduleWbsList();
                if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
                    List<JdglDayScheduleWbs> jdglDayScheduleWbs = TreeUtil.treeToList(jdglDayScheduleWbsList);
                    if(!CollectionUtils.isEmpty(jdglDayScheduleWbs)) {
                        for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbs) {
                            jdglDayScheduleWbs1.setDayScheduleId(id);
                        }
                    }
                    iJdglDayScheduleWbsService.insertJdglDayScheduleWbsList(jdglDayScheduleWbs);
                }
            }

            return i;
        }
        return 0;
    }

    @Transactional
    public int insertJdglDayScheduleList(List<JdglDaySchedule> jdglDayScheduleList) {
        for (JdglDaySchedule jdglDaySchedule : jdglDayScheduleList) {
            jdglDaySchedule.setId(IdWorker.createId());
            jdglDaySchedule.setCreateUser(SecurityUtils.getUserName());
            jdglDaySchedule.setCreateTime(DateUtils.getNowDate());
        }
        return jdglDayScheduleMapper.insertJdglDayScheduleList(jdglDayScheduleList);
    }

    @Transactional
    public int updateJdglDaySchedule(JdglDaySchedule jdglDaySchedule) {
        if(jdglDaySchedule == null) {
            return 0;
        }

        int i = jdglDayScheduleMapper.updateJdglDaySchedule(jdglDaySchedule);
        Long id = jdglDaySchedule.getId();
        jdglDaySchedule.setUpdateUser(SecurityUtils.getUserName());
        jdglDaySchedule.setUpdateTime(DateUtils.getNowDate());

        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDaySchedule.getJdglDayScheduleWbsListNeedUpdate();
        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs: jdglDayScheduleWbsList) {
                jdglDayScheduleWbs.setDayScheduleId(jdglDaySchedule.getId());
            }
        }

        iJdglDayScheduleWbsService.updateJdglDayScheduleWbsList(jdglDayScheduleWbsList, id);

        List<Long> deleteWbsIdList = jdglDaySchedule.getDeleteWbsIdList();
        if(!CollectionUtils.isEmpty(deleteWbsIdList)) {
            iJdglDayScheduleWbsService.deleteJdglDayScheduleWbsByPks(deleteWbsIdList);
        }

        // wbs清单更新后，更新主表每日产值
        jdglDayScheduleMapper.updateJdglDayScheduleValue(id, jdglDaySchedule.getDate());

        return i;
    }

    @Transactional
    public int updateJdglDayScheduleList(List<JdglDaySchedule> jdglDayScheduleList) {
        for (JdglDaySchedule jdglDaySchedule : jdglDayScheduleList) {
            jdglDaySchedule.setUpdateUser(SecurityUtils.getUserName());
            jdglDaySchedule.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDayScheduleMapper.updateJdglDayScheduleList(jdglDayScheduleList);
    }

    @Transactional
    public int deleteJdglDaySchedule(JdglDaySchedule jdglDaySchedule) {
        jdglDaySchedule.setUpdateUser(SecurityUtils.getUserName());
        jdglDaySchedule.setUpdateTime(DateUtils.getNowDate());
        return jdglDayScheduleMapper.deleteJdglDaySchedule(jdglDaySchedule);
    }

    @Transactional
    public int deleteJdglDayScheduleByPks(List<Long> jdglDaySchedulePkList) {
        return jdglDayScheduleMapper.deleteJdglDayScheduleByPks(jdglDaySchedulePkList);
    }


}
