package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;
import com.hhwy.pm.xmsl.implement.mapper.XmslClimateConditionMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslClimateConditionService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        // 先清空旧数据
        XmslClimateCondition deleteParam = new XmslClimateCondition();
        deleteParam.setDelFlag("1");
        xmslClimateConditionMapper.updateXmslClimateCondition(deleteParam);

        if (!CollectionUtils.isEmpty(xmslClimateConditionList)) {
            for (XmslClimateCondition xmslClimateCondition : xmslClimateConditionList) {
                xmslClimateCondition.setId(IdWorker.createId());
                xmslClimateCondition.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslClimateCondition.setCreateUserName(SecurityUtils.getUserName());
                xmslClimateCondition.setCreateTime(DateUtils.getNowDate());
            }
            xmslClimateConditionMapper.insertXmslClimateConditionList(xmslClimateConditionList);
        }
    }

    @Transactional
    public int deleteXmslClimateConditionByPks(List<Long> xmslClimateConditionPkList) {
        return xmslClimateConditionMapper.deleteXmslClimateConditionByPks(xmslClimateConditionPkList);
    }

}
