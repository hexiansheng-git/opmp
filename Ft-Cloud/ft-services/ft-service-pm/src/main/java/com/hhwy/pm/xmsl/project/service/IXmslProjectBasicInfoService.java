package com.hhwy.pm.xmsl.project.service;

import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectInfoWithOther;

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

    /**
     * 项目台账
     * @param xmslProjectBasicInfo
     * @return
     */
    List<XmslProjectBasicInfo> getProjectBasicInfoList(XmslProjectBasicInfo xmslProjectBasicInfo);

    /**
     * 新增项目信息
     * @param xmslProjectBasicInfo
     * @return
     */
    int insertProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    /**
     * 修改项目信息
     * @param xmslProjectBasicInfo
     * @return
     */
    int updateProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int deleteProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int deleteProjectBasicInfoByPks(List<Long> projectBasicInfoPkList);

    XmslProjectBasicInfo getProjectBasicInfo(XmslProjectBasicInfo projectBasicInfo);

    /**
     * 获取项目信息详情（不带子表）
     *
     * @return
     */
    ProjectBasicInfo projectInfo();

    void insertProjectInvokeProject(XmslProjectBasicInfo projectBasicInfo);

    /**
     * 获取项目基本信息（附带其他信息）
     * @return
     */
    ProjectInfoWithOther getProjectInfoWithOther();
}
