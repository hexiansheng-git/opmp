package com.hhwy.pm.qqch.sgch.managementPersonConfig.service;

import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.vo.QqchManagementPersonConfigVo;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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


    QqchManagementPersonConfigVo synchData(QqchManagementPersonConfigVo qqchManagementPersonConfigVo);

    Map<String, Integer> personNumCalc(QqchManagementPersonConfig vo);

    /**
     * 获取 “项目领导层” 层级下的人员数据
     *
     * @return
     */
    List<QqchManagementPersonConfig> getProjectLeadershipPersonList();

    String getPersonType(String userName) throws ParserConfigurationException, IOException, SAXException;

    /**
     * 获取 “项目领导层” 层级下的人员用户名
     *
     * @return
     */
    Map<String, String> getProjectLeadershipPersonUserNameMap();
}
