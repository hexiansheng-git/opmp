package com.hhwy.pm.xmsl.bid.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverFile;
import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverInfo;
import com.hhwy.pm.xmsl.bid.mapper.XmslBidWinHandoverFileMapper;
import com.hhwy.pm.xmsl.bid.mapper.XmslBidWinHandoverInfoMapper;
import com.hhwy.pm.xmsl.bid.service.IXmslBidWinHandoverFileService;
import com.hhwy.pm.xmsl.bid.service.IXmslBidWinHandoverInfoService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-06 15:13:41
 * @remark 中标项目移交信息
 */
@Service
public class XmslBidWinHandoverInfoServiceImpl implements IXmslBidWinHandoverInfoService {

    @Autowired
    private XmslBidWinHandoverInfoMapper xmslBidWinHandoverInfoMapper;
    @Autowired
    private IXmslBidWinHandoverFileService xmslBidWinHandoverFileService;
    @Autowired
    private XmslBidWinHandoverFileMapper xmslBidWinHandoverFileMapper;
    @Autowired
    private SystemServiceApi systemServiceApi;

    public XmslBidWinHandoverInfo getXmslBidWinHandoverInfo() {
        XmslBidWinHandoverInfo xmslBidWinHandoverInfo = new XmslBidWinHandoverInfo();

        XmslBidWinHandoverInfo result = new XmslBidWinHandoverInfo();
        XmslBidWinHandoverInfo info = xmslBidWinHandoverInfoMapper.getXmslBidWinHandoverInfo(xmslBidWinHandoverInfo);

        XmslBidWinHandoverFile xmslBidWinHandoverFile = new XmslBidWinHandoverFile();

        List<XmslBidWinHandoverFile> fileList;

        // 若中标项目移交信息无数据，则移交文件获取初始化数据
        if (info == null || info.getId() == null) {
            fileList = this.getInitializeData();
        } else {
            BeanUtils.copyProperties(info, result);

            xmslBidWinHandoverFile.setHandoverInfoId(xmslBidWinHandoverInfo.getId());
            fileList =
                xmslBidWinHandoverFileService.getXmslBidWinHandoverFileList(xmslBidWinHandoverFile);
        }
        result.setXmslBidWinHandoverFileList(fileList);
        return result;
    }

    @Transactional
    public void save(XmslBidWinHandoverInfo xmslBidWinHandoverInfo) {
        XmslBidWinHandoverInfo infoParam = new XmslBidWinHandoverInfo();
        XmslBidWinHandoverInfo handoverInfo = xmslBidWinHandoverInfoMapper.getXmslBidWinHandoverInfo(infoParam);

        if (handoverInfo == null || handoverInfo.getId() == null) {
            xmslBidWinHandoverInfo.setId(IdWorker.createId());
            xmslBidWinHandoverInfo.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslBidWinHandoverInfo.setCreateUserName(SecurityUtils.getUserName());
            xmslBidWinHandoverInfo.setCreateTime(DateUtils.getNowDate());
            xmslBidWinHandoverInfoMapper.insertXmslBidWinHandoverInfo(xmslBidWinHandoverInfo);
        } else {
            xmslBidWinHandoverInfo.setId(handoverInfo.getId());
            xmslBidWinHandoverInfo.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslBidWinHandoverInfo.setUpdateTime(DateUtils.getNowDate());
            xmslBidWinHandoverInfoMapper.updateXmslBidWinHandoverInfo(xmslBidWinHandoverInfo);
        }

        // 清空数据库表中标项目移交文件数据
        XmslBidWinHandoverFile deleteParam = new XmslBidWinHandoverFile();
        deleteParam.setDelFlag("1");
        xmslBidWinHandoverFileMapper.updateXmslBidWinHandoverFile(deleteParam);

        int sort = 1;
        // 中标项目移交文件
        if (!CollectionUtils.isEmpty(xmslBidWinHandoverInfo.getXmslBidWinHandoverFileList())) {
            for (XmslBidWinHandoverFile bidWinHandoverFile : xmslBidWinHandoverInfo.getXmslBidWinHandoverFileList()) {
                bidWinHandoverFile.setId(IdWorker.createId());
                bidWinHandoverFile.setHandoverInfoId(xmslBidWinHandoverInfo.getId());
                bidWinHandoverFile.setSort(sort++);
                bidWinHandoverFile.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                bidWinHandoverFile.setCreateUserName(SecurityUtils.getUserName());
                bidWinHandoverFile.setCreateTime(DateUtils.getNowDate());
            }
            xmslBidWinHandoverFileMapper
                .insertXmslBidWinHandoverFileList(xmslBidWinHandoverInfo.getXmslBidWinHandoverFileList());
        }
    }

    /**
     * 获取初始化数据
     *
     * @return
     */
    public List<XmslBidWinHandoverFile> getInitializeData() {
        List<XmslBidWinHandoverFile> fileList = new ArrayList<>();

        // 移交资料初始化数据字典
        AjaxResult result = systemServiceApi.dictType(DictType.HANDOVER_FILE_INIT_DATA);
        List<Map<String, Object>> dictDataList = (List<Map<String, Object>>) result.get("data");

        for (Map<String, Object> map : dictDataList) {
            String dictLabel = (String) map.get("dictLabel");
            XmslBidWinHandoverFile xmslBidWinHandoverFile = new XmslBidWinHandoverFile();
            xmslBidWinHandoverFile.setFileName(dictLabel);
            xmslBidWinHandoverFile.setImportance("2");
            fileList.add(xmslBidWinHandoverFile);
        }

        return fileList;
    }
}
