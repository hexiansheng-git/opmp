package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslExtend;
import com.hhwy.pm.xmsl.implement.mapper.XmslExtendMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslExtendService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:35
 * @remark 当地政策要点说明, 社会和人文条件说明, 气候条件附件
 */
@Service
public class XmslExtendServiceImpl implements IXmslExtendService {

    @Autowired
    private XmslExtendMapper xmslExtendMapper;

    public XmslExtend getXmslExtend(XmslExtend xmslExtend) {
        return xmslExtendMapper.getXmslExtend(xmslExtend);
    }

    @Transactional
    public XmslExtend save(XmslExtend xmslExtend) {
        XmslExtend extend = xmslExtendMapper.getXmslExtend(new XmslExtend());
        if (extend == null || extend.getId() == null) {
            xmslExtend.setId(IdWorker.createId());
            xmslExtend.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslExtend.setCreateUserName(SecurityUtils.getUserName());
            xmslExtend.setCreateTime(DateUtils.getNowDate());
            xmslExtendMapper.insertXmslExtend(xmslExtend);
        } else {
            xmslExtend.setId(extend.getId());
            xmslExtend.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslExtend.setUpdateTime(DateUtils.getNowDate());
            xmslExtendMapper.updateXmslExtend(xmslExtend);
        }
        return xmslExtend;
    }
}
