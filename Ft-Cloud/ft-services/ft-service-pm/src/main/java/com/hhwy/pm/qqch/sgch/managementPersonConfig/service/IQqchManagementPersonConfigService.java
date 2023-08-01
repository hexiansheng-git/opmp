package com.hhwy.pm.qqch.sgch.managementPersonConfig.service;

import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.vo.QqchManagementPersonConfigVo;

import java.util.List;

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


    QqchManagementPersonConfigVo synchData();
}
