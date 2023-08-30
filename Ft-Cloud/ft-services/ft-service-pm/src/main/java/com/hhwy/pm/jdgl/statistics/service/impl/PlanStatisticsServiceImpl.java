package com.hhwy.pm.jdgl.statistics.service.impl;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.statistics.service.IPlanStatisticsService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlanStatisticsServiceImpl implements IPlanStatisticsService {

    @Autowired
    private IJdglDayScheduleService iJdglDayScheduleService;

    /**
     * 获取产值完成情况
     * @param queryDateType 周(z)、月(y)、季(j)、年(n)
     * @param year 年度
     * @param quarter 季度
     * @param month 月度
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    @Override
    public Map<String, Map<String, BigDecimal>> getValueCompData(String queryDateType, String year, String quarter, String month, Date startDate, Date endDate) {

        // 自动化补充参数
        Map<String, Object> stringObjectMap = StatisticsUtils.initDateParams(queryDateType, year, quarter, month, startDate, endDate);

        year = (String) stringObjectMap.get("year");
        quarter = (String) stringObjectMap.get("quarter");
        month = (String) stringObjectMap.get("month");
        startDate = (Date) stringObjectMap.get("startDate");
        endDate = (Date) stringObjectMap.get("endDate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = sdf.format(startDate);
        String endDateStr = sdf.format(endDate);

        Map<String, Map<String, BigDecimal>> return2Map = new HashMap<>();

        // 根据结束日期获取日填报数据
        List<JdglDaySchedule> listByDateRange = iJdglDayScheduleService.getListByDateRange(null, endDate);

        BigDecimal totalActAmt = new BigDecimal(0);
        BigDecimal yearActAmt = new BigDecimal(0);
        BigDecimal quarterActAmt = new BigDecimal(0);
        BigDecimal monthActAmt = new BigDecimal(0);
        BigDecimal weekActAmt = new BigDecimal(0);
        if(!CollectionUtils.isEmpty(listByDateRange)) {
            for (JdglDaySchedule jdglDaySchedule : listByDateRange) {
                BigDecimal dayValueDl = jdglDaySchedule.getDayValueDl();
                Date date = jdglDaySchedule.getDate();
                Calendar cl = Calendar.getInstance();
                cl.setTime(date);
                if(endDate.before(date)) {
                    continue;
                }
                totalActAmt.add(dayValueDl);
                if(!year.equals(cl.get(Calendar.YEAR))) {
                    continue;
                }
                yearActAmt.add(dayValueDl);
                int dateMonth = cl.get(Calendar.MONTH) + 1;

                if(Integer.parseInt(quarter)*3 > dateMonth || Integer.parseInt(quarter)*3-2 < dateMonth) {
                    continue;
                }
                quarterActAmt.add(dayValueDl);

                if(!month.equals(dateMonth+"")){
                    continue;
                }
                monthActAmt.add(dayValueDl);

                if(startDate.before(date) && endDate.after(date)) {
                    weekActAmt.add(dayValueDl);
                }
            }
        }

        BigDecimal planAmt = new BigDecimal(0);

        switch (queryDateType) {
            case "n":
                // 年实际产值


            case "j":
                // 根据季查询产值比例

            case "y":
                // 根据月查询产值比例

            case "z":
                // 根据周查询产值比例

        }
        //查询开累比例

        return null;
    }
}
