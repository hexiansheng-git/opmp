package com.hhwy.pm.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 通用持久层
 *
 * @author mls
 */
@Repository
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

    /**
     * 查询单据是否能够被调整(一条数据只能调整一次)
     *
     * @param tableName projectId
     * @param tableName
     * @return
     */
    Long selectCanAdjustOnlyValid(@Param("tableName") String tableName);

    /**
     * 生效的最大版本的单据id
     *
     * @param businessId
     * @param tableName
     * @return
     */
    Integer deleteDetailsByMainId(@Param("mainId") Long businessId, @Param("tableName") String tableName);


    /**
     * 根据表名，查询最大有效版本号，如果查不到，版本号赋默认值1.0
     *
     * @param tableName
     * @return
     */
    BigDecimal selectMaxVersion(@Param("tableName") String tableName);

    /**
     * 查询表中最接近（小于等于）指定版本的版本号
     *
     * @param tableName
     * @param version
     * @return
     */
    BigDecimal selectLessOrEqualAssignVersion(@Param("tableName") String tableName,
        @Param("version") BigDecimal version);

    void deleteByVersion(@Param("tn") String tn, @Param("version") BigDecimal version);

    /**
     * 根据表名，查询最大有效版本号，如果查不到，版本号赋默认值1.0 适用于多个功能公用一个表，通过类型区分
     *
     * @param tableName
     * @return
     */
    BigDecimal selectMaxVersionByType(@Param("tableName") String tableName, @Param("type") String type);

    /**
     * 查询表中最接近（小于等于）指定版本的版本号 适用于多个功能公用一个表，通过类型区分
     *
     * @param tableName
     * @param version
     * @return
     */
    BigDecimal selectLessOrEqualAssignVersionByType(@Param("tableName") String tableName,
        @Param("version") BigDecimal version, @Param("type") String type);

    int getCountByVersion(@Param("tableName") String tableName, @Param("version") BigDecimal version);
}
