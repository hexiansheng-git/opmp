package com.hhwy.pm.xmsl.wbs.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsHistory;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsHistoryMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsHistoryService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wk
 * @date 2023-07-14 11:09:09
 * @remark
 */
@Service
public class XmslWbsHistoryServiceImpl implements IXmslWbsHistoryService {

    @Autowired
    private XmslWbsHistoryMapper xmslWbsHistoryMapper;


    public XmslWbsHistory getXmslWbsHistory(XmslWbsHistory xmslWbsHistory) {
        return xmslWbsHistoryMapper.getXmslWbsHistory(xmslWbsHistory);
    }

    public List<XmslWbsHistory> getXmslWbsHistoryList(XmslWbsHistory xmslWbsHistory) {
        return xmslWbsHistoryMapper.getXmslWbsHistoryList(xmslWbsHistory);
    }

    @Override
    public List<XmslWbsHistory> getListByParentIds(Collection<Long> collection, Long mainId) {
        if(mainId == null || CollectionUtils.isEmpty(collection))
            return new ArrayList<>(2);
        XmslWbsHistory wbsHistory = new XmslWbsHistory();
        wbsHistory.setParams(ObjectUtils.toMap("parentIds", collection));
        wbsHistory.setMainId(mainId);
        return  xmslWbsHistoryMapper.getXmslWbsHistoryList(wbsHistory);
    }

    @Transactional
    public int insertXmslWbsHistory(XmslWbsHistory xmslWbsHistory) {
        xmslWbsHistory.setId(IdWorker.createId()+"");
        xmslWbsHistory.setCreateUser(SecurityUtils.getUserName());
        xmslWbsHistory.setCreateTime(DateUtils.getNowDate());
        return xmslWbsHistoryMapper.insertXmslWbsHistory(xmslWbsHistory);
    }

    @Transactional
    public int insertXmslWbsHistoryList(List<XmslWbsHistory> xmslWbsHistoryList) {
        return xmslWbsHistoryMapper.insertXmslWbsHistoryList(xmslWbsHistoryList);
    }

    @Transactional
    public int updateXmslWbsHistory(XmslWbsHistory xmslWbsHistory) {
        xmslWbsHistory.setUpdateUser(SecurityUtils.getUserName());
        xmslWbsHistory.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsHistoryMapper.updateXmslWbsHistory(xmslWbsHistory);
    }

    @Transactional
    public int updateXmslWbsHistoryList(List<XmslWbsHistory> xmslWbsHistoryList) {
//        for (XmslWbsHistory xmslWbsHistory : xmslWbsHistoryList) {
//            xmslWbsHistory.setUpdateUser(SecurityUtils.getUserName());
//            xmslWbsHistory.setUpdateTime(DateUtils.getNowDate());
//        }
        return xmslWbsHistoryMapper.updateXmslWbsHistoryList(xmslWbsHistoryList);
    }

    @Transactional
    public int deleteXmslWbsHistory(XmslWbsHistory xmslWbsHistory) {
        xmslWbsHistory.setUpdateUser(SecurityUtils.getUserName());
        xmslWbsHistory.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsHistoryMapper.deleteXmslWbsHistory(xmslWbsHistory);
    }

    @Transactional
    public int deleteXmslWbsHistoryByPks(List<Long> xmslWbsHistoryPkList) {
        return xmslWbsHistoryMapper.deleteXmslWbsHistoryByPks(xmslWbsHistoryPkList);
    }

    @Override
    public int deleteByParentIds(List<Long> list) {
        if(CollectionUtils.isEmpty(list))
            return 0;
        return xmslWbsHistoryMapper.deleteByParentIds(list);
    }
}
