package com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchKeyDifficultProjectArchives;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-24 15:32:19
 * @remark qqch_key_difficult_project_archives
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyDifficultWbsVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    //主数据
    private List<KeyDifficultWbs> list;

    //右侧全量数据
    private List<QqchKeyDifficultProjectArchives> allList;
}
