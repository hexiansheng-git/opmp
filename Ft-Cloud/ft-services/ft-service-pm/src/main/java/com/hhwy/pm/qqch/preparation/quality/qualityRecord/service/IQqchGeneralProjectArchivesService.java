package com.hhwy.pm.qqch.preparation.quality.qualityRecord.service;

import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchGeneralProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.GeneralProjectArchivesWbs;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.GeneralProjectArchivesWbsVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:35
 * @remark
 */
public interface IQqchGeneralProjectArchivesService {

    QqchGeneralProjectArchives getQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    List<QqchGeneralProjectArchives> getQqchGeneralProjectArchivesList(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    int insertQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    int insertQqchGeneralProjectArchivesList(List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList);

    int updateQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    int updateQqchGeneralProjectArchivesList(List<QqchGeneralProjectArchives> qqchGeneralProjectArchivesList);

    int deleteQqchGeneralProjectArchives(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    int deleteQqchGeneralProjectArchivesByPks(List<Long> qqchGeneralProjectArchivesPkList);

    /**
     * 获取台账Vo
     * @param qqchGeneralProjectArchives
     * @return
     */
    GeneralProjectArchivesWbsVo getGeneralProjectArchivesWbsVo(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    /**
     * 点击获取下级
     * @param qqchGeneralProjectArchives
     * @return
     */
    List<GeneralProjectArchivesWbs> getLowerLevel(QqchGeneralProjectArchives qqchGeneralProjectArchives);

    /**
     * 保存/确认/提交
     * @param generalProjectArchivesWbsVo
     * @return
     */
    void save(GeneralProjectArchivesWbsVo generalProjectArchivesWbsVo);
}
