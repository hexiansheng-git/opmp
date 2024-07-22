package com.hhwy.sp.common.mapper;

import com.hhwy.utils.common.CommonBaseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * 查询流程审批历史记录
     * return name_/assignee_  节点名称/处理人
     */
    List<Map<String, String>> getHistoryTaskInfo(@Param("businessId") Long businessId, @Param("nodeName") String nodeName);

    /**
     * 查询流程信息
     */
    List<CommonBaseEntity> flowByTBNameAndId(@Param("tableName") String tableName, @Param("businessIds") String[] businessIds, @Param("tenantKey") String tenantKey);

}
