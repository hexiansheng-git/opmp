package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslConstructionInterference;
import com.hhwy.pm.xmsl.implement.mapper.XmslConstructionInterferenceMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslConstructionInterferenceService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:29
 * @remark 施工干扰
 */
@Service
public class XmslConstructionInterferenceServiceImpl implements
    IXmslConstructionInterferenceService {

    @Autowired
    private XmslConstructionInterferenceMapper xmslConstructionInterferenceMapper;

    public List<XmslConstructionInterference> getXmslConstructionInterferenceList(
        XmslConstructionInterference xmslConstructionInterference) {
        return xmslConstructionInterferenceMapper.getXmslConstructionInterferenceList(xmslConstructionInterference);
    }

    @Transactional
    public void save(List<XmslConstructionInterference> xmslConstructionInterferenceList) {
        if (xmslConstructionInterferenceList == null || xmslConstructionInterferenceList.size() == 0) {
            return;
        }
        List<XmslConstructionInterference> insertList = new ArrayList<>();
        List<XmslConstructionInterference> updateList = new ArrayList<>();
        for (XmslConstructionInterference xmslConstructionInterference : xmslConstructionInterferenceList) {
            if (xmslConstructionInterference.getId() == null) {
                xmslConstructionInterference.setId(IdWorker.createId());
                xmslConstructionInterference.setCreateUser(SecurityUtils.getUserName());
                xmslConstructionInterference.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslConstructionInterference);
            } else {
                xmslConstructionInterference.setUpdateUser(SecurityUtils.getUserName());
                xmslConstructionInterference.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslConstructionInterference);
            }
        }

        if (insertList.size() > 0) {
            xmslConstructionInterferenceMapper.insertXmslConstructionInterferenceList(insertList);
        }
        if (updateList.size() > 0) {
            xmslConstructionInterferenceMapper.updateXmslConstructionInterferenceList(updateList);
        }
    }

    @Transactional
    public int deleteXmslConstructionInterferenceByPks(List<Long> xmslConstructionInterferencePkList) {
        return xmslConstructionInterferenceMapper
            .deleteXmslConstructionInterferenceByPks(xmslConstructionInterferencePkList);
    }
}
