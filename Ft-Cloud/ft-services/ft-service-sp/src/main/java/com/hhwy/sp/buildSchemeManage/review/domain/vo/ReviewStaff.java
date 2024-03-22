package com.hhwy.sp.buildSchemeManage.review.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewStaff {
    /**
     * 字段描述：评审人员id
     */
    private String reviewStaffId;
    /**
     * 字段描述：评审人员姓名
     */
    private String reviewStaffName;
}
