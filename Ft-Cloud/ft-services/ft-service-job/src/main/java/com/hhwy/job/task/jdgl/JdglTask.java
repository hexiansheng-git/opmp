package com.hhwy.job.task.jdgl;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("JdglTask")
public class JdglTask {

    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 差异化分析20号生成台账数据
     */
    public void initDiffAnalysis(){
        System.out.println("----------生成差异化数据------------");
        pmServiceApi.initDiffAnalysis();
    }

    /**
     * 总体进度计划凌晨更新P6数据
     */
    public void initJdglData4P6ByAll(){
        System.out.println("----------总体进度计划凌晨更新P6数据------------");
        pmServiceApi.initJdglData4P6ByAll();
    }
}
