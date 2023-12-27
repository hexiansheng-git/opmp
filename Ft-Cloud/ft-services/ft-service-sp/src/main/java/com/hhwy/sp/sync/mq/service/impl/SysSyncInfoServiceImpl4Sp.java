package com.hhwy.sp.sync.mq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据同步节点记录Service业务层处理
 * 
 * @author wk
 * @date 2023-09-04
 */
@Service
public class SysSyncInfoServiceImpl4Sp implements ISysSyncInfoService4Sp {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Override
    @Transactional
    public void pushSgjsDiscloseRecord(List<SgjsDiscloseRecord> list) {
        try{
            List<JSONObject> finalList = new ArrayList<>();
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            for (int i = 0; i < list.size(); i++) {
                SgjsDiscloseRecord temp = list.get(i);
                temp.setPtVar2(SecurityUtils.getTenantKey());
                temp.setRegionId((Long) prjInfo.get("regionId"));
                temp.setRegionName((String) prjInfo.get("regionName"));
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)));
                finalList.add(json);
            }
            rocketMQTemplate.convertAndSend("sgjs_disclose_record:tenantSuccess", JSONObject.toJSONString(finalList));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}
