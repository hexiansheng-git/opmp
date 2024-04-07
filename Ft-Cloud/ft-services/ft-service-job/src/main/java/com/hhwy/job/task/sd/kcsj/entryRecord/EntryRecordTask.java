package com.hhwy.job.task.sd.kcsj.entryRecord;

import com.hhwy.job.feign.service.SdServiceApi;
import com.hhwy.job.task.sd.kcsj.designFile.DesignFileTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("entryRecordTask")
public class EntryRecordTask {

    @Autowired
    private SdServiceApi sdServiceApi;

    private Logger logger= LoggerFactory.getLogger(DesignFileTask.class);

    /**
     * 勘察设备进场记录--同步物设
     */
    public void sysncWushe(){
        sdServiceApi.syncWusheJob();
        logger.info("请注意，勘察设计--文件报批发消息发送。。。。。。。。。。。。。。");

    }
}
