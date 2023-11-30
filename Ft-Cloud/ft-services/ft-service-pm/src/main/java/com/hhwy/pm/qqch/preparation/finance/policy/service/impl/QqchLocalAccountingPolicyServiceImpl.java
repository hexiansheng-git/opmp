package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalAccountingPolicy;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalAccountingPolicyVo;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchLocalAccountingPolicyMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchLocalAccountingPolicyService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.finance.qyzsFinanceAccountPolicy.domain.QyzsFinanceAccountPolicy;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:44
 * @remark 10.2.3当地会计政策描述
 */
@Service
public class QqchLocalAccountingPolicyServiceImpl implements IQqchLocalAccountingPolicyService {

    @Autowired
    private QqchLocalAccountingPolicyMapper qqchLocalAccountingPolicyMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchLocalAccountingPolicyVo getQqchLocalAccountingPolicyList(BigDecimal version) {
        QqchLocalAccountingPolicyVo vo = new QqchLocalAccountingPolicyVo();
        version = VersionUtil.getVersion("qqch_local_accounting_policy", version);

        QqchLocalAccountingPolicy qryParam = new QqchLocalAccountingPolicy();
        qryParam.setVersion(version);
        List<QqchLocalAccountingPolicy> list =
            qqchLocalAccountingPolicyMapper.getQqchLocalAccountingPolicyList(qryParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     */
    @Transactional
    public void batchSave(QqchLocalAccountingPolicyVo voParam) {
        // 清空数据库表中数据
        QqchLocalAccountingPolicy deleteParam = new QqchLocalAccountingPolicy();
        deleteParam.setVersion(voParam.getVersion());
        qqchLocalAccountingPolicyMapper.deleteQqchLocalAccountingPolicy(deleteParam);

        String buttonMark = voParam.getButtonMark();
        List<QqchLocalAccountingPolicy> list = voParam.getList();
        if (!CollectionUtils.isEmpty(list)) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchLocalAccountingPolicy qqchLocalAccountingPolicy : voParam.getList()) {
                qqchLocalAccountingPolicy.setId(IdWorker.createId());
                qqchLocalAccountingPolicy.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchLocalAccountingPolicy.setValid(Valid.YES);
                }
                qqchLocalAccountingPolicy.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchLocalAccountingPolicy.setCreateUserName(SecurityUtils.getUserName());
                qqchLocalAccountingPolicy.setCreateTime(DateUtils.getNowDate());
            }
            qqchLocalAccountingPolicyMapper.insertQqchLocalAccountingPolicyList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            this.pushQyzsFinanceAccountPolicy(list);
        }
    }

    /**
     * 推送当地会计政策到总部版
     * @param list
     */
    public void pushQyzsFinanceAccountPolicy(List<QqchLocalAccountingPolicy> list){
        Map<String,Object> map = new HashMap<>();

        String projectLocation = xmslProjectBasicInfoService.projectInfo().getProjectLocation();
        if(StringUtils.isBlank(projectLocation) || CollectionUtils.isEmpty(list)){
            return;
        }

        List<QyzsFinanceAccountPolicy> accountPolicyList = new ArrayList<>();
        for (QqchLocalAccountingPolicy qqchLocalAccountingPolicy : list) {
            String isSelect = qqchLocalAccountingPolicy.getIsSelect();
            if(!"1".equals(isSelect)){
                QyzsFinanceAccountPolicy policy = new QyzsFinanceAccountPolicy();
                policy.setId(IdWorker.createId());
                policy.setCountryCode(projectLocation);
                policy.setAccountingPolicy(qqchLocalAccountingPolicy.getAccountingPolicy());
                policy.setContent(qqchLocalAccountingPolicy.getContent());
                policy.setCreateTime(DateUtils.getNowDate());
                policy.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                policy.setCreateUserName(SecurityUtils.getUserName());
                accountPolicyList.add(policy);
            }
        }

        if(CollectionUtils.isEmpty(accountPolicyList)){
            return;
        }

        map.put("countryCode",projectLocation);
        map.put("accountPolicyList",accountPolicyList);
        rocketMQTemplate.convertAndSend("qyzs_finance_account_policy:tenantSuccess", map);
    }
}
