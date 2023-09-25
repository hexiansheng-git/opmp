package com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qualityRecord.domain.QqchGeneralProjectArchives;
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
public class GeneralProjectArchivesWbsVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<GeneralProjectArchivesWbs> list;

    //一般工程档案全量列表
    private List<QqchGeneralProjectArchives> allGeneralSublist;

    //重难点工程档案全量列表
    private List<QqchKeyDifficultProjectArchives> allDifficultSublist;
}
