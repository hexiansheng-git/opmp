package com.hhwy.pm.qqch.preparation.quality.qualityRecord.mapper;

import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchKeyDifficultProjectArchives;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:19
 * @remark
 */
@Repository
public interface QqchKeyDifficultProjectArchivesMapper {

    QqchKeyDifficultProjectArchives getQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    List<QqchKeyDifficultProjectArchives> getQqchKeyDifficultProjectArchivesList(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    int insertQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    int insertQqchKeyDifficultProjectArchivesList(@Param("qqchKeyDifficultProjectArchivesList") List<QqchKeyDifficultProjectArchives> qqchKeyDifficultProjectArchivesList);

    int updateQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    int updateQqchKeyDifficultProjectArchivesList(@Param("list") List<QqchKeyDifficultProjectArchives> qqchKeyDifficultProjectArchivesList);

    int deleteQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    int deleteQqchKeyDifficultProjectArchivesByPks(@Param("qqchKeyDifficultProjectArchivesPkList") List<Long> qqchKeyDifficultProjectArchivesPkList);
}
