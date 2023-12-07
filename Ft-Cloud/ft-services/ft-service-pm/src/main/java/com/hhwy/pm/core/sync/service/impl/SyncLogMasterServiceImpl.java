package com.hhwy.pm.core.sync.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.pm.core.sync.mapper.SysSyncInfoLogMapper;
import com.hhwy.pm.core.sync.service.ISyncLogMasterService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ThreadPoolUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SyncLogMasterServiceImpl implements ISyncLogMasterService {
    @Autowired
    private SysSyncInfoLogMapper sysSyncInfoLogMapper;

    @Override
    @Transactional
    public void success(String busName, String param, String result) {
        save(busName,param,result, Constant.YES_INT,null);
    }

    @Override
    @Transactional
    public void fail(String busName, String param, String result, String failMsg) {
        save(busName,param,result, Constant.NO_INT,failMsg);
    }


    public void save(String busName, String param, String result,Integer status,String failMsg){
        ThreadPoolUtil.execute(()->{
            //切换到master
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push("master");
            try {
                SysSyncInfoLog log = new SysSyncInfoLog();
                log.setId(IdWorker.createId());
                log.setBusinessName(busName);
                log.setPushCount(0);
                log.setUseTime(0L);
                log.setStatus(status==null?1:status);
                log.setFailMsg(failMsg);
                log.setPtVar1(param);
                log.setPtVar2(result);
                new AddBaseInfoUtil<>().addBaseEntity(log);
                this.sysSyncInfoLogMapper.insertSysSyncInfoLog(log);
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        });
    }
}
