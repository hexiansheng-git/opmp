package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:13
 * @remark 地形地貌
 */
public interface XmslTerrainLandformsMapper {

    XmslTerrainLandforms getXmslTerrainLandforms(XmslTerrainLandforms xmslTerrainLandforms);

    List<XmslTerrainLandforms> getXmslTerrainLandformsList(
        XmslTerrainLandforms xmslTerrainLandforms);

    int insertXmslTerrainLandforms(XmslTerrainLandforms xmslTerrainLandforms);

    int insertXmslTerrainLandformsList(
        @Param("xmslTerrainLandformsList") List<XmslTerrainLandforms> xmslTerrainLandformsList);

    int updateXmslTerrainLandforms(XmslTerrainLandforms xmslTerrainLandforms);

    int updateXmslTerrainLandformsList(@Param("list") List<XmslTerrainLandforms> xmslTerrainLandformsList);

    int deleteXmslTerrainLandforms(XmslTerrainLandforms xmslTerrainLandforms);

    int deleteXmslTerrainLandformsByPks(
        @Param("xmslTerrainLandformsPkList") List<Long> xmslTerrainLandformsPkList);
}
