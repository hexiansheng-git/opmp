package com.hhwy.sp.experiment.mixRatioManage.mapper;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageMaterial;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:27:44
 * @remark
 */
@Repository
public interface SgjsMixRatioManageMaterialMapper {

    SgjsMixRatioManageMaterial getSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    List<SgjsMixRatioManageMaterial> getListByForeignId(@Param("foreignId") Long foreignId);

    List<SgjsMixRatioManageMaterial> getSgjsMixRatioManageMaterialList(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    int insertSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    int insertSgjsMixRatioManageMaterialList(@Param("sgjsMixRatioManageMaterialList") List<SgjsMixRatioManageMaterial> sgjsMixRatioManageMaterialList);

    int updateSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    int updateSgjsMixRatioManageMaterialList(@Param("list") List<SgjsMixRatioManageMaterial> sgjsMixRatioManageMaterialList);

    int deleteSgjsMixRatioManageMaterial(SgjsMixRatioManageMaterial sgjsMixRatioManageMaterial);

    int deleteSgjsMixRatioManageMaterialByPks(@Param("sgjsMixRatioManageMaterialPkList") List<Long> sgjsMixRatioManageMaterialPkList);

    void deleteByForeignId(@Param("foreignId") Long foreignId);

    List<SgjsMixRatioManageMaterial> getListByIds(@Param("ids") List<Long> ids);
}
