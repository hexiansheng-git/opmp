package com.hhwy.pm.qqch.preparation.quality.qualityRecord.service;

import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchKeyDifficultProjectArchives;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo.KeyDifficultWbsVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:19
 * @remark
 */
public interface IQqchKeyDifficultProjectArchivesService {

    QqchKeyDifficultProjectArchives getQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    List<QqchKeyDifficultProjectArchives> getQqchKeyDifficultProjectArchivesList(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    int insertQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    int updateQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    int updateQqchKeyDifficultProjectArchivesList(List<QqchKeyDifficultProjectArchives> qqchKeyDifficultProjectArchivesList);

    int deleteQqchKeyDifficultProjectArchives(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    int deleteQqchKeyDifficultProjectArchivesByPks(List<Long> qqchKeyDifficultProjectArchivesPkList);

    /**
     * 获取最新版本的重难点工程清单数据
     * @return
     */
    List<QqchKeyDifficultProjectArchives> getValidMaxVersionData();

    /**
     * 获取台账页Vo
     * @param qqchKeyDifficultProjectArchives
     * @return
     */
    KeyDifficultWbsVo getKeyDifficultWbsVo(QqchKeyDifficultProjectArchives qqchKeyDifficultProjectArchives);

    /**
     * 保存/确认/提交
     * @param keyDifficultWbsVo
     * @return
     */
    void save(KeyDifficultWbsVo keyDifficultWbsVo);
}
