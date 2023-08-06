package com.hhwy.pm.qqch.preparation.measureexp.equ.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.QqchMeasureExpEqu;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:29
 * @remark 3.6.4测量仪器设备配置计划、3.7.4试验仪器设备配置计划
 */
@Data
public class QqchMeasureExpEquVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：配置类别 1-测量仪器设备配置计划，2-试验仪器设备配置计划
     */
    private String type;

    /**
     * 字段描述：测量仪器设备配置计划集合
     */
    private List<QqchMeasureExpEqu> measureList;

    /**
     * 字段描述：试验仪器设备配置计划集合
     */
    private List<QqchMeasureExpEqu> experimentList;
}
