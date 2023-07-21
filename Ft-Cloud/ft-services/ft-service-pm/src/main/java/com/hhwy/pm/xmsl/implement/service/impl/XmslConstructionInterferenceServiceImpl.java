package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslConstructionInterference;
import com.hhwy.pm.xmsl.implement.mapper.XmslConstructionInterferenceMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslConstructionInterferenceService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        // 先清空旧数据
        XmslConstructionInterference deleteParam = new XmslConstructionInterference();
        deleteParam.setDelFlag("1");
        xmslConstructionInterferenceMapper.updateXmslConstructionInterference(deleteParam);

        if (!CollectionUtils.isEmpty(xmslConstructionInterferenceList)) {
            for (XmslConstructionInterference xmslConstructionInterference : xmslConstructionInterferenceList) {
                xmslConstructionInterference.setId(IdWorker.createId());
                xmslConstructionInterference.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslConstructionInterference.setCreateUserName(SecurityUtils.getUserName());
                xmslConstructionInterference.setCreateTime(DateUtils.getNowDate());
            }
            xmslConstructionInterferenceMapper.insertXmslConstructionInterferenceList(xmslConstructionInterferenceList);
        }
    }

    @Transactional
    public int deleteXmslConstructionInterferenceByPks(List<Long> xmslConstructionInterferencePkList) {
        return xmslConstructionInterferenceMapper
            .deleteXmslConstructionInterferenceByPks(xmslConstructionInterferencePkList);
    }
}
