package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.common.domain.FtActBusiness;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.xmsl.contractInfo.domain.*;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.service.*;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
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
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;


    /***
     * 功能描述: 拉取项目信息，入库合同表
     * 作者: fushudong
     * 时间: 2023/8/18
     */
    private void getProjectInfo() {

        ProjectBasicInfo projectInfo = projectBasicInfoService.projectInfo();
        XmslContractInfo contractInfo = new XmslContractInfo();
        contractInfo.setProjectCode(projectInfo.getProjectCode());
        contractInfo.setProjectNameYw(projectInfo.getProjectNameForeignLang());
        contractInfo.setProjectName(projectInfo.getProjectName());
        contractInfo.setWinDate(projectInfo.getWinTheBiddingDate());
        contractInfo.setProjectType(projectInfo.getProjectType());
        contractInfo.setProjectCategory(projectInfo.getProjectType());
        //承包方式字段
        contractInfo.setContractingMethod(projectInfo.getContractingMethod());
        //业务领域及产品
        contractInfo.setBusinessAreasAndProducts(projectInfo.getBusinessAreasAndProducts());
        //资金来源
        contractInfo.setCapitalSource(projectInfo.getCapitalSource());
        //项目所在地
        contractInfo.setProjectLocation(projectInfo.getProjectLocation());
        contractInfo.setSubsidiaryOrgan(projectInfo.getSubsidiaryOrgan());
        contractInfo.setProjectManager(projectInfo.getProjectManager());
        contractInfo.setInternalContactWay(projectInfo.getInternalContactWay());
        contractInfo.setForeignContactWay(projectInfo.getForeignContactWay());
        contractInfo.setWinTheBiddingUnit(projectInfo.getWinTheBiddingUnit());
        //业主单位
        contractInfo.setProprietorUnit(projectInfo.getProprietorUnit());
        //设计单位
        contractInfo.setDesignUnit(projectInfo.getDesignUnit());
        //监理单位
        contractInfo.setSupervisorUnit(projectInfo.getSupervisorUnit());
        //详细地址
        contractInfo.setDetailedAddress(projectInfo.getDetailedAddress());
        //项目规模
        contractInfo.setProjectScale(projectInfo.getProjectScale());
        contractInfo.setContractPrice(projectInfo.getContractPrice());
        //编制日期
        contractInfo.setOperateTime(projectInfo.getEstablishDate());
        //编制人
        contractInfo.setOperateUserName(projectInfo.getEstablishPersonnel());

        contractInfo.setVersion(BigDecimal.valueOf(1.0));
        contractInfo.setId(IdWorker.createId());
        contractInfo.setCreateUser(SecurityUtils.getUserName());
        contractInfo.setCreateTime(DateUtils.getNowDate());
        xmslContractInfoMapper.insertXmslContractInfo(contractInfo);

    }


    /**
     * 获取最新有效版本的合同信息
     * @return
     */
    @Override
    public XmslContractInfo getValidMaxVersionContractInfo() {
        return xmslContractInfoMapper.getValidMaxVersionContractInfo();
    }

    /**
     *  回显接口
     *
     * @param xmslContractInfo
     * @return
     */
    public XmslContractInfo getXmslContractInfo(XmslContractInfo xmslContractInfoParam) {
        BigDecimal maxVersion = commonMapper.selectMaxVersion("xmsl_contract_info");
        if (xmslContractInfoParam.getId() == null){
            //查询最大有效版本号，如果查不到，版本号赋默认值1.0
            xmslContractInfoParam.setVersion(maxVersion);
        }
        XmslContractInfo xmslContractInfo = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfoParam);

        //如果为空,说明第一次进入，从项目信息中拉取项目数据
        if (xmslContractInfo == null) {
            getProjectInfo();
            xmslContractInfo = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfoParam);
        }
        //查询子表数据
        if(xmslContractInfo!=null){
            getSonTable(xmslContractInfo, maxVersion);
        }
        List<XmslContractInfo> historyList = this.getXmslContractInfoList(new XmslContractInfo());
        if (CollectionUtils.isNotEmpty(historyList) && historyList.size() > 1){
            xmslContractInfo.setIsShowRecord(1);
        }else {
            xmslContractInfo.setIsShowRecord(0);
        }

        return xmslContractInfo;
    }



    /***
     * 功能描述: 调整功能
     * 逻辑：只有有效版本才可以调整；从历史记录界面选择有效版本点击调整，
     * 后台判断：如果当前版本是数据库中最大版本则新增一条记录，内容与当前版本一致，只有版本号+1，返回前端；否则将大于当前版本的最小版本记录返回前端
     * 作者: fushudong
     * 时间: 2023/8/29
     */
    @Override
    public XmslContractInfo adjustXmslContractInfo(XmslContractInfo xmslContractInfoParam) {
        //获取当前版本数据
        XmslContractInfo xmslContractInfo = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfoParam);
        getSonTable(xmslContractInfo, xmslContractInfo.getVersion());
        //查询当前版本是否是数据库中最大版本
        XmslContractInfo bean = xmslContractInfoMapper.getMaxVersionRecordByVersion(xmslContractInfoParam);
        if (bean == null){
            //当前版本是数据库中最大版本，新增一条数据，内容与当前版本一致，只有版本号+1
            BigDecimal version = xmslContractInfo.getVersion();
            version = version.add(new BigDecimal("1.0"));
            xmslContractInfo.setVersion(version);
            xmslContractInfo.setValid("0");
            xmslContractInfo.setTaskStatus("");
            insertXmslContractInfo(xmslContractInfo);
            XmslContractInfo param = new XmslContractInfo();
            param.setVersion(version);
            XmslContractInfo result = xmslContractInfoMapper.getXmslContractInfo(param);
            getSonTable(result, result.getVersion());
            return result;
        }else {
            //当前版本不是最大版本，将大于当前版本的最小版本记录返回前端
            XmslContractInfo param = new XmslContractInfo();
            param.setVersion(bean.getVersion());
            XmslContractInfo result = xmslContractInfoMapper.getXmslContractInfo(param);
            getSonTable(result, bean.getVersion());
            return result;
        }
    }

    public List<XmslContractInfo> getXmslContractInfoList(XmslContractInfo xmslContractInfo) {
        List<XmslContractInfo> historyList =xmslContractInfoMapper.getXmslContractInfoList(xmslContractInfo);
        if (CollectionUtils.isNotEmpty(historyList) && historyList.size() > 1) {
            for (XmslContractInfo contractInfo : historyList) {
                FtActBusiness flowInfo = FlowInfoSearchUtil.getFlowInfo(FlowEnum.XMSL_CONTRACT.getTableName(), String.valueOf(contractInfo.getId()));
                contractInfo.setProcessTaskMan(flowInfo.getAssignee());
                contractInfo.setCreateUserName(flowInfo.getName());
                contractInfo.setCreateUser(flowInfo.getCreateUser());
                contractInfo.setCreateTime(flowInfo.getCreateTime());
            }
        }
        return historyList;
    }

    /**
     *  主合同信息新增
     *
     * @param xmslContractInfo
     * @return
     */
    @Transactional
    public Long insertXmslContractInfo(XmslContractInfo xmslContractInfo) {
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
        xmslContractInfoMapper.insertXmslContractInfo(xmslContractInfo);
        return xmslContractInfo.getId();
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
        xmslContractInfo.setValid("0");
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

    private void getSonTable(XmslContractInfo xmslContractInfo, BigDecimal maxVersion) {
        //1.1投保险种
        XmslContractInsure xmslContractInsure = new XmslContractInsure();
        xmslContractInsure.setMasterId(xmslContractInfo.getId());
        List<XmslContractInsure> xmslContractInsureList = xmslContractInsureService.getXmslContractInsureList(xmslContractInsure);
        if(CollectionUtils.isNotEmpty(xmslContractInsureList)){
            xmslContractInfo.setXmslContractInsureList(xmslContractInsureList);
        }
        //1.2签订信息
        XmslContractSign xmslContractSign = new XmslContractSign();
        xmslContractSign.setMasterId(xmslContractInfo.getId());
        List<XmslContractSign> xmslContractSignList = xmslContractSignService.getXmslContractSignList(xmslContractSign);
        if(CollectionUtils.isNotEmpty(xmslContractSignList)){
            xmslContractInfo.setXmslContractSignList(xmslContractSignList);
        }
        //1.3项目支付信息
        XmslContractPayinfo xmslContractPayinfo = new XmslContractPayinfo();
        xmslContractPayinfo.setMasterId(xmslContractInfo.getId());
        List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfo);
        if(CollectionUtils.isNotEmpty(xmslContractPayinfoList)){
            xmslContractInfo.setXmslContractPayinfoList(xmslContractPayinfoList);
        }
        //最大有效版本号为1.0 说明不存在历史版本
        if(maxVersion.compareTo(new BigDecimal(1.0))==0){
            xmslContractInfo.setIsShowRecord(0);
        }else {
            xmslContractInfo.setIsShowRecord(1);
        }

        //查询主合同清单 金额
        XmslContractList xmslContractList = new XmslContractList();
        xmslContractList.setListType("1");
        XmslContractList resultPrice = xmslContractListService.getContractPriceByListtype(xmslContractList);
        if (resultPrice != null) {
            //合同不含税金额   “主合同清单”页签变更后清单_不含税金额，末级合计
            // todo 合同变更功能未做，暂时用“中标合同清单”中的金额
            xmslContractInfo.setExcludingAmout(resultPrice.getWinNum());
            //有效合同金额  主合同清单，清单类型是普通清单的所有末级节点的含税金额的合计
            // todo 合同变更功能未做，暂时用“中标合同清单”中的金额
            xmslContractInfo.setEffectiveAmout(resultPrice.getWinUnitPrice());
        }
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
