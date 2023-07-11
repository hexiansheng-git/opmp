package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInsure;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSign;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInsureService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractPayinfoService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractSignService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
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
    @Autowired
    private IXmslContractInsureService xmslContractInsureService;
    @Autowired
    private IXmslContractPayinfoService xmslContractPayinfoService;
    @Autowired
    private IXmslContractSignService xmslContractSignService;


    /**
     *  回显接口
     *
     * @param xmslContractInfo
     * @return
     */
    public XmslContractInfo getXmslContractInfo(XmslContractInfo xmslContractInfo) {
        XmslContractInfo xmslContractInfo1 = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);
        //1.1投保险种
        XmslContractInsure xmslContractInsure = new XmslContractInsure();
        xmslContractInsure.setMasterId(xmslContractInfo.getId());
        List<XmslContractInsure> xmslContractInsureList = xmslContractInsureService.getXmslContractInsureList(xmslContractInsure);
        if(CollectionUtils.isNotEmpty(xmslContractInsureList)){
            xmslContractInfo1.setXmslContractInsureList(xmslContractInsureList);
        }
        //1.2 签订信息
        XmslContractSign xmslContractSign = new XmslContractSign();
        xmslContractSign.setMasterId(xmslContractInfo.getId());
        List<XmslContractSign> xmslContractSignList = xmslContractSignService.getXmslContractSignList(xmslContractSign);
        if(CollectionUtils.isNotEmpty(xmslContractSignList)){
            xmslContractInfo1.setXmslContractSignList(xmslContractSignList);
        }
        //1.3  项目支付信息
        XmslContractPayinfo xmslContractPayinfo = new XmslContractPayinfo();
        xmslContractPayinfo.setMasterId(xmslContractInfo.getId());
        List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfo);
        if(CollectionUtils.isNotEmpty(xmslContractPayinfoList)){
            xmslContractInfo1.setXmslContractPayinfoList(xmslContractPayinfoList);
        }
        return xmslContractInfo1;
    }

    public List<XmslContractInfo> getXmslContractInfoList(XmslContractInfo xmslContractInfo) {
        return xmslContractInfoMapper.getXmslContractInfoList(xmslContractInfo);
    }

    /**
     *  主合同信息新增
     *
     * @param xmslContractInfo
     * @return
     */
    @Transactional
    public int insertXmslContractInfo(XmslContractInfo xmslContractInfo) {
        xmslContractInfo.setId(IdWorker.createId());
        this.addSonTable(xmslContractInfo);
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
        //第一步 根据外键清空子表
        //1.1投保险种
        XmslContractInsure xmslContractInsure = new XmslContractInsure();
        xmslContractInsure.setMasterId(xmslContractInfo.getId());
        xmslContractInsureService.deleteXmslContractInsure(xmslContractInsure);
        //1.2 签订信息
        XmslContractSign xmslContractSign = new XmslContractSign();
        xmslContractSign.setMasterId(xmslContractInfo.getId());
        xmslContractSignService.deleteXmslContractSign(xmslContractSign);
        //1.3  项目支付信息
        XmslContractPayinfo xmslContractPayinfo = new XmslContractPayinfo();
        xmslContractPayinfo.setMasterId(xmslContractInfo.getId());
        xmslContractPayinfoService.deleteXmslContractPayinfo(xmslContractPayinfo);
        //第二步 从新添加子表
        this.addSonTable(xmslContractInfo);
        //第三步 修改主表
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


    private void addSonTable(XmslContractInfo xmslContractInfo){
        //投保险种
        List<XmslContractInsure> xmslContractInsureList = xmslContractInfo.getXmslContractInsureList();
        if(CollectionUtils.isNotEmpty(xmslContractInsureList)){
            xmslContractInsureService.insertXmslContractInsureList(xmslContractInsureList,xmslContractInfo);
        }
        //签订信息
        List<XmslContractSign> xmslContractSignList = xmslContractInfo.getXmslContractSignList();
        if(CollectionUtils.isNotEmpty(xmslContractSignList)){
            xmslContractSignService.insertXmslContractSignList(xmslContractSignList,xmslContractInfo);
        }
        //项目支付信息
        List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractInfo.getXmslContractPayinfoList();
        if(CollectionUtils.isNotEmpty(xmslContractPayinfoList)){
            xmslContractPayinfoService.insertXmslContractPayinfoList(xmslContractPayinfoList,xmslContractInfo);
        }
    }
}
