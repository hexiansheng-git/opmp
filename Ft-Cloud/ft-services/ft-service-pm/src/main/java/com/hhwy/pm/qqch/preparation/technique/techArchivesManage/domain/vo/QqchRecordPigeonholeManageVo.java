package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchRecordPigeonholeManage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:49:08
 * @remark 技术档案归档管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchRecordPigeonholeManageVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：技术档案归档管理集合
     */
    private List<QqchRecordPigeonholeManage> list;
}
