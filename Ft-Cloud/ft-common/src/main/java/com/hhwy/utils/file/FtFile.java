package com.hhwy.utils.file;

import lombok.Data;

import java.util.Date;

@Data
public class FtFile {
    private String id;
    private String fileGroupId;
    private String groupId;
    private String fileId;
    private String fileName;
    private String fileSize;
    private String contextType;
    private String filePath;
    private String tenantUri;
    private String md5;
    private String extension;
    private Date createTime;
    private Date updateTime;
    private String delFlag;
    private String storageMode;
    private String var1;
    private String var2;
    private String var3;
}
