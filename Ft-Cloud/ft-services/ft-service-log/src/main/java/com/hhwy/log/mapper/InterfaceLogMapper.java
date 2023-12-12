package com.hhwy.log.mapper;

import com.hhwy.domain.base.InterfaceLog.InterfaceLog;

import java.util.List;

public interface InterfaceLogMapper {
    /**
     * 根据id查日志信息
     *
     * @param id
     * @return
     */
   InterfaceLog selectInterfaceLogById (Long id);

    /**
     * 列表查询
     *
     * @param interfaceLog
     * @return
     */
   List<InterfaceLog> selectInterfaceLogList(InterfaceLog interfaceLog);

    /**
     * 数据插入
     *
     * @param interfaceLog
     * @return
     */
   int insertInterfaceLog(InterfaceLog interfaceLog);
}
