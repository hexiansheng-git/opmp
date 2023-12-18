package com.hhwy.job.task.sd.kcsj.planProcess.monthlyReport;

import com.hhwy.job.feign.service.SdServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("MonthlyReportTask")
public class MonthlyReportTask {

    @Autowired
    private SdServiceApi sdServiceApi;

    /**
     * 每月21号自动生成月报
     */
    public void generateMonthlyReport(){
        System.out.println("----------生成月报数据------------");
        sdServiceApi.generateMonthlyReport();
    }
}
