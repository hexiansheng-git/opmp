package com.hhwy.pm.xmsl.bid.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverFile;
import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverInfo;
import com.hhwy.pm.xmsl.bid.mapper.XmslBidWinHandoverFileMapper;
import com.hhwy.pm.xmsl.bid.mapper.XmslBidWinHandoverInfoMapper;
import com.hhwy.pm.xmsl.bid.service.IXmslBidWinHandoverFileService;
import com.hhwy.pm.xmsl.bid.service.IXmslBidWinHandoverInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-06 15:13:41
 * @remark
 */
@Service
public class XmslBidWinHandoverInfoServiceImpl implements IXmslBidWinHandoverInfoService {

    @Autowired
    private XmslBidWinHandoverInfoMapper xmslBidWinHandoverInfoMapper;
    @Autowired
    private IXmslBidWinHandoverFileService xmslBidWinHandoverFileService;
    @Autowired
    private XmslBidWinHandoverFileMapper xmslBidWinHandoverFileMapper;

    public XmslBidWinHandoverInfo getXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo) {
        XmslBidWinHandoverInfo result = xmslBidWinHandoverInfoMapper.getXmslBidWinHandoverInfo(xmslBidWinHandoverInfo);

        XmslBidWinHandoverFile xmslBidWinHandoverFile = new XmslBidWinHandoverFile();
        if (result != null && result.getId() != null) {
            xmslBidWinHandoverFile.setHandoverInfoId(xmslBidWinHandoverInfo.getId());
            List<XmslBidWinHandoverFile> fileList =
                xmslBidWinHandoverFileService.getXmslBidWinHandoverFileList(xmslBidWinHandoverFile);
            result.setXmslBidWinHandoverFileList(fileList);
        }
        return result;
    }

    public List<XmslBidWinHandoverInfo> getXmslBidWinHandoverInfoList(XmslBidWinHandoverInfo xmslBidWinHandoverInfo) {
        return xmslBidWinHandoverInfoMapper.getXmslBidWinHandoverInfoList(xmslBidWinHandoverInfo);
    }

    @Transactional
    public void save(XmslBidWinHandoverInfo xmslBidWinHandoverInfo) {
        XmslBidWinHandoverInfo infoParam = new XmslBidWinHandoverInfo();
        XmslBidWinHandoverInfo handoverInfo = xmslBidWinHandoverInfoMapper.getXmslBidWinHandoverInfo(infoParam);

        XmslBidWinHandoverInfo info = new XmslBidWinHandoverInfo();
        if (handoverInfo == null || handoverInfo.getId() == null) {
            info.setId(IdWorker.createId());
            info.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setCreateUserName(SecurityUtils.getUserName());
            info.setCreateTime(DateUtils.getNowDate());
            xmslBidWinHandoverInfoMapper.insertXmslBidWinHandoverInfo(info);
        } else {
            info.setId(handoverInfo.getId());
            info.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            info.setUpdateTime(DateUtils.getNowDate());
            xmslBidWinHandoverInfoMapper.updateXmslBidWinHandoverInfo(info);
        }

        // 中标项目移交文件
        if (xmslBidWinHandoverInfo.getXmslBidWinHandoverFileList() != null
            && xmslBidWinHandoverInfo.getXmslBidWinHandoverFileList().size() != 0) {
            List<XmslBidWinHandoverFile> insertList = new ArrayList<>();
            List<XmslBidWinHandoverFile> updateList = new ArrayList<>();
            for (XmslBidWinHandoverFile bidWinHandoverFile : xmslBidWinHandoverInfo.getXmslBidWinHandoverFileList()) {
                if (bidWinHandoverFile.getId() == null) {
                    bidWinHandoverFile.setId(IdWorker.createId());
                    bidWinHandoverFile.setHandoverInfoId(info.getId());
                    bidWinHandoverFile.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    bidWinHandoverFile.setCreateUserName(SecurityUtils.getUserName());
                    bidWinHandoverFile.setCreateTime(DateUtils.getNowDate());
                    insertList.add(bidWinHandoverFile);
                } else {
                    bidWinHandoverFile.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    bidWinHandoverFile.setUpdateTime(DateUtils.getNowDate());
                    updateList.add(bidWinHandoverFile);
                }
            }
            if (insertList.size() > 0) {
                xmslBidWinHandoverFileMapper.insertXmslBidWinHandoverFileList(insertList);
            }
            if (updateList.size() > 0) {
                xmslBidWinHandoverFileMapper.updateXmslBidWinHandoverFileList(updateList);
            }
        }
    }

    @Transactional
    public int insertXmslBidWinHandoverInfoList(List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoList) {
        for (XmslBidWinHandoverInfo xmslBidWinHandoverInfo : xmslBidWinHandoverInfoList) {
            xmslBidWinHandoverInfo.setId(IdWorker.createId());
            xmslBidWinHandoverInfo.setCreateUser(SecurityUtils.getUserName());
            xmslBidWinHandoverInfo.setCreateTime(DateUtils.getNowDate());
        }
        return xmslBidWinHandoverInfoMapper.insertXmslBidWinHandoverInfoList(xmslBidWinHandoverInfoList);
    }

    @Transactional
    public int updateXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo) {
        xmslBidWinHandoverInfo.setUpdateUser(SecurityUtils.getUserName());
        xmslBidWinHandoverInfo.setUpdateTime(DateUtils.getNowDate());
        return xmslBidWinHandoverInfoMapper.updateXmslBidWinHandoverInfo(xmslBidWinHandoverInfo);
    }

    @Transactional
    public int updateXmslBidWinHandoverInfoList(List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoList) {
        for (XmslBidWinHandoverInfo xmslBidWinHandoverInfo : xmslBidWinHandoverInfoList) {
            xmslBidWinHandoverInfo.setUpdateUser(SecurityUtils.getUserName());
            xmslBidWinHandoverInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslBidWinHandoverInfoMapper.updateXmslBidWinHandoverInfoList(xmslBidWinHandoverInfoList);
    }

    @Transactional
    public int deleteXmslBidWinHandoverInfo(XmslBidWinHandoverInfo xmslBidWinHandoverInfo) {
        xmslBidWinHandoverInfo.setUpdateUser(SecurityUtils.getUserName());
        xmslBidWinHandoverInfo.setUpdateTime(DateUtils.getNowDate());
        return xmslBidWinHandoverInfoMapper.deleteXmslBidWinHandoverInfo(xmslBidWinHandoverInfo);
    }

    @Transactional
    public int deleteXmslBidWinHandoverInfoByPks(List<Long> xmslBidWinHandoverInfoPkList) {
        return xmslBidWinHandoverInfoMapper.deleteXmslBidWinHandoverInfoByPks(xmslBidWinHandoverInfoPkList);
    }
}
