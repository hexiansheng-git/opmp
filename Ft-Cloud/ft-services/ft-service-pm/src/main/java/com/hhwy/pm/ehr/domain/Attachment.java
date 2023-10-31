package com.hhwy.pm.ehr.domain;

import lombok.Data;

@Data
public class Attachment {

    /*附件名称*/
    private String name;
    /*附件ID 用于下载附件使用*/
    private String id;
}
