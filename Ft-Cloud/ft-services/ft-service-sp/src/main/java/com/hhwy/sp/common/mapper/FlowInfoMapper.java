package com.hhwy.sp.common.mapper;

import com.hhwy.utils.common.CommonBaseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流程查询
 *
 * @author mls
 */
@Repository
public interface FlowInfoMapper {

    /**
     * 查询流程信息
     */
    List<CommonBaseEntity> flowByTBNameAndId(@Param("tableName") String tableName, @Param("businessIds") String[] businessIds, @Param("tenantKey") String tenantKey);

}
