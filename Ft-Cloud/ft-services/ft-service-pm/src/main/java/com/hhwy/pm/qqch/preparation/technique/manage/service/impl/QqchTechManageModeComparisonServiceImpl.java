package com.hhwy.pm.qqch.preparation.technique.manage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.common.defaultData.service.IQqchDefaultDataInitializeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchTechManageModeComparison;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchTechManageModeComparisonVo;
import com.hhwy.pm.qqch.preparation.technique.manage.mapper.QqchTechManageModeComparisonMapper;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchTechManageModeComparisonService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
@Service
public class QqchTechManageModeComparisonServiceImpl implements IQqchTechManageModeComparisonService {

    @Autowired
    private QqchTechManageModeComparisonMapper qqchTechManageModeComparisonMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private SystemServiceApi systemServiceApi;
    @Autowired
    private IQqchDefaultDataInitializeService qqchDefaultDataInitializeService;

    public QqchTechManageModeComparisonVo getQqchTechManageModeComparisonList(BigDecimal version) {
        QqchTechManageModeComparisonVo vo = new QqchTechManageModeComparisonVo();
        version = VersionUtil.getVersion("qqch_tech_manage_mode_comparison", version);
        vo.setVersion(version);

        QqchTechManageModeComparison qryParam = new QqchTechManageModeComparison();
        qryParam.setVersion(version);
        List<QqchTechManageModeComparison> list = qqchTechManageModeComparisonMapper
            .getQqchTechManageModeComparisonList(qryParam);

        if (CollectionUtils.isEmpty(list)) {
            // 判断是否已经初始化过
            boolean initialize = qqchDefaultDataInitializeService
                .interpretInitializeStatus("qqch_tech_manage_mode_comparison", version);
            if (!initialize) {
                list = this.getInitializeData();
            }
            vo.setList(list);
        }

        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchTechManageModeComparisonVo voParam) {
        // 先批量删除当前版本所有数据
        QqchTechManageModeComparison deleteParam = new QqchTechManageModeComparison();
        deleteParam.setVersion(voParam.getVersion());
        qqchTechManageModeComparisonMapper.deleteQqchTechManageModeComparison(deleteParam);

        int sort = 1;
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchTechManageModeComparison qqchTechManageModeComparison : voParam.getList()) {
                qqchTechManageModeComparison.setId(IdWorker.createId());
                qqchTechManageModeComparison.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchTechManageModeComparison.setValid(Valid.YES);
                }
                qqchTechManageModeComparison.setSort(sort++);
                qqchTechManageModeComparison.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchTechManageModeComparison.setCreateUserName(SecurityUtils.getUserName());
                qqchTechManageModeComparison.setCreateTime(DateUtils.getNowDate());
            }
            qqchTechManageModeComparisonMapper.insertQqchTechManageModeComparisonList(voParam.getList());
        }

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 获取初始化数据
     *
     * @return
     */
    public List<QqchTechManageModeComparison> getInitializeData() {
        List<QqchTechManageModeComparison> fileList = new ArrayList<>();

        // 初始化数据
        AjaxResult result = systemServiceApi.dictType(DictType.MANAGE_MODE_INIT_DATA);
        List<Map<String, Object>> dictDataList = (List<Map<String, Object>>) result.get("data");

        for (Map<String, Object> map : dictDataList) {
            String dictLabel = (String) map.get("dictLabel");
            QqchTechManageModeComparison qqchTechManageModeComparison = new QqchTechManageModeComparison();
            qqchTechManageModeComparison.setManageMode(dictLabel);
            fileList.add(qqchTechManageModeComparison);
        }

        return fileList;
    }
}
