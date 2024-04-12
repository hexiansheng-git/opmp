package com.hhwy.sp.experiment.sgjsExperimentRecordInfo.mapper;

import java.util.List;

import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.experiment.sgjsExperimentRecordInfo.domain.SgjsExperimentRecordInfo;

/**
 * @author lcf--设备实际进场记录
 * @date 2023-12-11 15:03:58
 * @remark
 */
public interface SgjsExperimentRecordInfoMapper {

    SgjsExperimentRecordInfo getSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    List<SgjsExperimentRecordInfo> getSgjsExperimentRecordInfoList(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int insertSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int insertSgjsExperimentRecordInfoList(@Param("sgjsExperimentRecordInfoList") List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList);

    int updateSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int updateSgjsExperimentRecordInfoList(@Param("list") List<SgjsExperimentRecordInfo> sgjsExperimentRecordInfoList);

    int deleteSgjsExperimentRecordInfo(SgjsExperimentRecordInfo sgjsExperimentRecordInfo);

    int deleteSgjsExperimentRecordInfoByPks(@Param("sgjsExperimentRecordInfoPkList") List<Long> sgjsExperimentRecordInfoPkList);

    /**
     * 删除子表数据
     *
     * @param record
     * @return
     */
    int deleteAll(SgjsEquipEntryRecordInfo record);

    /**
     * 根据试验编号查询是否重复
     *
     * @param codeList
     * @return
     */
    List<SgjsExperimentRecordInfo> selectByExperimentNos(@Param(value = "codeList") List<SgjsExperimentRecordInfo> codeList);

    /**
     * 根据主表id查询子表信息
     *
     * @param idList
     * @return
     */
    List<SgjsExperimentRecordInfo> selectByIdList(@Param(value = "idList") List<String> idList);

    /**
     * 日期查询
     *
     * @return
     */
    List<SgjsExperimentRecordInfo> selectByDate();
}
