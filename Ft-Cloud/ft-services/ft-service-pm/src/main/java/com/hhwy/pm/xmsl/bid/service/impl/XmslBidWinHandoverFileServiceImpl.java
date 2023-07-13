package com.hhwy.pm.xmsl.bid.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverFile;
import com.hhwy.pm.xmsl.bid.mapper.XmslBidWinHandoverFileMapper;
import com.hhwy.pm.xmsl.bid.service.IXmslBidWinHandoverFileService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-06 15:18:31
 * @remark
 */
@Service
public class XmslBidWinHandoverFileServiceImpl implements IXmslBidWinHandoverFileService {

    @Autowired
    private XmslBidWinHandoverFileMapper xmslBidWinHandoverFileMapper;


    public XmslBidWinHandoverFile getXmslBidWinHandoverFile(
        XmslBidWinHandoverFile xmslBidWinHandoverFile) {
        return xmslBidWinHandoverFileMapper.getXmslBidWinHandoverFile(xmslBidWinHandoverFile);
    }

    public List<XmslBidWinHandoverFile> getXmslBidWinHandoverFileList(
        XmslBidWinHandoverFile xmslBidWinHandoverFile) {
        return xmslBidWinHandoverFileMapper.getXmslBidWinHandoverFileList(xmslBidWinHandoverFile);
    }

    @Transactional
    public int insertXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile) {
        xmslBidWinHandoverFile.setId(IdWorker.createId());
        xmslBidWinHandoverFile.setCreateUser(SecurityUtils.getUserName());
        xmslBidWinHandoverFile.setCreateTime(DateUtils.getNowDate());
        return xmslBidWinHandoverFileMapper.insertXmslBidWinHandoverFile(xmslBidWinHandoverFile);
    }

    @Transactional
    public int insertXmslBidWinHandoverFileList(List<XmslBidWinHandoverFile> xmslBidWinHandoverFileList) {
        for (XmslBidWinHandoverFile xmslBidWinHandoverFile : xmslBidWinHandoverFileList) {
            xmslBidWinHandoverFile.setId(IdWorker.createId());
            xmslBidWinHandoverFile.setCreateUser(SecurityUtils.getUserName());
            xmslBidWinHandoverFile.setCreateTime(DateUtils.getNowDate());
        }
        return xmslBidWinHandoverFileMapper.insertXmslBidWinHandoverFileList(xmslBidWinHandoverFileList);
    }

    @Transactional
    public int updateXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile) {
        xmslBidWinHandoverFile.setUpdateUser(SecurityUtils.getUserName());
        xmslBidWinHandoverFile.setUpdateTime(DateUtils.getNowDate());
        return xmslBidWinHandoverFileMapper.updateXmslBidWinHandoverFile(xmslBidWinHandoverFile);
    }

    @Transactional
    public int updateXmslBidWinHandoverFileList(List<XmslBidWinHandoverFile> xmslBidWinHandoverFileList) {
        for (XmslBidWinHandoverFile xmslBidWinHandoverFile : xmslBidWinHandoverFileList) {
            xmslBidWinHandoverFile.setUpdateUser(SecurityUtils.getUserName());
            xmslBidWinHandoverFile.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslBidWinHandoverFileMapper.updateXmslBidWinHandoverFileList(xmslBidWinHandoverFileList);
    }

    @Transactional
    public int deleteXmslBidWinHandoverFile(XmslBidWinHandoverFile xmslBidWinHandoverFile) {
        xmslBidWinHandoverFile.setUpdateUser(SecurityUtils.getUserName());
        xmslBidWinHandoverFile.setUpdateTime(DateUtils.getNowDate());
        return xmslBidWinHandoverFileMapper.deleteXmslBidWinHandoverFile(xmslBidWinHandoverFile);
    }

    @Transactional
    public int deleteXmslBidWinHandoverFileByPks(List<Long> xmslBidWinHandoverFilePkList) {
        return xmslBidWinHandoverFileMapper.deleteXmslBidWinHandoverFileByPks(xmslBidWinHandoverFilePkList);
    }
}
