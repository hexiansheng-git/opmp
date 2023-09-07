package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisCorrect;
import com.hhwy.pm.jdgl.diff.analysis.domain.vo.DiffAnalysisQueryVo;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisCorrectService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisPathService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisSvService;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;
import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private IJdglDiffAnalysisSvService iJdglDiffAnalysisSvService;

    @Autowired
    private IQqchScheService qqchScheService;
    
    @Autowired
    private IJdglDiffAnalysisPathService jdglDiffAnalysisPathService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;

    @Autowired
    private ISysSyncInfoService sysSyncInfoService;


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
        Date period = jdglDiffAnalysis.getPeriod();
        jdglDiffAnalysis.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysis.setUpdateTime(DateUtils.getNowDate());

        BigDecimal totalCompValue = jdglDiffAnalysis.getTotalCompValue();
        BigDecimal totalMeterValue = jdglDiffAnalysis.getMeterValue() == null ? new BigDecimal(0) : jdglDiffAnalysis.getMeterValue();

        QqchScheDTO dto = new QqchScheDTO();

        List<JdglDiffAnalysis> jdglDiffAnalysisList = getJdglDiffAnalysisList(new JdglDiffAnalysis());

        if(!CollectionUtils.isEmpty(jdglDiffAnalysisList)) {
            for (JdglDiffAnalysis jdglDiffAnalysis1 : jdglDiffAnalysisList) {
                if(period.after(jdglDiffAnalysis1.getPeriod())) {
                    totalMeterValue = totalMeterValue.add(jdglDiffAnalysis1.getMeterValue());
                }
            }
        }
        BigDecimal sumMin = new BigDecimal(0);
        if(totalCompValue != null && new BigDecimal(0).equals(totalCompValue)) {
            sumMin = totalMeterValue.divide(totalCompValue);
        }

        // 调取获取进度差异化管控策划列表接口
        QqchScheDTO list = qqchScheService.list(dto);

        if(list != null) {
            List<QqchScheAnalyse> analyseList = list.getAnalyseList();
            if(!CollectionUtils.isEmpty(analyseList)) {
                for (QqchScheAnalyse qqchScheAnalyse : analyseList) {
                    BigDecimal score = qqchScheAnalyse.getScore();
                    BigDecimal sumMaxScore = qqchScheAnalyse.getSumMaxScore();
                    BigDecimal sumMinScore = qqchScheAnalyse.getSumMinScore();

                    if(sumMin.compareTo(sumMaxScore) < 0 && sumMin.compareTo(sumMinScore) >= 0) {
                        jdglDiffAnalysis.setValueGrade(score);
                    }
                }
            }
        }

        sysSyncInfoService.pushJdglDiffAnalysis(jdglDiffAnalysis);

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
        jdglDiffAnalysis.setId(IdWorker.createId());
        jdglDiffAnalysis.setPeriod(nowDate);
        jdglDiffAnalysis.setCreateTime(nowDate);
        jdglDiffAnalysis.setCreateUser(SecurityUtils.getUserName());

        QqchScheDTO dto = new QqchScheDTO();

        // 调取获取进度差异化管控策划列表接口
        QqchScheDTO list = qqchScheService.list(dto);

        List<QqchScheAnalyse> analyseList = null;

        if(list != null) {
            analyseList = list.getAnalyseList();
        }

        // 初始化sv曲线
        BigDecimal diffGradeValue = iJdglDiffAnalysisSvService.initJdglDiffAnalysisSv(jdglDiffAnalysis);
        
        // 初始化关键线路
        BigDecimal keyGradeValue = jdglDiffAnalysisPathService.initKeyJdglDiffAnalysisPath(jdglDiffAnalysis);

        // 初始化非关键线路
        jdglDiffAnalysisPathService.initNotKeyJdglDiffAnalysisPath(jdglDiffAnalysis);

        // 合同超期
        XmslContractInfo validMaxVersionContractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();

        String isOver = "";
        if(validMaxVersionContractInfo != null) {
            Date nowDate1 = DateUtils.getNowDate();
            Date handoverTime = validMaxVersionContractInfo.getHandoverTime();
            if(nowDate1.before(handoverTime)) {
                isOver = "1";
            }
            jdglDiffAnalysis.setContractAmtDl(validMaxVersionContractInfo.getEffectiveAmout());
            jdglDiffAnalysis.setContractStartDate(validMaxVersionContractInfo.getStartTime());
            jdglDiffAnalysis.setContractEndDate(validMaxVersionContractInfo.getCompletedTime());
        }



        ProjectBasicInfo projectInfo = projectBasicInfoService.projectInfo();
        // 项目规模
        String type1 = "1-1,1-2";
        String type2 = "";
        BigDecimal scaleGradeValue = new BigDecimal(0);

        // 项目重要性
        String weightedGrade = projectInfo.getWeightedGrade();


        if(validMaxVersionContractInfo != null) {
            BigDecimal effectiveAmout = validMaxVersionContractInfo.getEffectiveAmout();
            String duration = validMaxVersionContractInfo.getDuration();
            BigDecimal durationM = BigDecimal.valueOf(Double.valueOf(duration)/12);
            if(durationM != null && new BigDecimal(0).equals(durationM)) {
                scaleGradeValue = effectiveAmout.divide(durationM);
            }
        }

        if(!CollectionUtils.isEmpty(analyseList)) {
            for (QqchScheAnalyse qqchScheAnalyse : analyseList) {
                // 得分
                BigDecimal score = qqchScheAnalyse.getScore();
                // s差异最大
                BigDecimal diffMaxScore = qqchScheAnalyse.getDiffMaxScore();
                // s差异最小
                BigDecimal diffMinScore = qqchScheAnalyse.getDiffMinScore();
                // 关键线路最大
                BigDecimal lineMaxScore = qqchScheAnalyse.getLineMaxScore();
                // 关键线路最小
                BigDecimal lineMinScore = qqchScheAnalyse.getLineMinScore();
                // 合同超期
                String contFlag = qqchScheAnalyse.getContFlag();
                // 重要性
                String importance = qqchScheAnalyse.getImportance();
                // 公路铁路最大
                BigDecimal roadMaxScore = qqchScheAnalyse.getRoadMaxScore();
                // 公路铁路最小
                BigDecimal roadMinScore = qqchScheAnalyse.getRoadMinScore();
                // 基建房建最大
                BigDecimal buildMaxScore = qqchScheAnalyse.getBuildMaxScore();
                // 基建房建最小
                BigDecimal buildMinScore = qqchScheAnalyse.getBuildMinScore();

                if(diffGradeValue.compareTo(diffMaxScore) < 0 && diffGradeValue.compareTo(diffMinScore) >= 0){
                    jdglDiffAnalysis.setSDiffGrade(score);
                }
                if(keyGradeValue.compareTo(lineMaxScore) <0 && keyGradeValue.compareTo(lineMinScore) >= 0) {
                    jdglDiffAnalysis.setKeyGrade(score);
                }
                if(isOver.equals(contFlag)) {
                    jdglDiffAnalysis.setContractOverGrade(score);
                }
                if(StringUtils.isNotEmpty(weightedGrade) && weightedGrade.equals(importance)) {
                    jdglDiffAnalysis.setImportanceGrade(score);
                }
                if(type1.contains(projectInfo.getBusinessAreasAndProducts())
                        && scaleGradeValue.compareTo(roadMaxScore) < 0 && scaleGradeValue.compareTo(roadMinScore) >= 0) {
                    jdglDiffAnalysis.setScaleGrade(score);
                }
                if(type2.contains(projectInfo.getBusinessAreasAndProducts())
                        && scaleGradeValue.compareTo(buildMaxScore) < 0 && scaleGradeValue.compareTo(buildMinScore) >= 0) {
                    jdglDiffAnalysis.setScaleGrade(score);
                }
            }
        }

        int i = jdglDiffAnalysisMapper.insertJdglDiffAnalysis(jdglDiffAnalysis);

        if(i > 0) {
            sysSyncInfoService.pushJdglDiffAnalysis(jdglDiffAnalysis);
        }

    }

    @Override
    public List<JdglDiffAnalysis> gmList(DiffAnalysisQueryVo queryVo) {
        List<JdglDiffAnalysis> jdglDiffAnalysisList = jdglDiffAnalysisMapper.gmList(queryVo);
        if(CollectionUtils.isEmpty(jdglDiffAnalysisList)) {
            return jdglDiffAnalysisList;
        }
        for (JdglDiffAnalysis diffAnalysis : jdglDiffAnalysisList) {
            // 修正表单数据
            JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect = new JdglDiffAnalysisCorrect();
            jdglDiffAnalysisCorrect.setDiffAnalysisId(diffAnalysis.getId());
            Map<String, List<JdglDiffAnalysisCorrect>> jdglDiffAnalysisCorrectMapList = iJdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectMapList(jdglDiffAnalysisCorrect);
            diffAnalysis.setJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectMapList);
        }
        return jdglDiffAnalysisList;
    }

}













