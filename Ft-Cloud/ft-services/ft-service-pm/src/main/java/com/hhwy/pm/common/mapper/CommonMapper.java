package com.hhwy.pm.common.mapper;

import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;

/**
 * 通用持久层
 *
 * @author mls
 */
public interface CommonMapper {

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

    Integer deleteDetailsByMainId(@Param("mainId") Long businessId, @Param("tableName") String tableName);


    /**
     * 根据表名，查询最大版本号，如果查不到，版本号赋默认值1.0
     *
     * @param tableName
     * @return
     */
    BigDecimal selectMaxVersion(@Param("tableName") String tableName);

}
