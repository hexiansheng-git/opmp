package com.hhwy.pm.qqch.wzch.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物资策划通用持久层
 *
 * @author mls
 */
public interface WzchCommonMapper {

    /**
     * 查询项目再指定表中的数据量
     *
     * @param projectId  项目id
     * @param tableName  表名称
     * @param businessId 业务id
     * @return
     */
    int verifyProjectSelected(@Param("projectId") Long projectId,
                              @Param("tableName") String tableName,
                              @Param("businessId") Long businessId,
                              @Param("code") String code,
                              @Param("codeFiledName") String codeFiledName);

    /**
     * 查询单据是否能够被调整
     *
     * @param businessId
     * @param tableName
     * @return
     */
    String selectCanAdjust(@Param("businessId") Long businessId, @Param("tableName") String tableName);

    String selectCanAdjust2(@Param("businessId") Long businessId, @Param("tableName") String tableName);
    /**
     * 查询单据是否能够被调整(一条数据只能调整一次)
     *
     * @param tableName
     * @param projectId
     * @return
     */
    Long selectCanAdjustOnly(@Param("tableName") String tableName, @Param("projectId") Long projectId);


    int updateValidStatus(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("fieldValue") long fieldValue);

    int updateValidStatusByProjectId(@Param("tableName") String tableName, @Param("projectId") long projectId);



    /**
     * 逻辑删除数据
     *
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    int deleteByField(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("fieldValue") Long fieldValue);
    int batchDeleteByField(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("fieldValues") List<String> fieldValues);

    Long selectCanAdjustOnly2(@Param("tableName")String tableName, @Param("projectId")Long projectId);
}
