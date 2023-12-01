package com.hhwy.pm.qyzs.speciallistOrg.qyzsSpeciallistLibrary.domain;

import lombok.Data;

/**
 * @author fsd
 * @date 2023-11-30 17:34:54
 * @remark qyzs_speciallist_library
 */
@Data
public class SpeciallistLibraryQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：姓名
     */
    private String speciallistName;
    /**
     * 字段描述：所属单位
     */
    private String department;
    /**
     * 字段描述：业务领域
     */
    private String businessAreas;
}
