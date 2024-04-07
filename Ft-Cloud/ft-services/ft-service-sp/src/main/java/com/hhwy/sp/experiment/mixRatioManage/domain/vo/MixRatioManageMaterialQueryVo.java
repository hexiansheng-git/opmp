package com.hhwy.sp.experiment.mixRatioManage.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:27:44
 * @remark sgjs_mix_ratio_manage_material
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MixRatioManageMaterialQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long foreignId;

    private List<Long> ids;
}
