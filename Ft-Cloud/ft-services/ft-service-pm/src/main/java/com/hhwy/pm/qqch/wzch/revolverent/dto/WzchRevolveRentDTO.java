package com.hhwy.pm.qqch.wzch.revolverent.dto;

import com.hhwy.pm.qqch.wzch.revolverent.domain.WzchRevolveRent;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author mls
 */
@Data
@ToString
public class WzchRevolveRentDTO extends WzchRevolveRent {
    /**
     * 物资详情
     */
    private List<WzchRevolveRentDetailDTO> detailList;

    /**
     * 版本号 v1.0
     */
    private String versionCodeStr;



    private String actCode;


}
