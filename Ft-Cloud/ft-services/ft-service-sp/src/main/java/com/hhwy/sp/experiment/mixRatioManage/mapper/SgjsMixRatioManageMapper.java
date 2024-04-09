package com.hhwy.sp.experiment.mixRatioManage.mapper;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManage;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.MixRatioManageQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:38:19
 * @remark
 */
@Repository
public interface SgjsMixRatioManageMapper {

    SgjsMixRatioManage getSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage);

    List<SgjsMixRatioManage> getSgjsMixRatioManageList(SgjsMixRatioManage sgjsMixRatioManage);

    int insertSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage);

    int insertSgjsMixRatioManageList(@Param("sgjsMixRatioManageList") List<SgjsMixRatioManage> sgjsMixRatioManageList);

    int updateSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage);

    int updateSgjsMixRatioManageList(@Param("list") List<SgjsMixRatioManage> sgjsMixRatioManageList);

    int deleteSgjsMixRatioManage(SgjsMixRatioManage sgjsMixRatioManage);

    int deleteSgjsMixRatioManageByPks(@Param("sgjsMixRatioManagePkList") List<Long> sgjsMixRatioManagePkList);

    List<SgjsMixRatioManage> getListByQueryVo(MixRatioManageQueryVo queryVo);

    SgjsMixRatioManage getById(@Param("id") Long id);

    List<SgjsMixRatioManage> getListByIds(@Param("ids") List<Long> ids);

    int getByMixRatioCodeExceptId(@Param("mixRatioCode") String mixRatioCode, @Param("id") Long id);
}
