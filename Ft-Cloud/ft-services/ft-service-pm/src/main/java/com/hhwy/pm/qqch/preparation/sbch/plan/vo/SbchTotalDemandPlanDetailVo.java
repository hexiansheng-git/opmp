package com.hhwy.pm.qqch.preparation.sbch.plan.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zqq
 * @create 2023-08-23 16:34
 */
@Data
public class SbchTotalDemandPlanDetailVo extends PreparationEntity {
    private List<SbchTotalDemandPlanDetail> list;
    @NotNull(message = "项目id不能为空",groups = {ValidationGroups.Save.class})
    private Long projectId;
    @NotBlank(message = "项目编码不能为空",groups = {ValidationGroups.Save.class})
    private String prjCode;
    @NotBlank(message = "项目名称不能为空",groups = {ValidationGroups.Save.class})
    private String projectName;
    @NotNull(message = "区域id不能为空",groups = {ValidationGroups.Save.class})
    private Long regionId;
    @NotBlank(message = "区域名称不能为空",groups = {ValidationGroups.Save.class})
    private String regionName;

}
