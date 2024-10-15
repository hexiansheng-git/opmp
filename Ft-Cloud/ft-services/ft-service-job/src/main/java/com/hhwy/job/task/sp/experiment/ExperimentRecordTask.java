package com.hhwy.job.task.sp.experiment;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.job.feign.service.SpServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("experimentRecordTask")
public class ExperimentRecordTask {

    @Autowired
    private SpServiceApi spServiceApi;

    private Logger logger= LoggerFactory.getLogger(ExperimentRecordTask.class);

    /**
     * 每天发预警消息
     */
    public void getExperimentRecordTask(){
        logger.info("----------施工技术--设备台账及检验报告---预警预警开始了--------");
        AjaxResult result = spServiceApi.experimentRecordJob();
        logger.info("----------施工技术--设备台账及检验报告---预警预警结束了--------");
    }

    /**
     * 试验--物设同步
     */
    public void syncWusheTask(){
        logger.info("syncWusheTask开始了。。。。。。。。");
        spServiceApi.getWuSheMaterialRecord();
        logger.info("syncWusheTask结束了。。。。。。。。");
    }

    /**
     * 测量--物设同步
     */
    public void syncWusheInfoTask(){
        logger.info("syncWusheInfoTask开始了。。。。。。。。");
        spServiceApi.syncWusheJob();
        logger.info("syncWusheInfoTask结束了。。。。。。。。");
    }

}
