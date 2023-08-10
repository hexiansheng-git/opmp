package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.vo;

import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:11
 * @remark 普通要点策划/变更策划/索赔策划 Vo类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondManageKeyPointPlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：附件组id
     */
    @Excel(name = "附件组id")
    private String fileGroupId;
    /**
     * 字段描述：要点类型（字典项：key_point_type）
     */
    @NotBlank(message = "要点类型不能为空！",groups = ValidationGroups.Save.class)
    private String keyPointType;

    private List<SecondManageKeyPointPlan> list;
}
