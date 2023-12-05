package com.hhwy.job.task.jdgl;

import com.hhwy.job.feign.service.PmServiceApi;
import feign.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
        Request.Options options =new Request.Options(60, TimeUnit.SECONDS,1,TimeUnit.DAYS,true);
        pmServiceApi.initJdglData4P6ByAll(options);
    }
}
