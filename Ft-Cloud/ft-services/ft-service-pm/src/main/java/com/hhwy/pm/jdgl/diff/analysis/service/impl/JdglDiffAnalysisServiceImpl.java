package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisCorrect;
import com.hhwy.pm.jdgl.diff.analysis.domain.vo.DiffAnalysisQueryVo;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisCorrectService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisPathService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisSvService;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiff;
import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IJdglCorrectionMeasuresMakeService jdglCorrectionMeasuresMakeService;


    public JdglDiffAnalysis getJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis) {
        JdglDiffAnalysis jdglDiffAnalysis1 = jdglDiffAnalysisMapper.getJdglDiffAnalysis(jdglDiffAnalysis);
        if(jdglDiffAnalysis1 == null) {
            return null;
        }
        // 修正表单数据
        JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect = new JdglDiffAnalysisCorrect();
        jdglDiffAnalysisCorrect.setDiffAnalysisId(jdglDiffAnalysis1.getId());
        List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList = iJdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrect);
//        Map<String, List<JdglDiffAnalysisCorrect>> jdglDiffAnalysisCorrectMapList = iJdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectMapList(jdglDiffAnalysisCorrect);
//        jdglDiffAnalysis1.setJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectMapList);
        jdglDiffAnalysis1.setJdglDiffAnalysisCorrectList4push(jdglDiffAnalysisCorrectList);
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
//        jdglDiffAnalysis.setCreateUser(SecurityUtils.getUserName());
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

        if(jdglDiffAnalysis.getId() == null) {
            return 0;
        }

//        jdglDiffAnalysis.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysis.setUpdateTime(DateUtils.getNowDate());

        BigDecimal totalCompValue = jdglDiffAnalysis.getTotalCompValue();
        BigDecimal totalMeterValue = jdglDiffAnalysis.getMeterValue();

        QqchScheDTO dto = new QqchScheDTO();

        List<JdglDiffAnalysis> jdglDiffAnalysisList = getJdglDiffAnalysisList(new JdglDiffAnalysis());

        if(!CollectionUtils.isEmpty(jdglDiffAnalysisList)) {
            for (JdglDiffAnalysis jdglDiffAnalysis1 : jdglDiffAnalysisList) {
                if(period.after(jdglDiffAnalysis1.getPeriod())) {
                    if(totalMeterValue != null && jdglDiffAnalysis1.getMeterValue() != null) {
                        totalMeterValue = totalMeterValue.add(jdglDiffAnalysis1.getMeterValue());
                    }
                }
            }
        }
        BigDecimal sumMin = null;
        if(totalMeterValue != null && totalCompValue != null && BigDecimal.ZERO.compareTo(totalCompValue) != 0) {
            sumMin = totalMeterValue.divide(totalCompValue, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
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

                    if(sumMin != null && (sumMaxScore == null || sumMin.compareTo(sumMaxScore) < 0) && (sumMinScore == null || sumMin.compareTo(sumMinScore) >= 0)) {
                        jdglDiffAnalysis.setValueGrade(score);
                        countTotalGrade(jdglDiffAnalysis);
                    }
                }
            }
        }

        int i = jdglDiffAnalysisMapper.updateJdglDiffAnalysis(jdglDiffAnalysis);

        if(i > 0) {
            sysSyncInfoService.pushJdglDiffAnalysis(jdglDiffAnalysis);
            if(jdglDiffAnalysis.getRiskLevel() != null) {
                jdglCorrectionMeasuresMakeService.syncData(period);
            }
        }

        return i;
    }

    /**
     * 计算总得分及风险等级
     * @param jdglDiffAnalysis
     * @return
     */
    public JdglDiffAnalysis countTotalGrade(JdglDiffAnalysis jdglDiffAnalysis) {

        if(jdglDiffAnalysis == null) {
            return jdglDiffAnalysis;
        }

        if(jdglDiffAnalysis.getValueGrade() == null || jdglDiffAnalysis.getCorrectGrade() == null) {
            return jdglDiffAnalysis;
        }

        // 总得分
        BigDecimal thisTotalGrage = BigDecimal.ZERO;
        // 修改前总得分
        BigDecimal oldTotalGrage = jdglDiffAnalysis.getTotalGrade();

        // 累计计量产值/累计施工产值得分
        thisTotalGrage = thisTotalGrage.add(jdglDiffAnalysis.getValueGrade());

        // 修正得分
        thisTotalGrage = thisTotalGrage.add(jdglDiffAnalysis.getCorrectGrade());

        // S曲线差异得分
        if(jdglDiffAnalysis.getSDiffGrade() != null) {
            thisTotalGrage = thisTotalGrage.add(jdglDiffAnalysis.getSDiffGrade());
        }

        // 关键线路形象进度得分
        if(jdglDiffAnalysis.getKeyGrade() != null) {
            thisTotalGrage = thisTotalGrage.add(jdglDiffAnalysis.getKeyGrade());
        }

        // 合同超期得分
        if(jdglDiffAnalysis.getContractOverGrade() != null) {
            thisTotalGrage = thisTotalGrage.add(jdglDiffAnalysis.getContractOverGrade());
        }

        // 重要性得分
        if(jdglDiffAnalysis.getImportanceGrade() != null) {
            thisTotalGrage = thisTotalGrage.add(jdglDiffAnalysis.getImportanceGrade());
        }

        // 重要性得分
        if(jdglDiffAnalysis.getScaleGrade() != null) {
            thisTotalGrage = thisTotalGrage.add(jdglDiffAnalysis.getScaleGrade());
        }

        jdglDiffAnalysis.setTotalGrade(thisTotalGrage);

        if(oldTotalGrage == null || thisTotalGrage.compareTo(oldTotalGrage) != 0) {

            jdglDiffAnalysis.setIsWarn(true);

            QqchScheDTO dto = new QqchScheDTO();
            // 调取获取进度差异化管控策划列表接口
            QqchScheDTO list = qqchScheService.list(dto);

            if(list == null || CollectionUtils.isEmpty(list.getDiffList())) {
                return jdglDiffAnalysis;
            }

            List<QqchScheDiff> diffList = list.getDiffList();
            for (QqchScheDiff qqchScheDiff : diffList) {
                BigDecimal maxScore = qqchScheDiff.getMaxScore();
                BigDecimal minScore = qqchScheDiff.getMinScore();
                if(minScore != null && maxScore != null) {
                    if(thisTotalGrage.compareTo(minScore) >= 0 && thisTotalGrage.compareTo(maxScore) <= 0) {
                        jdglDiffAnalysis.setRiskLevel(qqchScheDiff.getRiskLevel());
                    }
                }
            }

        }

        return jdglDiffAnalysis;
    }

    @Transactional
    public int updateJdglDiffAnalysisList(List<JdglDiffAnalysis> jdglDiffAnalysisList) {
        if(CollectionUtils.isEmpty(jdglDiffAnalysisList)) {
            return 0;
        }
        for (JdglDiffAnalysis jdglDiffAnalysis : jdglDiffAnalysisList) {
            jdglDiffAnalysis.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            jdglDiffAnalysis.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisMapper.updateJdglDiffAnalysisList(jdglDiffAnalysisList);
    }

    @Transactional
    public int deleteJdglDiffAnalysis(JdglDiffAnalysis jdglDiffAnalysis) {
//        jdglDiffAnalysis.setUpdateUser(SecurityUtils.getUserName());
//        jdglDiffAnalysis.setUpdateTime(DateUtils.getNowDate());
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

        // 获取租户集合
        List<String> tenantKeyList = new ArrayList<>();
        //
        List<SysTenant> sysTenants = systemServiceApi.tenantList();

        if(!CollectionUtils.isEmpty(sysTenants)) {
            sysTenants.forEach(vo -> tenantKeyList.add(vo.getTenantKey()));
        }

        if(!CollectionUtils.isEmpty(tenantKeyList)) {
            for (String tenantKey : tenantKeyList) {
                //切换租户
                String oldDataSource = DynamicDataSourceContextHolder.peek();
                DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
                try {
                    initDiffData();
                }catch (Exception e){
                    e.printStackTrace();
                    throw new CustomBusinessException(e.getMessage());
                }finally {
                    DynamicDataSourceContextHolder.poll();
                    DynamicDataSourceContextHolder.push(oldDataSource);
                }
            }
        }

    }

    @Transactional
    public void initDiffData() {
        JdglDiffAnalysis jdglDiffAnalysis = new JdglDiffAnalysis();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date nowDate = null;
//        try {
//            nowDate = simpleDateFormat.parse("2023-10-20");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Date nowDate = FtDateUtils.getYearMonthDate(new Date());

        deleteDiffAnalysisByPeriod(nowDate);

        jdglDiffAnalysis.setId(IdWorker.createId());
        jdglDiffAnalysis.setPeriod(nowDate);
        jdglDiffAnalysis.setCreateTime(nowDate);
//        jdglDiffAnalysis.setCreateUser(SecurityUtils.getUserName());

        QqchScheDTO dto = new QqchScheDTO();

        // 调取获取进度差异化管控策划列表接口
        QqchScheDTO list = qqchScheService.list(dto);

        List<QqchScheAnalyse> analyseList = null;

        if(list != null) {
            analyseList = list.getAnalyseList();
        }

        // 初始化sv曲线
        BigDecimal diffGradeValue = iJdglDiffAnalysisSvService.initJdglDiffAnalysisSv(jdglDiffAnalysis);
        if(diffGradeValue == null) diffGradeValue = BigDecimal.ZERO;

        // 初始化关键线路
        BigDecimal keyGradeValue = jdglDiffAnalysisPathService.initKeyJdglDiffAnalysisPath(jdglDiffAnalysis);
        if(keyGradeValue == null) keyGradeValue = BigDecimal.ZERO;

        // 初始化非关键线路
        jdglDiffAnalysisPathService.initNotKeyJdglDiffAnalysisPath(jdglDiffAnalysis);

        // 合同超期
        XmslContractInfo validMaxVersionContractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();

        String isOver = "";
        BigDecimal effectiveAmountDollar = BigDecimal.ZERO;
        if(validMaxVersionContractInfo != null) {
            Date nowDate1 = DateUtils.getNowDate();
            Date handoverTime = validMaxVersionContractInfo.getHandoverTime();
            if(handoverTime != null && nowDate1.after(handoverTime)) {
                isOver = "1";
            }
            effectiveAmountDollar = validMaxVersionContractInfo.getEffectiveAmountDollar();
            if(effectiveAmountDollar != null)jdglDiffAnalysis.setContractAmtDl(StatisticsUtils.getDivideTenThousand(effectiveAmountDollar));
            jdglDiffAnalysis.setContractStartDate(validMaxVersionContractInfo.getStartTime());
            jdglDiffAnalysis.setContractEndDate(validMaxVersionContractInfo.getCompletedTime());
        }



        ProjectBasicInfo projectInfo = projectBasicInfoService.projectInfo();
        // 项目规模
        String type11 = "1-1";
        String type12 = "1-2";
        String type29 = "2-9";
        BigDecimal scaleGradeValue = BigDecimal.ZERO;

        // 项目重要性
        String weightedGrade = projectInfo.getWeightedGrade();

        if(validMaxVersionContractInfo != null) {
            String duration = validMaxVersionContractInfo.getDuration();
            BigDecimal durationM = duration == null ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(duration)/12);
            if(effectiveAmountDollar != null && BigDecimal.ZERO.compareTo(durationM) != 0) {
                scaleGradeValue = effectiveAmountDollar.divide(durationM, 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
            }
        }

        if(!CollectionUtils.isEmpty(analyseList)) {
            for (QqchScheAnalyse qqchScheAnalyse : analyseList) {
                // 得分
                BigDecimal score = qqchScheAnalyse.getScore() == null ? BigDecimal.ZERO : qqchScheAnalyse.getScore();
                // s差异最大
                BigDecimal diffMaxScore = qqchScheAnalyse.getDiffMaxScore();//== null ? BigDecimal.ZERO : qqchScheAnalyse.getDiffMaxScore();
                // s差异最小
                BigDecimal diffMinScore = qqchScheAnalyse.getDiffMinScore();// == null ? BigDecimal.ZERO : qqchScheAnalyse.getDiffMinScore();
                // 关键线路最大
                BigDecimal lineMaxScore = qqchScheAnalyse.getLineMaxScore();// == null ? BigDecimal.ZERO : qqchScheAnalyse.getLineMaxScore();
                // 关键线路最小
                BigDecimal lineMinScore = qqchScheAnalyse.getLineMinScore();// == null ? BigDecimal.ZERO : qqchScheAnalyse.getLineMinScore();
                // 合同超期
                String contFlag = qqchScheAnalyse.getContFlag();
                // 重要性
                String importance = qqchScheAnalyse.getImportance();
                // 公路铁路最大
                BigDecimal roadMaxScore = qqchScheAnalyse.getRoadMaxScore();// == null ? BigDecimal.ZERO : qqchScheAnalyse.getRoadMaxScore();
                // 公路铁路最小
                BigDecimal roadMinScore = qqchScheAnalyse.getRoadMinScore();// == null ? BigDecimal.ZERO : qqchScheAnalyse.getRoadMinScore();
                // 基建房建最大
                BigDecimal buildMaxScore = qqchScheAnalyse.getBuildMaxScore();// == null ? BigDecimal.ZERO : qqchScheAnalyse.getBuildMaxScore();
                // 基建房建最小
                BigDecimal buildMinScore = qqchScheAnalyse.getBuildMinScore();// == null ? BigDecimal.ZERO : qqchScheAnalyse.getBuildMinScore();

                if((diffMaxScore == null || diffGradeValue.compareTo(diffMaxScore) < 0) && (diffMinScore == null || diffGradeValue.compareTo(diffMinScore) >= 0)){
                    jdglDiffAnalysis.setSDiffGrade(score);
                }
                if((lineMaxScore == null || keyGradeValue.compareTo(lineMaxScore) <0) && (lineMinScore == null || keyGradeValue.compareTo(lineMinScore) >= 0)) {
                    jdglDiffAnalysis.setKeyGrade(score);
                }
                if(isOver.equals(contFlag)) {
                    jdglDiffAnalysis.setContractOverGrade(score);
                }
                if(StringUtils.isNotEmpty(weightedGrade) && weightedGrade.equals(importance)) {
                    jdglDiffAnalysis.setImportanceGrade(score);
                }
                if(projectInfo.getBusinessAreasAndProducts() != null) {
                    String businessAreasAndProducts = projectInfo.getBusinessAreasAndProducts();
                    String[] split = businessAreasAndProducts.split(",");
                    String s = Arrays.stream(split).filter(str -> str.equals(type11) || str.equals(type12) || str.equals(type29)).findFirst().orElse(null);
                    if(StringUtils.isEmpty(s)) {
                        if((buildMaxScore == null || scaleGradeValue.compareTo(buildMaxScore) < 0) && (buildMinScore == null || scaleGradeValue.compareTo(buildMinScore) >= 0)) {
                            jdglDiffAnalysis.setScaleGrade(score);
                        }
                    } else {
                        if((roadMaxScore == null || scaleGradeValue.compareTo(roadMaxScore) < 0) && (roadMinScore == null || scaleGradeValue.compareTo(roadMinScore) >= 0)) {
                            jdglDiffAnalysis.setScaleGrade(score);
                        }
                    }
                } else {
                    jdglDiffAnalysis.setScaleGrade(BigDecimal.ZERO);
                }
            }
        }

        if(jdglDiffAnalysis.getSDiffGrade() == null) jdglDiffAnalysis.setSDiffGrade(BigDecimal.ZERO);
        if(jdglDiffAnalysis.getKeyGrade() == null) jdglDiffAnalysis.setKeyGrade(BigDecimal.ZERO);
        if(jdglDiffAnalysis.getContractOverGrade() == null) jdglDiffAnalysis.setContractOverGrade(BigDecimal.ZERO);
        if(jdglDiffAnalysis.getImportanceGrade() == null) jdglDiffAnalysis.setImportanceGrade(BigDecimal.ZERO);
        if(jdglDiffAnalysis.getScaleGrade() == null) jdglDiffAnalysis.setScaleGrade(BigDecimal.ZERO);
        if(jdglDiffAnalysis.getContractAmtDl() == null) jdglDiffAnalysis.setContractAmtDl(BigDecimal.ZERO);
        jdglDiffAnalysis.setTotalGrade(BigDecimal.ZERO);
        int i = jdglDiffAnalysisMapper.insertJdglDiffAnalysis(jdglDiffAnalysis);

        if(i > 0) {
            // 修正表单数据
            JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect = new JdglDiffAnalysisCorrect();
            jdglDiffAnalysisCorrect.setDiffAnalysisId(jdglDiffAnalysis.getId());
            List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList = iJdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrect);
            jdglDiffAnalysis.setJdglDiffAnalysisCorrectList4push(jdglDiffAnalysisCorrectList);
            sysSyncInfoService.pushJdglDiffAnalysis(jdglDiffAnalysis);

//            jdglCorrectionMeasuresMakeService.syncData(nowDate);
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

    @Override
    public void updateGrage(String field, Long id, BigDecimal grade) {

        JdglDiffAnalysis query = new JdglDiffAnalysis();
        query.setId(id);
        JdglDiffAnalysis jdglDiffAnalysis = getJdglDiffAnalysis(query);
        if(jdglDiffAnalysis  == null) {
            return;
        }
        int count = 0;
        if("correctGrade".equals(field)) {
            jdglDiffAnalysis.setCorrectGrade(grade);
            countTotalGrade(jdglDiffAnalysis);
            count ++;
        }

        if(count > 0) {
            int i = updateJdglDiffAnalysis(jdglDiffAnalysis);
//            if(i > 0) {
//                if(jdglDiffAnalysis.getRiskLevel() != null) {
//                    Date period = jdglDiffAnalysis.getPeriod();
//                    jdglCorrectionMeasuresMakeService.syncData(period);
//                }
//            }
        }
    }

    @Override
    public int deleteDiffAnalysisByPeriod(Date period) {
        if(period == null) {
            return 0;
        }

        JdglDiffAnalysis query = new JdglDiffAnalysis();
        query.setPeriod(period);
        JdglDiffAnalysis jdglDiffAnalysis = jdglDiffAnalysisMapper.getJdglDiffAnalysis(query);
        if(jdglDiffAnalysis != null) {
            Long id = jdglDiffAnalysis.getId();
            iJdglDiffAnalysisSvService.deleteJdglDiffAnalysisSvByDiffAnalysisId(id);
            iJdglDiffAnalysisCorrectService.deleteJdglDiffAnalysisCorrectByDiffAnalysisId(id);
            jdglDiffAnalysisPathService.deleteJdglDiffAnalysisPathByDiffAnalysisId(id);
        }

        return deleteJdglDiffAnalysis(jdglDiffAnalysis);
    }

    @Override
    public JdglDiffAnalysis getJdglDiffAnalysisById(Long id) {
        JdglDiffAnalysis query = new JdglDiffAnalysis();
        query.setId(id);
        return jdglDiffAnalysisMapper.getJdglDiffAnalysis(query);
    }

}













