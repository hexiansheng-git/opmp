package com.hhwy.pm.common.mapper;

import com.hhwy.pm.common.domain.FtActBusiness;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
    FtActBusiness flowByTBNameAndId(@Param("tableName") String tableName, @Param("businessId") String businessId);

}
