package com.hhwy.pm.word.export.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FileDto {
    @JsonProperty
    private String id;
    @JsonProperty
    private String fileGroupId;
    @JsonProperty
    private String groupId;
    @JsonProperty
    private String fileId;
    @JsonProperty
    private String fileName;
    @JsonProperty
    private Long fileSize;
    @JsonProperty
    private String contextType;
    @JsonProperty
    private String filePath;
}
