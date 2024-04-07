package com.hhwy.sp.experiment.mixRatioManage.service;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManage;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.MixRatioManageQueryVo;

import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:38:19
 * @remark
 */
public interface ISgjsMixRatioManageService {

    List<SgjsMixRatioManage> getSgjsMixRatioManageList(SgjsMixRatioManage sgjsMixRatioManage);

    int deleteSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage);

    int deleteSgjsMixRatioManageByPks(List<Long> sgjsMixRatioManagePkList);

    List<SgjsMixRatioManage> getListByQueryVo(MixRatioManageQueryVo queryVo);

    SgjsMixRatioManage getById(Long id);

    void save(SgjsMixRatioManage mixRatioManage);

    List<SgjsMixRatioManage> getListByIds(List<Long> ids);
}
