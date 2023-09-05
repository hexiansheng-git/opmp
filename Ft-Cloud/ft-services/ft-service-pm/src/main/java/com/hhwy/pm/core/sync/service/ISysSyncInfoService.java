package com.hhwy.pm.core.sync.service;

import com.hhwy.pm.core.sync.domain.SysSyncInfo;
import com.hhwy.pm.core.sync.enums.SyncBusinessEnum;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.review.domain.Review;

import java.util.List;

/**
 * 数据同步节点记录Service接口
 * 
 * @author XXX
 * @date 2023-09-04
 */
public interface ISysSyncInfoService {
    /**
     * 获取业务最新ID
     * @param syncBusinessEnum 
     * @return
     */
    public Long getLastId(SyncBusinessEnum syncBusinessEnum);
    
    /**
     * 查询数据同步节点记录
     * @param syncBusinessEnum 
     * @return 数据同步节点记录
     */
    public SysSyncInfo selectSysSyncInfoByEnum(SyncBusinessEnum syncBusinessEnum);

    /**
     * 推送前期策划小组数据到总部
     */
    public void pushQqchWorkGroup(List<QqchWorkGroup> List);
    public void pushQqchWorkGroup(QqchWorkGroup qqchWorkGroup);

    /**
     * 推送前期策划小组数据到总部
     */
    public void pushQqchWorkPlan(List<QqchWorkPlan> List);
    public void pushQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    /**
     * 推送前期策划评审数据到总部
     */
    public void pushQqchReview(List<Review> List);
    public void pushQqchReview(Review review);


    /**
     * 推送前期策划评审数据到总部
     */
    public void pushQqchPerformInspection(List<QqchPerformInspection> List);
    public void pushQqchPerformInspection(QqchPerformInspection inspection);

    /**
     * 推送差异化分析数据到总部
     * @param list
     */
    void pushJdglDiffAnalysis(List<JdglDiffAnalysis> list);
    void pushJdglDiffAnalysis(JdglDiffAnalysis diffAnalysis);

    /**
     * 推送进度纠偏跟踪数据到总部
     * @param list
     */
    void pushJdglProgressCorrectionTrack(List<JdglProgressCorrectionTrack> list);
    void pushJdglProgressCorrectionTrack(JdglProgressCorrectionTrack diffAnalysis);
}
