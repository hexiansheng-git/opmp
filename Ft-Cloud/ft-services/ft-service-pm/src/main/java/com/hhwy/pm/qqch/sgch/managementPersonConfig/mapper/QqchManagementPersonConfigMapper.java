package com.hhwy.pm.qqch.sgch.managementPersonConfig.mapper;

import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.vo.QqchManagementPersonConfigVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    Map<String, Integer> personNumCalc(QqchManagementPersonConfigVo vo);
}
