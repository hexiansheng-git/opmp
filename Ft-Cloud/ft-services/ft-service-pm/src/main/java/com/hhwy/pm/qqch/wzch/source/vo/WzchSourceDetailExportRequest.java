package com.hhwy.pm.qqch.wzch.source.vo;


import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author HCT
 */
@Data
public class WzchSourceDetailExportRequest implements Serializable {

    @NotNull(message = "来源策划ID不能为空",groups = {ValidationGroups.Select.class} )
    private Long sourceId;
    private List<Long> idList;
    @NotNull(message = "项目ID不能为空",groups = {ValidationGroups.Select.class} )
    private Long projectId;

}
