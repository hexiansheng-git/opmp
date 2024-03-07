package com.hhwy.sd.sync.mq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain.KcsjPlanCommunicationRecords;
import com.hhwy.sd.sync.mq.ISysSyncInfoService4Sd;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        try{
            List<JSONObject> finalList = new ArrayList<>();
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            for (int i = 0; i < list.size(); i++) {
                KcsjPlanCommunicationRecords temp = list.get(i);
                temp.setPtVar2(SecurityUtils.getTenantKey());
                if(prjInfo.get("regionId") != null)temp.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
                temp.setRegionName((String) prjInfo.get("regionName"));
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)));
                finalList.add(json);
            }
            rocketMQTemplate.convertAndSend("kcsj_plan_communication_records:tenantSuccess", JSONObject.toJSONString(finalList));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}
