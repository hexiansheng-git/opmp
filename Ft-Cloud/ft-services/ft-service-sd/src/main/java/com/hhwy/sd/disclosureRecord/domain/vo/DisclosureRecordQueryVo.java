package com.hhwy.sd.disclosureRecord.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark kcsj_disclosure_record
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisclosureRecordQueryVo {
    /**
     * 字段描述：交底名称
     */
    private String disclosureName;
    /**
     * 字段描述：被交底单位
     */
    private String beDisclosureUnit;
    /**
     * 字段描述：交底内容
     */
    private String disclosureContent;
    /**
     * 字段描述：实际交底日期
     */
    private String actualDisclosureDate;
    /**
     * 字段描述：选择的数据id集合
     */
    private List<Long> ids;
}
