package com.hhwy.pm.xmsl.project.service;

import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:24
 * @remark 项目基本信息
 */
public interface IXmslProjectBasicInfoService {

    /**
     * 根据id获取项目基本信息
     * @param id
     * @return
     */
    XmslProjectBasicInfo getProjectBasicInfoById(Long id);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    XmslProjectBasicInfo getProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    List<XmslProjectBasicInfo> getProjectBasicInfoList(XmslProjectBasicInfo xmslProjectBasicInfo);

    int insertProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int updateProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int deleteProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int deleteProjectBasicInfoByPks(List<Long> projectBasicInfoPkList);
}
