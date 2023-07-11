package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSign;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractSignMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractSignService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:49
 * @remark
 */
@Service
public class XmslContractSignServiceImpl implements IXmslContractSignService {

    @Autowired
    private XmslContractSignMapper xmslContractSignMapper;


    public XmslContractSign getXmslContractSign(XmslContractSign xmslContractSign) {
        return xmslContractSignMapper.getXmslContractSign(xmslContractSign);
    }

    public List<XmslContractSign> getXmslContractSignList(XmslContractSign xmslContractSign) {
        return xmslContractSignMapper.getXmslContractSignList(xmslContractSign);
    }

    @Transactional
    public int insertXmslContractSign(XmslContractSign xmslContractSign) {
        xmslContractSign.setId(IdWorker.createId());
        xmslContractSign.setCreateUser(SecurityUtils.getUserName());
        xmslContractSign.setCreateTime(DateUtils.getNowDate());
        return xmslContractSignMapper.insertXmslContractSign(xmslContractSign);
    }

    @Transactional
    public int insertXmslContractSignList(List<XmslContractSign> xmslContractSignList) {
        for (XmslContractSign xmslContractSign : xmslContractSignList) {
            xmslContractSign.setId(IdWorker.createId());
            xmslContractSign.setCreateUser(SecurityUtils.getUserName());
            xmslContractSign.setCreateTime(DateUtils.getNowDate());
        }
        return xmslContractSignMapper.insertXmslContractSignList(xmslContractSignList);
    }

    @Transactional
    public int updateXmslContractSign(XmslContractSign xmslContractSign) {
        xmslContractSign.setUpdateUser(SecurityUtils.getUserName());
        xmslContractSign.setUpdateTime(DateUtils.getNowDate());
        return xmslContractSignMapper.updateXmslContractSign(xmslContractSign);
    }

    @Transactional
    public int updateXmslContractSignList(List<XmslContractSign> xmslContractSignList) {
        for (XmslContractSign xmslContractSign : xmslContractSignList) {
            xmslContractSign.setUpdateUser(SecurityUtils.getUserName());
            xmslContractSign.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslContractSignMapper.updateXmslContractSignList(xmslContractSignList);
    }

    @Transactional
    public int deleteXmslContractSign(XmslContractSign xmslContractSign) {
        xmslContractSign.setUpdateUser(SecurityUtils.getUserName());
        xmslContractSign.setUpdateTime(DateUtils.getNowDate());
        return xmslContractSignMapper.deleteXmslContractSign(xmslContractSign);
    }

    @Transactional
    public int deleteXmslContractSignByPks(List<Long> xmslContractSignPkList) {
        return xmslContractSignMapper.deleteXmslContractSignByPks(xmslContractSignPkList);
    }
}
