package com.hhwy.sp.experiment.mixRatioManage.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:38:19
 * @remark sgjs_mix_ratio_manage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MixRatioManageQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：配合比编号
     */
    private String mixRatioCode;
    /**
     * 字段描述：配合比类型
     */
    private String mixRatioType;
    /**
     * 字段描述：配合比名称
     */
    private String mixRatioName;
    /**
     * 字段描述：是否批复
     */
    private String approveOrNot;

    private List<Long> ids;
}
