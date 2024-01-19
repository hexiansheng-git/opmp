package com.hhwy.job.task.sd.kcsj.designFile;

import com.hhwy.job.feign.service.SdServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 勘察设计--文件报批
 *
 * @author lcf
 * @date 2024-01-19
 */
@Component("designFileTask")
public class DesignFileTask {

    @Autowired
    private SdServiceApi sdServiceApi;

    private Logger logger= LoggerFactory.getLogger(DesignFileTask.class);

    /**
     * 未填写实际情况反馈日期  进预警  每晚20点执行一次
     */
    public void designFile(){
        logger.info("请注意，勘察设计--文件报批，预警啦。。。。。。。。。。。。。。");
        //sdServiceApi.   TODO
    }
}
