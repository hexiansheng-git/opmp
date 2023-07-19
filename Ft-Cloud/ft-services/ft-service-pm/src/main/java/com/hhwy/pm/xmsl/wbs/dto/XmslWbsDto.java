package com.hhwy.pm.xmsl.wbs.dto;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class XmslWbsDto {
    @Valid
    private List<XmslWbs> list;
    private String delIds;
    //wbs_main_id
    private Long mainId;
    //提交标志
    private Integer submitFlag;

}
