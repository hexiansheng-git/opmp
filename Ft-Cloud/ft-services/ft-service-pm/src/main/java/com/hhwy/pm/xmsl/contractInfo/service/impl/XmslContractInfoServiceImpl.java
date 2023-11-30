package com.hhwy.pm.xmsl.contractInfo.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.enums.FlowEnum;
import com.hhwy.excel.Util;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.xmsl.contractInfo.domain.*;
import com.hhwy.pm.xmsl.contractInfo.mapper.XmslContractInfoMapper;
import com.hhwy.pm.xmsl.contractInfo.service.*;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.idworker.IdWorker;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ldd
 * @date 2023-07-10 13:57:39
 * @remark
 */
@Service
@Log4j2
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
    @Autowired
    private SystemApiService systemApiService;
    @Autowired
    private SystemServiceApi systemServiceApi;


    /***
     * 功能描述: 拉取项目信息，入库合同表
     * 作者: fushudong
     * 时间: 2023/8/18
     */
    private synchronized XmslContractInfo getProjectInfo(ProjectBasicInfo projectInfo, XmslContractInfo xmslContractInfoParam) {
        //双重判断，防止重复进入
        XmslContractInfo xmslContractInfo = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfoParam);
        if (xmslContractInfo != null) {
            return xmslContractInfo;
        }
        XmslContractInfo contractInfo = new XmslContractInfo();
        projectBeanToContract(projectInfo, contractInfo, "1");

        contractInfo.setVersion(BigDecimal.valueOf(1.0));
        contractInfo.setId(IdWorker.createId());
        contractInfo.setCreateUser(SecurityUtils.getUserName());
        contractInfo.setCreateTime(DateUtils.getNowDate());
        xmslContractInfoMapper.insertXmslContractInfo(contractInfo);
        return xmslContractInfoMapper.getXmslContractInfo(xmslContractInfoParam);
    }

    /**
     *
     * @param projectInfo
     * @param contractInfo
     * @param type  : 1,拉取   2：总部版同步
     */
    //项目信息写入合同实体
    private void projectBeanToContract(ProjectBasicInfo projectInfo, XmslContractInfo contractInfo, String type) {
        contractInfo.setProjectCode(projectInfo.getProjectCode());
        contractInfo.setProjectNameYw(projectInfo.getProjectNameForeignLang());
        contractInfo.setProjectName(projectInfo.getProjectName());
        contractInfo.setWinDate(projectInfo.getWinTheBiddingDate());
        contractInfo.setProjectType(projectInfo.getProjectType());
        contractInfo.setProjectCategory(projectInfo.getProjectCategory());
        //承包方式字段
        contractInfo.setContractingMethod(projectInfo.getContractingMethod());
        ////业务领域及产品 编号
        String businessAreasAndProducts = projectInfo.getBusinessAreasAndProducts();
        contractInfo.setPtVar2(businessAreasAndProducts);
        //业务领域及产品
        if (StringUtils.isNotEmpty(businessAreasAndProducts)){
            Util util = new Util();
            String[] split = businessAreasAndProducts.split(",");
            StringBuilder sb = new StringBuilder();
            for (String s : split) {
                String business_areas_and_products = util.resolveDict("business_areas_and_products", s);
                sb.append(",").append(business_areas_and_products);
            }
            contractInfo.setBusinessAreasAndProducts(sb.toString().substring(1));
        }

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
//        contractInfo.setContractPrice(projectInfo.getContractPrice());
        //编制日期
        contractInfo.setOperateTime(DateUtils.getNowDate());
        //编制人
        if("1".equals(type)){
            contractInfo.setOperateUserId(String.valueOf(SecurityUtils.getUserId()));
            contractInfo.setOperateUserName(SecurityUtils.getSysUser().getNickName());
        }
    }


    /**
     * 获取最新有效版本的合同信息
     * @return
     */
    @Override
    public XmslContractInfo getValidMaxVersionContractInfo() {
        XmslContractInfo contractInfo = xmslContractInfoMapper.getValidMaxVersionContractInfo();
        if(contractInfo != null){
            //有效合同金额对美元转换
            this.setEffectiveAmountDollar(contractInfo);
        }else {
            contractInfo = new XmslContractInfo();
        }
        return contractInfo;
    }

    /**
     * 设置有效合同金额-美元
     * @param contractInfo
     */
    @Override
    public void setEffectiveAmountDollar(XmslContractInfo contractInfo){
        String listCurrencyCode = contractInfo.getListCurrencyCode();
        if("USD".equals(listCurrencyCode)){
            return;
        }
        /*汇率*/
        BigDecimal exchangeRate = null;
        //项目支付信息数据
        XmslContractPayinfo xmslContractPayinfo = new XmslContractPayinfo();
        xmslContractPayinfo.setMasterId(contractInfo.getId());
        List<XmslContractPayinfo> payinfoList = xmslContractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfo);
        if(!CollectionUtils.isEmpty(payinfoList)) {
            XmslContractPayinfo USD = payinfoList.stream().filter(o -> listCurrencyCode.equals(o.getCurrencyCode())).findFirst().orElse(null);
            if(USD != null){
                String rateType = USD.getRateType();
                if("1".equals(rateType)){
                    exchangeRate = BigDecimal.valueOf(Double.parseDouble(USD.getObversionRate()));
                }
            }
        }

        if(exchangeRate == null){
            Date nowDate = DateUtils.getNowDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String nowStr = sdf.format(nowDate);

            PeriodInfo periodInfo = new PeriodInfo();
            periodInfo.setCurrencyCode(listCurrencyCode);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            int year = calendar.get(Calendar.YEAR);
            periodInfo.setQueryDate(String.valueOf(year));
            AjaxResult ajaxResult = systemServiceApi.selectPeriodByYear(periodInfo);
            if(ajaxResult.get("data") != null) {
                List<Map> data = (List<Map>) ajaxResult.get("data");
                Map periodMap = data.stream().filter(map -> nowStr.equals(map.get("periodCode"))).findFirst().orElse(null);
                if(periodMap != null && periodMap.get("rate") != null) {
                    exchangeRate = BigDecimal.valueOf((double) periodMap.get("rate"));
                }
            }
        }

        BigDecimal effectiveAmout = contractInfo.getEffectiveAmout();
        BigDecimal effectiveAmoutDollar = BigDecimal.ZERO;
        if(effectiveAmout != null && exchangeRate != null && exchangeRate.compareTo(BigDecimal.ZERO) != 0){
            effectiveAmoutDollar = effectiveAmout.divide(exchangeRate,4, RoundingMode.HALF_UP);
        }
        contractInfo.setEffectiveAmountDollar(effectiveAmoutDollar);
    }

    /**
     *  回显接口
     *
     * @return
     */
    public XmslContractInfo getXmslContractInfo(XmslContractInfo xmslContractInfoParam) {
        BigDecimal maxVersion = commonMapper.selectMaxVersion("xmsl_contract_info");
        if (xmslContractInfoParam.getId() == null){
            //查询最大有效版本号，如果查不到，版本号赋默认值1.0
            xmslContractInfoParam.setVersion(maxVersion);
        }
        //查询最新合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoMapper.getXmslContractInfo(xmslContractInfoParam);
        ProjectBasicInfo projectInfo = new ProjectBasicInfo();
        if (xmslContractInfo == null) {
            //如果为空,说明第一次进入，执行项目信息拉取
            projectInfo = projectBasicInfoService.projectInfo();
            if (projectInfo == null) {
                log.info("项目信息表无数据");
                return new XmslContractInfo();
            }
            //将项目信息写入合同表
            xmslContractInfo = getProjectInfo(projectInfo, xmslContractInfoParam);
        }
        //查询子表数据
        this.getSonTable(xmslContractInfo, maxVersion);
        //查询历史记录，根据记录数给showRecord字段赋值
        List<XmslContractInfo> historyList = this.getXmslContractInfoList(new XmslContractInfo());
        if (CollectionUtils.isNotEmpty(historyList) && historyList.size() > 1){
            xmslContractInfo.setIsShowRecord(1);
        }else {
            xmslContractInfo.setIsShowRecord(0);
        }
        List<XmslContractInfo> list = new ArrayList<>();
        list.add(xmslContractInfo);
        FlowInfoSearchUtil.getFlowInfo(list, FlowEnum.XMSL_CONTRACT);
//        this.getDict(xmslContractInfo);
        return xmslContractInfo;
    }

    private void getDict(XmslContractInfo xmslContractInfo){
        Util util = new Util();
        //不可选，只需返回label
//        String projectLocation = xmslContractInfo.getProjectLocation();
//        String projectType = xmslContractInfo.getProjectType();
//        String projectCategory = xmslContractInfo.getProjectCategory();
//        String contractingMethod = xmslContractInfo.getContractingMethod();
        String businessAreasAndProducts = xmslContractInfo.getBusinessAreasAndProducts();
//        String capitalSource = xmslContractInfo.getCapitalSource();
//        String listCurrencyCode = xmslContractInfo.getListCurrencyCode();
//
//        String project_type = util.resolveDict("project_type", projectType);
//        xmslContractInfo.setProjectType(project_type);
//        String project_category = util.resolveDict("project_category", projectCategory);
//        xmslContractInfo.setProjectCategory(project_category);
//        String constract_form = util.resolveDict("constract_form", contractingMethod);
//        xmslContractInfo.setContractingMethod(constract_form);
        String[] split = businessAreasAndProducts.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            String business_areas_and_products = util.resolveDict("business_areas_and_products", s);
            sb.append(",").append(business_areas_and_products);
        }
        xmslContractInfo.setBusinessAreasAndProducts(sb.toString().substring(1));
//        String capital_source = util.resolveDict("capital_source", capitalSource);
//        xmslContractInfo.setCapitalSource(capital_source);
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add(listCurrencyCode);
//        Map<String, String> currencyNamesByCodes = CommonServiceUtil.getCurrencyNamesByCodes(strings);
//        xmslContractInfo.setListCurrencyName(currencyNamesByCodes.get(listCurrencyCode));
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
        if (xmslContractInfo == null)
            return new XmslContractInfo();
        //查询当前版本是否是数据库中最大版本
        XmslContractInfo bean = xmslContractInfoMapper.getMaxVersionRecordByVersion(xmslContractInfoParam);
        if (bean == null){
            //返回当前有效数据，并将版本号加1
            XmslContractInfo result = xmslContractInfo;
            result.setVersion(result.getVersion().add(new BigDecimal("1.0")));
            result.setValid("0");
            result.setTaskStatus("");
            getSonTable(result, result.getVersion());
            //旧数据ID，用于子表获取数据
            result.setPtVar1(String.valueOf(result.getId()));
            result.setId(null);
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
        FlowInfoSearchUtil.getFlowInfo(historyList,FlowEnum.XMSL_CONTRACT);
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
        if (xmslContractInfo.getId() == null){
            xmslContractInfo.setId(IdWorker.createId());
        }
        this.addSonTable(xmslContractInfo);
        //编制日期
        xmslContractInfo.setOperateTime(DateUtils.getNowDate());
        //编制人
        xmslContractInfo.setOperateUserId(String.valueOf(SecurityUtils.getUserId()));
        xmslContractInfo.setOperateUserName(SecurityUtils.getSysUser().getNickName());
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
        //第二步 重新添加子表
        this.addSonTable(xmslContractInfo);
        //第三步 修改主表
        xmslContractInfo.setUpdateUser(SecurityUtils.getUserName());
        xmslContractInfo.setUpdateTime(DateUtils.getNowDate());
//        xmslContractInfo.setValid("0");
        return xmslContractInfoMapper.updateXmslContractInfo(xmslContractInfo);
    }

    /***
     * 功能描述:  项目信息修改同步
     * @param projectInfo
     * @return int
     * 作者: fushudong
     * 时间: 2023/10/9
     */
    public void updateProjectInfo(ProjectBasicInfo projectInfo) {
        //获取最新有效版本的合同信息
        XmslContractInfo latestContractInfo = xmslContractInfoMapper.getValidMaxVersionContractInfo();
        if (latestContractInfo == null) return;
        BigDecimal version = latestContractInfo.getVersion();
        //查询当前版本是否是数据库中最大版本
        XmslContractInfo xmslContractInfo1 = new XmslContractInfo();
        xmslContractInfo1.setVersion(version);
        XmslContractInfo contractInfo1 = xmslContractInfoMapper.getMaxVersionRecordByVersion(xmslContractInfo1);
        ArrayList<XmslContractInfo> objects = new ArrayList<>();
        if (contractInfo1 != null){
            objects.add(contractInfo1);
        }
        objects.add(latestContractInfo);
        objects.forEach(contractInfo -> {
            this.projectBeanToContract(projectInfo, contractInfo, "2");
            contractInfo.setUpdateTime(DateUtils.getNowDate());
            xmslContractInfoMapper.updateXmslContractInfo(contractInfo);
        });
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
        List<Long> longs = Arrays.asList(xmslContractInfo.getId());
        return xmslContractInfoMapper.deleteXmslContractInfoByPks(longs);
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
        xmslContractList.setMasterId(xmslContractInfo.getId());
        XmslContractList resultPrice = xmslContractListService.getContractPriceByListtype(xmslContractList);
        if (resultPrice != null) {
            //合同不含税金额   “主合同清单”页签变更后清单_不含税金额，末级合计
            // todo 合同变更功能未做，暂时用“中标合同清单”中的金额
//            xmslContractInfo.setExcludingAmout(resultPrice.getWinNum());
            //有效合同金额  主合同清单，清单类型是普通清单的所有末级节点的含税金额的合计
            // todo 合同变更功能未做，暂时用“中标合同清单”中的金额
            xmslContractInfo.setEffectiveAmout(resultPrice.getWinAmount());
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

    @Override
    @Transactional
    public void updateAllToInvalid(Long id) {
        xmslContractInfoMapper.updateAllToInvalid();
        //给合同清单打标记（已生效的合同清单不能删除）
        xmslContractListService.updateToRemoveDisable(id);
    }

    /***
     * 功能描述: 发起流程时，回填发布人和发布时间
     */
    @Override
    public void updateIssueNameAndDate(Long id) {
        XmslContractInfo xmslContractInfo = new XmslContractInfo();
        xmslContractInfo.setId(id);
        xmslContractInfo.setIssueDate(DateUtils.getNowDate());
        xmslContractInfo.setIssuePersonName(SecurityUtils.getSysUser().getNickName());
        xmslContractInfo.setIssuePersonId(String.valueOf(SecurityUtils.getUserId()));
        xmslContractInfoMapper.updateXmslContractInfo(xmslContractInfo);
    }

    @Override
    public List<SysDictData> selectDict() {
        //下拉框，可选
        List<SysDictData> contract_attribute = systemApiService.selectDictDataByType("contract_attribute");
        List<SysDictData> brand_name = systemApiService.selectDictDataByType("brand_name");
        List<SysDictData> contract_type = systemApiService.selectDictDataByType("contract_type");
        List<SysDictData> time_zone = systemApiService.selectDictDataByType("time_zone");
        time_zone.addAll(contract_attribute);
        time_zone.addAll(brand_name);
        time_zone.addAll(contract_type);
        return time_zone;
    }
}
