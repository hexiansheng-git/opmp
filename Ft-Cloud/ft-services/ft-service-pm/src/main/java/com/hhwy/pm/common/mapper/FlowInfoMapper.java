package com.hhwy.pm.common.mapper;

import com.hhwy.pm.common.domain.FtActBusiness;
import com.hhwy.utils.common.CommonBaseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
