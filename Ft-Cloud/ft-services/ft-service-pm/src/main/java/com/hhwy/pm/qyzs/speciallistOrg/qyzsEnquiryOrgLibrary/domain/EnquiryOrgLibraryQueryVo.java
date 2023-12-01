package com.hhwy.pm.qyzs.speciallistOrg.qyzsEnquiryOrgLibrary.domain;

import lombok.Data;

/**
 * @author fsd
 * @date 2023-11-30 17:32:56
 * @remark qyzs_enquiry_org_library
 */
@Data
public class EnquiryOrgLibraryQueryVo {
    /**
     * 字段描述：机构名称
     */
    private String orgName;
    /**
     * 字段描述：机构类型
     */
    private String orgType;
    /**
     * 字段描述：主营业务
     */
    private String mainBusiness;
}
