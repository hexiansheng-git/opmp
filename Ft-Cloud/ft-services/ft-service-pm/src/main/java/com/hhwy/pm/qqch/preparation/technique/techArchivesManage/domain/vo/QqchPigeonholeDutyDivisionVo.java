package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchPigeonholeDutyDivision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:48:27
 * @remark 技术档案归档责任分工
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchPigeonholeDutyDivisionVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：优技术档案归档责任分工集合
     */
    private List<QqchPigeonholeDutyDivision> list;
}
