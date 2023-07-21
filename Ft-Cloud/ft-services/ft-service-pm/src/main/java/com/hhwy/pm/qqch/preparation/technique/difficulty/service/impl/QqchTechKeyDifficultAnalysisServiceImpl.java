package com.hhwy.pm.qqch.preparation.technique.difficulty.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.QqchTechKeyDifficultAnalysis;
import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.vo.QqchTechKeyDifficultAnalysisVo;
import com.hhwy.pm.qqch.preparation.technique.difficulty.mapper.QqchTechKeyDifficultAnalysisMapper;
import com.hhwy.pm.qqch.preparation.technique.difficulty.service.IQqchTechKeyDifficultAnalysisService;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-10 14:20:39
 * @remark 3.2施工技术重难点分析
 */
@Service
public class QqchTechKeyDifficultAnalysisServiceImpl implements IQqchTechKeyDifficultAnalysisService {

    @Autowired
    private QqchTechKeyDifficultAnalysisMapper qqchTechKeyDifficultAnalysisMapper;
    @Autowired
    private CommonMapper commonMapper;

    public QqchTechKeyDifficultAnalysisVo getQqchTechKeyDifficultAnalysisList(BigDecimal version) {
        QqchTechKeyDifficultAnalysisVo keyDifficultAnalysisVo = new QqchTechKeyDifficultAnalysisVo();
        if (version == null) {
            // 获取最大版本号
            version = commonMapper.selectMaxVersion("qqch_tech_key_difficult_analysis");
        }
        keyDifficultAnalysisVo.setVersion(version);

        QqchTechKeyDifficultAnalysis qryParam = new QqchTechKeyDifficultAnalysis();
        qryParam.setVersion(version);
        List<QqchTechKeyDifficultAnalysis> list = qqchTechKeyDifficultAnalysisMapper
            .getQqchTechKeyDifficultAnalysisList(qryParam);

        List<QqchTechKeyDifficultAnalysis> keyList = new ArrayList<>();
        List<QqchTechKeyDifficultAnalysis> difficultList = new ArrayList<>();

        for (QqchTechKeyDifficultAnalysis analysis : list) {
            // 技术重点
            if ("1".equals(analysis.getType())) {
                keyList.add(analysis);
            }
            // 技术难点
            if ("2".equals(analysis.getType())) {
                difficultList.add(analysis);
            }
        }

        keyDifficultAnalysisVo.setKeyAnalysisList(keyList);
        keyDifficultAnalysisVo.setDifficultAnalysisList(difficultList);
        return keyDifficultAnalysisVo;
    }

    @Transactional
    public void batchSave(QqchTechKeyDifficultAnalysisVo voParam) {
        // 先批量删除当前版本所有数据
        QqchTechKeyDifficultAnalysis deleteParam = new QqchTechKeyDifficultAnalysis();
        deleteParam.setVersion(voParam.getVersion());
        deleteParam.setDelFlag("1");
        qqchTechKeyDifficultAnalysisMapper.updateQqchTechKeyDifficultAnalysis(deleteParam);

        List<QqchTechKeyDifficultAnalysis> insertList = new ArrayList<>();

        // 技术重点
        if (!CollectionUtils.isEmpty(voParam.getKeyAnalysisList())) {
            for (QqchTechKeyDifficultAnalysis key : voParam.getKeyAnalysisList()) {
                key.setType("1");
                key.setId(IdWorker.createId());
                key.setVersion(voParam.getVersion());
                key.setValid(Valid.YES);
                key.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                key.setCreateUserName(SecurityUtils.getUserName());
                key.setCreateTime(DateUtils.getNowDate());
                insertList.add(key);
            }
        }

        // 技术难点
        if (!CollectionUtils.isEmpty(voParam.getDifficultAnalysisList())) {
            for (QqchTechKeyDifficultAnalysis difficult : voParam.getDifficultAnalysisList()) {
                if (difficult.getId() == null) {
                    difficult.setType("2");
                    difficult.setId(IdWorker.createId());
                    difficult.setVersion(voParam.getVersion());
                    difficult.setValid(Valid.YES);
                    difficult.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    difficult.setCreateUserName(SecurityUtils.getUserName());
                    difficult.setCreateTime(DateUtils.getNowDate());
                    insertList.add(difficult);
                }
            }
        }

        if (insertList.size() > 0) {
            qqchTechKeyDifficultAnalysisMapper.insertQqchTechKeyDifficultAnalysisList(insertList);
        }
    }
}
