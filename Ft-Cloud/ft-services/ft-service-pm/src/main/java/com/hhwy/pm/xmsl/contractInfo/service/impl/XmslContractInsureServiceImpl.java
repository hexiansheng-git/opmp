package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInsure;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInsureMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInsureService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:36
 * @remark
 */
@Service
public class XmslContractInsureServiceImpl implements IXmslContractInsureService {


    @Autowired
    private XmslContractInsureMapper xmslContractInsureMapper;


    public XmslContractInsure getXmslContractInsure(XmslContractInsure xmslContractInsure) {
        return xmslContractInsureMapper.getXmslContractInsure(xmslContractInsure);
    }

    public List<XmslContractInsure> getXmslContractInsureList(XmslContractInsure xmslContractInsure) {
        return xmslContractInsureMapper.getXmslContractInsureList(xmslContractInsure);
    }

    @Transactional
    public int insertXmslContractInsure(XmslContractInsure xmslContractInsure) {
        xmslContractInsure.setId(IdWorker.createId());
        xmslContractInsure.setCreateUser(SecurityUtils.getUserName());
        xmslContractInsure.setCreateTime(DateUtils.getNowDate());
        return xmslContractInsureMapper.insertXmslContractInsure(xmslContractInsure);
    }

    @Transactional
    public int insertXmslContractInsureList(List<XmslContractInsure> xmslContractInsureList) {
        for (XmslContractInsure xmslContractInsure : xmslContractInsureList) {
            xmslContractInsure.setId(IdWorker.createId());
            xmslContractInsure.setCreateUser(SecurityUtils.getUserName());
            xmslContractInsure.setCreateTime(DateUtils.getNowDate());
        }
        return xmslContractInsureMapper.insertXmslContractInsureList(xmslContractInsureList);
    }

    @Transactional
    public int updateXmslContractInsure(XmslContractInsure xmslContractInsure) {
        xmslContractInsure.setUpdateUser(SecurityUtils.getUserName());
        xmslContractInsure.setUpdateTime(DateUtils.getNowDate());
        return xmslContractInsureMapper.updateXmslContractInsure(xmslContractInsure);
    }

    @Transactional
    public int updateXmslContractInsureList(List<XmslContractInsure> xmslContractInsureList) {
        for (XmslContractInsure xmslContractInsure : xmslContractInsureList) {
            xmslContractInsure.setUpdateUser(SecurityUtils.getUserName());
            xmslContractInsure.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslContractInsureMapper.updateXmslContractInsureList(xmslContractInsureList);
    }

    @Transactional
    public int deleteXmslContractInsure(XmslContractInsure xmslContractInsure) {
        xmslContractInsure.setUpdateUser(SecurityUtils.getUserName());
        xmslContractInsure.setUpdateTime(DateUtils.getNowDate());
        return xmslContractInsureMapper.deleteXmslContractInsure(xmslContractInsure);
    }

    @Transactional
    public int deleteXmslContractInsureByPks(List<Long> xmslContractInsurePkList) {
        return xmslContractInsureMapper.deleteXmslContractInsureByPks(xmslContractInsurePkList);
    }
}
