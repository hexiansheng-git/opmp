package com.hhwy.sp.experiment.sgjsCriticalExpReport.mapper;

import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportQueryVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.SgjsCriticalExpReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-12-11 16:41:12
 * @remark
 */
@Repository
public interface SgjsCriticalExpReportMapper {

    SgjsCriticalExpReport getSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport);

    List<SgjsCriticalExpReport> getSgjsCriticalExpReportList(CriticalExpReportQueryVo queryVo);

    List<SgjsCriticalExpReport> getListByIds(@Param("ids") List<Long> ids);

    int insertSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport);

    int insertSgjsCriticalExpReportList(@Param("sgjsCriticalExpReportList") List<SgjsCriticalExpReport> sgjsCriticalExpReportList);

    int updateSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport);

    int updateSgjsCriticalExpReportList(@Param("list") List<SgjsCriticalExpReport> sgjsCriticalExpReportList);

    int deleteSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport);

    int deleteSgjsCriticalExpReportByPks(@Param("idList") List<Long> idList);

    //获取最新数据
    List<SgjsCriticalExpReport> getAll();
}
