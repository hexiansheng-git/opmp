package com.hhwy.job.task.sp.disClosedRecord;

import com.hhwy.job.feign.service.SpServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("disClosedRecordTask")
public class DisClosedRecordTask {

    private Logger logger= LoggerFactory.getLogger(DisClosedRecordTask.class);

    @Autowired
    private SpServiceApi spServiceApi;

    /**
     * 每天发预警消息
     */
    public void handleTask(){
        logger.info("----------施工技术--一、二级方案安全交底---预警预警--------");
        spServiceApi.disCloseRecord();
        logger.info("----------end--------");
    }
}
