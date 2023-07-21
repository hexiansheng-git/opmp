package com.hhwy.pm.qqch.preparation.technique.manage.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchTechManageModeComparison;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.vo.QqchTechManageModeComparisonVo;
import com.hhwy.pm.qqch.preparation.technique.manage.mapper.QqchTechManageModeComparisonMapper;
import com.hhwy.pm.qqch.preparation.technique.manage.service.IQqchTechManageModeComparisonService;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.List;
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
    private CommonMapper commonMapper;

    public QqchTechManageModeComparisonVo getQqchTechManageModeComparisonList() {
        QqchTechManageModeComparisonVo vo = new QqchTechManageModeComparisonVo();
        // 获取最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_tech_manage_mode_comparison");

        QqchTechManageModeComparison qryParam = new QqchTechManageModeComparison();
        qryParam.setVersion(maxVersion);
        List<QqchTechManageModeComparison> list = qqchTechManageModeComparisonMapper
            .getQqchTechManageModeComparisonList(qryParam);
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchTechManageModeComparisonVo voParam) {
        if (voParam.getVersion() == null) {
            // 获取最大版本号
            BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_tech_manage_mode_comparison");
            voParam.setVersion(maxVersion);
        }

        // 先批量删除当前版本所有数据
        QqchTechManageModeComparison deleteParam = new QqchTechManageModeComparison();
        deleteParam.setVersion(voParam.getVersion());
        deleteParam.setDelFlag("1");
        qqchTechManageModeComparisonMapper.updateQqchTechManageModeComparison(deleteParam);

        if (CollectionUtils.isEmpty(voParam.getList())) {
            return;
        }

        for (QqchTechManageModeComparison qqchTechManageModeComparison : voParam.getList()) {
            qqchTechManageModeComparison.setId(IdWorker.createId());
            qqchTechManageModeComparison.setVersion(voParam.getVersion());
            qqchTechManageModeComparison.setValid(Valid.YES);
            qqchTechManageModeComparison.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchTechManageModeComparison.setCreateUserName(SecurityUtils.getUserName());
            qqchTechManageModeComparison.setCreateTime(DateUtils.getNowDate());
        }

        qqchTechManageModeComparisonMapper.insertQqchTechManageModeComparisonList(voParam.getList());
    }
}
