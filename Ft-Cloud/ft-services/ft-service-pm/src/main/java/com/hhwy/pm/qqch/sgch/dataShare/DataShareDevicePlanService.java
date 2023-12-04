package com.hhwy.pm.qqch.sgch.dataShare;

import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.sgch.dataShare.mapper.DataShareDevicePlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能：设备策划数据推送
 * 作者: fushudong
 * 时间: 2023/11/22
 */
@Service
public class DataShareDevicePlanService {

    @Autowired
    private DataShareDevicePlanMapper dataShareDevicePlanMapper;

    //三阶段数据推送，3个阶段版本都是1.0, 每次变更清除原有的数据
    @Transactional
    public void eachStagePush(String tenantKey){
        //清空目标数据库版本为1.0的数据
        dataShareDevicePlanMapper.deleteByOneVersion();
        //新增数据
        dataShareDevicePlanMapper.dataPush(tenantKey);
    }

    //变更数据推送；每次变更版本+1,数据追加推送，最后把上一版本valid改为无效
    @Transactional
    public void eachChangePush(String tenantKey){
        //新增数据
        dataShareDevicePlanMapper.dataPush(tenantKey);
        //修改有效标识为失效
        dataShareDevicePlanMapper.updateValidFlag();
    }
}