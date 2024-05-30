package com.hhwy.pm.jdgl.diff.make.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.flowable.api.RemoteBpmnService;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.common.FlowStartUtil;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisPath;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisPathService;
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
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-08-25 14:33:50
 * @remark 纠偏措施制定
 */
@Service
@Slf4j
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
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IJdglDiffAnalysisPathService jdglDiffAnalysisPathService;
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
        Date maxDate = treeList.stream()
                .filter(p -> p.getCorrectionCompleteDate() != null)
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
     * @param 期次 yyyy-MM
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncData(Date yearMonth) {
        // 获取差异化分析主表数据
        JdglDiffAnalysis qryAnalysis = new JdglDiffAnalysis();
        qryAnalysis.setPeriod(yearMonth);
        JdglDiffAnalysis JdglDiffAnalysis = jdglDiffAnalysisService.getJdglDiffAnalysis(qryAnalysis);
        if (JdglDiffAnalysis == null) {
            log.info("纠偏措施制定当前期次无数据:{}", DateUtil.format(yearMonth, DatePattern.NORM_MONTH_PATTERN));
            return;
        }
        //时间处理
        String yearMonthStr = FtDateUtils.getYearMonthStr(yearMonth);
        DateTime dateTime = DateUtil.offsetDay(yearMonth, -1);
        String startDateStr = DateUtil.format(dateTime, DatePattern.NORM_MONTH_FORMAT) + "-21";
        String endDateStr = yearMonthStr + "-20";
        DateTime startDate = DateUtil.parse(startDateStr, DatePattern.NORM_DATE_PATTERN);
        DateTime endDate = DateUtil.parse(endDateStr, DatePattern.NORM_DATE_PATTERN);
        //查询是否已存在当期数据,已存在则删除
        JdglCorrectionMeasuresMake qryMake = new JdglCorrectionMeasuresMake();
        qryMake.setWarnPeriod(yearMonthStr);
        JdglCorrectionMeasuresMake make = jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMake(qryMake);
        if (make != null) {
            //删除该期次历史版本
            jdglCorrectionMeasuresMakeMapper.deleteJdglCorrectionMeasuresMake(qryMake);
            JdglCorrectionMeasuresMakeDetail makeDetail = new JdglCorrectionMeasuresMakeDetail();
            makeDetail.setMakeId(qryMake.getId());
            jdglCorrectionMeasuresMakeDetailService.deleteJdglCorrectionMeasuresMakeDetail(makeDetail);
        }
        // 获取项目信息数据
        ProjectBasicInfo projectBasicInfo = xmslProjectBasicInfoService.projectInfo();
        //保存纠偏措施制定主表数据
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake = new JdglCorrectionMeasuresMake();
        Long id = IdWorker.createId();
        jdglCorrectionMeasuresMake.setId(id);
        jdglCorrectionMeasuresMake.setProjectId(projectBasicInfo.getProjectId());
        jdglCorrectionMeasuresMake.setProjectName(projectBasicInfo.getProjectName());
        jdglCorrectionMeasuresMake.setWarnPeriod(yearMonthStr);
        jdglCorrectionMeasuresMake.setWarnTime(FtDateUtils.getYearMonthDayDate());
        jdglCorrectionMeasuresMake.setRiskLevel(JdglDiffAnalysis.getRiskLevel());
        jdglCorrectionMeasuresMake.setPeriodTotalScore(JdglDiffAnalysis.getTotalGrade());
        jdglCorrectionMeasuresMake.setCreateTime(DateUtils.getNowDate());
        jdglCorrectionMeasuresMake.setTaskStatus("0");
        // 纠偏措施制定入库
        jdglCorrectionMeasuresMakeMapper.insertJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);

        // 获取差异化分析-sv偏差分析
        JdglDiffAnalysisSv qrySv = new JdglDiffAnalysisSv();
        qrySv.setDiffAnalysisId(JdglDiffAnalysis.getId());
        List<JdglDiffAnalysisSv> svTreeList = jdglDiffAnalysisSvService.getJdglDiffAnalysisSvList(qrySv);
        if (CollectionUtils.isEmpty(svTreeList)) {
            return;
        }
        // 获取差异化分析-sv偏差分析  数据过滤，同时将满足条件的数据上层级拿到转成树
        List<JdglDiffAnalysisSv> svListList = TreeUtil.treeToList(svTreeList);
        TreeCountUtils treeCountUtils = new TreeCountUtils();
        treeCountUtils.toAncestrals(svListList, null);
        //只需要偏差值小于0的
        List<JdglDiffAnalysisSv> svList = svListList.stream()
                .filter(p -> null != p.getThisDeviationNum() && p.getThisDeviationNum().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.toList());
        ArrayList<JdglDiffAnalysisSv> objects = new ArrayList<>();
        svList.forEach(p -> {
            List<JdglDiffAnalysisSv> collect = svListList.stream()
                    .filter(p1 -> p.getPtVar5().contains(p1.getPtVar5())).collect(Collectors.toList());
            objects.addAll(collect);
        });
        List<JdglDiffAnalysisSv> diffAnalysisSvList = objects.stream().distinct().collect(Collectors.toList());
        // 总体进度计划详情
        List<JdglMainPlanItem> mainPlanItemList = new ArrayList<>();
        List<JdglMainPlanItem> mainPlanItemListTree = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(startDate, endDate);
        if (CollectionUtil.isNotEmpty(mainPlanItemListTree)) {
            mainPlanItemList = TreeUtil.treeToList(mainPlanItemListTree);
        }
        Map<String, JdglMainPlanItem> mainPlanItemMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(mainPlanItemList)) {
            //用于回填总时差、责任人
            mainPlanItemMap = mainPlanItemList.stream()
                    .filter(p -> StrUtil.isNotBlank(p.getItemCode()))
                    .collect(Collectors.toMap(JdglMainPlanItem::getItemCode, Function.identity(), (k1, k2) -> k1));
        }
        //获取 差异化分析-关键/非关键线路进度分析  回填工期完成百分比、进度完成百分比
        JdglDiffAnalysisPath jdglDiffAnalysisPath = new JdglDiffAnalysisPath();
        jdglDiffAnalysisPath.setDiffAnalysisId(JdglDiffAnalysis.getId());
        List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList = jdglDiffAnalysisPathService.getList(jdglDiffAnalysisPath);
        Map<String, JdglDiffAnalysisPath> pathMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(jdglDiffAnalysisPathList)) {
            pathMap = jdglDiffAnalysisPathList.stream()
                    .filter(p -> StrUtil.isNotBlank(p.getPlanItemCode()) && null != p.getTotalDayCompRate())
                    .collect(Collectors.toMap(JdglDiffAnalysisPath::getPlanItemCode, Function.identity(), (k1, k2) -> k1));
        }
        //开始处理数据，差异化分析数据组装到纠偏指定
        List<JdglCorrectionMeasuresMakeDetail> newDetailList = new ArrayList<>();
        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : diffAnalysisSvList) {
            JdglCorrectionMeasuresMakeDetail JdglCorrectionMeasuresMakeDetail = new JdglCorrectionMeasuresMakeDetail();
            JdglCorrectionMeasuresMakeDetail.setId(jdglDiffAnalysisSv.getId());
            JdglCorrectionMeasuresMakeDetail.setPid(jdglDiffAnalysisSv.getPid());
            JdglCorrectionMeasuresMakeDetail.setMakeId(jdglCorrectionMeasuresMake.getId());
            JdglCorrectionMeasuresMakeDetail.setWorkCode(jdglDiffAnalysisSv.getPlanItemCode());
            JdglCorrectionMeasuresMakeDetail.setWorkName(jdglDiffAnalysisSv.getPlanItemName());
            JdglCorrectionMeasuresMakeDetail.setIsKeyLine(jdglDiffAnalysisSv.getIsCriticalPath());
            JdglCorrectionMeasuresMakeDetail.setUnit(jdglDiffAnalysisSv.getUnit());
            JdglCorrectionMeasuresMakeDetail.setQuantity(jdglDiffAnalysisSv.getDesignNum() == null ? BigDecimal.ZERO : jdglDiffAnalysisSv.getDesignNum());
            JdglCorrectionMeasuresMakeDetail.setDeviationQuantity(jdglDiffAnalysisSv.getThisDeviationNum() == null ? BigDecimal.ZERO : jdglDiffAnalysisSv.getThisDeviationNum());
            //总时差
            JdglMainPlanItem jdglMainPlanItems = mainPlanItemMap.get(jdglDiffAnalysisSv.getPlanItemCode());
            if (jdglMainPlanItems != null) {
                Integer totalFloat = jdglMainPlanItems.getTotalFloat();
                BigDecimal aa = BigDecimal.ZERO;
                if (totalFloat != null ){
                    Integer day = totalFloat / 8;
                    aa = NumberUtil.toBigDecimal(day);
                }
                JdglCorrectionMeasuresMakeDetail.setTotalFloat(aa);
                // 责任人
                JdglCorrectionMeasuresMakeDetail.setDirectorId(jdglMainPlanItems.getExecuterId());
                JdglCorrectionMeasuresMakeDetail.setDirector(jdglMainPlanItems.getExecuter());
                if (jdglMainPlanItems.getFinishDateVariance() != null){
                    JdglCorrectionMeasuresMakeDetail.setExpectLagDay(new BigDecimal(jdglMainPlanItems.getFinishDateVariance()));
                }
            }
            //SV值
            JdglCorrectionMeasuresMakeDetail.setSvValue(jdglDiffAnalysisSv.getSvNum());
            JdglDiffAnalysisPath jdglDiffAnalysis = pathMap.get(jdglDiffAnalysisSv.getPlanItemCode());
            if (jdglDiffAnalysis != null ) {
                //完成进度百分比
                BigDecimal totalProgressCompRate = jdglDiffAnalysis.getTotalProgressCompRate();
                JdglCorrectionMeasuresMakeDetail.setCompleteProgressPercentage(totalProgressCompRate == null ? BigDecimal.ZERO : totalProgressCompRate);
                // 完成工期百分比
                BigDecimal totalDayCompRate = jdglDiffAnalysis.getTotalDayCompRate();
                JdglCorrectionMeasuresMakeDetail.setCompleteDatePercentage(totalDayCompRate == null ? BigDecimal.ZERO : totalDayCompRate);
            }
            newDetailList.add(JdglCorrectionMeasuresMakeDetail);
        }
        // 纠偏方案入库
        if (CollectionUtil.isEmpty(newDetailList)) {
            log.info("newDetailList 空");
            return;
        }
        jdglCorrectionMeasuresMakeDetailService.insertJdglCorrectionMeasuresMakeDetailList(newDetailList);
        log.info("纠偏措施制定，纠偏方案入库完成; 开始发起流程...");
        //发起流程
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(() -> {
            //获取用户名
            List<String> userNameList = newDetailList.stream()
                    .map(JdglCorrectionMeasuresMakeDetail::getDirectorId)
                    .filter(StrUtil::isNotBlank)
                    .distinct().collect(Collectors.toList());
            //获取菜单id
        if (CollectionUtil.isEmpty(userNameList)) {
            log.info("获取责任人为空:{}", JSON.toJSONString(userNameList));
            return;
        }
        String tenantKey = SecurityUtils.getTenantKey();
        String component = "scheduleManagement/FormulateCorrectiveMeasures/detail";
        List<SysMenu> menuIdList = systemServiceApi.getMenuId(component, tenantKey + ",master");
        if (CollectionUtil.isEmpty(menuIdList)){
            log.info("当前租户没有此菜单,租户:{}, 菜单:{}", tenantKey, component);
            return;
        }
        List<SysMenu> collect = menuIdList.stream().filter(p -> tenantKey.equals(p.getTenantKey())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) {
            collect = menuIdList.stream().filter(p -> "master".equals(p.getTenantKey())).collect(Collectors.toList());
        }
        if (CollectionUtil.isEmpty(collect)) {
            log.info("当前租户没有菜单:{}", tenantKey);
            return;
        }
        String processKey = "process_jdgl_correction_measures_make";
        String tableName = "jdgl_correction_measures_make";
//        log.info("流程发起参数, 流程定义key:{}, 业务id:{}, 表名:{}, 提交目标:{}, 路由id:{}", processKey, id, tableName, JSON.toJSONString(userNameList), collect.get(0).getMenuId());
//        userNameList.clear();
//        userNameList.add("guolan");
        FlowStartUtil.start(processKey, String.valueOf(id), tableName, userNameList, String.valueOf(collect.get(0).getMenuId()), "纠偏措施制定-0530");
//        });
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
