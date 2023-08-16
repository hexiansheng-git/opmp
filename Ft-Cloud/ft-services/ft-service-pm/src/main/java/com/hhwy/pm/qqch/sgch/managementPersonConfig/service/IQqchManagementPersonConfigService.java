package com.hhwy.pm.qqch.sgch.managementPersonConfig.service;

import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.vo.QqchManagementPersonConfigVo;

import java.util.List;
import java.util.Map;

/**
 * @author ldd
 * @date 2023-07-31 15:15:56
 * @remark 
 */
public interface IQqchManagementPersonConfigService {
                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchManagementPersonConfig getQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig);

    QqchManagementPersonConfigVo getQqchManagementPersonConfigList(QqchManagementPersonConfig qqchManagementPersonConfig);

    int insertQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig);


    int updateQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig);

            int updateQqchManagementPersonConfigList(List<QqchManagementPersonConfig> qqchManagementPersonConfigList);
    
    int deleteQqchManagementPersonConfig(QqchManagementPersonConfig qqchManagementPersonConfig);

            int deleteQqchManagementPersonConfigByPks(List<Long> qqchManagementPersonConfigPkList);

    void save(QqchManagementPersonConfigVo qqchManagementPersonConfigVo);


    QqchManagementPersonConfigVo synchData( QqchManagementPersonConfigVo qqchManagementPersonConfigVo);

    Map<String, Integer> personNumCalc(QqchManagementPersonConfig vo);
}
