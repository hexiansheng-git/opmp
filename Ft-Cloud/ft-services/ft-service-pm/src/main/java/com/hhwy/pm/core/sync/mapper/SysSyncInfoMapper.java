package com.hhwy.pm.core.sync.mapper;

import com.hhwy.pm.core.sync.domain.SysSyncInfo;

import java.util.List;


/**
 * 数据同步节点记录Mapper接口
 * 
 * @author XXX
 * @date 2023-09-04
 */
public interface SysSyncInfoMapper {
    /**
     * 查询数据同步节点记录
     * 
     * @param businessName 数据同步节点记录ID
     * @return 数据同步节点记录
     */
    public SysSyncInfo selectSysSyncInfoById(String businessName);

    /**
     * 查询数据同步节点记录列表
     * 
     * @param sysSyncInfo 数据同步节点记录
     * @return 数据同步节点记录集合
     */
    public List<SysSyncInfo> selectSysSyncInfoList(SysSyncInfo sysSyncInfo);

    /**
     * 新增数据同步节点记录
     * 
     * @param sysSyncInfo 数据同步节点记录
     * @return 结果
     */
    public int insertSysSyncInfo(SysSyncInfo sysSyncInfo);

    /**
     * 修改数据同步节点记录
     * 
     * @param sysSyncInfo 数据同步节点记录
     * @return 结果
     */
    public int updateSysSyncInfo(SysSyncInfo sysSyncInfo);

    /**
     * 删除数据同步节点记录
     * 
     * @param businessName 数据同步节点记录ID
     * @return 结果
     */
    public int deleteSysSyncInfoById(String businessName);

    /**
     * 批量删除数据同步节点记录
     * 
     * @param businessNames 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSyncInfoByIds(String[] businessNames);
}
