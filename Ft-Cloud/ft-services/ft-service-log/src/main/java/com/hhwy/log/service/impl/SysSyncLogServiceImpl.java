package com.hhwy.log.service.impl;

import com.hhwy.domain.log.SysSyncLog;
import com.hhwy.log.mapper.SysSyncLogMapper;
import com.hhwy.log.service.ISysSyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志处理
 *
 * @author lcf
 * @date 2023-9-20
 */
@Service
public class SysSyncLogServiceImpl implements ISysSyncLogService {

    @Autowired
    private SysSyncLogMapper sysSyncLogMapper;

    @Override
    public int insertSysSyncLog(SysSyncLog sysSyncLog) {
        return sysSyncLogMapper.insertSysSyncLog(sysSyncLog);
    }

    @Override
    public int updateSysSyncLog(SysSyncLog sysSyncLog) {
        return sysSyncLogMapper.updateSysSyncLog(sysSyncLog);
    }

    @Override
    public List<SysSyncLog> selectSysSyncLogList(SysSyncLog sysSyncLog) {
        return sysSyncLogMapper.selectSysSyncLogList(sysSyncLog);
    }
}
