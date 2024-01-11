package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchKeyDifficultConstructionBrief;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchSimilarProjectScheme;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.SimilarProjectSchemeQueryVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchSimilarProjectSchemeMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionReviewPlanService;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchKeyDifficultConstructionBriefService;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchSimilarProjectSchemeService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import io.seata.common.util.CollectionUtils;
import io.seata.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2024-01-05 11:49:06
 * @remark
 */
@Service
public class QqchSimilarProjectSchemeServiceImpl implements IQqchSimilarProjectSchemeService {

    @Autowired
    private QqchSimilarProjectSchemeMapper qqchSimilarProjectSchemeMapper;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    @Autowired
    private IQqchKeyDifficultConstructionBriefService qqchKeyDifficultConstructionBriefService;

    @Autowired
    private IQqchConstructionReviewPlanService qqchConstructionReviewPlanService;


    public QqchSimilarProjectScheme getQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        return qqchSimilarProjectSchemeMapper.getQqchSimilarProjectScheme(qqchSimilarProjectScheme);
    }

    public List<QqchSimilarProjectScheme> getQqchSimilarProjectSchemeList(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        return qqchSimilarProjectSchemeMapper.getQqchSimilarProjectSchemeList(qqchSimilarProjectScheme);
    }

    @Transactional
    public int insertQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        qqchSimilarProjectScheme.setId(IdWorker.createId());
        qqchSimilarProjectScheme.setCreateUser(SecurityUtils.getUserName());
        qqchSimilarProjectScheme.setCreateTime(DateUtils.getNowDate());
        return qqchSimilarProjectSchemeMapper.insertQqchSimilarProjectScheme(qqchSimilarProjectScheme);
    }

    public int insertQqchSimilarProjectSchemeList(List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList) {
        for (QqchSimilarProjectScheme qqchSimilarProjectScheme : qqchSimilarProjectSchemeList) {
            qqchSimilarProjectScheme.setCreateUser(SecurityUtils.getUserName());
            qqchSimilarProjectScheme.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSimilarProjectSchemeMapper.insertQqchSimilarProjectSchemeList(qqchSimilarProjectSchemeList);
    }

    @Transactional
    public int updateQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        qqchSimilarProjectScheme.setUpdateUser(SecurityUtils.getUserName());
        qqchSimilarProjectScheme.setUpdateTime(DateUtils.getNowDate());
        return qqchSimilarProjectSchemeMapper.updateQqchSimilarProjectScheme(qqchSimilarProjectScheme);
    }

    @Transactional
    public int updateQqchSimilarProjectSchemeList(List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList) {
        for (QqchSimilarProjectScheme qqchSimilarProjectScheme : qqchSimilarProjectSchemeList) {
            qqchSimilarProjectScheme.setUpdateUser(SecurityUtils.getUserName());
            qqchSimilarProjectScheme.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSimilarProjectSchemeMapper.updateQqchSimilarProjectSchemeList(qqchSimilarProjectSchemeList);
    }

    @Transactional
    public int deleteQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        qqchSimilarProjectScheme.setUpdateUser(SecurityUtils.getUserName());
        qqchSimilarProjectScheme.setUpdateTime(DateUtils.getNowDate());
        return qqchSimilarProjectSchemeMapper.deleteQqchSimilarProjectScheme(qqchSimilarProjectScheme);
    }

    @Transactional
    public int deleteQqchSimilarProjectSchemeByPks(List<Long> qqchSimilarProjectSchemePkList) {
        return qqchSimilarProjectSchemeMapper.deleteQqchSimilarProjectSchemeByPks(qqchSimilarProjectSchemePkList);
    }

    /**
     * 修改项目的业务领域及产品
     * @param projectCode
     * @param businessAreasAndProducts
     */
    public void updateBAPByProjectCode(String projectCode,String businessAreasAndProducts){
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        try {
            //切换到master
            DynamicDataSourceContextHolder.push("master");
            qqchSimilarProjectSchemeMapper.updateBAPByProjectCode(projectCode,businessAreasAndProducts);
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    @Override
    public void pushData() {
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        String currentPrjCode = projectInfo.getProjectCode();
        //当前数据源
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        String projectName = projectInfo.getProjectName();
        Long regionId = projectInfo.getRegionId();
        String regionName = projectInfo.getRegionName();
        String businessAreasAndProducts = projectInfo.getBusinessAreasAndProducts();
        /*查询最新版本的重难点分项施工方案简述数据*/
        List<QqchKeyDifficultConstructionBrief> briefList = qqchKeyDifficultConstructionBriefService.getLatestList();
        /*查询最新版本的施工方案编审计划数据*/
        List<QqchConstructionReviewPlan> reviewPlanList = qqchConstructionReviewPlanService.getLatestList();
        if(StringUtils.isBlank(businessAreasAndProducts) || CollectionUtils.isEmpty(briefList)){
            return;
        }

        List<QqchSimilarProjectScheme> schemeList = new ArrayList<>();

        QqchSimilarProjectScheme prjScheme = new QqchSimilarProjectScheme();
        Long id = IdWorker.createId();
        prjScheme.setId(id);
        prjScheme.setProjectCode(currentPrjCode);
        prjScheme.setProjectName(projectName);
        prjScheme.setBusinessAreasAndProducts(businessAreasAndProducts);
        prjScheme.setRegionId(regionId);
        prjScheme.setRegionName(regionName);
        prjScheme.setPtVar1("prj");
        schemeList.add(prjScheme);


        Map<String, QqchConstructionReviewPlan> reviewPlanMap = reviewPlanList.stream().collect(Collectors.toMap(QqchConstructionReviewPlan::getSchemeCode, o -> o));

        for (QqchKeyDifficultConstructionBrief brief : briefList) {
            QqchSimilarProjectScheme scheme = new QqchSimilarProjectScheme();
            scheme.setId(IdWorker.createId());
            scheme.setPid(id);
            scheme.setProjectCode(currentPrjCode);
            scheme.setProjectName(projectName);
            scheme.setBusinessAreasAndProducts(businessAreasAndProducts);
            scheme.setSchemeName(brief.getSchemeName());
            scheme.setSchemeLevel(brief.getSchemeLevel());
            scheme.setWbsName(brief.getWbsName());
            scheme.setConstructionContent(brief.getConstructionContent());
            scheme.setAdoptProcess(brief.getAdoptProcess());
            scheme.setMainEquipment(brief.getMainEquipment());
            scheme.setFileGroupId(brief.getFileGroupId());
            scheme.setRemark(brief.getRemark());
            scheme.setRegionId(regionId);
            scheme.setRegionName(regionName);
            scheme.setPtVar1("data");

            if(reviewPlanMap.containsKey(brief.getSchemeCode())){
                QqchConstructionReviewPlan plan = reviewPlanMap.get(brief.getSchemeCode());
                scheme.setSchemeLevelDescription(plan.getSchemeLevelDescription());
            }
            schemeList.add(scheme);
        }
        try {
            //切换到master
            DynamicDataSourceContextHolder.push("master");
            this.deleteByProjectCode(currentPrjCode);
            this.insertQqchSimilarProjectSchemeList(schemeList);
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    private void deleteByProjectCode(String projectCode){
        QqchSimilarProjectScheme delParam = new QqchSimilarProjectScheme();
        delParam.setProjectCode(projectCode);
        qqchSimilarProjectSchemeMapper.deleteQqchSimilarProjectScheme(delParam);
    }

    @Override
    public List<QqchSimilarProjectScheme> getSimilarProjectScheme(SimilarProjectSchemeQueryVo queryVo) {
        ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
        //当前项目的项目编码
        String currentPrjCode = projectInfo.getProjectCode();
        //当前项目的业务领域及产品
        String currentBAP = projectInfo.getBusinessAreasAndProducts();
        List<QqchSimilarProjectScheme> schemeList = new ArrayList<>();
        if(StringUtils.isBlank(currentBAP)){
            return schemeList;
        }
        //当前数据源
        String oldDataSource = DynamicDataSourceContextHolder.peek();

        try {
            //切换到master
            DynamicDataSourceContextHolder.push("master");
            QqchSimilarProjectScheme query = new QqchSimilarProjectScheme();
            query.setProjectName(queryVo.getProjectName());
            query.setBusinessAreasAndProducts(currentBAP);
            schemeList = qqchSimilarProjectSchemeMapper.getQqchSimilarProjectSchemeList(query);
            String schemeName = queryVo.getSchemeName();
            if(StringUtils.isNotBlank(schemeName)){
                schemeList = schemeList.stream().filter(scheme -> {
                    String ptVar1 = scheme.getPtVar1();
                    if ("prj".equals(ptVar1)) {
                        return true;
                    }
                    String name = scheme.getSchemeName();
                    return StringUtils.isNotBlank(name) && name.contains(schemeName);
                }).collect(Collectors.toList());
            }
            String schemeLevel = queryVo.getSchemeLevel();
            if(StringUtils.isNotBlank(schemeLevel)){
                schemeList = schemeList.stream().filter(scheme -> {
                    String ptVar1 = scheme.getPtVar1();
                    if ("prj".equals(ptVar1)) {
                        return true;
                    }
                    String level = scheme.getSchemeLevel();
                    return StringUtils.isNotBlank(level) && level.equals(schemeLevel);
                }).collect(Collectors.toList());
            }

            schemeList = ListTreeUtil.formatTree(
                    schemeList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSimilarProjectScheme::getChildren,
                    QqchSimilarProjectScheme::setChildren);

            schemeList.removeIf(scheme -> CollectionUtils.isEmpty(scheme.getChildren()) || scheme.getProjectCode().equals(currentPrjCode));

            return schemeList;
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }
}
