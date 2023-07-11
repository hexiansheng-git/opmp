package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractPayinfoMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractPayinfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:46
 * @remark
 */
@Service
public class XmslContractPayinfoServiceImpl implements IXmslContractPayinfoService {

    @Autowired
    private XmslContractPayinfoMapper xmslContractPayinfoMapper;


    public XmslContractPayinfo getXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo) {
        return xmslContractPayinfoMapper.getXmslContractPayinfo(xmslContractPayinfo);
    }

    public List<XmslContractPayinfo> getXmslContractPayinfoList(XmslContractPayinfo xmslContractPayinfo) {
        return xmslContractPayinfoMapper.getXmslContractPayinfoList(xmslContractPayinfo);
    }

    @Transactional
    public int insertXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo) {
        xmslContractPayinfo.setId(IdWorker.createId());
        xmslContractPayinfo.setCreateUser(SecurityUtils.getUserName());
        xmslContractPayinfo.setCreateTime(DateUtils.getNowDate());
        return xmslContractPayinfoMapper.insertXmslContractPayinfo(xmslContractPayinfo);
    }

    @Transactional
    public int insertXmslContractPayinfoList(List<XmslContractPayinfo> xmslContractPayinfoList, XmslContractInfo xmslContractInfo) {
        for (XmslContractPayinfo xmslContractPayinfo : xmslContractPayinfoList) {
            xmslContractPayinfo.setId(IdWorker.createId());
            xmslContractPayinfo.setMasterId(xmslContractInfo.getId());
            xmslContractPayinfo.setProjectId(xmslContractInfo.getProjectId());
            xmslContractPayinfo.setProjectName(xmslContractInfo.getProjectName());
            xmslContractPayinfo.setRegionId(xmslContractInfo.getRegionId());
            xmslContractPayinfo.setRegionName(xmslContractInfo.getRegionName());
            xmslContractPayinfo.setDeptId(xmslContractInfo.getDeptId());
            xmslContractPayinfo.setCreateUser(SecurityUtils.getUserName());
            xmslContractPayinfo.setCreateTime(DateUtils.getNowDate());
        }
        return xmslContractPayinfoMapper.insertXmslContractPayinfoList(xmslContractPayinfoList);
    }

    @Transactional
    public int updateXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo) {
        xmslContractPayinfo.setUpdateUser(SecurityUtils.getUserName());
        xmslContractPayinfo.setUpdateTime(DateUtils.getNowDate());
        return xmslContractPayinfoMapper.updateXmslContractPayinfo(xmslContractPayinfo);
    }

    @Transactional
    public int updateXmslContractPayinfoList(List<XmslContractPayinfo> xmslContractPayinfoList) {
        for (XmslContractPayinfo xmslContractPayinfo : xmslContractPayinfoList) {
            xmslContractPayinfo.setUpdateUser(SecurityUtils.getUserName());
            xmslContractPayinfo.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslContractPayinfoMapper.updateXmslContractPayinfoList(xmslContractPayinfoList);
    }

    @Transactional
    public int deleteXmslContractPayinfo(XmslContractPayinfo xmslContractPayinfo) {
        xmslContractPayinfo.setUpdateUser(SecurityUtils.getUserName());
        xmslContractPayinfo.setUpdateTime(DateUtils.getNowDate());
        return xmslContractPayinfoMapper.deleteXmslContractPayinfo(xmslContractPayinfo);
    }

    @Transactional
    public int deleteXmslContractPayinfoByPks(List<Long> xmslContractPayinfoPkList) {
        return xmslContractPayinfoMapper.deleteXmslContractPayinfoByPks(xmslContractPayinfoPkList);
    }
}
