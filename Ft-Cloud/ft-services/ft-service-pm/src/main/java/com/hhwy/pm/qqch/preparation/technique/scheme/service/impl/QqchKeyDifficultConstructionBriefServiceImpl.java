package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchKeyDifficultConstructionBrief;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchKeyDifficultConstructionBriefVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchKeyDifficultConstructionBriefMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchKeyDifficultConstructionBriefService;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenlili
 * @date 2023-07-17 15:29:49
 * @remark
 */
@Service
public class QqchKeyDifficultConstructionBriefServiceImpl implements IQqchKeyDifficultConstructionBriefService {

    @Autowired
    private QqchKeyDifficultConstructionBriefMapper qqchKeyDifficultConstructionBriefMapper;
    @Autowired
    private CommonMapper commonMapper;

    public QqchKeyDifficultConstructionBriefVo getQqchKeyDifficultConstructionBriefList() {
        QqchKeyDifficultConstructionBriefVo vo = new QqchKeyDifficultConstructionBriefVo();

        // 获取最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_key_difficult_construction_brief");
        vo.setVersion(maxVersion);

        QqchKeyDifficultConstructionBrief qryParam = new QqchKeyDifficultConstructionBrief();
        qryParam.setVersion(maxVersion);
        List<QqchKeyDifficultConstructionBrief> list = qqchKeyDifficultConstructionBriefMapper
            .getQqchKeyDifficultConstructionBriefList(qryParam);
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo) {
        if (qqchKeyDifficultConstructionBriefVo.getVersion() == null) {
            throw new RuntimeException("版本号不能为空！");
        }

        // 先批量删除当前版本所有数据
        QqchKeyDifficultConstructionBrief deleteParam = new QqchKeyDifficultConstructionBrief();
        deleteParam.setVersion(qqchKeyDifficultConstructionBriefVo.getVersion());
        deleteParam.setDelFlag("1");
        qqchKeyDifficultConstructionBriefMapper.updateQqchKeyDifficultConstructionBrief(deleteParam);

        if (CollectionUtils.isEmpty(qqchKeyDifficultConstructionBriefVo.getList())) {
            return;
        }

        List<QqchKeyDifficultConstructionBrief> insertList = new ArrayList<>();

        // 技术重点
        for (QqchKeyDifficultConstructionBrief brief : qqchKeyDifficultConstructionBriefVo.getList()) {
            brief.setId(IdWorker.createId());
            brief.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            brief.setCreateUserName(SecurityUtils.getUserName());
            brief.setCreateTime(DateUtils.getNowDate());

            brief.setVersion(qqchKeyDifficultConstructionBriefVo.getVersion());
            brief.setValid("1");
            insertList.add(brief);
        }

        if (insertList.size() > 0) {
            qqchKeyDifficultConstructionBriefMapper.insertQqchKeyDifficultConstructionBriefList(insertList);
        }
    }
}
