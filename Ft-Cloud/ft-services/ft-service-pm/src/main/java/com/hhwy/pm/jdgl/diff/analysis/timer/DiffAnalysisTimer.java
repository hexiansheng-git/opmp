package com.hhwy.pm.jdgl.diff.analysis.timer;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class DiffAnalysisTimer {

    @Autowired
    private IJdglDiffAnalysisService iJdglDiffAnalysisService;

    /**
     * 每月20号生成差异化数据
     */
    @Scheduled(cron = "0 0 0 20 * ?")
    public void initDiffAnalysis(){

        JdglDiffAnalysis jdglDiffAnalysis = new JdglDiffAnalysis();

        jdglDiffAnalysis.setPeriod(new Date());

        iJdglDiffAnalysisService.insertJdglDiffAnalysis(jdglDiffAnalysis);

    }

    /**
     * 每月月底计算差异化数据分数
     */
    @Scheduled(cron = "0 0 0 L * ?")
    public void countDiffAnalysis(){

    }

}
