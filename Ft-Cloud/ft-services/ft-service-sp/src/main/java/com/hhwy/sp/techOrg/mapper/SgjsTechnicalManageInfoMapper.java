package com.hhwy.sp.techOrg.mapper;

import java.util.List;

import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author lcf
 * @date 2023-11-23 10:21:37
 * @remark
 */
public interface SgjsTechnicalManageInfoMapper {

    SgjsTechnicalManageInfo getSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    List<SgjsTechnicalManageInfo> getSgjsTechnicalManageInfoList(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int insertSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int insertSgjsTechnicalManageInfoList(@Param("sgjsTechnicalManageInfoList") List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList);

    int updateSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int updateSgjsTechnicalManageInfoList(@Param("sgjsTechnicalManageInfoList") List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList);

    int deleteSgjsTechnicalManageInfo(SgjsTechnicalManageInfo sgjsTechnicalManageInfo);

    int deleteSgjsTechnicalManageInfoByPks(@Param("sgjsTechnicalManageInfoPkList") List<Long> sgjsTechnicalManageInfoPkList);

    /**
     * 根据主表id进行子表数据删除
     *
     * @param techIdList
     * @return
     */
    int deleteInfoByPIds(@Param("techIdList") List<String> techIdList);
}
