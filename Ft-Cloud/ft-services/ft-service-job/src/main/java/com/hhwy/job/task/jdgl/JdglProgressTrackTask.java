package com.hhwy.job.task.jdgl;

import com.hhwy.job.feign.service.PmServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("JdglProgressTrackTask")
public class JdglProgressTrackTask {

    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 差异化分析20号生成台账数据
     */
    public void weekTimerTrack(){
        System.out.println("----------进度纠偏跟踪------------");
        pmServiceApi.weekTimerTrack();
    }

}
