package com.hhwy.pm.qqch.wzch.specialproject.dto;

import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProject;
import com.hhwy.pm.qqch.wzch.specialproject.domain.WzchSpecialProjectDetail;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class WzchSpecialProjectDTO extends WzchSpecialProject {

    /**
     * 物资详情
     */
    private List<WzchSpecialProjectDetail> detailList;

    /**
     * 版本号 v1.0
     */
    private String versionCodeStr;


    private String actCode;
}
