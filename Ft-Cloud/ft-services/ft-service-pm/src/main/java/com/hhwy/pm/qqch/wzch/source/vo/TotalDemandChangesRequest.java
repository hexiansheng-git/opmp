package com.hhwy.pm.qqch.wzch.source.vo;

import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author HCT
 */
@Data
public class TotalDemandChangesRequest implements Serializable {

    @NotNull(message = "项目ID不能为空",groups = {ValidationGroups.Save.class})
    private Long projectId;
    @NotNull(message = "物资总需版本不能为空",groups = {ValidationGroups.Save.class})
    private String demandVersion;


}
