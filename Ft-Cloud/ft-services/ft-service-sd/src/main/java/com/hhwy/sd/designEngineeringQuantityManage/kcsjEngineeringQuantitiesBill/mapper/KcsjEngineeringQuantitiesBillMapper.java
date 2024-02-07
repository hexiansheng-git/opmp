package com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.mapper;

import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2024-02-04 14:04:56
 * @remark
 */
public interface KcsjEngineeringQuantitiesBillMapper {

    KcsjEngineeringQuantitiesBill getKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    //列表查询
    List<KcsjEngineeringQuantitiesBill> getKcsjEngineeringQuantitiesBillList(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    int insertKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    int insertKcsjEngineeringQuantitiesBillList(@Param("kcsjEngineeringQuantitiesBillList") List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList);

    int updateKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    int updateKcsjEngineeringQuantitiesBillList(@Param("kcsjEngineeringQuantitiesBillList") List<KcsjEngineeringQuantitiesBill> kcsjEngineeringQuantitiesBillList);

    int deleteKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    int deleteKcsjEngineeringQuantitiesBillByPks(@Param("kcsjEngineeringQuantitiesBillPkList") List<Long> kcsjEngineeringQuantitiesBillPkList,@Param("delUser") String delUser);

    //根据id获取数据集合
    List<KcsjEngineeringQuantitiesBill> getKcsjEngineeringQuantitiesBillPks(@Param("kcsjEngineeringQuantitiesBillPkList") List<Long> kcsjEngineeringQuantitiesBillPkList);

    void updateNewVersion(String listLocation);
}
