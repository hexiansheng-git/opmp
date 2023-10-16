package com.hhwy.pm.qqch.preparation.survey.managemodel.service.impl;

import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcTopicList;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModelVo;
import com.hhwy.pm.qqch.preparation.survey.managemodel.mapper.QqchSurveyManageModelMapper;
import com.hhwy.pm.qqch.preparation.survey.managemodel.service.IQqchSurveyManageModelService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-18 14:57:01
 * @remark 总体勘察设计经营模式确定
 */
@Service
public class QqchSurveyManageModelServiceImpl implements IQqchSurveyManageModelService {

    @Autowired
    private QqchSurveyManageModelMapper qqchSurveyManageModelMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;


    /**
     * 总体勘察设计经营模式确定 列表查询
     *
     * @param qqchSurveyManageModel
     * @return
     */
    public QqchSurveyManageModelVo getQqchSurveyManageModelList(QqchSurveyManageModel qqchSurveyManageModel) {
        QqchSurveyManageModelVo vo = new QqchSurveyManageModelVo();
        BigDecimal version = VersionUtil.getVersion("qqch_survey_manage_model", qqchSurveyManageModel.getVersion());
        qqchSurveyManageModel.setVersion(version);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        List<QqchSurveyManageModel> qqchSurveyManageModelList = qqchSurveyManageModelMapper.getQqchSurveyManageModelList(qqchSurveyManageModel);
        vo.setQqchSurveyManageModelList(qqchSurveyManageModelList);
        return vo;
    }


    /**
     * 确认状态
     *
     * @param qqchSurveyManageModelVo
     */
    @Override
    public QqchSurveyManageModelVo confirm(QqchSurveyManageModelVo qqchSurveyManageModelVo) {
        this.save(qqchSurveyManageModelVo);
        String buttonMark = qqchSurveyManageModelVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认状态
            String menuId = qqchSurveyManageModelVo.getMenuId();
            String stageIdentity = qqchSurveyManageModelVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            qqchSurveyManageModelVo.setConfirmStatus("1");
        }
        return qqchSurveyManageModelVo;
    }

    @Override
    public int save(QqchSurveyManageModelVo qqchSurveyManageModelVo) {
        //删除旧数据
        QqchSurveyManageModel qqchSurveyManageModel = new QqchSurveyManageModel();
        qqchSurveyManageModel.setVersion(qqchSurveyManageModelVo.getVersion());
        qqchSurveyManageModelMapper.deleteQqchSurveyManageModel(qqchSurveyManageModel);
        //插入新数据
        return this.insertQqchSurveyManageModelList(qqchSurveyManageModelVo.getQqchSurveyManageModelList(), qqchSurveyManageModelVo.getVersion());
    }


    private int insertQqchSurveyManageModelList(List<QqchSurveyManageModel> qqchSurveyManageModelList, BigDecimal version) {
        for (QqchSurveyManageModel qqchSurveyManageModel : qqchSurveyManageModelList) {
            qqchSurveyManageModel.setId(IdWorker.createId());
            qqchSurveyManageModel.setVersion(version);
            if (version.compareTo(BigDecimal.valueOf(1)) == 0) {
                qqchSurveyManageModel.setValid(Valid.YES);
            }
            qqchSurveyManageModel.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyManageModel.setCreateUserName(SecurityUtils.getUserName());
            qqchSurveyManageModel.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyManageModelMapper.insertQqchSurveyManageModelList(qqchSurveyManageModelList);
    }

    @Override
    public List<ProjectBasicInfo> getSameTypeProject(QqchSurveyManageModel param) {
        List<ProjectBasicInfo> result = new ArrayList<>();
        // 获取当前租户
        String currentTenantKey = SecurityUtils.getTenantKey();
        // 获取当前租户的项目
        ProjectBasicInfo currentProjectInfo = xmslProjectBasicInfoService.projectInfo();
        if (currentProjectInfo == null || StringUtils.isBlank(currentProjectInfo.getBusinessAreasAndProducts())) {
            return null;
        }
        // 切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        // 获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();
        try {
            for (SysTenant tenant : tenantList) {
                if (currentTenantKey.equals(tenant.getTenantKey())) {
                    continue;
                }
                // 切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);

                // 获取项目数据
                ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
//                if (projectInfo == null || !currentProjectInfo.getBusinessAreasAndProducts()
//                        .equals(projectInfo.getBusinessAreasAndProducts())) {
//                    continue;
//                }
                if (ObjectUtil.isEmpty(projectInfo) || StrUtil.isBlank(projectInfo.getProjectName())) {
                    continue;
                }
                result.add(projectInfo);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        return result;
    }

}
