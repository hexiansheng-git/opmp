package com.hhwy.pm.qqch.preparation.sbch.equAllot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllotDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.SbchEquipmentAllotTransnationalDetails;
import com.hhwy.utils.common.MyPrepareBaseEntity;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**7.2.2shebe
 * @author zqq
 * @create 2023-08-25 15:53
 */
@Data
public class EquAllotVo extends MyPrepareBaseEntity {
    //同国别调拨
    private List<SbchEquipmentAllotDetails> sameCountryList;
    //跨国别调拨
    private List<SbchEquipmentAllotTransnationalDetails> internationList;

}
