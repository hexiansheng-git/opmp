package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractListMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark
 */
@Service
public class XmslContractListServiceImpl implements IXmslContractListService {

    @Autowired
    private XmslContractListMapper xmslContractListMapper;


    public XmslContractList getXmslContractList(XmslContractList xmslContractList) {
        return xmslContractListMapper.getXmslContractList(xmslContractList);
    }

    public List<XmslContractList> getXmslContractListList(XmslContractList xmslContractList) {
        return xmslContractListMapper.getXmslContractListList(xmslContractList);
    }

    @Transactional
    public int insertXmslContractList(XmslContractList xmslContractList) {
        xmslContractList.setId(IdWorker.createId());
        xmslContractList.setCreateUser(SecurityUtils.getUserName());
        xmslContractList.setCreateTime(DateUtils.getNowDate());
        return xmslContractListMapper.insertXmslContractList(xmslContractList);
    }

    @Transactional
    public int insertXmslContractListList(List<XmslContractList> xmslContractListList) {
        for (XmslContractList xmslContractList : xmslContractListList) {
            xmslContractList.setId(IdWorker.createId());
            xmslContractList.setCreateUser(SecurityUtils.getUserName());
            xmslContractList.setCreateTime(DateUtils.getNowDate());
        }
        return xmslContractListMapper.insertXmslContractListList(xmslContractListList);
    }

    @Transactional
    public int updateXmslContractList(XmslContractList xmslContractList) {
        xmslContractList.setUpdateUser(SecurityUtils.getUserName());
        xmslContractList.setUpdateTime(DateUtils.getNowDate());
        return xmslContractListMapper.updateXmslContractList(xmslContractList);
    }

    @Transactional
    public int updateXmslContractListList(List<XmslContractList> xmslContractListList) {
        for (XmslContractList xmslContractList : xmslContractListList) {
            xmslContractList.setUpdateUser(SecurityUtils.getUserName());
            xmslContractList.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslContractListMapper.updateXmslContractListList(xmslContractListList);
    }

    @Transactional
    public int deleteXmslContractList(XmslContractList xmslContractList) {
        xmslContractList.setUpdateUser(SecurityUtils.getUserName());
        xmslContractList.setUpdateTime(DateUtils.getNowDate());
        return xmslContractListMapper.deleteXmslContractList(xmslContractList);
    }

    @Transactional
    public int deleteXmslContractListByPks(List<Long> xmslContractListPkList) {
        return xmslContractListMapper.deleteXmslContractListByPks(xmslContractListPkList);
    }
}
