package com.hhwy.pm.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通用持久层
 *
 * @author mls
 */
public interface WzchCommonMapper {
    /**
     * 查询单据是否能够被调整
     *
     * @param businessId
     * @param tableName
     * @return
     */
    String selectCanAdjust(@Param("businessId") Long businessId, @Param("tableName") String tableName);
    /**
     * 查询单据是否能够被调整(一条数据只能调整一次)
     *
     * @param tableName projectId
     * @param tableName
     * @return
     */
    Long selectCanAdjustOnly(@Param("tableName") String tableName);

}
