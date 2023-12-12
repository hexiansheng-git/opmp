package com.hhwy.log.mapper;

import com.hhwy.domain.log.SysSyncLog;

import java.util.List;

public interface SysSyncLogMapper {
    /**
     * 新增
     *
     * @param sysSyncLog
     * @return
     */
    int insertSysSyncLog(SysSyncLog sysSyncLog);

    /**
     * 修改
     *
     * @param sysSyncLog
     * @return
     */
    int updateSysSyncLog(SysSyncLog sysSyncLog);

    /**
     * 查询
     *
     * @param sysSyncLog
     * @return
     */
    List<SysSyncLog> selectSysSyncLogList(SysSyncLog sysSyncLog);
}
