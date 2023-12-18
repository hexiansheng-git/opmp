package com.hhwy.sd.planProcess.kcsjPlanProcess.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjPlanProcess;

/**
 * @author cjh
 * @date 2023-12-18 11:13:27
 * @remark
 */
public interface KcsjPlanProcessMapper {

    KcsjPlanProcess getKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess);

    List<KcsjPlanProcess> getKcsjPlanProcessList(KcsjPlanProcess kcsjPlanProcess);

    int insertKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess);

    int insertKcsjPlanProcessList(@Param("kcsjPlanProcessList") List<KcsjPlanProcess> kcsjPlanProcessList);

    int updateKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess);

    int updateKcsjPlanProcessList(@Param("kcsjPlanProcessList") List<KcsjPlanProcess> kcsjPlanProcessList);

    int deleteKcsjPlanProcess(KcsjPlanProcess kcsjPlanProcess);

    int deleteKcsjPlanProcessByPks(@Param("kcsjPlanProcessPkList") List<Long> kcsjPlanProcessPkList);
}
