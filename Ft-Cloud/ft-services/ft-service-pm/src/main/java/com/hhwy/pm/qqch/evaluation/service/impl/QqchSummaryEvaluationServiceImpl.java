package com.hhwy.pm.qqch.evaluation.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.evaluation.mapper.QqchSummaryEvaluationMapper;
import com.hhwy.pm.qqch.evaluation.service.IQqchSummaryEvaluationService;
import com.hhwy.pm.warn.WarnService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-24 16:48:10
 * @remark 前期策划总结评价
 */
@Service
public class QqchSummaryEvaluationServiceImpl implements IQqchSummaryEvaluationService {

    @Autowired
    private QqchSummaryEvaluationMapper qqchSummaryEvaluationMapper;
    @Autowired
    private ISysSyncInfoService sysSyncInfoService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private WarnService warnService;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    public QqchSummaryEvaluation getQqchSummaryEvaluation(QqchSummaryEvaluation qqchSummaryEvaluation) {
        QqchSummaryEvaluation summaryEvaluation = qqchSummaryEvaluationMapper.getQqchSummaryEvaluation(qqchSummaryEvaluation);
        FlowInfoSearchUtil.getFlowInfo(summaryEvaluation, FlowEnum.QQCH_SUMMARY_EVALUATION);
        return summaryEvaluation;
    }

    @Transactional
    public void save(QqchSummaryEvaluation qqchSummaryEvaluation) {
        if (qqchSummaryEvaluation == null) {
            return;
        }
        if (qqchSummaryEvaluation.getId() == null) {
            qqchSummaryEvaluation.setId(IdWorker.createId());
            qqchSummaryEvaluation.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSummaryEvaluation.setCreateUserName(SecurityUtils.getUserName());
            qqchSummaryEvaluation.setCreateTime(DateUtils.getNowDate());
            qqchSummaryEvaluationMapper.insertQqchSummaryEvaluation(qqchSummaryEvaluation);
        } else {
            qqchSummaryEvaluation.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSummaryEvaluation.setUpdateTime(DateUtils.getNowDate());
            qqchSummaryEvaluationMapper.updateQqchSummaryEvaluation(qqchSummaryEvaluation);
        }
        // 推送数据到总部版
        sysSyncInfoService.pushQqchSummaryEvaluation(qqchSummaryEvaluation);
    }

    @Transactional
    public void submit(QqchSummaryEvaluation qqchSummaryEvaluation) {
        CommonAssert.isEmpty(qqchSummaryEvaluation.getInitialUnitId(), "初评单位不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getInitialPersonId(), "评价人不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getInitialDate(), "评价时间不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getInitialFileGroupId(), "策划评价报告不能为空");

        CommonAssert.isEmpty(qqchSummaryEvaluation.getFinalUnitId(), "初评单位不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getFinalPersonId(), "评价人不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getFinalDate(), "评价时间不能为空");
        CommonAssert.isEmpty(qqchSummaryEvaluation.getFinalFileGroupId(), "策划评价报告不能为空");

        qqchSummaryEvaluation.setTaskStatus("1");
        // 保存数据
        this.save(qqchSummaryEvaluation);

    }

    /**
     * 修改流程数据
     *
     * @param id
     */
    @Transactional
    public void updateQqchSummaryEvaluationProcess(Long id) {
        QqchSummaryEvaluation query = new QqchSummaryEvaluation();
        query.setId(id);
        QqchSummaryEvaluation summaryEvaluation = qqchSummaryEvaluationMapper.getQqchSummaryEvaluation(query);
        summaryEvaluation.setTaskStatus("5");
        summaryEvaluation.setPtVar3(DateUtils.getDate());
        qqchSummaryEvaluationMapper.updateQqchSummaryEvaluation(summaryEvaluation);
        //推送到总部
        sysSyncInfoService.pushQqchSummaryEvaluation(summaryEvaluation);
    }

    @Override
    public void summaryEvaluationWarn(String type) {
        // 切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        // 获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();

        try {
            for (SysTenant tenant : tenantList) {
                // 切换租户
                String tenantKey = tenant.getTenantKey();
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);

                QqchSummaryEvaluation qqchSummaryEvaluation = this
                    .getQqchSummaryEvaluation(new QqchSummaryEvaluation());

                Date nowDate = FtDateUtils.getYearMonthDayDate();
                long diffDays;

                // type为1时，总结预警
                if ("1".equals(type)) {
                    // 获取项目数据
                    ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
                    if (projectInfo == null) {
                        continue;
                    }

                    // 项目初验时间
                    Date projectInitialInspectionDate = projectInfo.getProjectInitialInspectionDate();
                    if (projectInitialInspectionDate == null) {
                        continue;
                    }
                    diffDays = FtDateUtils.getDiffDays(projectInitialInspectionDate, nowDate);

                    // 项目初验时间超过10天，预警
                    if (diffDays >= 10) {
                        if (qqchSummaryEvaluation == null) {
                            // 发送预警
                            warnService.addWarn(WarnItem.SUMMARY, WarnScopeType.USER, null, "admin", tenantKey);
                        }
                    }
                }

                // type为2时，评价预警
                if ("2".equals(type)) {
                    if (qqchSummaryEvaluation == null) {
                        continue;
                    }
                    // 总结评价创建时间
                    Date createTime = FtDateUtils.getFormatDate(qqchSummaryEvaluation.getCreateTime());
                    if (createTime == null) {
                        continue;
                    }
                    diffDays = FtDateUtils.getDays(createTime, nowDate);

                    // 在项目报送前期策划总结后7天内进行评价，未完成进行预警
                    if (diffDays > 7) {
                        if ("".equals(qqchSummaryEvaluation.getTaskStatus()) || "0"
                            .equals(qqchSummaryEvaluation.getTaskStatus())) {
                            // 发送预警
                            warnService
                                .addWarn(WarnItem.EVALUATION, WarnScopeType.USER, null, "admin", tenantKey);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        } finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }
}
