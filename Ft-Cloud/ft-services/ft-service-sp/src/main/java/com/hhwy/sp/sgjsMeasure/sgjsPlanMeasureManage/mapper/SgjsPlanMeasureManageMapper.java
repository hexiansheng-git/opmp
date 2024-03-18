package com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.mapper;

import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zmh
 * @date 2023-12-07 18:13:51
 * @remark
 */
public interface SgjsPlanMeasureManageMapper {

    SgjsPlanMeasureManage getSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    List<SgjsPlanMeasureManage> getSgjsPlanMeasureManageList(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    int insertSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    int insertSgjsPlanMeasureManageList(@Param("sgjsPlanMeasureManageList") List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList);

    int updateSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    int updateSgjsPlanMeasureManageList(@Param("list") List<SgjsPlanMeasureManage> sgjsPlanMeasureManageList);

    int deleteSgjsPlanMeasureManage(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    int deleteSgjsPlanMeasureManageByPks(@Param("sgjsPlanMeasureManagePkList") List<Long> sgjsPlanMeasureManagePkList);

    void deleteAll(@Param("delIdList") List<String> delIdList);

    List<SgjsPlanMeasureManage> getIds(@Param("ids") List<String> ids);

    int deleteInfoData(List<SgjsPlanMeasureManage> list);

    // 同步覆盖，数据全部删除
    void delAll(SgjsPlanMeasureManage sgjsPlanMeasureManage);

    //获取该项目的全部数据
    List<SgjsPlanMeasureManage> getAll();

}
