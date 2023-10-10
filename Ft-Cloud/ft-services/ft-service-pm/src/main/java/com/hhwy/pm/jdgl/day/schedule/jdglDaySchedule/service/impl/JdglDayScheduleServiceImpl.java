package com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.mapper.JdglDayScheduleMapper;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            FlowInfoSearchUtil.getFlowInfo(jdglDaySchedule1, FlowEnum.JDGL_DAYSCHEDULE);
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
            FlowInfoSearchUtil.getFlowInfo(jdglDaySchedule1, FlowEnum.JDGL_DAYSCHEDULE);
        }
        return jdglDaySchedule1;
    }

    @Override
    public Map<String, BigDecimal> getMonthScheduleByMonthRange(Date startPeriod, Date endPeriod) {

        Map<String, BigDecimal> map = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Map<String, Date> startPeriodRange = StatisticsUtils.getDateRange4YearMonth(startPeriod);
        Map<String, Date> endPeriodRange = StatisticsUtils.getDateRange4YearMonth(endPeriod);
        Date start = startPeriodRange.get("start");
        Date end = endPeriodRange.get("end");

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startPeriod);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(endPeriod);

        LocalDate ofStart = LocalDate.of(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, 1);
        LocalDate ofEnd = LocalDate.of(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH) + 1, 1);

        Period between = Period.between(ofStart, ofEnd);
        int months = between.getYears() * 12 + between.getMonths();
        List<JdglDaySchedule> listByDateRange = getListByDateRange(start, end);
        for (int i = 0; i<=months; i++) {
            calendar1.add(Calendar.MONTH, i==0?0:1);
            Date time = calendar1.getTime();
            String format = sdf.format(time);
            Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(time);

            Date start1 = dateRange4YearMonth.get("start");
            Date end1 = dateRange4YearMonth.get("end");

            if(!CollectionUtils.isEmpty(listByDateRange)) {
                BigDecimal bigDecimal = new BigDecimal(0);
                for (JdglDaySchedule jdglDaySchedule : listByDateRange) {
                    if((start1.before(jdglDaySchedule.getDate()) || start1.equals(jdglDaySchedule.getDate()))
                            && end1.after(jdglDaySchedule.getDate())) {
                        if(jdglDaySchedule.getDayValueDl() != null) {
                            bigDecimal = bigDecimal.add(jdglDaySchedule.getDayValueDl());
                        }
                    }
                }
                map.put(format, bigDecimal);
            } else {
                map.put(format, new BigDecimal(0));
            }
        }

        return map;
    }

    @Override
    public void updateTaskStatus(Long id) {

        JdglDaySchedule jdglDaySchedule = new JdglDaySchedule();
        jdglDaySchedule.setId(id);
        jdglDaySchedule.setTaskStatus("5");
        jdglDayScheduleMapper.updateJdglDaySchedule(jdglDaySchedule);

    }

    public List<JdglDaySchedule> getJdglDayScheduleList(JdglDaySchedule jdglDaySchedule) {
        List<JdglDaySchedule> jdglDayScheduleList = jdglDayScheduleMapper.getJdglDayScheduleList(jdglDaySchedule);
        String tenantKey = SecurityUtils.getTenantKey();
        if(!CollectionUtils.isEmpty(jdglDayScheduleList)) {
            for (JdglDaySchedule jdglDaySchedule1 : jdglDayScheduleList) {
                JdglDayScheduleWbs jdglDayScheduleWbs = new JdglDayScheduleWbs();
                jdglDayScheduleWbs.setDayScheduleId(jdglDaySchedule1.getId());
                // 懒加载
                List<JdglDayScheduleWbs> jdglDayScheduleWbsList = iJdglDayScheduleWbsService.getJdglDayScheduleWbsList(jdglDayScheduleWbs);
                jdglDaySchedule1.setJdglDayScheduleWbsList(jdglDayScheduleWbsList);
            }
        }
        FlowInfoSearchUtil.getFlowInfo(jdglDayScheduleList,FlowEnum.JDGL_DAYSCHEDULE);
        return jdglDayScheduleList;
    }

    @Override
    public List<JdglDaySchedule> getListBy(JdglDaySchedule jdglDaySchedule){
        return jdglDayScheduleMapper.getJdglDayScheduleList(jdglDaySchedule);
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
        jdglMonthPlan.setMonth((cl.get(Calendar.MONTH)+1) < 10 ? "0" + (cl.get(Calendar.MONTH)+1) : ""+(cl.get(Calendar.MONTH)+1));
        jdglMonthPlan.setYear(cl.get(Calendar.YEAR) +"");
        jdglMonthPlan.setTaskStatus("5");
        JdglMonthPlan jdglMonthPlan1 = jdglMonthPlanService.getJdglMonthPlan(jdglMonthPlan);

        if(jdglMonthPlan1 != null) {
            jdglDayScheduleParam.setCustUnitCode(jdglMonthPlan1.getUnicode());
            jdglDayScheduleParam.setCustUnit(jdglMonthPlan1.getCustUnit());
            jdglDayScheduleParam.setExchangeRate(jdglMonthPlan1.getExchangeRate());
        }

        if(jdglDaySchedule != null) {
            jdglDayScheduleParam.setTotalValueCu(jdglDaySchedule.getTotalValueCu());
            jdglDayScheduleParam.setTotalValueDl(jdglDaySchedule.getTotalValueDl());
        }
        FlowInfoSearchUtil.getFlowInfo(jdglDayScheduleParam, FlowEnum.JDGL_DAYSCHEDULE);

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
            jdglDaySchedule.setCreateUser(SecurityUtils.getSysUser().getNickName());
            jdglDaySchedule.setCreateTime(DateUtils.getNowDate());
            jdglDaySchedule.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            jdglDaySchedule.setUpdateTime(DateUtils.getNowDate());

            JdglDaySchedule query = new JdglDaySchedule();
            Date date = jdglDaySchedule.getDate();
            query.setDate(date);
            JdglDaySchedule jdglDaySchedule1 = getJdglDaySchedule(query);
            if(jdglDaySchedule1 != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                throw new RuntimeException("已存在" + sdf.format(date) +"日期数据!");
            }

            List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDaySchedule.getJdglDayScheduleWbsList();

            int i = jdglDayScheduleMapper.insertJdglDaySchedule(jdglDaySchedule);

            if(i > 0) {

                if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
                    List<JdglDayScheduleWbs> jdglDayScheduleWbs = TreeUtil.treeToList(jdglDayScheduleWbsList);
                    if(!CollectionUtils.isEmpty(jdglDayScheduleWbs)) {
                        for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbs) {
                            jdglDayScheduleWbs1.setDayScheduleId(id);
                        }
                    }
                    iJdglDayScheduleWbsService.insertJdglDayScheduleWbsList(jdglDayScheduleWbs);
                }

                List<JdglDayScheduleWbs> jdglDayScheduleWbsListNeedUpdate = jdglDaySchedule.getJdglDayScheduleWbsListNeedUpdate();
                if(!CollectionUtils.isEmpty(jdglDayScheduleWbsListNeedUpdate)) {
                    for (JdglDayScheduleWbs jdglDayScheduleWbs: jdglDayScheduleWbsListNeedUpdate) {
                        jdglDayScheduleWbs.setDayScheduleId(jdglDaySchedule.getId());
                    }
                }

                iJdglDayScheduleWbsService.updateJdglDayScheduleWbsList(jdglDayScheduleWbsListNeedUpdate, id);

                // wbs清单更新后，更新主表每日产值
                jdglDayScheduleMapper.updateJdglDayScheduleValue(id, jdglDaySchedule.getDate());
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

        Long id = jdglDaySchedule.getId();
        jdglDaySchedule.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglDaySchedule.setUpdateTime(DateUtils.getNowDate());

        int i = jdglDayScheduleMapper.updateJdglDaySchedule(jdglDaySchedule);

        List<JdglDayScheduleWbs> jdglDayScheduleWbsList1 = jdglDaySchedule.getJdglDayScheduleWbsList();
        List<JdglDayScheduleWbs> jdglDayScheduleWbsListAdd = new ArrayList<>();
        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList1)) {
            List<JdglDayScheduleWbs> jdglDayScheduleWbsList = TreeUtil.treeToList(jdglDayScheduleWbsList1);
            for (JdglDayScheduleWbs jdglDayScheduleWbs1 : jdglDayScheduleWbsList) {
                if("1".equals(jdglDayScheduleWbs1.getIsAdd())) {
                    jdglDayScheduleWbs1.setDayScheduleId(id);
                    jdglDayScheduleWbsListAdd.add(jdglDayScheduleWbs1);
                }
            }
        }

        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsListAdd)) {
            iJdglDayScheduleWbsService.insertJdglDayScheduleWbsList(jdglDayScheduleWbsListAdd);
        }

        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDaySchedule.getJdglDayScheduleWbsListNeedUpdate();
        if(!CollectionUtils.isEmpty(jdglDayScheduleWbsList)) {
            for (JdglDayScheduleWbs jdglDayScheduleWbs: jdglDayScheduleWbsList) {
                jdglDayScheduleWbs.setDayScheduleId(jdglDaySchedule.getId());
            }
        }

        iJdglDayScheduleWbsService.updateJdglDayScheduleWbsList(jdglDayScheduleWbsList, id);

        List<JdglDayScheduleWbs> deleteWbsList = jdglDaySchedule.getDeleteWbsList();

        if(!CollectionUtils.isEmpty(deleteWbsList)) {
            iJdglDayScheduleWbsService.deleteJdglDayScheduleWbsByOrLevel(deleteWbsList, id);
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

    /**
     * 获取日期区间内的每日产值合计，如果不传值，则获取所有。不需要审批通过
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public BigDecimal getCountValueNotApprove(Date startDate, Date endDate) {
        return jdglDayScheduleMapper.getCountValueNotApprove(startDate, endDate);
    }
}
