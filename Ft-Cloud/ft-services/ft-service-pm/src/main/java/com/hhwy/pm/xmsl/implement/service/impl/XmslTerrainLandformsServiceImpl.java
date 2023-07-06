package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;
import com.hhwy.pm.xmsl.implement.mapper.XmslTerrainLandformsMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslTerrainLandformsService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (xmslTerrainLandformsList == null || xmslTerrainLandformsList.size() == 0) {
            return;
        }
        List<XmslTerrainLandforms> insertList = new ArrayList<>();
        List<XmslTerrainLandforms> updateList = new ArrayList<>();
        for (XmslTerrainLandforms xmslTerrainLandforms : xmslTerrainLandformsList) {
            if (xmslTerrainLandforms.getId() == null) {
                xmslTerrainLandforms.setId(IdWorker.createId());
                xmslTerrainLandforms.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslTerrainLandforms.setCreateUserName(SecurityUtils.getUserName());
                xmslTerrainLandforms.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslTerrainLandforms);
            } else {
                xmslTerrainLandforms.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslTerrainLandforms.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslTerrainLandforms);
            }
        }

        if (insertList.size() > 0) {
            xmslTerrainLandformsMapper.insertXmslTerrainLandformsList(insertList);
        }
        if (updateList.size() > 0) {
            xmslTerrainLandformsMapper.updateXmslTerrainLandformsList(updateList);
        }
    }

    @Transactional
    public int deleteXmslTerrainLandformsByPks(List<Long> xmslTerrainLandformsPkList) {
        return xmslTerrainLandformsMapper.deleteXmslTerrainLandformsByPks(xmslTerrainLandformsPkList);
    }
}
