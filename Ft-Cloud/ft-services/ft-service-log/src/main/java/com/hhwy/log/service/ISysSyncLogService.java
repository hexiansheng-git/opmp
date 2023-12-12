package com.hhwy.log.service;

import com.hhwy.domain.log.SysSyncLog;

import java.util.List;

/**
 * 日志处理
 *
 * @author lcf
 * @date 2023-09-20
 */
public interface ISysSyncLogService {
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
