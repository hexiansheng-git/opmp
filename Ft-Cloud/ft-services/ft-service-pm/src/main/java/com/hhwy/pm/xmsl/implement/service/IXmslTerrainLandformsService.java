package com.hhwy.pm.xmsl.implement.service;

import java.util.List;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:13
 * @remark 地形地貌
 */
public interface IXmslTerrainLandformsService {

    List<XmslTerrainLandforms> getXmslTerrainLandformsList(XmslTerrainLandforms xmslTerrainLandforms);

    void save(List<XmslTerrainLandforms> xmslTerrainLandformsList);

    int deleteXmslTerrainLandformsByPks(List<Long> xmslTerrainLandformsPkList);
}
