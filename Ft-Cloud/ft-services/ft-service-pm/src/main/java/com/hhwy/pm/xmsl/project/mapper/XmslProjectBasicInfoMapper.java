package com.hhwy.pm.xmsl.project.mapper;

import java.util.List;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-03 09:48:24
 * @remark 项目基本信息
 */
@Repository
public interface XmslProjectBasicInfoMapper {

    XmslProjectBasicInfo getProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    List<XmslProjectBasicInfo> getProjectBasicInfoList(XmslProjectBasicInfo xmslProjectBasicInfo);

    int insertProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int insertProjectBasicInfoList(@Param("xmslProjectBasicInfoList") List<XmslProjectBasicInfo> xmslProjectBasicInfoList);

    int updateProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int updateProjectBasicInfoList(@Param("list") List<XmslProjectBasicInfo> xmslProjectBasicInfoList);

    int deleteProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo);

    int deleteProjectBasicInfoByPks(@Param("projectBasicInfoPkList") List<Long> projectBasicInfoPkList);
}
