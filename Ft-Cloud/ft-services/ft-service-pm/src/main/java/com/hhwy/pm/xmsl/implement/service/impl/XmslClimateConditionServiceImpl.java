package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;
import com.hhwy.pm.xmsl.implement.mapper.XmslClimateConditionMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslClimateConditionService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:07
 * @remark 气候条件
 */
@Service
public class XmslClimateConditionServiceImpl implements IXmslClimateConditionService {

    @Autowired
    private XmslClimateConditionMapper xmslClimateConditionMapper;

    public List<XmslClimateCondition> getXmslClimateConditionList(
        XmslClimateCondition xmslClimateCondition) {
        return xmslClimateConditionMapper.getXmslClimateConditionList(xmslClimateCondition);
    }

    @Transactional
    public void save(List<XmslClimateCondition> xmslClimateConditionList) {
        if (xmslClimateConditionList == null || xmslClimateConditionList.size() == 0) {
            return;
        }

        List<XmslClimateCondition> insertList = new ArrayList<>();
        List<XmslClimateCondition> updateList = new ArrayList<>();
        for (XmslClimateCondition xmslClimateCondition : xmslClimateConditionList) {
            if (xmslClimateCondition.getId() == null) {
                xmslClimateCondition.setId(IdWorker.createId());
                xmslClimateCondition.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslClimateCondition.setCreateUserName(SecurityUtils.getUserName());
                xmslClimateCondition.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslClimateCondition);
            } else {
                xmslClimateCondition.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslClimateCondition.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslClimateCondition);
            }
        }

        if (insertList.size() > 0) {
            xmslClimateConditionMapper.insertXmslClimateConditionList(insertList);
        }
        if (updateList.size() > 0) {
            xmslClimateConditionMapper.updateXmslClimateConditionList(updateList);
        }
    }

    @Transactional
    public int deleteXmslClimateConditionByPks(List<Long> xmslClimateConditionPkList) {
        return xmslClimateConditionMapper.deleteXmslClimateConditionByPks(xmslClimateConditionPkList);
    }

}
