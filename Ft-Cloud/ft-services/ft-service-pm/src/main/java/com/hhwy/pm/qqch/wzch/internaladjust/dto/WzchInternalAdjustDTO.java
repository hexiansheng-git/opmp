package com.hhwy.pm.qqch.wzch.internaladjust.dto;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjust;
import com.hhwy.pm.qqch.wzch.internaladjust.domain.WzchInternalAdjustDetail;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 内部调剂材料策划对象 wzch_internal_adjust
 *
 * @author mls
 * @date 2022-11-17
 */

@Data
@ToString
public class WzchInternalAdjustDTO extends WzchInternalAdjust {
    private static final long serialVersionUID = 1L;

    public WzchInternalAdjustDTO() {
    }
    public WzchInternalAdjustDTO(BigDecimal version) {
        super.setVersion(version);
    }

    /**
     * 物资详情
     */
    private List<WzchInternalAdjustDetail> detailList;
    /**
     * 版本号 v1.0
     */
    private String versionCodeStr;



    private String actCode;


}
