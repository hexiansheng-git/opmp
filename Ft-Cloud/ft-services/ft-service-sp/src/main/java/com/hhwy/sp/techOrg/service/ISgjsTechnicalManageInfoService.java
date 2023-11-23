package com.hhwy.sp.techOrg.service;

import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageInfo;

import java.util.List;

/**
 * @author lcf
 * @date 2023-11-23 10:21:37
 * @remark
 */
public interface ISgjsTechnicalManageInfoService {

    SgjsTechnicalManageInfo getSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    List<SgjsTechnicalManageInfo> getSgjsTechnicalManageInfoList(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int insertSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int insertSgjsTechnicalManageInfoList(List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList);

    int updateSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int updateSgjsTechnicalManageInfoList(List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList);

    int deleteSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int deleteSgjsTechnicalManageInfoByPks(List<Long> sgjsTechnicalManageInfoPkList);
}
