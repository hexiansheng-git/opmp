package com.hhwy.pm.qqch.preparation.quality.qc.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcTopicList;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcTopicListVo;
import com.hhwy.pm.qqch.preparation.quality.qc.mapper.QqchQcTopicListMapper;
import com.hhwy.pm.qqch.preparation.quality.qc.service.IQqchQcTopicListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:25
 * @remark 9.6.1 QC课题清单
 */
@Service
public class QqchQcTopicListServiceImpl implements IQqchQcTopicListService {

    @Autowired
    private QqchQcTopicListMapper qqchQcTopicListMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    public QqchQcTopicListVo getQqchQcTopicListList(BigDecimal version) {
        QqchQcTopicListVo vo = new QqchQcTopicListVo();
        version = VersionUtil.getVersion("qqch_qc_topic_list", version);

        QqchQcTopicList qryParam = new QqchQcTopicList();
        qryParam.setVersion(version);
        List<QqchQcTopicList> list = qqchQcTopicListMapper.getQqchQcTopicListList(qryParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchQcTopicListVo voParam) {
        // 清空数据库表中QC课题清单数据
        QqchQcTopicList deleteParam = new QqchQcTopicList();
        deleteParam.setVersion(voParam.getVersion());
        qqchQcTopicListMapper.deleteQqchQcTopicList(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchQcTopicList qqchQcTopicList : voParam.getList()) {
                if (StringUtils.isBlank(qqchQcTopicList.getTopicCode())) {
                    qqchQcTopicList.setTopicCode(UUID.randomUUID().toString().replaceAll("-", ""));
                }

                qqchQcTopicList.setId(IdWorker.createId());
                qqchQcTopicList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQcTopicList.setValid(Valid.YES);
                }
                qqchQcTopicList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQcTopicList.setCreateUserName(SecurityUtils.getUserName());
                qqchQcTopicList.setCreateTime(DateUtils.getNowDate());
            }
            // 课题清单入库
            qqchQcTopicListMapper.insertQqchQcTopicListList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    public List<QqchQcTopicList> getHistoryList(QqchQcTopicList param) {
        List<QqchQcTopicList> allList = new ArrayList<>();

        // 获取当前租户
        String currentTenantKey = SecurityUtils.getTenantKey();
        // 获取当前租户的项目
        ProjectBasicInfo currentProjectInfo = xmslProjectBasicInfoService.projectInfo();
        if (currentProjectInfo == null || StringUtils.isBlank(currentProjectInfo.getBusinessAreasAndProducts())) {
            return allList;
        }

        // 切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();

        DynamicDataSourceContextHolder.push("master");
        // 获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();

        try {
            for (SysTenant tenant : tenantList) {
                if (!currentTenantKey.equals(tenant.getTenantKey())) {
                    // 切换租户
                    String tenantKey = tenant.getTenantKey();
                    String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                    DynamicDataSourceContextHolder.push(dataSource);

                    // 获取项目数据
                    ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
                    if (projectInfo == null || !currentProjectInfo.getBusinessAreasAndProducts()
                        .equals(projectInfo.getBusinessAreasAndProducts())) {
                        continue;
                    }

                    if (StringUtils.isNotBlank(param.getProjectName()) && !projectInfo.getProjectName()
                        .contains(param.getProjectName())) {
                        continue;
                    }

                    BigDecimal version = VersionUtil.getVersion("qqch_qc_topic_list", null);
                    QqchQcTopicList qryParam = new QqchQcTopicList();
                    qryParam.setVersion(version);
                    qryParam.setTopicName(param.getTopicName());
                    qryParam.setProfessionalCategory(param.getProfessionalCategory());
                    List<QqchQcTopicList> qcTopicList = qqchQcTopicListMapper.getQqchQcTopicListList(qryParam);

                    if (!CollectionUtils.isEmpty(qcTopicList)) {
                        QqchQcTopicList qqchQcTopicList = new QqchQcTopicList();
                        qqchQcTopicList.setProjectId(projectInfo.getProjectId());
                        qqchQcTopicList.setProjectName(projectInfo.getProjectName());
                        qqchQcTopicList.setChildren(qcTopicList);
                        allList.add(qqchQcTopicList);
                    }
                }
            }
        } catch (Exception e) {
            throw new CustomBusinessException(e.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        return allList;
    }
}
