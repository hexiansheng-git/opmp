package com.hhwy.pm.qqch.wzch.source.vo;

import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProjectOfChangeInfoRequest {
    @NotNull(message = "项目ID不能为空",groups = {ValidationGroups.Select.class})
    private Long projectId;
    @NotBlank(message = "物资总需新版本不能为空",groups = {ValidationGroups.Select.class})
    private String demandNewVersion;
}
