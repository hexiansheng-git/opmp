package com.hhwy.sd.sync.mq.service.impl;

import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain.KcsjPlanCommunicationRecords;
import com.hhwy.sd.sync.mq.ISysSyncInfoService4Sd;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wll
 * 2024/3/6
 */
@Service
public class SysSyncInfoServiceImpl4Sd implements ISysSyncInfoService4Sd {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Override
    @Transactional
    public void pushKcsjPlanCommunicationRecords(List<KcsjPlanCommunicationRecords> list) {

    }

}
