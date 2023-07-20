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
import org.springframework.util.CollectionUtils;

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

    public XmslBidWinHandoverInfo getXmslBidWinHandoverInfo() {
        XmslBidWinHandoverInfo xmslBidWinHandoverInfo = new XmslBidWinHandoverInfo();
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

        // 清空数据库表中标项目移交文件数据
        XmslBidWinHandoverFile deleteParam = new XmslBidWinHandoverFile();
        deleteParam.setDelFlag("1");
        xmslBidWinHandoverFileMapper.updateXmslBidWinHandoverFile(deleteParam);

        // 中标项目移交文件
        if (!CollectionUtils.isEmpty(xmslBidWinHandoverInfo.getXmslBidWinHandoverFileList())) {
            List<XmslBidWinHandoverFile> insertList = new ArrayList<>();
            for (XmslBidWinHandoverFile bidWinHandoverFile : xmslBidWinHandoverInfo.getXmslBidWinHandoverFileList()) {
                bidWinHandoverFile.setId(IdWorker.createId());
                bidWinHandoverFile.setHandoverInfoId(info.getId());
                bidWinHandoverFile.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                bidWinHandoverFile.setCreateUserName(SecurityUtils.getUserName());
                bidWinHandoverFile.setCreateTime(DateUtils.getNowDate());
                insertList.add(bidWinHandoverFile);
            }
            if (insertList.size() > 0) {
                xmslBidWinHandoverFileMapper.insertXmslBidWinHandoverFileList(insertList);
            }
        }
    }
}
