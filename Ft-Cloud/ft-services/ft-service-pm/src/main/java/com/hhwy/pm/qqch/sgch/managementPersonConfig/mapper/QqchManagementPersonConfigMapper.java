package com.hhwy.pm.qqch.sgch.managementPersonConfig.mapper;

import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-31 15:15:56
 * @remark 
 */
public interface QqchManagementPersonConfigMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchManagementPersonConfig getQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig);

    List<QqchManagementPersonConfig> getQqchManagementPersonConfigList(QqchManagementPersonConfig qqchManagementPersonConfig);

    int insertQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig);

    int insertQqchManagementPersonConfigList(@Param("qqchManagementPersonConfigList") List<QqchManagementPersonConfig> qqchManagementPersonConfigList);

    int updateQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig);

    int updateQqchManagementPersonConfigList(@Param("qqchManagementPersonConfigList") List<QqchManagementPersonConfig> qqchManagementPersonConfigList);
    
    int deleteQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig);

    int deleteQqchManagementPersonConfigByPks(@Param("qqchManagementPersonConfigPkList") List<Long> qqchManagementPersonConfigPkList);
    }
