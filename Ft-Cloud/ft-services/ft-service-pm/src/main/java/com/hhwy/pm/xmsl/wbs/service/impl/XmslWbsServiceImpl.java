package com.hhwy.pm.xmsl.wbs.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
@Service
public class XmslWbsServiceImpl implements IXmslWbsService {

    @Autowired
    private XmslWbsMapper xmslWbsMapper;


    public XmslWbs getXmslWbs(XmslWbs xmslWbs) {
        return xmslWbsMapper.getXmslWbs(xmslWbs);
    }

    public List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs) {
        return xmslWbsMapper.getXmslWbsList(xmslWbs);
    }

    @Transactional
    public int insertXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setId(IdWorker.createId());
        xmslWbs.setCreateUser(SecurityUtils.getUserName());
        xmslWbs.setCreateTime(DateUtils.getNowDate());
        return xmslWbsMapper.insertXmslWbs(xmslWbs);
    }

    @Transactional
    public int insertXmslWbsList(List<XmslWbs> xmslWbsList) {
        for (XmslWbs xmslWbs : xmslWbsList) {
            xmslWbs.setId(IdWorker.createId());
            xmslWbs.setCreateUser(SecurityUtils.getUserName());
            xmslWbs.setCreateTime(DateUtils.getNowDate());
        }
        return xmslWbsMapper.insertXmslWbsList(xmslWbsList);
    }

    @Transactional
    public int updateXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setUpdateUser(SecurityUtils.getUserName());
        xmslWbs.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsMapper.updateXmslWbs(xmslWbs);
    }

    @Transactional
    public int updateXmslWbsList(List<XmslWbs> xmslWbsList) {
        for (XmslWbs xmslWbs : xmslWbsList) {
            xmslWbs.setUpdateUser(SecurityUtils.getUserName());
            xmslWbs.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslWbsMapper.updateXmslWbsList(xmslWbsList);
    }

    @Transactional
    public int deleteXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setUpdateUser(SecurityUtils.getUserName());
        xmslWbs.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsMapper.deleteXmslWbs(xmslWbs);
    }

    @Transactional
    public int deleteXmslWbsByPks(List<Long> xmslWbsPkList) {
        return xmslWbsMapper.deleteXmslWbsByPks(xmslWbsPkList);
    }
}
