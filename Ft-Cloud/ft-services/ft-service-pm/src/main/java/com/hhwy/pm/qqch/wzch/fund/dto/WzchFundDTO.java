package com.hhwy.pm.qqch.wzch.fund.dto;

import com.hhwy.pm.qqch.wzch.fund.domain.WzchFund;
import com.hhwy.pm.qqch.wzch.fund.domain.WzchFundDetail;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author mls
 */
@Data
@ToString
public class WzchFundDTO extends WzchFund {


    private String actCode;
    private String versionCodeStr;

    private List<WzchFundDetail> detailList;

}
