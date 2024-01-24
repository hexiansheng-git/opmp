package com.hhwy.sp.techOrg.service;

import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageInfo;
import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @author lcf
 * @date 2023-11-23 10:21:37
 * @remark
 */
public interface ISgjsTechnicalManageInfoService {

    SgjsTechnicalManageInfo getSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    List<SgjsTechnicalManageInfo> getSgjsTechnicalManageInfoList(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int insertSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int insertSgjsTechnicalManageInfoList(SgjsTechnicalManageInfoVo vo);

    int updateSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int updateSgjsTechnicalManageInfoList(List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList);

    int deleteSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int deleteSgjsTechnicalManageInfoByPks(List<Long> sgjsTechnicalManageInfoPkList);
}
