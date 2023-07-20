package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;
import com.hhwy.pm.xmsl.implement.mapper.XmslTerrainLandformsMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslTerrainLandformsService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:13
 * @remark 地形地貌
 */
@Service
public class XmslTerrainLandformsServiceImpl implements IXmslTerrainLandformsService {

    @Autowired
    private XmslTerrainLandformsMapper xmslTerrainLandformsMapper;

    public List<XmslTerrainLandforms> getXmslTerrainLandformsList(XmslTerrainLandforms xmslTerrainLandforms) {
        return xmslTerrainLandformsMapper.getXmslTerrainLandformsList(xmslTerrainLandforms);
    }

    @Transactional
    public void save(List<XmslTerrainLandforms> xmslTerrainLandformsList) {
        // 先清空旧数据
        XmslTerrainLandforms deleteParam = new XmslTerrainLandforms();
        deleteParam.setDelFlag("1");
        xmslTerrainLandformsMapper.updateXmslTerrainLandforms(deleteParam);

        if (!CollectionUtils.isEmpty(xmslTerrainLandformsList)) {
            for (XmslTerrainLandforms xmslTerrainLandforms : xmslTerrainLandformsList) {
                xmslTerrainLandforms.setId(IdWorker.createId());
                xmslTerrainLandforms.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslTerrainLandforms.setCreateUserName(SecurityUtils.getUserName());
                xmslTerrainLandforms.setCreateTime(DateUtils.getNowDate());
            }
            xmslTerrainLandformsMapper.insertXmslTerrainLandformsList(xmslTerrainLandformsList);
        }
    }

    @Transactional
    public int deleteXmslTerrainLandformsByPks(List<Long> xmslTerrainLandformsPkList) {
        return xmslTerrainLandformsMapper.deleteXmslTerrainLandformsByPks(xmslTerrainLandformsPkList);
    }
}
