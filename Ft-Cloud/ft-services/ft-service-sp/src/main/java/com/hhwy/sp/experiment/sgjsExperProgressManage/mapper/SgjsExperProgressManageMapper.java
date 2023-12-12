package com.hhwy.sp.experiment.sgjsExperProgressManage.mapper;


import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark
 */
public interface SgjsExperProgressManageMapper {

    SgjsExperProgressManage getSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    /**
     * 列表查询，条件查询
     * @param sgjsExperProgressManage
     * @return
     */

    List<SgjsExperProgressManage> getSgjsExperProgressManageListByCondition(SgjsExperProgressManage sgjsExperProgressManage);

    List<SgjsExperProgressManage> getSgjsExperProgressManageList(SgjsExperProgressManage sgjsExperProgressManage);

    int insertSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    int insertSgjsExperProgressManageList(@Param("sgjsExperProgressManageList") List<SgjsExperProgressManage> sgjsExperProgressManageList);

    int updateSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    int updateSgjsExperProgressManageList(@Param("sgjsExperProgressManageList") List<SgjsExperProgressManage> sgjsExperProgressManageList);

    int deleteSgjsExperProgressManage(SgjsExperProgressManage sgjsExperProgressManage);

    int deleteSgjsExperProgressManageByPks(@Param("sgjsExperProgressManagePkList") List<Long> sgjsExperProgressManagePkList);

    /**
     * 全部删除
     * @param info
     */
    void deleteAll(SgjsExperProgressManage info);

    /**
     * 查询子节点的父节点集合
     * @param childrenIds
     */
    List<SgjsExperProgressManage> getParentList(@Param("childrenIds") List<Long> childrenIds);

}
