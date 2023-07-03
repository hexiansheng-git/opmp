package com.hhwy.pm.xmslClimateCondition.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.xmslClimateCondition.mapper.XmslClimateConditionMapper;
import com.hhwy.pm.xmslClimateCondition.service.IXmslClimateConditionService;
import com.hhwy.pm.xmslClimateCondition.domain.XmslClimateCondition;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zgx
 * @date 2023-07-03 18:09:54
 * @remark 
 */
@Service
public class XmslClimateConditionServiceImpl implements IXmslClimateConditionService{

    @Autowired
    private XmslClimateConditionMapper xmslClimateConditionMapper;

                                                                                                                                                                                                                                                                                                                                
    public XmslClimateCondition getXmslClimateCondition(XmslClimateCondition xmslClimateCondition) {
        return xmslClimateConditionMapper.getXmslClimateCondition(xmslClimateCondition);
    }

    public List<XmslClimateCondition> getXmslClimateConditionList(XmslClimateCondition xmslClimateCondition) {
        return xmslClimateConditionMapper.getXmslClimateConditionList(xmslClimateCondition);
    }

    @Transactional
    public int insertXmslClimateCondition(XmslClimateCondition xmslClimateCondition) {
        xmslClimateCondition.setId(IdWorker.createId());
        xmslClimateCondition.setCreateUser(SecurityUtils.getUserName());
        xmslClimateCondition.setCreateTime(DateUtils.getNowDate());
        return xmslClimateConditionMapper.insertXmslClimateCondition(xmslClimateCondition);
    }

    @Transactional
    public int insertXmslClimateConditionList(List<XmslClimateCondition> xmslClimateConditionList) {
        for (XmslClimateCondition xmslClimateCondition : xmslClimateConditionList) {
            xmslClimateCondition.setId(IdWorker.createId());
            xmslClimateCondition.setCreateUser(SecurityUtils.getUserName());
            xmslClimateCondition.setCreateTime(DateUtils.getNowDate());
        }
        return xmslClimateConditionMapper.insertXmslClimateConditionList(xmslClimateConditionList);
    }

    @Transactional
    public int updateXmslClimateCondition(XmslClimateCondition xmslClimateCondition) {
        xmslClimateCondition.setUpdateUser(SecurityUtils.getUserName());
        xmslClimateCondition.setUpdateTime(DateUtils.getNowDate());
        return xmslClimateConditionMapper.updateXmslClimateCondition(xmslClimateCondition);
    }

    
    @Transactional
    public int deleteXmslClimateCondition(XmslClimateCondition xmslClimateCondition) {
        xmslClimateCondition.setUpdateUser(SecurityUtils.getUserName());
        xmslClimateCondition.setUpdateTime(DateUtils.getNowDate());
        return xmslClimateConditionMapper.deleteXmslClimateCondition(xmslClimateCondition);
    }

    }
