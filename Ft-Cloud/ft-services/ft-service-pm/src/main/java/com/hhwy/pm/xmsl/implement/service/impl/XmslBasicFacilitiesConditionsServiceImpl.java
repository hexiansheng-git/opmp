package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslBasicFacilitiesConditions;
import com.hhwy.pm.xmsl.implement.mapper.XmslBasicFacilitiesConditionsMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslBasicFacilitiesConditionsService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        // 先清空旧数据
        XmslBasicFacilitiesConditions deleteParam = new XmslBasicFacilitiesConditions();
        deleteParam.setDelFlag("1");
        xmslBasicFacilitiesConditionsMapper.updateXmslBasicFacilitiesConditions(deleteParam);

        if (!CollectionUtils.isEmpty(xmslBasicFacilitiesConditionsList)) {
            for (XmslBasicFacilitiesConditions xmslBasicFacilitiesConditions : xmslBasicFacilitiesConditionsList) {
                xmslBasicFacilitiesConditions.setId(IdWorker.createId());
                xmslBasicFacilitiesConditions.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslBasicFacilitiesConditions.setCreateUserName(SecurityUtils.getUserName());
                xmslBasicFacilitiesConditions.setCreateTime(DateUtils.getNowDate());
            }
            xmslBasicFacilitiesConditionsMapper
                .insertXmslBasicFacilitiesConditionsList(xmslBasicFacilitiesConditionsList);
        }
    }

    @Transactional
    public int deleteXmslBasicFacilitiesConditionsByPks(List<Long> xmslBasicFacilitiesConditionsPkList) {
        return xmslBasicFacilitiesConditionsMapper
            .deleteXmslBasicFacilitiesConditionsByPks(xmslBasicFacilitiesConditionsPkList);
    }
}
