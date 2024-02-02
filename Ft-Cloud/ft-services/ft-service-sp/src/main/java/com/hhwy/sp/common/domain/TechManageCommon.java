package com.hhwy.sp.common.domain;

import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.excel.FtExcel;
import lombok.Data;

@Data
public class TechManageCommon extends CommonBaseEntity {
    @FtExcel(name = "成果奖项")
    private String allAward;
}
