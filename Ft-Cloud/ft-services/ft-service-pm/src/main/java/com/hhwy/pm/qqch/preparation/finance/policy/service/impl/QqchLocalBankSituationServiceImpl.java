package com.hhwy.pm.qqch.preparation.finance.policy.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalBankSituation;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalBankSituationVo;
import com.hhwy.pm.qqch.preparation.finance.policy.mapper.QqchLocalBankSituationMapper;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchLocalBankSituationService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.pm.qyzs.finance.qyzsFinanceBankStatus.domain.QyzsFinanceBankStatus;
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
 * @date 2023-08-03 13:45:08
 * @remark 10.2.5当地银行情况描述
 */
@Service
public class QqchLocalBankSituationServiceImpl implements IQqchLocalBankSituationService {

    @Autowired
    private QqchLocalBankSituationMapper qqchLocalBankSituationMapper;
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
    public QqchLocalBankSituationVo getQqchLocalBankSituationList(BigDecimal version) {
        QqchLocalBankSituationVo vo = new QqchLocalBankSituationVo();
        version = VersionUtil.getVersion("qqch_local_bank_situation", version);

        QqchLocalBankSituation qryParam = new QqchLocalBankSituation();
        qryParam.setVersion(version);
        List<QqchLocalBankSituation> list = qqchLocalBankSituationMapper.getQqchLocalBankSituationList(qryParam);

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
    public void batchSave(QqchLocalBankSituationVo voParam) {
        // 清空数据库表中数据
        QqchLocalBankSituation deleteParam = new QqchLocalBankSituation();
        deleteParam.setVersion(voParam.getVersion());
        qqchLocalBankSituationMapper.deleteQqchLocalBankSituation(deleteParam);

        String buttonMark = voParam.getButtonMark();
        List<QqchLocalBankSituation> list = voParam.getList();
        if (!CollectionUtils.isEmpty(list)) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchLocalBankSituation qqchLocalBankSituation : voParam.getList()) {
                qqchLocalBankSituation.setId(IdWorker.createId());
                qqchLocalBankSituation.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchLocalBankSituation.setValid(Valid.YES);
                }
                qqchLocalBankSituation.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchLocalBankSituation.setCreateUserName(SecurityUtils.getUserName());
                qqchLocalBankSituation.setCreateTime(DateUtils.getNowDate());
            }
            qqchLocalBankSituationMapper.insertQqchLocalBankSituationList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            this.pushQyzsFinanceBankStatus(list);
        }
    }

    /**
     * 推送当地银行状况到总部版
     * @param list
     */
    public void pushQyzsFinanceBankStatus(List<QqchLocalBankSituation> list){
        Map<String,Object> map = new HashMap<>();

        String projectLocation = xmslProjectBasicInfoService.projectInfo().getProjectLocation();
        if(StringUtils.isBlank(projectLocation) || CollectionUtils.isEmpty(list)){
            return;
        }

        List<QyzsFinanceBankStatus> bankStatusList = new ArrayList<>();
        for (QqchLocalBankSituation qqchLocalBankSituation : list) {
            String isSelect = qqchLocalBankSituation.getIsSelect();
            if(!"1".equals(isSelect)){
                QyzsFinanceBankStatus bankStatus = new QyzsFinanceBankStatus();
                bankStatus.setId(IdWorker.createId());
                bankStatus.setCountryCode(projectLocation);
                bankStatus.setBankName(qqchLocalBankSituation.getBankName());
                bankStatus.setNature(qqchLocalBankSituation.getBankNature());
                bankStatus.setReputationAndService(qqchLocalBankSituation.getReputationService());
                bankStatus.setServiceEfficiency(qqchLocalBankSituation.getServiceEfficiency());
                bankStatus.setCashSituation(qqchLocalBankSituation.getAccessCash());
                bankStatus.setBusinessDealing(qqchLocalBankSituation.getBusinessDealing());
                bankStatus.setDepositInterestRate(qqchLocalBankSituation.getDepositRate());
                bankStatus.setLendingRate(qqchLocalBankSituation.getLendRate());
                bankStatus.setOffering(qqchLocalBankSituation.getProvideService());
                bankStatus.setCreateTime(DateUtils.getNowDate());
                bankStatus.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                bankStatus.setCreateUserName(SecurityUtils.getUserName());
                bankStatusList.add(bankStatus);
            }
        }

        if(CollectionUtils.isEmpty(bankStatusList)){
            return;
        }

        map.put("countryCode",projectLocation);
        map.put("bankStatusList",bankStatusList);
        rocketMQTemplate.convertAndSend("qyzs_finance_bank_status:tenantSuccess", map);
    }
}
