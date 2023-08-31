package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisCorrect;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisCorrectService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 陈锦豪
 * @date 2023-08-28 15:06:24
 * @remark
 */
@Service
public class JdglDiffAnalysisServiceImpl implements IJdglDiffAnalysisService {

    @Autowired
    private JdglDiffAnalysisMapper jdglDiffAnalysisMapper;

    @Autowired
    private IJdglDiffAnalysisCorrectService iJdglDiffAnalysisCorrectService;


    public JdglDiffAnalysis getJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis) {
        JdglDiffAnalysis jdglDiffAnalysis1 = jdglDiffAnalysisMapper.getJdglDiffAnalysis(jdglDiffAnalysis);
        if(jdglDiffAnalysis1 == null) {
            return null;
        }
        // 修正表单数据
        JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect = new JdglDiffAnalysisCorrect();
        jdglDiffAnalysisCorrect.setDiffAnalysisId(jdglDiffAnalysis1.getId());
        Map<String, List<JdglDiffAnalysisCorrect>> jdglDiffAnalysisCorrectMapList = iJdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectMapList(jdglDiffAnalysisCorrect);
        jdglDiffAnalysis1.setJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectMapList);
        return jdglDiffAnalysis1;
    }

    public List<JdglDiffAnalysis> getJdglDiffAnalysisList(JdglDiffAnalysis jdglDiffAnalysis) {
        List<JdglDiffAnalysis> jdglDiffAnalysisList = jdglDiffAnalysisMapper.getJdglDiffAnalysisList(jdglDiffAnalysis);
        if(CollectionUtils.isEmpty(jdglDiffAnalysisList)) {
            return jdglDiffAnalysisList;
        }
        for (JdglDiffAnalysis jdglDiffAnalysis1 : jdglDiffAnalysisList) {
            // 修正表单数据
            JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect = new JdglDiffAnalysisCorrect();
            jdglDiffAnalysisCorrect.setDiffAnalysisId(jdglDiffAnalysis1.getId());
            Map<String, List<JdglDiffAnalysisCorrect>> jdglDiffAnalysisCorrectMapList = iJdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectMapList(jdglDiffAnalysisCorrect);
            jdglDiffAnalysis1.setJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectMapList);
        }
        return jdglDiffAnalysisList;
    }

    @Transactional
    public int insertJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis) {
        jdglDiffAnalysis.setId(IdWorker.createId());
        jdglDiffAnalysis.setCreateUser(SecurityUtils.getUserName());
        jdglDiffAnalysis.setCreateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisMapper.insertJdglDiffAnalysis(jdglDiffAnalysis);
    }

    @Transactional
    public int insertJdglDiffAnalysisList(List<JdglDiffAnalysis> jdglDiffAnalysisList) {
        for (JdglDiffAnalysis jdglDiffAnalysis : jdglDiffAnalysisList) {
            jdglDiffAnalysis.setId(IdWorker.createId());
            jdglDiffAnalysis.setCreateUser(SecurityUtils.getUserName());
            jdglDiffAnalysis.setCreateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisMapper.insertJdglDiffAnalysisList(jdglDiffAnalysisList);
    }

    @Transactional
    public int updateJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis) {
        jdglDiffAnalysis.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysis.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisMapper.updateJdglDiffAnalysis(jdglDiffAnalysis);
    }

    @Transactional
    public int updateJdglDiffAnalysisList(List<JdglDiffAnalysis> jdglDiffAnalysisList) {
        for (JdglDiffAnalysis jdglDiffAnalysis : jdglDiffAnalysisList) {
            jdglDiffAnalysis.setUpdateUser(SecurityUtils.getUserName());
            jdglDiffAnalysis.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisMapper.updateJdglDiffAnalysisList(jdglDiffAnalysisList);
    }

    @Transactional
    public int deleteJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis) {
        jdglDiffAnalysis.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysis.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisMapper.deleteJdglDiffAnalysis(jdglDiffAnalysis);
    }

    @Transactional
    public int deleteJdglDiffAnalysisByPks(List<Long> jdglDiffAnalysisPkList) {
        return jdglDiffAnalysisMapper.deleteJdglDiffAnalysisByPks(jdglDiffAnalysisPkList);
    }

    /**
     * 生成差异化数据
     */
    @Override
    public void initDiffAnalysis() {
        JdglDiffAnalysis jdglDiffAnalysis = new JdglDiffAnalysis();
        Date nowDate = new Date();
        jdglDiffAnalysis.setPeriod(nowDate);

        //同步SV偏差分析
        //获取本月计划

    }

}













