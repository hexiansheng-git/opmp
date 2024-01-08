package com.hhwy.pm.xmsl.project.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchSimilarProjectSchemeService;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.*;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectInfoWithOther;
import com.hhwy.pm.xmsl.project.mapper.*;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.tree.ListTreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-07-03 09:48:24
 * @remark 项目基本信息
 */
@Service
public class XmslProjectBasicInfoServiceImpl implements IXmslProjectBasicInfoService {

    @Autowired
    private XmslProjectBasicInfoMapper xmslProjectBasicInfoMapper;

    @Autowired
    private XmslProjectBridgeStructureServiceImpl projectBridgeStructureService;

    @Autowired
    private XmslProjectCulvertStructureServiceImpl projectCulvertStructureService;

    @Autowired
    private XmslProjectEngineeringAmountServiceImpl projectEngineeringAmountService;

    @Autowired
    private XmslProjectMaterialsAmountServiceImpl projectMaterialsAmountService;

    @Autowired
    private XmslProjectBridgeStructureMapper xmslProjectBridgeStructureMapper;

    @Autowired
    private XmslProjectCulvertStructureMapper xmslProjectCulvertStructureMapper;

    @Autowired
    private XmslProjectEngineeringAmountMapper xmslProjectEngineeringAmountMapper;

    @Autowired
    private XmslProjectMaterialsAmountMapper xmslProjectMaterialsAmountMapper;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private IQqchSimilarProjectSchemeService qqchSimilarProjectSchemeService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    /**
     * 根据id获取项目基本信息
     * @param id
     * @return
     */
    @Override
    public XmslProjectBasicInfo getProjectBasicInfoById(Long id) {
        //项目基本信息
        XmslProjectBasicInfo xmslProjectBasicInfo = new XmslProjectBasicInfo();
        xmslProjectBasicInfo.setId(id);
        xmslProjectBasicInfo = xmslProjectBasicInfoMapper.getProjectBasicInfo(xmslProjectBasicInfo);

        if(xmslProjectBasicInfo == null){
            throw new RuntimeException("获取项目信息失败！");
        }

        this.setProjectSublistInfo(xmslProjectBasicInfo);

        return xmslProjectBasicInfo;
    }

    /**
     * 设置子表信息
     * @param xmslProjectBasicInfo
     */
    public void setProjectSublistInfo(XmslProjectBasicInfo xmslProjectBasicInfo){
        Long id = xmslProjectBasicInfo.getId();
        //主要桥梁结构形式
        XmslProjectBridgeStructure xmslProjectBridgeStructure = new XmslProjectBridgeStructure();
        xmslProjectBridgeStructure.setProjectBasicInfoId(id);
        List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList = xmslProjectBridgeStructureMapper.getProjectBridgeStructureList(xmslProjectBridgeStructure);
        xmslProjectBasicInfo.setXmslProjectBridgeStructureList(xmslProjectBridgeStructureList);

        //主要涵洞结构形式
        XmslProjectCulvertStructure xmslProjectCulvertStructure = new XmslProjectCulvertStructure();
        xmslProjectCulvertStructure.setProjectBasicInfoId(id);
        List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList = xmslProjectCulvertStructureMapper.getProjectCulvertStructureList(xmslProjectCulvertStructure);
        xmslProjectBasicInfo.setXmslProjectCulvertStructureList(xmslProjectCulvertStructureList);

        //主要工程数量
        XmslProjectEngineeringAmount xmslProjectEngineeringAmount = new XmslProjectEngineeringAmount();
        xmslProjectEngineeringAmount.setProjectBasicInfoId(id);
        List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList = xmslProjectEngineeringAmountMapper.getProjectEngineeringAmountList(xmslProjectEngineeringAmount);
        List<XmslProjectEngineeringAmount> treeList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(xmslProjectEngineeringAmountList)){
            treeList = ListTreeUtil.formatTree(xmslProjectEngineeringAmountList, o -> o.getPid() == null,(r, n) -> r.getId().equals(n.getPid()),XmslProjectEngineeringAmount::getChildren,XmslProjectEngineeringAmount::setChildren);
        }
        xmslProjectBasicInfo.setXmslProjectEngineeringAmountList(treeList);

        //主要材料数量
        XmslProjectMaterialsAmount xmslProjectMaterialsAmount = new XmslProjectMaterialsAmount();
        xmslProjectMaterialsAmount.setProjectBasicInfoId(id);
        List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList = xmslProjectMaterialsAmountMapper.getProjectMaterialsAmountList(xmslProjectMaterialsAmount);
        xmslProjectBasicInfo.setXmslProjectMaterialsAmountList(xmslProjectMaterialsAmountList);
    }

    /**
     * 获取项目集合
     * @param xmslProjectBasicInfo
     * @return
     */
    public List<XmslProjectBasicInfo> getProjectBasicInfoList(XmslProjectBasicInfo xmslProjectBasicInfo) {
        return xmslProjectBasicInfoMapper.getProjectBasicInfoList(xmslProjectBasicInfo);
    }

    @Override
    @Transactional
    public void insertProjectInvokeProject(XmslProjectBasicInfo projectBasicInfo) {
        this.setSublist(projectBasicInfo);
        xmslProjectBasicInfoMapper.insertProjectBasicInfo(projectBasicInfo);
    }

    /**
     * 新增项目基本信息
     * @param xmslProjectBasicInfo
     * @return
     */
    @Transactional
    public int insertProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo) {
        this.setSublist(xmslProjectBasicInfo);

        xmslProjectBasicInfo.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        xmslProjectBasicInfo.setCreateUserName(SecurityUtils.getUserName());
        xmslProjectBasicInfo.setCreateTime(DateUtils.getNowDate());
        return xmslProjectBasicInfoMapper.insertProjectBasicInfo(xmslProjectBasicInfo);
    }

    /**
     * 处理子表
     * @param xmslProjectBasicInfo
     */
    @Transactional
    public void setSublist(XmslProjectBasicInfo xmslProjectBasicInfo){
        //主要桥梁结构形式
        List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList = xmslProjectBasicInfo.getXmslProjectBridgeStructureList();
        if(!CollectionUtils.isEmpty(xmslProjectBridgeStructureList)){
            projectBridgeStructureService.insertProjectBridgeStructureList(xmslProjectBasicInfo.getXmslProjectBridgeStructureList(), xmslProjectBasicInfo);
        }

        //主要涵洞结构形式
        List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList = xmslProjectBasicInfo.getXmslProjectCulvertStructureList();
        if(!CollectionUtils.isEmpty(xmslProjectCulvertStructureList)){
            projectCulvertStructureService.insertProjectCulvertStructureList(xmslProjectBasicInfo.getXmslProjectCulvertStructureList(), xmslProjectBasicInfo);
        }

        //主要工程数量
        List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList = xmslProjectBasicInfo.getXmslProjectEngineeringAmountList();
        if(!CollectionUtils.isEmpty(xmslProjectEngineeringAmountList)){
            projectEngineeringAmountService.editProjectEngineeringAmountList(xmslProjectBasicInfo.getXmslProjectEngineeringAmountList(), xmslProjectBasicInfo);
        }

        //主要材料数量
        List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList = xmslProjectBasicInfo.getXmslProjectMaterialsAmountList();
        if(!CollectionUtils.isEmpty(xmslProjectMaterialsAmountList)){
            projectMaterialsAmountService.insertProjectMaterialsAmountList(xmslProjectBasicInfo.getXmslProjectMaterialsAmountList(), xmslProjectBasicInfo);
        }
    }

    /**
     * 修改项目基本信息
     *
     * @param xmslProjectBasicInfo
     */
    @Transactional
    public void updateProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo) {
        this.updateQqchSimilarProjectScheme(xmslProjectBasicInfo.getBusinessAreasAndProducts());
        xmslProjectBasicInfoMapper.updateProjectBasicInfo(xmslProjectBasicInfo);

        //项目可以自行修改项目信息
        ProjectBasicInfo projectInfo = this.projectInfo();
        xmslContractInfoService.updateProjectInfo(projectInfo);

        xmslProjectBasicInfo.setId(projectInfo.getId());
        this.editSublist(xmslProjectBasicInfo);
    }

    private void updateQqchSimilarProjectScheme(String pageDataBAP){
        try {
            ProjectBasicInfo projectInfo = this.projectInfo();
            String oldBAP = projectInfo.getBusinessAreasAndProducts();
            String projectCode = projectInfo.getProjectCode();
            if(!pageDataBAP.equals(oldBAP)){
                qqchSimilarProjectSchemeService.updateBAPByProjectCode(projectCode, pageDataBAP);
            }
        }catch (Exception e){
            System.out.println("修改同类项目方案出错！！！！");
        }
    }

    @Override
    @Transactional
    public void syncData(XmslProjectBasicInfo xmslProjectBasicInfo) {
        //查询数据库中是否存在项目数据
        boolean exist = this.ifExistProject();
        if(exist){
            this.updateQqchSimilarProjectScheme(xmslProjectBasicInfo.getBusinessAreasAndProducts());
            xmslProjectBasicInfoMapper.updateProjectBasicInfo(xmslProjectBasicInfo);
        }else {
            xmslProjectBasicInfoMapper.insertProjectBasicInfo(xmslProjectBasicInfo);
        }

        ProjectBasicInfo projectInfo = this.projectInfo();
        if(exist){
            xmslContractInfoService.updateProjectInfo(projectInfo);
        }
        xmslProjectBasicInfo.setId(projectInfo.getId());
        this.editSublist(xmslProjectBasicInfo);
    }

    private boolean ifExistProject(){
        int count = xmslProjectBasicInfoMapper.getCount();
        return count > 0;
    }

    @Transactional
    public void editSublist(XmslProjectBasicInfo xmslProjectBasicInfo){
        //主要桥梁结构形式
        List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList = xmslProjectBasicInfo.getXmslProjectBridgeStructureList();
        projectBridgeStructureService.editProjectBridgeStructureList(xmslProjectBridgeStructureList, xmslProjectBasicInfo);

        //主要涵洞结构形式
        List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList = xmslProjectBasicInfo.getXmslProjectCulvertStructureList();
        projectCulvertStructureService.editProjectCulvertStructureList(xmslProjectCulvertStructureList, xmslProjectBasicInfo);

        //主要工程数量
        List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList = xmslProjectBasicInfo.getXmslProjectEngineeringAmountList();
        projectEngineeringAmountService.editProjectEngineeringAmountList(xmslProjectEngineeringAmountList, xmslProjectBasicInfo);

        //主要材料数量
        List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList = xmslProjectBasicInfo.getXmslProjectMaterialsAmountList();
        projectMaterialsAmountService.editProjectMaterialsAmountList(xmslProjectMaterialsAmountList, xmslProjectBasicInfo);
    }

    @Transactional
    public int deleteProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo) {
        xmslProjectBasicInfo.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        xmslProjectBasicInfo.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectBasicInfoMapper.deleteProjectBasicInfo(xmslProjectBasicInfo);
    }

    @Transactional
    public int deleteProjectBasicInfoByPks(List<Long> projectBasicInfoPkList) {
        return xmslProjectBasicInfoMapper.deleteProjectBasicInfoByPks(projectBasicInfoPkList);
    }

    @Override
    public XmslProjectBasicInfo getProjectBasicInfo(XmslProjectBasicInfo projectBasicInfo) {
        XmslProjectBasicInfo xmslProjectBasicInfo = xmslProjectBasicInfoMapper.getProjectBasicInfo(projectBasicInfo);
        this.setProjectSublistInfo(xmslProjectBasicInfo);
        return xmslProjectBasicInfo;
    }

    /**
     * 获取项目信息详情（不带子表）
     *
     * @return
     */
    @Override
    public ProjectBasicInfo projectInfo() {
        XmslProjectBasicInfo xmslProjectBasicInfo = xmslProjectBasicInfoMapper.getProjectBasicInfo(new XmslProjectBasicInfo());
        ProjectBasicInfo projectInfo = new ProjectBasicInfo();
        if(xmslProjectBasicInfo != null){
            BeanUtils.copyProperties(xmslProjectBasicInfo,projectInfo);
        }
        return projectInfo;
    }

    /**
     * 获取项目基本信息（附带其他信息）
     * @return
     */
    @Override
    public ProjectInfoWithOther getProjectInfoWithOther() {
        ProjectInfoWithOther projectInfoWithOther = new ProjectInfoWithOther();

        //获取项目基本信息
        XmslProjectBasicInfo xmslProjectBasicInfo = xmslProjectBasicInfoMapper.getProjectBasicInfo(new XmslProjectBasicInfo());
        if (xmslProjectBasicInfo != null){
            BeanUtils.copyProperties(xmslProjectBasicInfo,projectInfoWithOther);
        }

        //获取合同信息
        XmslContractInfo contractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();
        if(contractInfo != null){
            projectInfoWithOther.setContractAmount(contractInfo.getEffectiveAmout());
            projectInfoWithOther.setContractTypeInContract(contractInfo.getContractType());
            projectInfoWithOther.setContractSignDate(contractInfo.getSignDate());
            projectInfoWithOther.setBrandName(contractInfo.getBrandName());
        }

        //获取前期策划小组
        QqchWorkGroup workGroup = qqchWorkGroupService.getValidMaxVersionQqchWorkGroup();
        if(workGroup != null){
            projectInfoWithOther.setPlanDominantUnit(workGroup.getPlanDominantUnit());
            projectInfoWithOther.setPlanApprovalUnit(workGroup.getPlanApprovalUnit());
            projectInfoWithOther.setPlanEstablishDirector(workGroup.getPlanEstablishDirector());
            projectInfoWithOther.setContactWay(workGroup.getContactWay());
            projectInfoWithOther.setProjectOverview(workGroup.getProjectOverview());
        }

        //获取前期策划评审第三阶段数据
        Review review = qqchReviewService.getReviewByPlanStage("3");
        if(review != null){
            projectInfoWithOther.setPlanApprovalCompleteDate(review.getReviewCompleteDate());
        }

        return projectInfoWithOther;
    }

    @Override
    public Map<String, Object> getPrjInfo() {
        Map<String,Object> map = new HashMap<>();
        List<CountryInfo> countryInfoList = systemServiceApi.getCountryInfoList();
        Map<String, CountryInfo> countryMap = countryInfoList.stream().filter(o -> StringUtils.isNotBlank(o.getCountryCode())).collect(Collectors.toMap(CountryInfo::getCountryCode, o -> o, (k,v) -> k));
        XmslProjectBasicInfo xmslProjectBasicInfo = xmslProjectBasicInfoMapper.getProjectBasicInfo(new XmslProjectBasicInfo());
        String projectLocation = xmslProjectBasicInfo.getProjectLocation();
        /*项目id*/
        map.put("projectId",xmslProjectBasicInfo.getProjectId());
        /*项目名称*/
        map.put("projectName",xmslProjectBasicInfo.getProjectName());
        /*项目编码*/
        map.put("projectCode",xmslProjectBasicInfo.getProjectCode());
        /*机构id*/
        map.put("regionId",xmslProjectBasicInfo.getRegionId());
        /*机构名称*/
        map.put("regionName",xmslProjectBasicInfo.getRegionName());
        /*项目所在地（国）*/
        map.put("projectLocation",projectLocation);
        if(StringUtils.isNotBlank(projectLocation)){
            CountryInfo countryInfo = countryMap.get(projectLocation);
            if(countryInfo != null){
                /*国别中文名称*/
                map.put("countryName",countryInfo.getCountryName());
                /*国别英文名称*/
                map.put("countryEnName",countryInfo.getCountryEnName());
            }
        }
        /*中标单位*/
        map.put("winTheBiddingUnit",xmslProjectBasicInfo.getWinTheBiddingUnit());
        /*业务领域及产品*/
        map.put("businessAreasAndProductsLabel",xmslProjectBasicInfo.getBusinessAreasAndProductsLabel());
        return map;
    }

}
