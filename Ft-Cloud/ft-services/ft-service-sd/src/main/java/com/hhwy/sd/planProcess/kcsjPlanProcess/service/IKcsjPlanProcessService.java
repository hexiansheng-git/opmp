package com.hhwy.sd.planProcess.kcsjPlanProcess.service;

import java.util.List;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjPlanProcess;

/**
 * @author cjh
 * @date 2023-12-18 11:13:27
 * @remark
 */
public interface IKcsjPlanProcessService {

    KcsjPlanProcess getKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess);

    List<KcsjPlanProcess> getKcsjPlanProcessList(KcsjPlanProcess kcsjPlanProcess);

    int insertKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess);

    int insertKcsjPlanProcessList(List<KcsjPlanProcess> kcsjPlanProcessList);

    int updateKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess);

    int updateKcsjPlanProcessList(List<KcsjPlanProcess> kcsjPlanProcessList);

    int deleteKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess);

    int deleteKcsjPlanProcessByPks(List<Long> kcsjPlanProcessPkList);

    void sync();

    /**
     * 勘察设计--计划进度 预警消息发送
     *
     * @author lcf
     * @date 2024-04-01
     * @return
     */
    AjaxResult jobPlanProcess();
}
