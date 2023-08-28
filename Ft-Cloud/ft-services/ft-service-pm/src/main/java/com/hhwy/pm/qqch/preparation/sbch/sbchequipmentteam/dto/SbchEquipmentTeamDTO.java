package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.dto;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeam;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetails;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SbchEquipmentTeamDTO extends SbchEquipmentTeam {
    private List<SbchEquipmentTeamDetails> detailsList=new ArrayList<>();
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
