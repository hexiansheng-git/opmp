package com.hhwy.pm.qqch.wzch.demand.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 来源策划物资下总需详情对象
 * @author HCT
 */
@Data
public class WzchTotalDemandDetailVO extends PreparationEntity {

    private Long id;

    private String viewType; //视角类型 Y-年 M-月 Q-季
    
    private List<WzchTotalDemandDetail> wzchTotalDemandDetailList;

}
