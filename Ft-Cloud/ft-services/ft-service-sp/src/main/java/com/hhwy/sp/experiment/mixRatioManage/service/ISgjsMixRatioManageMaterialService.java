package com.hhwy.sp.experiment.mixRatioManage.service;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageMaterial;

import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:27:44
 * @remark
 */
public interface ISgjsMixRatioManageMaterialService {

    SgjsMixRatioManageMaterial getSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    List<SgjsMixRatioManageMaterial> getSgjsMixRatioManageMaterialList(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    int insertSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    int insertSgjsMixRatioManageMaterialList(List<SgjsMixRatioManageMaterial> sgjsMixRatioManageMaterialList);

    int updateSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    int updateSgjsMixRatioManageMaterialList(List<SgjsMixRatioManageMaterial> sgjsMixRatioManageMaterialList);

    int deleteSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    int deleteSgjsMixRatioManageMaterialByPks(List<Long> sgjsMixRatioManageMaterialPkList);

    List<SgjsMixRatioManageMaterial> getListByForeignId(Long foreignId);

    List<SgjsMixRatioManageMaterial> getListByIds(List<Long> ids);
}
