package com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.dto;

import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllot;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllotDetails;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SbchEquipmentAllotDTO extends SbchEquipmentAllot {
    private List<SbchEquipmentAllotDetails> detailsList=new ArrayList<>();
    /**
     * 版本号 v1.0
     */
    private String versionCodeStr;
    /**
     * 当前任务名
     */
    private String processTaskName;
    /**
     * 当前处理人
     */
    private String processTaskMan;
}
