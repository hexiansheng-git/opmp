package com.hhwy.job.task.sd.kcsj.planProcess.weekReport;

import com.hhwy.job.feign.service.SdServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("WeekReportTask")
public class WeekReportTask {

    @Autowired
    private SdServiceApi sdServiceApi;

    /**
     * 每周末生成自动生成月报
     */
    public void generateWeekReport(){
        System.out.println("----------生成周报数据------------");
        sdServiceApi.produceData();
    }
}
