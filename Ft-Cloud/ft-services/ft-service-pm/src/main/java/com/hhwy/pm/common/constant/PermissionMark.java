package com.hhwy.pm.common.constant;

import lombok.Data;

/**
 * 权限标识
 */
@Data
public class PermissionMark {

    /*前期策划评审是否已结束：0：未结束，1：已结束*/
    private String reviewEnd = "0";

    /*是否有编辑权限：0：不可编辑，1：可编辑*/
    private String editable = "1";

    /*是否已确认：0：未确认，1：已确认*/
    private String confirmed = "0";

    private String msg;
}
