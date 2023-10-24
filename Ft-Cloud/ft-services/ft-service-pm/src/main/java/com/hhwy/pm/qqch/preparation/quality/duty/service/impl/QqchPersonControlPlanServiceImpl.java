package com.hhwy.pm.qqch.preparation.quality.duty.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchPersonControlPlan;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchPersonControlPlanVo;
import com.hhwy.pm.qqch.preparation.quality.duty.mapper.QqchPersonControlPlanMapper;
import com.hhwy.pm.qqch.preparation.quality.duty.service.IQqchPersonControlPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.warn.WarnService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-03 14:29:43
 * @remark 9.1.2 人员管控策划
 */
@Service
public class QqchPersonControlPlanServiceImpl implements IQqchPersonControlPlanService {

    @Autowired
    private QqchPersonControlPlanMapper qqchPersonControlPlanMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private WarnService warnService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchPersonControlPlanVo getQqchPersonControlPlanList(BigDecimal version) {
        QqchPersonControlPlanVo vo = new QqchPersonControlPlanVo();

        List<QqchPersonControlPlan> treeList = this.getTreeList(version);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(treeList);
        return vo;
    }

    private List<QqchPersonControlPlan> getTreeList(BigDecimal version){
        version = VersionUtil.getVersion("qqch_person_control_plan", version);

        QqchPersonControlPlan qryParam = new QqchPersonControlPlan();
        qryParam.setVersion(version);
        List<QqchPersonControlPlan> list = qqchPersonControlPlanMapper.getQqchPersonControlPlanList(qryParam);

        //转树列表
        List<QqchPersonControlPlan> treeList = ListTreeUtil.formatTree(
                list,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchPersonControlPlan::getChildren,
                QqchPersonControlPlan::setChildren);
        return treeList;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void insertQqchPersonControlPlanList(QqchPersonControlPlanVo voParam) {
        // 清空数据库表中数据
        QqchPersonControlPlan deleteParam = new QqchPersonControlPlan();
        deleteParam.setVersion(voParam.getVersion());
        qqchPersonControlPlanMapper.deleteQqchPersonControlPlan(deleteParam);

        String buttonMark = voParam.getButtonMark();
        List<QqchPersonControlPlan> list = voParam.getList();
        if (!CollectionUtils.isEmpty(list)) {
            list = ListTreeUtil.formatList(
                    list,
                    QqchPersonControlPlan::setId,
                    QqchPersonControlPlan::setPid,
                    QqchPersonControlPlan::setSort,
                    QqchPersonControlPlan::getChildren,
                    QqchPersonControlPlan::setChildren);

            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyRoot(list, QqchPersonControlPlan::getPid, ValidationGroups.Save.class);
            }

            for (QqchPersonControlPlan qqchPersonControlPlan : list) {
                qqchPersonControlPlan.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchPersonControlPlan.setValid(Valid.YES);
                }
                qqchPersonControlPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchPersonControlPlan.setCreateUserName(SecurityUtils.getUserName());
                qqchPersonControlPlan.setCreateTime(DateUtils.getNowDate());
            }
            qqchPersonControlPlanMapper.insertQqchPersonControlPlanList(list);
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Override
    public void personControlPlanWarn() {
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

                List<QqchPersonControlPlan> list = this.getTreeList(null);
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }

                Date nowDate = FtDateUtils.getYearMonthDayDate();
                for (QqchPersonControlPlan plan : list) {
                    //用户名
                    String personId = plan.getPersonId();
                    List<QqchPersonControlPlan> children = plan.getChildren();
                    for (QqchPersonControlPlan child : children) {
                        if (child.getIssueDate() == null || child.getLimitPeriod() == null || StringUtils
                                .isBlank(child.getMessageWarn())) {
                            continue;
                        }

                        // 签发日期
                        Date issueDate = child.getIssueDate();
                        // 有限期限（年）换算成天
                        BigDecimal limitPeriod = BigDecimal.valueOf(child.getLimitPeriod()).multiply(new BigDecimal(365));

                        // 证件到期日
                        Date dueDate = FtDateUtils.getDateAddDays(issueDate, limitPeriod.intValue());

                        // 日期相差天数
                        long diffDays = FtDateUtils.getDays(nowDate, dueDate);

                        // 到期前1个月提醒
                        if ("1".equals(plan.getMessageWarn()) && diffDays < 30) {
                            // 发送预警
                            warnService.addWarn(WarnItem.PERSON_CONTROL_PLAN_ONE, WarnScopeType.USER, null, personId, tenantKey);
                        }

                        // 到期前2个月提醒
                        if ("2".equals(plan.getMessageWarn()) && diffDays < 60) {
                            // 发送预警
                            warnService.addWarn(WarnItem.PERSON_CONTROL_PLAN_TWO, WarnScopeType.USER, null, personId, tenantKey);
                        }

                        // 到期前3个月提醒
                        if ("3".equals(plan.getMessageWarn()) && diffDays < 90) {
                            // 发送预警
                            warnService.addWarn(WarnItem.PERSON_CONTROL_PLAN_THREE, WarnScopeType.USER, null, personId, tenantKey);
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
