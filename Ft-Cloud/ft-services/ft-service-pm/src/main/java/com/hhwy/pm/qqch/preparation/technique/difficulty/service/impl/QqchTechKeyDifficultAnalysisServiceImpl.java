package com.hhwy.pm.qqch.preparation.technique.difficulty.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.QqchTechKeyDifficultAnalysis;
import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.QqchTechKeyDifficultAnalysisVo;
import com.hhwy.pm.qqch.preparation.technique.difficulty.mapper.QqchTechKeyDifficultAnalysisMapper;
import com.hhwy.pm.qqch.preparation.technique.difficulty.service.IQqchTechKeyDifficultAnalysisService;
import com.hhwy.utils.idworker.IdWorker;
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

    public QqchTechKeyDifficultAnalysisVo getQqchTechKeyDifficultAnalysisList(
        QqchTechKeyDifficultAnalysis qqchTechKeyDifficultAnalysis) {
        QqchTechKeyDifficultAnalysisVo keyDifficultAnalysisVo = new QqchTechKeyDifficultAnalysisVo();

        List<QqchTechKeyDifficultAnalysis> list = qqchTechKeyDifficultAnalysisMapper
            .getQqchTechKeyDifficultAnalysisList(qqchTechKeyDifficultAnalysis);

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
    public void batchSave(QqchTechKeyDifficultAnalysisVo qqchTechKeyDifficultAnalysisVo) {
        List<QqchTechKeyDifficultAnalysis> insertList = new ArrayList<>();
        List<QqchTechKeyDifficultAnalysis> updateList = new ArrayList<>();

        // 技术重点
        if (!CollectionUtils.isEmpty(qqchTechKeyDifficultAnalysisVo.getKeyAnalysisList())) {
            for (QqchTechKeyDifficultAnalysis key : qqchTechKeyDifficultAnalysisVo.getKeyAnalysisList()) {
                if (key.getId() == null) {
                    key.setType("1");
                    key.setId(IdWorker.createId());
                    key.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    key.setCreateUserName(SecurityUtils.getUserName());
                    key.setCreateTime(DateUtils.getNowDate());
                    insertList.add(key);
                } else {
                    key.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    key.setUpdateTime(DateUtils.getNowDate());
                    updateList.add(key);
                }
            }
        }

        // 技术难点
        if (!CollectionUtils.isEmpty(qqchTechKeyDifficultAnalysisVo.getDifficultAnalysisList())) {
            for (QqchTechKeyDifficultAnalysis difficult : qqchTechKeyDifficultAnalysisVo.getDifficultAnalysisList()) {
                if (difficult.getId() == null) {
                    difficult.setType("2");
                    difficult.setId(IdWorker.createId());
                    difficult.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    difficult.setCreateUserName(SecurityUtils.getUserName());
                    difficult.setCreateTime(DateUtils.getNowDate());
                    insertList.add(difficult);
                } else {
                    difficult.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                    difficult.setUpdateTime(DateUtils.getNowDate());
                    updateList.add(difficult);
                }
            }
        }

        if (insertList.size() > 0) {
            qqchTechKeyDifficultAnalysisMapper.insertQqchTechKeyDifficultAnalysisList(insertList);
        }
        if (updateList.size() > 0) {
            qqchTechKeyDifficultAnalysisMapper.updateQqchTechKeyDifficultAnalysisList(updateList);
        }
    }


    @Transactional
    public int deleteQqchTechKeyDifficultAnalysisByPks(List<Long> qqchTechKeyDifficultAnalysisPkList) {
        return qqchTechKeyDifficultAnalysisMapper
            .deleteQqchTechKeyDifficultAnalysisByPks(qqchTechKeyDifficultAnalysisPkList);
    }
}
