package com.hhwy.pm.qqch.preparation.safe.monitor.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.QqchVideoMonitorInfo;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo.QqchVideoMonitorInfoVo;
import com.hhwy.pm.qqch.preparation.safe.monitor.mapper.QqchVideoMonitorInfoMapper;
import com.hhwy.pm.qqch.preparation.safe.monitor.service.IQqchVideoMonitorInfoService;
import com.hhwy.pm.qqch.preparation.survey.extend.service.impl.QqchPreparationSurveyExtendServiceImpl;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:39
 * @remark 8.12.1 视频监控信息
 */
@Service
public class QqchVideoMonitorInfoServiceImpl implements IQqchVideoMonitorInfoService {

    @Autowired
    private QqchVideoMonitorInfoMapper qqchVideoMonitorInfoMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private QqchPreparationSurveyExtendServiceImpl qqchPreparationSurveyExtendService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchVideoMonitorInfoVo getQqchVideoMonitorInfoList(BigDecimal version) {
        QqchVideoMonitorInfoVo vo = new QqchVideoMonitorInfoVo();
        version = VersionUtil.getVersion("qqch_video_monitor_info", version);

        QqchVideoMonitorInfo qryParam = new QqchVideoMonitorInfo();
        qryParam.setVersion(version);
        List<QqchVideoMonitorInfo> list = qqchVideoMonitorInfoMapper.getQqchVideoMonitorInfoList(qryParam);

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
    public void batchSave(QqchVideoMonitorInfoVo voParam) {
        // 清空数据库表中数据
        QqchVideoMonitorInfo deleteParam = new QqchVideoMonitorInfo();
        deleteParam.setVersion(voParam.getVersion());
        qqchVideoMonitorInfoMapper.deleteQqchVideoMonitorInfo(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchVideoMonitorInfo qqchVideoMonitorInfo : voParam.getList()) {
                qqchVideoMonitorInfo.setId(IdWorker.createId());
                qqchVideoMonitorInfo.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchVideoMonitorInfo.setValid(Valid.YES);
                }
                qqchVideoMonitorInfo.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchVideoMonitorInfo.setCreateUserName(SecurityUtils.getUserName());
                qqchVideoMonitorInfo.setCreateTime(DateUtils.getNowDate());
            }
            qqchVideoMonitorInfoMapper.insertQqchVideoMonitorInfoList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
