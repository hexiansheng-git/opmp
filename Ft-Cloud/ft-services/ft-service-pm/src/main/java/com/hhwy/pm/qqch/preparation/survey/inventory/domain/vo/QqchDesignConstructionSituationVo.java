package com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchDesignConstructionSituation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2023-07-13 10:09:03
 * @remark 边设计边施工情况Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchDesignConstructionSituationVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：模块确认情况
     */
    private QqchModuleConfirmCase qqchModuleConfirmCase;
    /**
     * 字段描述：
     */
    private List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList;
}
