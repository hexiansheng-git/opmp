package com.hhwy.pm.qqch.wzch.demand.domain;

import lombok.Data;

import java.util.List;

/**
 * @author HCT
 */
@Data
public class WzchTotalDemandPO {

    private List<Long> projectIds;
    private String valid;
    private List<WzchTotalDemand> wzchTotalDemands;


}
