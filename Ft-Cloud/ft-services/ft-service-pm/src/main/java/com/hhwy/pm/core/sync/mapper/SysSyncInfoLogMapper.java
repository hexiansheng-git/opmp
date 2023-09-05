package com.hhwy.pm.core.sync.mapper;

import com.hhwy.pm.core.sync.domain.SysSyncInfoLog;

import java.util.List;

/**
 * 数据同步日志Mapper接口
 * 
 * @author XXX
 * @date 2023-09-04
 */
public interface SysSyncInfoLogMapper {
    /**
     * 查询数据同步日志
     * 
     * @param id 数据同步日志ID
     * @return 数据同步日志
     */
    public SysSyncInfoLog selectSysSyncInfoLogById(Long id);

    /**
     * 查询数据同步日志列表
     * 
     * @param sysSyncInfoLog 数据同步日志
     * @return 数据同步日志集合
     */
    public List<SysSyncInfoLog> selectSysSyncInfoLogList(SysSyncInfoLog sysSyncInfoLog);

    /**
     * 新增数据同步日志
     * 
     * @param sysSyncInfoLog 数据同步日志
     * @return 结果
     */
    public int insertSysSyncInfoLog(SysSyncInfoLog sysSyncInfoLog);

    /**
     * 修改数据同步日志
     * 
     * @param sysSyncInfoLog 数据同步日志
     * @return 结果
     */
    public int updateSysSyncInfoLog(SysSyncInfoLog sysSyncInfoLog);

    /**
     * 删除数据同步日志
     * 
     * @param id 数据同步日志ID
     * @return 结果
     */
    public int deleteSysSyncInfoLogById(Long id);

    /**
     * 批量删除数据同步日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSyncInfoLogByIds(String[] ids);
}
