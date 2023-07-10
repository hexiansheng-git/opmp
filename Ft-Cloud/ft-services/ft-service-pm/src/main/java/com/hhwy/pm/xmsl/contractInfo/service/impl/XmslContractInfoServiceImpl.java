package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 13:57:39
 * @remark
 */
@Service
public class XmslContractInfoServiceImpl implements IXmslContractInfoService {

    @Autowired
    private XmslContractInfoMapper xmslContractInfoMapper;


    public XmslContractInfo getXmslContractInfo(XmslContractInfo xmslContractInfo) {
        return xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);
    }

    public List<XmslContractInfo> getXmslContractInfoList(XmslContractInfo xmslContractInfo) {
        return xmslContractInfoMapper.getXmslContractInfoList(xmslContractInfo);
    }

    @Transactional
    public int insertXmslContractInfo(XmslContractInfo xmslContractInfo) {
        xmslContractInfo.setId(IdWorker.createId());
        xmslContractInfo.setCreateUser(SecurityUtils.getUserName());
        xmslContractInfo.setCreateTime(DateUtils.getNowDate());
        return xmslContractInfoMapper.insertXmslContractInfo(xmslContractInfo);
    }

    @Transactional
    public int insertXmslContractInfoList(List<XmslContractInfo> xmslContractInfoList) {
        for (XmslContractInfo xmslContractInfo : xmslContractInfoList) {
            xmslContractInfo.setId(IdWorker.createId());
            xmslContractInfo.setCreateUser(SecurityUtils.getUserName());
            xmslContractInfo.setCreateTime(DateUtils.getNowDate());
        }
        return xmslContractInfoMapper.insertXmslContractInfoList(xmslContractInfoList);
    }

    @Transactional
    public int updateXmslContractInfo(XmslContractInfo xmslContractInfo) {
        xmslContractInfo.setUpdateUser(SecurityUtils.getUserName());
        xmslContractInfo.setUpdateTime(DateUtils.getNowDate());
        return xmslContractInfoMapper.updateXmslContractInfo(xmslContractInfo);
    }

    @Transactional
    public int updateXmslContractInfoList(List<XmslContractInfo> xmslContractInfoList) {
        for (XmslContractInfo xmslContractInfo : xmslContractInfoList) {
            xmslContractInfo.setUpdateUser(SecurityUtils.getUserName());
            xmslContractInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslContractInfoMapper.updateXmslContractInfoList(xmslContractInfoList);
    }

    @Transactional
    public int deleteXmslContractInfo(XmslContractInfo xmslContractInfo) {
        xmslContractInfo.setUpdateUser(SecurityUtils.getUserName());
        xmslContractInfo.setUpdateTime(DateUtils.getNowDate());
        return xmslContractInfoMapper.deleteXmslContractInfo(xmslContractInfo);
    }

    @Transactional
    public int deleteXmslContractInfoByPks(List<Long> xmslContractInfoPkList) {
        return xmslContractInfoMapper.deleteXmslContractInfoByPks(xmslContractInfoPkList);
    }
}
