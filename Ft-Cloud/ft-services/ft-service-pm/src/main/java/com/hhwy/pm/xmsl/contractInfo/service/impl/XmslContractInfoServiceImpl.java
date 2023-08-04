package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.xmsl.contractInfo.domain.*;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.service.*;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IXmslContractListService xmslContractListService;
    @Autowired
    private  IXmslContractGeneralService xmslContractGeneralService;
    @Autowired
    private IXmslContractSpecialService xmslContractSpecialService;


    /**
     *  回显接口
     *
     * @param xmslContractInfo
     * @return
     */
    public XmslContractInfo getXmslContractInfo(XmslContractInfo xmslContractInfo) {
        //查询最大有效版本号，如果查不到，版本号赋默认值1.0
        BigDecimal maxVersion = commonMapper.selectMaxVersion("xmsl_contract_info");
        xmslContractInfo.setVersion(maxVersion);
        XmslContractInfo xmslContractInfo1 = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfo);
        if(xmslContractInfo1!=null){
            //1.1投保险种
            XmslContractInsure xmslContractInsure = new XmslContractInsure();
            xmslContractInsure.setMasterId(xmslContractInfo.getId());
            List<XmslContractInsure> xmslContractInsureList = xmslContractInsureService.getXmslContractInsureList(xmslContractInsure);
            if(CollectionUtils.isNotEmpty(xmslContractInsureList)){
                xmslContractInfo1.setXmslContractInsureList(xmslContractInsureList);
            }
            //1.2签订信息
            XmslContractSign xmslContractSign = new XmslContractSign();
            xmslContractSign.setMasterId(xmslContractInfo.getId());
            List<XmslContractSign> xmslContractSignList = xmslContractSignService.getXmslContractSignList(xmslContractSign);
            if(CollectionUtils.isNotEmpty(xmslContractSignList)){
                xmslContractInfo1.setXmslContractSignList(xmslContractSignList);
            }
            //1.3项目支付信息
            XmslContractPayinfo xmslContractPayinfo = new XmslContractPayinfo();
            xmslContractPayinfo.setMasterId(xmslContractInfo.getId());
            List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfo);
            if(CollectionUtils.isNotEmpty(xmslContractPayinfoList)){
                xmslContractInfo1.setXmslContractPayinfoList(xmslContractPayinfoList);
            }
            //最大有效版本号为1.0 说明不存在历史版本
            if(maxVersion.compareTo(new BigDecimal(1.0))==0){
                xmslContractInfo1.setIsShowRecord(0);
            }else {
                xmslContractInfo1.setIsShowRecord(1);
            }
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
        //删除旧数据
        XmslContractInfo info = new XmslContractInfo();
        info.setVersion(xmslContractInfo.getVersion());
        XmslContractInfo xmslContractInfo1 = xmslContractInfoMapper.getXmslContractInfo(info);
        if(xmslContractInfo1!=null){
            //1.1投保险种
            XmslContractInsure xmslContractInsure = new XmslContractInsure();
            xmslContractInsure.setMasterId(xmslContractInfo1.getId());
            xmslContractInsureService.deleteXmslContractInsure(xmslContractInsure);
            //1.2 签订信息
            XmslContractSign xmslContractSign = new XmslContractSign();
            xmslContractSign.setMasterId(xmslContractInfo1.getId());
            xmslContractSignService.deleteXmslContractSign(xmslContractSign);
            //1.3  项目支付信息
            XmslContractPayinfo xmslContractPayinfo = new XmslContractPayinfo();
            xmslContractPayinfo.setMasterId(xmslContractInfo1.getId());
            xmslContractPayinfoService.deleteXmslContractPayinfo(xmslContractPayinfo);
            xmslContractInfoMapper.deleteXmslContractInfo(info);
        }
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
        //删除对应子表数据
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
        //1.4 主合同清单
        XmslContractList xmslContractList = new XmslContractList();
        xmslContractList.setMasterId(xmslContractInfo.getId());
        xmslContractListService.deleteXmslContractList(xmslContractList);
        //1.5 通用条件
        XmslContractGeneral xmslContractGeneral = new XmslContractGeneral();
        xmslContractGeneral.setMasterId(xmslContractInfo.getId());
        xmslContractGeneralService.deleteXmslContractGeneral(xmslContractGeneral);
        //1.6 特殊条件
        XmslContractSpecial xmslContractSpecial = new XmslContractSpecial();
        xmslContractSpecial.setMasterId(xmslContractInfo.getId());
        xmslContractSpecialService.deleteXmslContractSpecial(xmslContractSpecial);
        //删除子表
        xmslContractInfo.setUpdateUser(SecurityUtils.getUserName());
        xmslContractInfo.setUpdateTime(DateUtils.getNowDate());
        return xmslContractInfoMapper.deleteXmslContractInfo(xmslContractInfo);
    }

    @Transactional
    public int deleteXmslContractInfoByPks(List<Long> xmslContractInfoPkList) {
        return xmslContractInfoMapper.deleteXmslContractInfoByPks(xmslContractInfoPkList);
    }

    @Override
    public int updateXmslContractInfo1(XmslContractInfo xmslContractInfo) {
        return xmslContractInfoMapper.updateXmslContractInfo(xmslContractInfo);
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
