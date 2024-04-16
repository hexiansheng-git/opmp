package com.hhwy.sp.designChangeList.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import lombok.Data;

import java.util.Date;

@Data
public class SgjsDesignChangeManageVo extends SgjsDesignChangeManage {
    
     @JsonFormat(pattern = "yyyy-MM-dd")
     public Date startDate;
     @JsonFormat(pattern = "yyyy-MM-dd")
     public Date endDate;
}
