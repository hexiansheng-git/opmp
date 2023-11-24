package com.hhwy.pm.core.sync.service.impl;

import com.graphbuilder.math.func.LgFunction;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.pm.core.sync.enums.SyncBusinessEnum;
import com.hhwy.pm.core.sync.mapper.SysSyncInfoLogMapper;
import com.hhwy.pm.core.sync.service.ISysSyncInfoLogService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据同步日志Service业务层处理
 * 
 * @author XXX
 * @date 2023-09-04
 */
@Service
public class SysSyncInfoLogServiceImpl implements ISysSyncInfoLogService {
    @Autowired
    private SysSyncInfoLogMapper sysSyncInfoLogMapper;

    /**
     * 查询数据同步日志
     * 
     * @param id 数据同步日志ID
     * @return 数据同步日志
     */
    @Override
    public SysSyncInfoLog selectSysSyncInfoLogById(Long id) {
        return sysSyncInfoLogMapper.selectSysSyncInfoLogById(id);
    }

    /**
     * 查询数据同步日志列表
     * 
     * @param sysSyncInfoLog 数据同步日志
     * @return 数据同步日志
     */
    @Override
    public List<SysSyncInfoLog> selectSysSyncInfoLogList(SysSyncInfoLog sysSyncInfoLog) {
        return sysSyncInfoLogMapper.selectSysSyncInfoLogList(sysSyncInfoLog);
    }

    @Override
    @Transactional
    public int success(SyncBusinessEnum businessEnum, String ids, Long count, Long useMills) {
        return insert(businessEnum,ids,count,useMills,null,null);
    }

    @Override
    @Transactional
    public int insert(SyncBusinessEnum businessEnum, String ids, Long count, Long useMills, Integer status,String msg) {
        return insert(businessEnum.busType(),ids,count,useMills,status,msg);
    }

    @Override
    public int insert(String busType, String ids, Long count, Long useMills, Integer status,String msg) {
        SysSyncInfoLog log = new SysSyncInfoLog();
        log.setId(IdWorker.createId());
        log.setBusinessName(busType);
        log.setPushCount(count.intValue());
        log.setUseTime(useMills);
        log.setStatus(status==null?1:status);
        log.setFailMsg(msg);
        log.setPtVar1(ids);
        new AddBaseInfoUtil<>().addBaseEntity(log);
        this.sysSyncInfoLogMapper.insertSysSyncInfoLog(log);
        return 0;
    }

    /**
     * 修改数据同步日志
     * 
     * @param sysSyncInfoLog 数据同步日志
     * @return 结果
     */
    @Override
    public int updateSysSyncInfoLog(SysSyncInfoLog sysSyncInfoLog) {
        sysSyncInfoLog.setUpdateTime(DateUtils.getNowDate());
        return sysSyncInfoLogMapper.updateSysSyncInfoLog(sysSyncInfoLog);
    }

    /**
     * 删除数据同步日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysSyncInfoLogByIds(String ids) {
        return sysSyncInfoLogMapper.deleteSysSyncInfoLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除数据同步日志信息
     * 
     * @param id 数据同步日志ID
     * @return 结果
     */
    public int deleteSysSyncInfoLogById(Long id) {
        return sysSyncInfoLogMapper.deleteSysSyncInfoLogById(id);
    }
}
