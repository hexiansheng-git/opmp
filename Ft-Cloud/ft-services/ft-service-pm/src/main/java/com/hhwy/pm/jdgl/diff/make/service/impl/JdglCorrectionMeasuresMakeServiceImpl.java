package com.hhwy.pm.jdgl.diff.make.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.common.domain.FtActBusiness;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisSvService;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import com.hhwy.pm.jdgl.diff.make.mapper.JdglCorrectionMeasuresMakeMapper;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeDetailService;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 查询单条数据-详情
     *
     * @param JdglCorrectionMeasuresMake
     * @return
     */
    public JdglCorrectionMeasuresMake getJdglCorrectionMeasuresMake(
        JdglCorrectionMeasuresMake JdglCorrectionMeasuresMake) {
        JdglCorrectionMeasuresMake make = jdglCorrectionMeasuresMakeMapper
            .getJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake);
        if (make != null) {
            List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMakeDetailService
                .getDetailListByMakeId(make.getId());
            make.setDetailList(TreeUtil.build(detailList, null));
        }
        return make;
    }

    /**
     * 列表
     *
     * @param jdglCorrectionMeasuresMake
     * @return
     */
    public List<JdglCorrectionMeasuresMake> getJdglCorrectionMeasuresMakeList(
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake) {
        List<JdglCorrectionMeasuresMake> list =
            jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMakeList(jdglCorrectionMeasuresMake);
        FlowInfoSearchUtil.getFlowInfo(list,FlowEnum.JDGL_CORRECTION_MEASURES_MAKE);
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
        jdglCorrectionMeasuresMake.setUpdateUser(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setUpdateTime(DateUtils.getNowDate());
        jdglCorrectionMeasuresMakeMapper.updateJdglCorrectionMeasuresMake(jdglCorrectionMeasuresMake);

        List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMake.getDetailList();
        if (!CollectionUtils.isEmpty(detailList)) {
            // 树转列表
            List<JdglCorrectionMeasuresMakeDetail> treeList = TreeUtil.treeToListWithoutId(detailList);
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
     * 同步差异化分析数据
     *
     * @param period
     * @return
     */
    @Transactional
    public void syncData(Date period) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String periodStr = sdf.format(period);

        JdglCorrectionMeasuresMake qryMake = new JdglCorrectionMeasuresMake();
        qryMake.setWarnPeriod(periodStr);
        JdglCorrectionMeasuresMake make = jdglCorrectionMeasuresMakeMapper.getJdglCorrectionMeasuresMake(qryMake);
        if (make == null) {
            return;
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

        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake = new JdglCorrectionMeasuresMake();
        jdglCorrectionMeasuresMake.setId(IdWorker.createId());
        jdglCorrectionMeasuresMake.setProjectName(projectBasicInfo.getProjectName());
        jdglCorrectionMeasuresMake.setWarnPeriod(periodStr);
        jdglCorrectionMeasuresMake.setRiskLevel(JdglDiffAnalysis.getRiskLevel());
        jdglCorrectionMeasuresMake.setPeriodTotalScore(JdglDiffAnalysis.getTotalGrade());
        jdglCorrectionMeasuresMake.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        jdglCorrectionMeasuresMake.setCreateUserName(SecurityUtils.getUserName());
        jdglCorrectionMeasuresMake.setCreateTime(DateUtils.getNowDate());
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
        List<JdglDiffAnalysisSv> svList = TreeUtil.treeToList(svTreeList);

        // 构建新的list
        List<JdglCorrectionMeasuresMakeDetail> newDetailList = new ArrayList<>();
        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : svList) {
            JdglCorrectionMeasuresMakeDetail JdglCorrectionMeasuresMakeDetail = new JdglCorrectionMeasuresMakeDetail();
            JdglCorrectionMeasuresMakeDetail.setId(jdglDiffAnalysisSv.getId());
            JdglCorrectionMeasuresMakeDetail.setPid(jdglDiffAnalysisSv.getPid());
            JdglCorrectionMeasuresMakeDetail.setMakeId(make.getId());
            JdglCorrectionMeasuresMakeDetail.setWorkCode(jdglDiffAnalysisSv.getPlanItemCode());
            JdglCorrectionMeasuresMakeDetail.setWorkName(jdglDiffAnalysisSv.getPlanItemName());
            JdglCorrectionMeasuresMakeDetail.setIsKeyLine(jdglDiffAnalysisSv.getIsCriticalPath());
            JdglCorrectionMeasuresMakeDetail.setUnit(jdglDiffAnalysisSv.getUnit());
            JdglCorrectionMeasuresMakeDetail.setQuantity(jdglDiffAnalysisSv.getDesignNum());
            JdglCorrectionMeasuresMakeDetail.setDeviationQuantity(jdglDiffAnalysisSv.getThisDeviationNum());
            // todo 总时差 暂无来源
            //JdglCorrectionMeasuresMakeDetail.setTotalFloat();
            JdglCorrectionMeasuresMakeDetail.setSvValue(jdglDiffAnalysisSv.getSvNum());
            // todo 完成进度百分比 暂无来源
            //JdglCorrectionMeasuresMakeDetail.setCompleteProgressPercentage();
            // todo 完成工期百分比 暂无来源
            //JdglCorrectionMeasuresMakeDetail.setCompleteDatePercentage();
            // todo 完成工期百分比 暂无来源
            //JdglCorrectionMeasuresMakeDetail.setDirector();
            newDetailList.add(JdglCorrectionMeasuresMakeDetail);
        }
        // 纠偏方案入库
        jdglCorrectionMeasuresMakeDetailService.insertJdglCorrectionMeasuresMakeDetailList(newDetailList);
    }
}
