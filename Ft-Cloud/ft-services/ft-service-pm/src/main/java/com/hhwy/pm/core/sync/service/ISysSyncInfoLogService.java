package com.hhwy.pm.core.sync.service;


import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.pm.core.sync.enums.SyncBusinessEnum;

import java.util.List;

/**
 * 数据同步日志Service接口
 * 
 * @author XXX
 * @date 2023-09-04
 */
public interface ISysSyncInfoLogService {
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

    public int success(SyncBusinessEnum businessEnum,String id,Long count,Long useMills);
    /**
     * 插入日志
     * @param businessEnum  enum
     * @param id
     * @param count        推送数量
     * @param useMills     耗时
     * @param status       状态,0:失败,1:成功
     * @param msg         消息
     * @return
     */
    public int insert(SyncBusinessEnum businessEnum,String id,Long count,Long useMills,Integer status,String msg);

    public int insert(String busType,String id,Long count,Long useMills,Integer status,String msg);

    /**
     * 修改数据同步日志
     * 
     * @param sysSyncInfoLog 数据同步日志
     * @return 结果
     */
    public int updateSysSyncInfoLog(SysSyncInfoLog sysSyncInfoLog);

    /**
     * 批量删除数据同步日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSyncInfoLogByIds(String ids);

    /**
     * 删除数据同步日志信息
     * 
     * @param id 数据同步日志ID
     * @return 结果
     */
    public int deleteSysSyncInfoLogById(Long id);
}
