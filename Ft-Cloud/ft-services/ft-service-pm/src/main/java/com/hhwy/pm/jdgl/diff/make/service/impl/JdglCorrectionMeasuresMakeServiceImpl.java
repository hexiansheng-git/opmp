package com.hhwy.pm.jdgl.diff.make.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisSvService;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import com.hhwy.pm.jdgl.diff.make.mapper.JdglCorrectionMeasuresMakeMapper;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeDetailService;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.TreeCountUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.core.DateUtil;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-08-25 14:33:50
 * @remark 纠偏措施制定
 */
@Service
public class JdglCorrectionMeasuresMakeServiceImpl implements IJdglCorrectionMeasuresMakeService {

    @Autowired
    private JdglCorrectionMeasuresMakeMapper jdglCorrectionMeasuresMakeMapper;
    @Autowired
    private IJdglCorrectionMeasuresMakeDetailService jdglCorrectionMeasuresMakeDetailService;
    @Autowired
    private IJdglDiffAnalysisService jdglDiffAnalysisService;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;
    @Autowired
    private IJdglDiffAnalysisSvService jdglDiffAnalysisSvService;
    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;
    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

    /**
     * 查询单条数据-详情
     *
     * @param JdglCorrectionMeasuresMake
     * @return
     */
    public JdglCorrectionMeasuresMake getJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake JdglCorrectionMeasuresMake) {
        JdglCorrectionMeasuresMake make = jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake);
        if (make != null) {
            List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMakeDetailService.getDetailListByMakeId(make);
            make.setDetailList(TreeUtil.build(detailList, null));
//            make.setDetailList(detailList);
        }
        FlowInfoSearchUtil.getFlowInfo(make, FlowEnum.JDGL_CORRECTION_MEASURES_MAKE);
        return make;
    }

    /**
     * 列表
     *
     * @param jdglCorrectionMeasuresMake
     * @return
     */
    public List<JdglCorrectionMeasuresMake> getJdglCorrectionMeasuresMakeList(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        List<JdglCorrectionMeasuresMake> list = jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMake);
        FlowInfoSearchUtil.getFlowInfo(list, FlowEnum.JDGL_CORRECTION_MEASURES_MAKE);
        return list;
    }

    /**
     * 新增保存
     *
     * @param jdglCorrectionMeasuresMake
     */
    @Transactional
    public void insertJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        jdglCorrectionMeasuresMake.setId(IdWorker.createId());
        jdglCorrectionMeasuresMake.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        jdglCorrectionMeasuresMake.setCreateUserName(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setCreateTime(DateUtils.getNowDate());
        jdglCorrectionMeasuresMakeMapper.insertJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);

        List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMake.getDetailList();
        if (!CollectionUtils.isEmpty(detailList)) {
            for (JdglCorrectionMeasuresMakeDetail detail : detailList) {
                detail.setId(IdWorker.createId());
                detail.setMakeId(jdglCorrectionMeasuresMake.getId());
                detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                detail.setCreateUserName(SecurityUtils.getUserName());
                detail.setCreateTime(DateUtils.getNowDate());
            }
            jdglCorrectionMeasuresMakeDetailService.insertJdglCorrectionMeasuresMakeDetailList(detailList);
        }
    }

    @Transactional
    public int insertJdglCorrectionMeasuresMakeList(List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList) {
        for (JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake : jdglCorrectionMeasuresMakeList) {
            jdglCorrectionMeasuresMake.setId(IdWorker.createId());
            jdglCorrectionMeasuresMake.setCreateUser(SecurityUtils.getUserName());
            jdglCorrectionMeasuresMake.setCreateTime(DateUtils.getNowDate());
        }
        return jdglCorrectionMeasuresMakeMapper.insertJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMakeList);
    }

    /**
     * 更新保存
     *
     * @param jdglCorrectionMeasuresMake
     * @return
     */
    @Transactional
    public void updateJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        if (jdglCorrectionMeasuresMake == null || jdglCorrectionMeasuresMake.getId() == null) {
            return;
        }
        List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMake.getDetailList();
        List<JdglCorrectionMeasuresMakeDetail> treeList = TreeUtil.treeToListWithoutId(detailList);

        jdglCorrectionMeasuresMake.setUpdateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setUpdateTime(DateUtils.getNowDate());
        Date maxDate = treeList.stream().filter(p -> p.getCorrectionCompleteDate() != null)
                .map(JdglCorrectionMeasuresMakeDetail::getCorrectionCompleteDate)
                .max(Date::compareTo).orElse(null);
        jdglCorrectionMeasuresMake.setCorrectionDate(maxDate);
        jdglCorrectionMeasuresMakeMapper.updateJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);


        if (!CollectionUtils.isEmpty(detailList)) {
            // 树转列表
            for (JdglCorrectionMeasuresMakeDetail detail : treeList) {
                detail.setMakeId(jdglCorrectionMeasuresMake.getId());
                detail.setUpdateUser(SecurityUtils.getUserName());
                detail.setUpdateTime(DateUtils.getNowDate());
            }

            // 执行更改下操作
            jdglCorrectionMeasuresMakeDetailService.updateJdglCorrectionMeasuresMakeDetailList(treeList);
        }

    }

    @Transactional
    public int updateJdglCorrectionMeasuresMakeList(List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList) {
        for (JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake : jdglCorrectionMeasuresMakeList) {
            jdglCorrectionMeasuresMake.setUpdateUser(SecurityUtils.getUserName());
            jdglCorrectionMeasuresMake.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglCorrectionMeasuresMakeMapper.updateJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMakeList);
    }

    @Transactional
    public int deleteJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        jdglCorrectionMeasuresMake.setUpdateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setUpdateTime(DateUtils.getNowDate());
        return jdglCorrectionMeasuresMakeMapper.deleteJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);
    }

    /**
     * 批量删除
     *
     * @param jdglCorrectionMeasuresMakePkList
     * @return
     */
    @Transactional
    public int deleteJdglCorrectionMeasuresMakeByPks(List<Long> jdglCorrectionMeasuresMakePkList) {
        return jdglCorrectionMeasuresMakeMapper.deleteJdglCorrectionMeasuresMakeByPks(jdglCorrectionMeasuresMakePkList);
    }

    /**
     * 修改流程数据
     *
     * @param id
     */
    @Transactional
    public void updateJdglCorrectionMeasuresMakeProcess(Long id) {
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake = new JdglCorrectionMeasuresMake();
        jdglCorrectionMeasuresMake.setId(id);
        jdglCorrectionMeasuresMake.setTaskStatus("5");
        // 纠偏日期
        jdglCorrectionMeasuresMake.setCorrectionDate(FtDateUtils.getYearMonthDayDate());
        jdglCorrectionMeasuresMakeMapper.updateJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);
    }

    /**
     * 同步差异化分析数据
     *
     * @param period
     * @return
     */
    @Transactional
    public void syncData(Date period) {
        String periodStr = FtDateUtils.getYearMonthStr(period);

        Date firstDay = DateUtil.getfirstDay(period);
        Date lastDay = DateUtil.getLastDay(period);

        JdglCorrectionMeasuresMake qryMake = new JdglCorrectionMeasuresMake();
        qryMake.setWarnPeriod(periodStr);
        JdglCorrectionMeasuresMake make = jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMake(qryMake);
        if (make != null) {
            //删除该期次历史版本
            jdglCorrectionMeasuresMakeMapper.deleteJdglCorrectionMeasuresMake(qryMake);
            JdglCorrectionMeasuresMakeDetail makeDetail = new JdglCorrectionMeasuresMakeDetail();
            makeDetail.setMakeId(qryMake.getId());
            jdglCorrectionMeasuresMakeDetailService.deleteJdglCorrectionMeasuresMakeDetail(makeDetail);
        }

        // 获取差异化分析数据
        JdglDiffAnalysis qryAnalysis = new JdglDiffAnalysis();
        qryAnalysis.setPeriod(period);
        JdglDiffAnalysis JdglDiffAnalysis = jdglDiffAnalysisService.getJdglDiffAnalysis(qryAnalysis);
        if (JdglDiffAnalysis == null) {
            return;
        }

        // 获取项目信息数据
        ProjectBasicInfo projectBasicInfo = xmslProjectBasicInfoService.projectInfo();

        // 合同信息
        XmslContractInfo contractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();

        // 获取总体计划, 获取当月数据
        List<JdglMainPlanItem> mainPlanItemListTree = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(firstDay, lastDay);
        // 树转列表
        List<JdglMainPlanItem> mainPlanItemList = TreeUtil.treeToList(mainPlanItemListTree);
        Map<String, List<JdglMainPlanItem>> mainPlanItemMap = mainPlanItemList.stream().collect(Collectors.groupingBy(JdglMainPlanItem::getItemCode));

        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake = new JdglCorrectionMeasuresMake();
        jdglCorrectionMeasuresMake.setId(IdWorker.createId());
        jdglCorrectionMeasuresMake.setProjectId(projectBasicInfo.getProjectId());
        jdglCorrectionMeasuresMake.setProjectName(projectBasicInfo.getProjectName());
        jdglCorrectionMeasuresMake.setWarnPeriod(periodStr);
        jdglCorrectionMeasuresMake.setWarnTime(FtDateUtils.getYearMonthDayDate());
        jdglCorrectionMeasuresMake.setRiskLevel(JdglDiffAnalysis.getRiskLevel());
        jdglCorrectionMeasuresMake.setPeriodTotalScore(JdglDiffAnalysis.getTotalGrade());
        jdglCorrectionMeasuresMake.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        jdglCorrectionMeasuresMake.setCreateUserName(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setCreateTime(DateUtils.getNowDate());
        jdglCorrectionMeasuresMake.setTaskStatus("0");
        // 纠偏措施制定入库
        jdglCorrectionMeasuresMakeMapper.insertJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);

        // 获取差异分析得分
        JdglDiffAnalysisSv qrySv = new JdglDiffAnalysisSv();
        qrySv.setDiffAnalysisId(JdglDiffAnalysis.getId());
        List<JdglDiffAnalysisSv> svTreeList = jdglDiffAnalysisSvService.getJdglDiffAnalysisSvList(qrySv);
        if (CollectionUtils.isEmpty(svTreeList)) {
            return;
        }
        // 树转列表
        List<JdglDiffAnalysisSv> svListList = TreeUtil.treeToList(svTreeList);
        TreeCountUtils treeCountUtils = new TreeCountUtils();
        treeCountUtils.toAncestrals(svListList, null);
        //只需要偏差值小于0的
        List<JdglDiffAnalysisSv> svList = svListList.stream()
                .filter(p -> null != p.getThisDeviationNum() && p.getThisDeviationNum().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.toList());

        ArrayList<JdglDiffAnalysisSv> objects = new ArrayList<>();
        svList.forEach(p ->{
            List<JdglDiffAnalysisSv> collect = svListList.stream().filter(p1 -> p.getPtVar5().contains(p1.getPtVar5())).collect(Collectors.toList());
            objects.addAll(collect);
        });
        List<JdglDiffAnalysisSv> collect = objects.stream().distinct().collect(Collectors.toList());

        // 构建新的list
        List<JdglCorrectionMeasuresMakeDetail> newDetailList = new ArrayList<>();
        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : collect) {
            JdglCorrectionMeasuresMakeDetail JdglCorrectionMeasuresMakeDetail = new JdglCorrectionMeasuresMakeDetail();
            JdglCorrectionMeasuresMakeDetail.setId(jdglDiffAnalysisSv.getId());
            JdglCorrectionMeasuresMakeDetail.setPid(jdglDiffAnalysisSv.getPid());
            JdglCorrectionMeasuresMakeDetail.setMakeId(jdglCorrectionMeasuresMake.getId());
            JdglCorrectionMeasuresMakeDetail.setWorkCode(jdglDiffAnalysisSv.getPlanItemCode());
            JdglCorrectionMeasuresMakeDetail.setWorkName(jdglDiffAnalysisSv.getPlanItemName());
            JdglCorrectionMeasuresMakeDetail.setIsKeyLine(jdglDiffAnalysisSv.getIsCriticalPath());
            JdglCorrectionMeasuresMakeDetail.setUnit(jdglDiffAnalysisSv.getUnit());
            JdglCorrectionMeasuresMakeDetail.setQuantity(
                jdglDiffAnalysisSv.getDesignNum() == null ? BigDecimal.ZERO : jdglDiffAnalysisSv.getDesignNum());
            JdglCorrectionMeasuresMakeDetail.setDeviationQuantity(jdglDiffAnalysisSv.getThisDeviationNum() == null ?
                BigDecimal.ZERO : jdglDiffAnalysisSv.getThisDeviationNum());
            //总时差
            BigDecimal totalFloat = new BigDecimal("0");
            List<JdglMainPlanItem> jdglMainPlanItems = mainPlanItemMap.get(jdglDiffAnalysisSv.getPlanItemCode());
            if (CollectionUtil.isNotEmpty(jdglMainPlanItems)){
                totalFloat = BigDecimal.valueOf(jdglMainPlanItems.get(0).getTotalFloat());
            }
            JdglCorrectionMeasuresMakeDetail.setTotalFloat(totalFloat);
            //SV值
            JdglCorrectionMeasuresMakeDetail.setSvValue(jdglDiffAnalysisSv.getSvNum());
            // 实际工程量
            BigDecimal actQuantity = JdglCorrectionMeasuresMakeDetail.getQuantity().add(JdglCorrectionMeasuresMakeDetail.getDeviationQuantity());
            if (JdglCorrectionMeasuresMakeDetail.getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                actQuantity.divide(actQuantity, 2, RoundingMode.HALF_UP);
            }
            JdglCorrectionMeasuresMakeDetail.setCompleteProgressPercentage(actQuantity);

            // 完成工期百分比
            BigDecimal completeDatePercentage = BigDecimal.ZERO;
            // 责任人
            for (JdglMainPlanItem item : mainPlanItemList) {
                if (item.getItemCode().equals(jdglDiffAnalysisSv.getPlanItemCode())) {
                    JdglCorrectionMeasuresMakeDetail.setDirectorId(item.getExecuterId());
                    JdglCorrectionMeasuresMakeDetail.setDirector(item.getExecuter());

                    BigDecimal days = BigDecimal.ZERO;
                    if (item.getActualStartDate() != null) {
                        // 当前开始时间(当月底)—实际开始时间
                        days = new BigDecimal(FtDateUtils.getDays(item.getActualStartDate(), lastDay));
                    }

                    // 总体计划时间 取合同工期
                    BigDecimal totalDays = BigDecimal.ZERO;
//                    if (item.getStartDate() != null && item.getFinishDate() != null) {
//                        totalDays = new BigDecimal(
//                            FtDateUtils.getDays(item.getStartDate(), item.getFinishDate()).longValue());
//                    }
                    if (contractInfo != null && StringUtils.isNotBlank(contractInfo.getDuration())) {
                        // 工期
                        totalDays = new BigDecimal(contractInfo.getDuration());
                    }
                    // 完成工期百分比 = 总体计划：当前开始时间(当月底)—实际开始时间/总体计划时间
                    if (BigDecimal.ZERO.compareTo(totalDays) != 0) {
                        completeDatePercentage = BigDecimalUtils.divide0(days, totalDays, 4)
                            .multiply(new BigDecimal(100));
                    }
                }
            }
            JdglCorrectionMeasuresMakeDetail.setCompleteDatePercentage(completeDatePercentage);
            newDetailList.add(JdglCorrectionMeasuresMakeDetail);
        }
        // 纠偏方案入库
        jdglCorrectionMeasuresMakeDetailService.insertJdglCorrectionMeasuresMakeDetailList(newDetailList);
    }

    @Override
    public JdglCorrectionMeasuresMake getJdglCorrectionMeasuresMakeByDate(Date period) {
        String periodStr = FtDateUtils.getYearMonthStr(period);

        JdglCorrectionMeasuresMake qryMake = new JdglCorrectionMeasuresMake();
        qryMake.setWarnPeriod(periodStr);
        JdglCorrectionMeasuresMake make = jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMake(qryMake);
        if (make != null) {
            List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMakeDetailService.getDetailListByMakeId(make);
            make.setDetailList(detailList);
        }
        return make;
    }
}
