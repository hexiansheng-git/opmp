package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslBasicFacilitiesConditions;
import com.hhwy.pm.xmsl.implement.mapper.XmslBasicFacilitiesConditionsMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslBasicFacilitiesConditionsService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 11:59:44
 * @remark 水、电、交通、通讯条件
 */
@Service
public class XmslBasicFacilitiesConditionsServiceImpl implements IXmslBasicFacilitiesConditionsService {

    @Autowired
    private XmslBasicFacilitiesConditionsMapper xmslBasicFacilitiesConditionsMapper;

    public List<XmslBasicFacilitiesConditions> getXmslBasicFacilitiesConditionsList(
        XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions) {
        return xmslBasicFacilitiesConditionsMapper.getXmslBasicFacilitiesConditionsList(xmslBasicFacilitiesConditions);
    }

    @Transactional
    public void save(List<XmslBasicFacilitiesConditions> xmslBasicFacilitiesConditionsList) {
        if (xmslBasicFacilitiesConditionsList == null || xmslBasicFacilitiesConditionsList.size() == 0) {
            return;
        }
        List<XmslBasicFacilitiesConditions> insertList = new ArrayList<>();
        List<XmslBasicFacilitiesConditions> updateList = new ArrayList<>();
        for (XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions : xmslBasicFacilitiesConditionsList) {
            if (xmslBasicFacilitiesConditions.getId() == null) {
                xmslBasicFacilitiesConditions.setId(IdWorker.createId());
                xmslBasicFacilitiesConditions.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslBasicFacilitiesConditions.setCreateUserName(SecurityUtils.getUserName());
                xmslBasicFacilitiesConditions.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslBasicFacilitiesConditions);
            } else {
                xmslBasicFacilitiesConditions.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslBasicFacilitiesConditions.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslBasicFacilitiesConditions);
            }
        }

        if (insertList.size() > 0) {
            xmslBasicFacilitiesConditionsMapper.insertXmslBasicFacilitiesConditionsList(insertList);
        }
        if (updateList.size() > 0) {
            xmslBasicFacilitiesConditionsMapper.updateXmslBasicFacilitiesConditionsList(updateList);
        }
    }

    @Transactional
    public int deleteXmslBasicFacilitiesConditionsByPks(List<Long> xmslBasicFacilitiesConditionsPkList) {
        return xmslBasicFacilitiesConditionsMapper
            .deleteXmslBasicFacilitiesConditionsByPks(xmslBasicFacilitiesConditionsPkList);
    }
}
