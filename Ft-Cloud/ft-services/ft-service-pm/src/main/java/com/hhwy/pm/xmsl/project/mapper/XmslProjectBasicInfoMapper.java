package com.hhwy.pm.xmsl.project.mapper;

import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:24
 * @remark 项目基本信息
 */
@Repository
public interface XmslProjectBasicInfoMapper {


    ProjectBasicInfo projectInfo();

    XmslProjectBasicInfo getProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    List<XmslProjectBasicInfo> getProjectBasicInfoList(XmslProjectBasicInfo xmslProjectBasicInfo);

    int insertProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int insertProjectBasicInfoList(@Param("xmslProjectBasicInfoList") List<XmslProjectBasicInfo> xmslProjectBasicInfoList);

    int updateProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int updateProjectBasicInfoList(@Param("list") List<XmslProjectBasicInfo> xmslProjectBasicInfoList);

    int deleteProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int deleteProjectBasicInfoByPks(@Param("projectBasicInfoPkList") List<Long> projectBasicInfoPkList);
}
