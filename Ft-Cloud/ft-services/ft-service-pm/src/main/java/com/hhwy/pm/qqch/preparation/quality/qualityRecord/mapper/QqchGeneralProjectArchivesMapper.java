package com.hhwy.pm.qqch.preparation.quality.qualityRecord.mapper;

import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchGeneralProjectArchives;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:35
 * @remark
 */
@Repository
public interface QqchGeneralProjectArchivesMapper {

    QqchGeneralProjectArchives getQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    List<QqchGeneralProjectArchives> getQqchGeneralProjectArchivesList(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    int insertQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    int insertQqchGeneralProjectArchivesList(@Param("qqchGeneralProjectArchivesList") List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList);

    int updateQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    int updateQqchGeneralProjectArchivesList(@Param("list") List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList);

    int deleteQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    int deleteQqchGeneralProjectArchivesByPks(@Param("qqchGeneralProjectArchivesPkList") List<Long> qqchGeneralProjectArchivesPkList);
}
